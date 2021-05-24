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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PicturePrescriptionAndApplyOCRActivity extends AppCompatActivity {
    private Button btnConfirm;
    private Button btnReSize;
    private Button btnRetake;

    private ImageView img;

    private String imageFilePath;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_prescription_and_apply_ocr_activty);

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
                ApplyOCR();
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

    // startActivityForResult를 실행한 후에 다시 현재 Activity로 돌아와서 결과값을 처리해주는 메소드
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

    // 촬영한 사진을 외부 저장소에 jpg형식의 이미지 파일로 저장하는 메소드
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

    private void ApplyOCR() {
        String apiURL = "https://0e5de5a9aebe4da1bcd5ef84a78605f0.apigw.ntruss.com/custom/v1/6604/ebe336381e3a156e85375e32f99ee0a86a480f2747219998eff226d9234ee616/infer";
        String secretKey = "YnBEZ2dMTVljTkxrQ0FmS0Z2bll2SXdoenF4Z01KTXM=";

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apiURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    conn.setRequestProperty("X-OCR-SECRET", secretKey);

                    JSONObject json = new JSONObject();
                    json.put("version", "V2");
                    json.put("requestId", UUID.randomUUID().toString());
                    json.put("timestamp", System.currentTimeMillis());

                    JSONObject image = new JSONObject();
                    image.put("format", "jpg");
                    image.put("name", "ocr");
                    // Object Storage의 URL을 불러와서 OCR 적용
                    image.put("url", "https://kr.object.ncloudstorage.com/bitbucket/sample.jpg"); // image should be public, otherwise, should use data

                    // 저장소 내부의 파일을 불러와서 OCR 적용
                    // FileInputStream("경로") 경로에서 해당 파일을 찾아 열고, 파일을 읽을 수 있는 스트림 객체 inputStream 생성
//                    FileInputStream inputStream = new FileInputStream("/Users/SJH/eclipse-workspace/GraduationProject/src/com/ocr/sample.jpg");
//                    byte[] buffer = new byte[inputStream.available()];
//                    image.put("data", buffer);
//                    inputStream.read(buffer);
//                    inputStream.close();

                    // 다수의 이미지를 처리
                    JSONArray images = new JSONArray();
                    images.put(image);
                    json.put("images", images);

                    // JSON Request 전송
                    String postParams = json.toString();
                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(postParams);
                    wr.flush();
                    wr.close();

                    // JSON Response 처리
                    int responseCode = conn.getResponseCode();
                    BufferedReader br;
                    if (responseCode == 200) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }

                    // JSON Response 데이터 가공해서 내용 출력
                    String jsonString = br.readLine();
                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONObject jsonImages = jsonObj.getJSONArray("images").getJSONObject(0);
                    JSONArray jsonFields = jsonImages.getJSONArray("fields");

                    String[] medicineCode = new String[13];
                    Intent intent = new Intent(PicturePrescriptionAndApplyOCRActivity.this, CheckOCRResultActivity.class);
                    for (int i = 0; i < jsonFields.length(); i++) {
                        JSONObject obj = jsonFields.getJSONObject(i);
                        String name = obj.getString("name");
                        String inferText = obj.getString("inferText");

                        if(name.equals("doctor_name")) { // doctor_name에서 인식된 필기체 제거
                            String[] temp = inferText.split(" ", 2);
                            inferText = temp[0];
                        }
                        else if(inferText.length() > 9 && inferText.charAt(9) == ' ') { // 약학코드만 추출해서 medicineCode 배열에 삽입
                            String[] temp = inferText.split(" ", 2);
                            medicineCode[i] = temp[0];
                        }
                        else if(inferText.equals("")) inferText=null;
                        intent.putExtra(name, inferText);
                    }
                    intent.putExtra("medicineCode", medicineCode);
                    startActivity(intent);
                    br.close();
                } catch (Exception e) {
                    String error = e.toString();
                    Intent intent = new Intent(PicturePrescriptionAndApplyOCRActivity.this, CheckOCRResultActivity.class);
                    intent.putExtra("result", error);
                    startActivity(intent);
                }
            }
        }.start();
    }
}