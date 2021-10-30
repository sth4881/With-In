package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView tvPrescription;
    private ListView lvPrescriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_prescription_list);

        tvPrescription = (TextView)findViewById(R.id.tvPrescription);
        lvPrescriptionList = (ListView)findViewById(R.id.lvPrescriptionList);

        ArrayList<String> prescriptionList = getIntent().getStringArrayListExtra("prescriptionList");

        // 처방전 목록을 ListView에 표시
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prescriptionList);
        lvPrescriptionList.setAdapter(adapter);

        // 처방전 목록에서 각각의 아이템에 대한 클릭 이벤트
        lvPrescriptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PrescriptionManagementAdapter prescriptionManagementAdapter = new PrescriptionManagementAdapter(getApplicationContext());
                prescriptionManagementAdapter.create();
                prescriptionManagementAdapter.open();

                // 클릭한 아이템의 처방전 제목을 통해서 처방전 내용을 불러옴
                String prescription_title = (String)parent.getItemAtPosition(position);
                ArrayList<String> prescriptionData = prescriptionManagementAdapter.getPrescriptionData(prescription_title);

                StringBuilder sb = new StringBuilder();
                sb.append(prescriptionData.get(0)).append("\n"); // 처방전 제목
                sb.append("방문 날짜 : ").append(prescriptionData.get(1)).append("\n"); // 방문날짜
                sb.append("환자 성명(나이) : ").append(prescriptionData.get(2)); // 환자성명
                sb.append("(만 ").append(prescriptionData.get(3)).append("세)\n"); // 환자나이
                sb.append("의료기관 명칭 : ").append(prescriptionData.get(4)).append("\n"); // 의료기관명칭
                sb.append("의료기관 전화번호 : ").append(prescriptionData.get(5)).append("\n"); // 의료기관전화번호
                sb.append("처방 의료인의 성명 : ").append(prescriptionData.get(6)).append("\n"); // 처방의료인의성명
                tvPrescription.setText(sb.toString());
                prescriptionManagementAdapter.close();
            }
        });
    }
}