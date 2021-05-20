package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnPicturePrescription;
    private Button btnOCR;

    private Button btnCheckPrescription;
    private Button btnCheckDoseHistory;
    private Button btnAlarm;
    private Button btnMedicine;
    private Button btnMyPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 미구현 버튼
        btnCheckPrescription = findViewById(R.id.btnCheckPrescription);
        btnCheckDoseHistory = findViewById(R.id.btnCheckDoseHistory);
        btnAlarm = findViewById(R.id.btnAlarm);
        btnMedicine = findViewById(R.id.btnMedicine);
        btnMyPage = findViewById(R.id.btnMyPage);

        btnPicturePrescription = findViewById(R.id.btnPicturePrescription);
        btnPicturePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnPicturePrescription:
                        Intent intent = new Intent(MainActivity.this, PicturePrescriptionActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        btnOCR = findViewById(R.id.btnOCR);
        btnOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnOCR:
                        Intent intent = new Intent(MainActivity.this, ApplyOCRActivity.class);
                        startActivity(intent);
                }
            }
        });
    }
}