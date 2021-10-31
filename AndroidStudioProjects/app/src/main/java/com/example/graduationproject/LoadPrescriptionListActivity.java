package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LoadPrescriptionListActivity extends AppCompatActivity {

    private ListView lvPrescriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_prescription_list);

        lvPrescriptionList = (ListView)findViewById(R.id.lvPrescriptionList);

        ArrayList<String> prescription_list = getIntent().getStringArrayListExtra("prescription_list");

        // 처방전 목록을 ListView에 표시
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prescription_list);
        lvPrescriptionList.setAdapter(adapter);

        // 처방전 목록에서 각각의 아이템에 대한 클릭 이벤트
        lvPrescriptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PrescriptionManagementAdapter prescriptionManagementAdapter = new PrescriptionManagementAdapter(getApplicationContext());
                prescriptionManagementAdapter.create();
                prescriptionManagementAdapter.open();

                // 클릭한 아이템의 처방전 제목을 통해서 처방전 내용을 불러오고 다음 액티비티로 전송
                String prescription_title = (String)parent.getItemAtPosition(position);
                ArrayList<String> prescription_data = prescriptionManagementAdapter.getPrescriptionData(prescription_title);
                Intent intent = new Intent(LoadPrescriptionListActivity.this, LoadPrescriptionActivity.class);
                intent.putExtra("prescription_data", prescription_data);

                prescriptionManagementAdapter.close();
                startActivity(intent);
            }
        });
    }
}