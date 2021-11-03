package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchMedicineActivity extends AppCompatActivity {
    private Button btnSearchMedicine;

    private EditText inputMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicine);

        // 제품코드 또는 의약품명을 입력
        inputMedicine = (EditText)findViewById(R.id.inputMedicine);

        // 검색 버튼 클릭 이벤트
        btnSearchMedicine = (Button) findViewById(R.id.btnSearchMedicine);
        btnSearchMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}