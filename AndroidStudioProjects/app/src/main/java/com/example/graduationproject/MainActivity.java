package com.example.graduationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnPicturePrescriptionAndApplyOCR;

    private Button btnCheckPrescription;
    private Button btnMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 미구현 버튼
        btnCheckPrescription = findViewById(R.id.btnCheckPrescription);
        btnMedicine = findViewById(R.id.btnMedicine);

        btnPicturePrescriptionAndApplyOCR = findViewById(R.id.btnPicturePrescriptionAndOCR);
        btnPicturePrescriptionAndApplyOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnPicturePrescriptionAndOCR:
                        Intent intent = new Intent(MainActivity.this, PicturePrescriptionAndApplyOCRActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    // 취소 버튼 이벤트를 통해서 팝업창을 띄우고 앱을 종료할지 선택
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("애플리케이션 종료");
        alertBuilder.setMessage("앱을 종료하시겠습니까?");
        alertBuilder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "애플리케이션이 종료되었습니다.", Toast.LENGTH_SHORT).show();
                // 2021.10.26 finishAffinity()가 백 스택에 존재하는 모든 액티비티를 종료시키지만 카메라 어플은 종료되지 않아서 이슈가 발생
                finishAffinity(); // 백 스택에 존재하는 모든 액티비티 종료
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}