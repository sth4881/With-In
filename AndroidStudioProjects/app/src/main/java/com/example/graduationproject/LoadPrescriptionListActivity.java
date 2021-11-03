package com.example.graduationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

        // 처방전 목록에서 아이템을 길게 누를시 해당 아이템의 삭제 이벤트 발생
        lvPrescriptionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String prescription_title = (String)parent.getItemAtPosition(position);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoadPrescriptionListActivity.this);
                alertBuilder.setTitle("처방전 삭제");
                alertBuilder.setMessage("처방전 '"+prescription_title+"'을 삭제하시겠습니까?");
                alertBuilder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertBuilder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrescriptionManagementAdapter prescriptionManagementAdapter = new PrescriptionManagementAdapter(getApplicationContext());
                        prescriptionManagementAdapter.create();
                        prescriptionManagementAdapter.open();

                        // 처방전 목록에서 prescription_title과 이름이 같은 처방전을 삭제
                        prescriptionManagementAdapter.deletePrescriptionData(prescription_title);
                        // 처방전의 삭제를 통해서 데이터베이스가 갱신되었으므로 처방전 목록 데이터를 다시 받아옴
                        ArrayList<String> prescription_list = prescriptionManagementAdapter.getPrescriptionListData();
                        adapter.clear(); // 기존 어댑터에 담긴 내용 삭제
                        for(int i=0; i<prescription_list.size(); i++)
                            adapter.add(prescription_list.get(i)); // 어댑터에 아이템을 하나씩 추가
                        adapter.notifyDataSetChanged(); // 어댑터의 변경사항을 리스트뷰에 반영

                        prescriptionManagementAdapter.close();
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();

                // OnItemClickListener 이벤트를 호출하지 않고 OnItemLongClickListener만 호출
                return true;
            }
        });
    }
}