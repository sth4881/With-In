package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CheckOCRResultActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ocr_result_activity);

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

        String[] medicineCode = getIntent().getStringArrayExtra("medicineCode");

        String result =
                user_name+"\n"+
                hospital_name+"\n"+
                hospital_call+"\n"+
                doctor_name+"\n"+
                pharmacy_name+"\n"+
                pharmacist_name+"\n"+
                compound_date+"\n"+
                medicine_name1+"\n"+
                medicine_name2+"\n"+
                medicine_name3+"\n"+
                medicine_name4+"\n"+
                medicine_name5+"\n"+
                medicine_name6+"\n"+
                medicine_name7+"\n"+
                medicine_name8+"\n"+
                medicine_name9+"\n"+
                medicine_name10+"\n"+
                medicine_name11+"\n"+
                medicine_name12+"\n"+
                medicine_name13+"\n";

        textView = (TextView)findViewById(R.id.textview);
        textView.setText(result);
    }
}