package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CheckPhotoActivity extends AppCompatActivity {
    private Button btnConfirm;
    private Button btnReSize;
    private Button btnRetake;

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_photo_activty);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnReSize = findViewById(R.id.btnReSize);
        btnRetake = findViewById(R.id.btnRetake);

        img = (ImageView)findViewById(R.id.img);

        byte[] byteArray = getIntent().getByteArrayExtra("image"); // 별명이 "image"인 intent로부터 ByteArray 데이터를 받아옴
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); // byteArray 형식의 데이터를 bitmap으로 변환
        img.setImageBitmap(bitmap); // 비트맵 이미지를 뷰에 띄워주는 코드

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckPhotoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
