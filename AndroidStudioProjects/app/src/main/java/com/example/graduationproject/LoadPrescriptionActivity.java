package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class LoadPrescriptionActivity extends AppCompatActivity {

    private TextView tvPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_prescription);

        tvPrescription = (TextView)findViewById(R.id.tvPrescription);

        ArrayList<String> prescription_data = getIntent().getStringArrayListExtra("prescription_data");

        StringBuilder sb = new StringBuilder();
        sb.append(prescription_data.get(0)).append("\n"); // 처방전 제목
        sb.append("방문 날짜 : ").append(prescription_data.get(1)).append("\n"); // 방문날짜
        sb.append("환자 성명(나이) : ").append(prescription_data.get(2)); // 환자성명
        sb.append("(만 ").append(prescription_data.get(3)).append("세)\n"); // 환자나이
        sb.append("의료기관 명칭 : ").append(prescription_data.get(4)).append("\n"); // 의료기관명칭
        sb.append("의료기관 전화번호 : ").append(prescription_data.get(5)).append("\n"); // 의료기관전화번호
        sb.append("처방 의료인의 성명 : ").append(prescription_data.get(6)).append("\n"); //
        tvPrescription.setText(sb.toString());
    }
}