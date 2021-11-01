package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class LoadPrescriptionActivity extends AppCompatActivity {
    private TextView tvPrescriptionTitle;
    private TextView tvPrescriptionContents;
    private TextView tvMedicineList;
    private TextView tvDURContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_prescription);

        tvPrescriptionTitle = (TextView)findViewById(R.id.tvPrescriptionTitle);
        tvPrescriptionContents = (TextView)findViewById(R.id.tvPrescriptionContents);
        tvMedicineList = (TextView)findViewById(R.id.tvMedicineList);
        tvDURContents = (TextView)findViewById(R.id.tvDURContents);

        ArrayList<String> prescription_data = getIntent().getStringArrayListExtra("prescription_data");

        // 텍스트뷰에 처방전 제목 표시
        StringBuilder sb = new StringBuilder();
        sb.append(prescription_data.get(0));
        tvPrescriptionTitle.setText(sb.toString());

        // 텍스트뷰에 처방전 내용 표시
        sb.setLength(0); // StringBuilder 초기화
        sb.append("방문 날짜 : ").append(prescription_data.get(1)).append("\n"); // 방문날짜
        sb.append("환자 성명(나이) : ").append(prescription_data.get(2)); // 환자성명
        sb.append("(만 ").append(prescription_data.get(3)).append("세)\n"); // 환자나이
        sb.append("의료기관 명칭 : ").append(prescription_data.get(4)).append("\n"); // 의료기관명칭
        sb.append("의료기관 전화번호 : ").append(prescription_data.get(5)).append("\n"); // 의료기관전화번호
        sb.append("처방 의료인의 성명 : ").append(prescription_data.get(6)).append("\n"); // 처방의료인의성명
        tvPrescriptionContents.setText(sb.toString());

        // 텍스트뷰에 처방 의약품 목록 표시
        sb.setLength(0); // StringBuilder 초기화
        for(int i=7; i<prescription_data.size(); i++)
            sb.append(prescription_data.get(i)).append("\n"); // 약1 ~ 약13
        tvMedicineList.setText(sb.toString());
        
        // 텍스트뷰에 DUR 정보(연령금기, 임부금기) 표시
        sb.setLength(0);
    }
}