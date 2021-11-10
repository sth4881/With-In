package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DisplayMedicineResultActivity extends AppCompatActivity {
    private ImageView ivMedicineImage;
    private TextView tvMedicineNoimage;

    private TextView tvMedicineName;
    private TextView tvMedicineEffect;
    private TextView tvMedicineManual;
    private TextView tvMedicineWarning;
    private TextView tvMedicineSideEffect;

    private ArrayList<String> medicineInfoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine_result);


        tvMedicineNoimage = (TextView)findViewById(R.id.tvMedicineNoImage);

        tvMedicineName = (TextView)findViewById(R.id.tvMedicineName);
        tvMedicineEffect = (TextView)findViewById(R.id.tvMedicineEffect);
        tvMedicineManual = (TextView)findViewById(R.id.tvMedicineManual);
        tvMedicineWarning = (TextView)findViewById(R.id.tvMedicineWarning);
        tvMedicineSideEffect = (TextView)findViewById(R.id.tvMedicineSideEffect);

        String medicine_input = getIntent().getStringExtra("medicine_input");
        loadMedicineData(medicine_input);
        if(medicineInfoData.size()>0) {
            String medicine_name = medicineInfoData.get(0);
            String medicine_effect = medicineInfoData.get(2);
            String medicine_manual = medicineInfoData.get(3);
            String medicine_warning = medicineInfoData.get(4);
            String medicine_sideEffect = medicineInfoData.get(5);

            // 약 백과사전 검색 결과 표시
            StringBuilder sb = new StringBuilder();
            sb.append(medicine_name);
            tvMedicineName.setText(sb.toString());

            String medicine_image = medicineInfoData.get(6);
            if(medicine_image != null) {
                ivMedicineImage = (ImageView)findViewById(R.id.ivMedicineImage);
                Glide.with(this).load(medicine_image).into(ivMedicineImage);
            } else {
                tvMedicineNoimage.setText("이미지가 존재하지 않습니다");
            }

            sb.setLength(0);
            sb.append(medicine_effect).append("\n");
            tvMedicineEffect.setText(sb.toString());

            sb.setLength(0);
            sb.append(medicine_manual).append("\n");
            tvMedicineManual.setText(sb.toString());

            sb.setLength(0);
            if(medicine_warning != null) sb.append(medicine_warning).append("\n");
            else sb.append("X\n");
            tvMedicineWarning.setText(sb.toString());

            sb.setLength(0);
            if(medicine_sideEffect != null) sb.append(medicine_sideEffect).append("\n");
            else sb.append("X\n");
            tvMedicineSideEffect.setText(sb.toString());
        } else {
            Intent intent = new Intent(DisplayMedicineResultActivity.this, DisplayNoneResultActivity.class);
            startActivity(intent);
        }
    }

    private void loadMedicineData(String medicineInput) {
        MedicineManagementAdapter medicineManagementAdapter = new MedicineManagementAdapter(getApplicationContext());
        medicineManagementAdapter.create();
        medicineManagementAdapter.open();

        medicineInfoData = medicineManagementAdapter.getMedicineData(medicineInput);

        medicineManagementAdapter.close();
    }
}