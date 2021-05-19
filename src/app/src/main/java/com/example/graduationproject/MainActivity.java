package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnPicturePrescription;
    private Button btnCheckPrescription;
    private Button btnCheckDoseHistory;
    private Button btnAlarm;
    private Button btnMedicine;
    private Button btnMyPage;

    private String imageFilePath;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 해당 Activity의 생성
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPicturePrescription = findViewById(R.id.btnPicturePrescription);
        btnCheckPrescription = findViewById(R.id.btnCheckPrescription);
        btnCheckDoseHistory = findViewById(R.id.btnCheckDoseHistory);
        btnAlarm = findViewById(R.id.btnAlarm);
        btnMedicine = findViewById(R.id.btnMedicine);
        btnMyPage = findViewById(R.id.btnMyPage);

        // 카메라 및 외부 저장소 쓰기 권한 명시
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 카메라 권한 또는 외부 저장소 쓰기 권한이 허용되지 않았을 경우
        if(cameraPermission != PackageManager.PERMISSION_GRANTED || writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // 권한 설정 요청 코드
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_CAPTURE);
        }

        btnPicturePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnPicturePrescription:
                        requestTakePictureIntent();
                        break;
                }
            }
        });
    }

    // 권한 요청에 따른 사용자의 응답에 따라서 동작을 정의하는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // grantResults[0]은 카메라 권한, grantResults[1]은 외부 저장소 쓰기 권한을 말함
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "카메라 및 외부 저장소 쓰기 권한이 승인되었습니다", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "카메라 및 외부 저장소 쓰기 권한이 거부되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    // startActivityForResult를 실행한 후에 다시 현재 Activity로 돌아와서 결과값을 처리해주는 메소드(현재 사용 X)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    if(resultCode == RESULT_OK) {
                        File file = new File(imageFilePath);
                        if(Build.VERSION.SDK_INT>=29) { // API 29 버전 이상의 경우
                            ImageDecoder.Source src = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                Bitmap bitmap = ImageDecoder.decodeBitmap(src);
                                float scale = (float)(1024/(float)bitmap.getWidth());
                                int imgWidth = (int)(bitmap.getWidth() * scale);
                                int imgHeight = (int)(bitmap.getHeight() * scale);

                                Bitmap resized = Bitmap.createScaledBitmap(bitmap, imgWidth, imgHeight, true);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] byteArray = stream.toByteArray();

                                Intent intent = new Intent(MainActivity.this, CheckPhotoActivity.class);
                                intent.putExtra("image", byteArray);
                                startActivity(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else { // API 29 버전 미만일 경우
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                float scale = (float)(1024/(float)bitmap.getWidth());
                                int imgWidth = (int)(bitmap.getWidth() * scale);
                                int imgHeight = (int)(bitmap.getHeight() * scale);

                                Bitmap resized = Bitmap.createScaledBitmap(bitmap, imgWidth, imgHeight, true);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                resized.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] byteArray = stream.toByteArray();

                                Intent intent = new Intent(MainActivity.this, CheckPhotoActivity.class);
                                intent.putExtra("image", byteArray);
                                startActivity(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 촬영한 사진을 jpg형식의 이미지 파일로 저장하는 메소드
    private File createImageFile() throws IOException {
        String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    // Intent를 통해서 카메라 촬영 요청을 보내는 메소드
    private void requestTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
            if(imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(this, getPackageName(), imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
}