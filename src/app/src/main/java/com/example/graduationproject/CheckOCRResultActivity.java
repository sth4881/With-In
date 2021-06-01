package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckOCRResultActivity extends AppCompatActivity {
    private TextView textInfo;
    private ListView listMedicine;
    private TextView ageTaboo;
    private TextView combiTaboo;
    private TextView pregnantTaboo;
    private SQLiteDatabase db;

    private void initLoadDB(ArrayList<String> arr) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ocr_result_activity);

        textInfo = (TextView)findViewById(R.id.textInfo);
        listMedicine = (ListView)findViewById(R.id.listMedicine);
        ageTaboo = (TextView)findViewById(R.id.ageTaboo);
        combiTaboo = (TextView)findViewById(R.id.combiTaboo);
        pregnantTaboo = (TextView)findViewById(R.id.pregnantTaboo);

        String user_name = getIntent().getStringExtra("user_name");
        String hospital_name = getIntent().getStringExtra("hospital_name");
        String hospital_call = getIntent().getStringExtra("hospital_call");
        String doctor_name = getIntent().getStringExtra("doctor_name");
        String pharmacy_name = getIntent().getStringExtra("pharmacy_name");
        String pharmacist_name = getIntent().getStringExtra("pharmacist_name");
        String compound_date = getIntent().getStringExtra("compound_date");
        String medicine_name1 = getIntent().getStringExtra("medicine_name1");
        String medicine_name2 = getIntent().getStringExtra("medicine_name2");
        String medicine_name3 = getIntent().getStringExtra("medicine_name3");
        String medicine_name4 = getIntent().getStringExtra("medicine_name4");
        String medicine_name5 = getIntent().getStringExtra("medicine_name5");
        String medicine_name6 = getIntent().getStringExtra("medicine_name6");
        String medicine_name7 = getIntent().getStringExtra("medicine_name7");
        String medicine_name8 = getIntent().getStringExtra("medicine_name8");
        String medicine_name9 = getIntent().getStringExtra("medicine_name9");
        String medicine_name10 = getIntent().getStringExtra("medicine_name10");
        String medicine_name11 = getIntent().getStringExtra("medicine_name11");
        String medicine_name12 = getIntent().getStringExtra("medicine_name12");
        String medicine_name13 = getIntent().getStringExtra("medicine_name13");

        ArrayList<String> medicineName = new ArrayList<String>();
        if(medicine_name1 != null) medicineName.add(medicine_name1);
        if(medicine_name2 != null) medicineName.add(medicine_name2);
        if(medicine_name3 != null) medicineName.add(medicine_name3);
        if(medicine_name4 != null) medicineName.add(medicine_name4);
        if(medicine_name5 != null) medicineName.add(medicine_name5);
        if(medicine_name6 != null) medicineName.add(medicine_name6);
        if(medicine_name7 != null) medicineName.add(medicine_name7);
        if(medicine_name8 != null) medicineName.add(medicine_name8);
        if(medicine_name9 != null) medicineName.add(medicine_name9);
        if(medicine_name10 != null) medicineName.add(medicine_name10);
        if(medicine_name11 != null) medicineName.add(medicine_name11);
        if(medicine_name12 != null) medicineName.add(medicine_name12);
        if(medicine_name13 != null) medicineName.add(medicine_name13);

        ArrayList<String> medicineCode = new ArrayList<String>();
        for(int i=0; i<medicineName.size(); i++) {
            String[] temp = medicineName.get(i).split(" ", 2);
            medicineCode.add(temp[0]);
        }

        // 처방전 내 인식된 기본정보를 TextView에 표시
        StringBuilder sb = new StringBuilder();
        sb.append("환자 성명 : ").append(user_name).append("\n");
        sb.append("의료기관 명칭 : ").append(hospital_name).append("\n");
        sb.append("의료기관 전화번호 : ").append(hospital_call).append("\n");
        sb.append("처방 의료인의 성명 : ").append(doctor_name).append("\n");
        if(pharmacy_name != null)
            sb.append("조제기관 명칭 : ").append(pharmacy_name).append("\n");
        else sb.append("조제기관 명칭 : X").append("\n");
        if(pharmacist_name != null)
            sb.append("조제약사 성명 : ").append(pharmacist_name).append("\n");
        else sb.append("조제약사 성명 : X").append("\n");
        if(compound_date != null)
            sb.append("조제년월일 : ").append(compound_date).append("\n");
        else sb.append("조제년월일 : X");
        textInfo.setText(sb.toString());

        // 처방전 내 인식된 약을 ListView에 표시
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicineName);
        listMedicine.setAdapter(adapter);
    }
}