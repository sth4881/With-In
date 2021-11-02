package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadPrescriptionActivity extends AppCompatActivity {
    private TextView tvPrescriptionTitle;
    private TextView tvPrescriptionContents;
    private TextView tvMedicineList;
    private TextView tvAgeProhibitionContents;
    //    private TextView tvCombiProhibitionContents;
    private TextView tvPregnantProhibitionContents;

    private ArrayList<ArrayList<String>> ageProhibitionData, combiProhibitionData, pregnantProhibitionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_prescription);

        tvPrescriptionTitle = (TextView)findViewById(R.id.tvPrescriptionTitle);
        tvPrescriptionContents = (TextView)findViewById(R.id.tvPrescriptionContents);
        tvMedicineList = (TextView)findViewById(R.id.tvMedicineList);
        tvAgeProhibitionContents = (TextView)findViewById(R.id.tvAgeProhibitionContents);
//        tvCombiProhibitionContents = (TextView)findViewById(R.id.tvCombiProhibitionContents);
        tvPregnantProhibitionContents = (TextView)findViewById(R.id.tvPregnantProhibitionContents);

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
        ArrayList<String> medicineCode = new ArrayList<String>();
        for(int i=7; i<prescription_data.size(); i++) {
            sb.append(prescription_data.get(i)).append("\n"); // 약1 ~ 약13
            medicineCode.add(prescription_data.get(i).split(" ")[0]); // 제품코드만 받아서 저장
        }
        tvMedicineList.setText(sb.toString());

        // 텍스트뷰에 DUR 정보(연령금기, 임부금기) 불러오기
        loadProhibitionData(medicineCode);

        // 연령금기 정보 표시
        sb.setLength(0); // StringBuilder 초기화
        for(int i=0; i<ageProhibitionData.size(); i++) {
            String medicineName = ageProhibitionData.get(i).get(1).split("_")[0]; // 약 이름
            String age = ageProhibitionData.get(i).get(2); // 연령
            String unit1 = ageProhibitionData.get(i).get(3); // 세, 개월
            String unit2 = ageProhibitionData.get(i).get(4); // 이상, 이하, 미만
            sb.append(medicineName).append(" (").append(age).append(unit1).append(" ").append(unit2).append(" 복용금지)\n");
        }
        tvAgeProhibitionContents.setText(sb.toString());

        // 임부금기 정보 표시
        sb.setLength(0); // StringBuilder 초기화
        for (int i=0; i<pregnantProhibitionData.size(); i++) {
            String medicineName= pregnantProhibitionData.get(i).get(1).split("_")[0]; // 약 이름
            sb.append(medicineName).append("\n");
        }
        tvPregnantProhibitionContents.setText(sb.toString());
    }

    private void loadProhibitionData(ArrayList<String> medicineCode) {
        AgeProhibitionAdapter ageProhibitionAdapter = new AgeProhibitionAdapter(getApplicationContext());
        ageProhibitionAdapter.create();
        ageProhibitionAdapter.open();

//        CombiProhibitionAdapter combiProhibitionAdapter = new CombiProhibitionAdapter(getApplicationContext());
//        combiProhibitionAdapter.create();
//        combiProhibitionAdapter.open();

        PregnantProhibitionAdapter pregnantProhibitionAdapter = new PregnantProhibitionAdapter(getApplicationContext());
        pregnantProhibitionAdapter.create();
        pregnantProhibitionAdapter.open();

        ageProhibitionData = ageProhibitionAdapter.getAgeProhibitionData(medicineCode);
//        combiProhibitionData = combiProhibitionAdapter.getCombiProhibitionData(medicineCode);
        pregnantProhibitionData = pregnantProhibitionAdapter.getPregnantProhibitionData(medicineCode);

        ageProhibitionAdapter.close();
//        combiProhibitionAdapter.close();
        pregnantProhibitionAdapter.close();
    }
}