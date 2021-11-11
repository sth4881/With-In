package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchMedicineActivity extends AppCompatActivity {
    private Button btnSearchMedicine;

    private EditText etMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);

        // 제품코드 또는 제품명을 입력
        etMedicine = (EditText)findViewById(R.id.etMedicine);

        // 검색 버튼 클릭 이벤트
        btnSearchMedicine = (Button)findViewById(R.id.btnSearchMedicine);
        btnSearchMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineManagementAdapter medicineManagementAdapter = new MedicineManagementAdapter(getApplicationContext());
                medicineManagementAdapter.create();
                medicineManagementAdapter.open();

                // 입력받은 제품명 또는 제품코드가 '약백과사전' 테이블에 존재하지 않으면 검색하지 않음
                if(!medicineManagementAdapter.checkMedicineData(etMedicine.getText().toString())) {
                    Toast.makeText(SearchMedicineActivity.this, "제품명 또는 제품코드를 확인해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SearchMedicineActivity.this, DisplayMedicineResultActivity.class);
                    intent.putExtra("medicine_input", etMedicine.getText().toString());

                    medicineManagementAdapter.close();
                    startActivity(intent);
                }
            }
        });
    }
}