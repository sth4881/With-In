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

        ivMedicineImage = (ImageView)findViewById(R.id.ivMedicineImage);

        tvMedicineName = (TextView)findViewById(R.id.tvMedicineName);
        tvMedicineEffect = (TextView)findViewById(R.id.tvMedicineEffect);
        tvMedicineManual = (TextView)findViewById(R.id.tvMedicineManual);
        tvMedicineWarning = (TextView)findViewById(R.id.tvMedicineWarning);
        tvMedicineSideEffect = (TextView)findViewById(R.id.tvMedicineSideEffect);

        String medicine_input = getIntent().getStringExtra("medicine_input");
        loadMedicineData(medicine_input);
        if(medicineInfoData.size()>0) {
            String medicine_name = medicineInfoData.get(1) + " " + medicineInfoData.get(0);
            String medicine_effect = medicineInfoData.get(2);
            String medicine_manual = medicineInfoData.get(3);
            String medicine_warning = medicineInfoData.get(4);
            String medicine_sideEffect = medicineInfoData.get(5);

            String medicine_image = medicineInfoData.get(6);
            Glide.with(this).load(medicine_image).into(ivMedicineImage);

            tvMedicineName.setText(medicine_name);
            tvMedicineEffect.setText(medicine_effect);
            tvMedicineManual.setText(medicine_manual);
            tvMedicineWarning.setText(medicine_warning);
            tvMedicineSideEffect.setText(medicine_sideEffect);
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