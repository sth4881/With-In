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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PicturePrescriptionActivity extends AppCompatActivity {
    private Button btnConfirm;
    private Button btnReSize;
    private Button btnRetake;

    private ImageView img;

    private String imageFilePath;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_prescription_activty);

        img = (ImageView)findViewById(R.id.img);

        // 카메라 및 외부 저장소 쓰기 권한 명시
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 카메라 권한 또는 외부 저장소 쓰기 권한이 허용되지 않았을 경우
        if(cameraPermission != PackageManager.PERMISSION_GRANTED || writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // 권한 설정 요청 코드
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_CAPTURE);
        }

        requestTakePictureIntent();

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnReSize = findViewById(R.id.btnReSize);
        btnReSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRetake = findViewById(R.id.btnRetake);
        btnRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTakePictureIntent();
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
                                img.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else { // API 29 버전 미만일 경우
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                img.setImageBitmap(bitmap);
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
