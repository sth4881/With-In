package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ApplyOCRActivity extends AppCompatActivity {
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_ocractivity);

        result = findViewById(R.id.result);
        OCR ocr = new OCR(); // 여기부터 다시 시작(5/21)
    }
}