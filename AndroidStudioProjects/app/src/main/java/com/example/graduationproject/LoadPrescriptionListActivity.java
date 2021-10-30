package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LoadPrescriptionListActivity extends AppCompatActivity {
    private ListView lvPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_prescription_list);

        lvPrescription = (ListView)findViewById(R.id.lvPrescription);

        ArrayList<String> prescriptionList = getIntent().getStringArrayListExtra("prescriptionList");

        // 처방전 내 인식된 정보를 ListView에 표시
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prescriptionList);
        lvPrescription.setAdapter(adapter);
    }
}