package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplaySearchResultActivity extends AppCompatActivity {
    private ImageView ivMedicineImage;

    private TextView tvMedicineInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_result);

        ArrayList<String> medicine_data = getIntent().getStringArrayListExtra("medicine_data");

    }
}