package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.loader.content.AsyncTaskLoader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;

public class MainActivity extends AppCompatActivity {
    private Button btnPicturePrescriptionAndApplyOCR;
    private Button btnViewPrescription;
    private Button btnSearchMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 처방전 촬영 버튼 클릭 이벤트
        btnPicturePrescriptionAndApplyOCR = findViewById(R.id.btnPicturePrescriptionAndOCR);
        btnPicturePrescriptionAndApplyOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PicturePrescriptionAndApplyOCRActivity.class);
                startActivity(intent);
            }
        });

        // 처방전 조회 버튼 클릭 이벤트
        btnViewPrescription = findViewById(R.id.btnCheckPrescription);
        btnViewPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrescriptionManagementAdapter prescriptionManagementAdapter = new PrescriptionManagementAdapter(getApplicationContext());
                prescriptionManagementAdapter.create();
                prescriptionManagementAdapter.open();

                ArrayList<String> prescription_list = prescriptionManagementAdapter.getPrescriptionListData();
                Intent intent = new Intent(MainActivity.this, LoadPrescriptionListActivity.class);
                intent.putExtra("prescription_list", prescription_list);

                prescriptionManagementAdapter.close();
                startActivity(intent);
            }
        });

        // 약 백과사전 버튼 클릭 이벤트
        btnSearchMedicine = findViewById(R.id.btnSearchMedicine);
        btnSearchMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchMedicineActivity.class);
                startActivity(intent);
            }
        });
    }

    // 취소 버튼 이벤트를 통해서 팝업창을 띄우고 앱을 종료할지 선택
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("애플리케이션을 종료하시겠습니까?");
        alertBuilder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "애플리케이션이 종료되었습니다.", Toast.LENGTH_SHORT).show();
                // 2021.10.26 finishAffinity()가 백 스택에 존재하는 모든 액티비티를 종료시키지만 카메라 어플은 종료되지 않아서 이슈가 발생
                finishAffinity(); // 백 스택에 존재하는 모든 액티비티 종료
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}