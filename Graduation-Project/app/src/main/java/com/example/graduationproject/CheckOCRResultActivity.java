package com.example.graduationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckOCRResultActivity extends AppCompatActivity {
    private Button btnConfirm;
    private Button btnRetake;

    private TextView tvInfo;
    private ListView lvInfo;
    private TextView tvAgeProhibition;
//    private TextView tvCombiProhibition;
    private TextView tvPregnantProhibition;

    public ArrayList<ArrayList<String>> ageProhibitionData, combiProhibitionData, pregnantProhibitionData;

    // 만 나이 계산 함수
    public static int getAmericanAge(String rrn) {
        Calendar cal = Calendar.getInstance();
        int calYear = cal.get(Calendar.YEAR);
        int calMonth = cal.get(Calendar.MONTH)+1;
        int calDay = cal.get(Calendar.DAY_OF_MONTH);

        int birthYear = Integer.parseInt(rrn.substring(0,2));
        int birthMonth = Integer.parseInt(rrn.substring(3,4));
        int birthDay = Integer.parseInt(rrn.substring(4,6));
        int sex = Integer.parseInt(rrn.substring(7,8));

        int age;
        if(sex<3) age = calYear - (1900 + birthYear) + 1; // 2000년대 이전 출생
        else age = calYear - (2000 + birthYear) + 1; // 2000년대 이후 출생
        if(birthMonth*100 + birthDay < calMonth*100 + calDay) age--; // 생일이 아직 지나지 않은 경우
        return age;
    }

    private void loadDatabaseData(String[] medicineCode) {
        AgeProhibitionAdapter ageProhibitionAdapter = new AgeProhibitionAdapter(getApplicationContext());
        ageProhibitionAdapter.create();
        ageProhibitionAdapter.open();

//        CombiProhibitionAdapter combiProhibitionAdapter = new CombiProhibitionAdapter(getApplicationContext());
//        combiProhibitionAdapter.create();
//        combiProhibitionAdapter.open();

        PregnantProhibitionAdapter pregnantProhibitionAdapter = new PregnantProhibitionAdapter(getApplicationContext());
        pregnantProhibitionAdapter.create();
        pregnantProhibitionAdapter.open();

        ageProhibitionData = ageProhibitionAdapter.getAgeProhibitionData(medicineCode);
//        combiProhibitionData = combiProhibitionAdapter.getCombiProhibitionData(medicineCode);
        pregnantProhibitionData = pregnantProhibitionAdapter.getPregnantProhibitionData(medicineCode);

        ageProhibitionAdapter.close();
//        combiProhibitionAdapter.close();
        pregnantProhibitionAdapter.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ocr_result_activity);

        tvInfo = (TextView)findViewById(R.id.tvInfo);
        lvInfo = (ListView)findViewById(R.id.lvInfo);
        tvAgeProhibition = (TextView)findViewById(R.id.tvAgeProhibition);
//        tvCombiProhibition = (TextView)findViewById(R.id.tvCombiProhibition);
        tvPregnantProhibition = (TextView)findViewById(R.id.tvPregnantProhibition);

        String visit_date = getIntent().getStringExtra("visit_date");
        String user_name = getIntent().getStringExtra("user_name");
        String user_rrn = getIntent().getStringExtra("user_rrn");
        String hospital_name = getIntent().getStringExtra("hospital_name");
        String hospital_call = getIntent().getStringExtra("hospital_call");
        String doctor_name = getIntent().getStringExtra("doctor_name");
        String pharmacy_name = getIntent().getStringExtra("pharmacy_name");
        String pharmacist_name = getIntent().getStringExtra("pharmacist_name");
        String compound_date = getIntent().getStringExtra("compound_date");
        String medicine1 = getIntent().getStringExtra("medicine1");
        String medicine2 = getIntent().getStringExtra("medicine2");
        String medicine3 = getIntent().getStringExtra("medicine3");
        String medicine4 = getIntent().getStringExtra("medicine4");
        String medicine5 = getIntent().getStringExtra("medicine5");
        String medicine6 = getIntent().getStringExtra("medicine6");
        String medicine7 = getIntent().getStringExtra("medicine7");
        String medicine8 = getIntent().getStringExtra("medicine8");
        String medicine9 = getIntent().getStringExtra("medicine9");
        String medicine10 = getIntent().getStringExtra("medicine10");
        String medicine11 = getIntent().getStringExtra("medicine11");
        String medicine12 = getIntent().getStringExtra("medicine12");
        String medicine13 = getIntent().getStringExtra("medicine13");

        StringBuilder sb = new StringBuilder();

        // 방문 날짜 데이터 처리 및 표시
        if(visit_date != null) {
            visit_date = visit_date.replace(" ", "").replace("년", "년 ").replace("월", "월 ");
            visit_date = visit_date.replace("일", "일 ").replace("-", "").replace("제", "");
            sb.append("방문 날짜 : ").append(visit_date).append("\n");
        }

        // 현재 년도와 user_rrn 정보를 이용한 user_age 처리
        String user_age = Integer.toString(getAmericanAge(user_rrn));

        // 환자 성명, 나이, 병원명, 병원 전화번호, 의사 성명 표시
        if(user_name != null)
            sb.append("환자 성명(나이) : ").append(user_name).append("(만 ").append(user_age).append("세)").append("\n");
        if(hospital_name != null)
            sb.append("의료기관 명칭 : ").append(hospital_name).append("\n");
        if(hospital_call != null)
            sb.append("의료기관 전화번호 : ").append(hospital_call).append("\n");
        if(doctor_name != null)
            sb.append("처방 의료인의 성명 : ").append(doctor_name).append("\n");
        tvInfo.setText(sb.toString()); // 처방전 기본 정보 텍스트뷰로 표시
        
        // 약 정보 리스트뷰로 표시
        ArrayList<String> medicine = new ArrayList<String>();
        if(medicine1 != null) medicine.add(medicine1);
        if(medicine2 != null) medicine.add(medicine2);
        if(medicine3 != null) medicine.add(medicine3);
        if(medicine4 != null) medicine.add(medicine4);
        if(medicine5 != null) medicine.add(medicine5);
        if(medicine6 != null) medicine.add(medicine6);
        if(medicine7 != null) medicine.add(medicine7);
        if(medicine8 != null) medicine.add(medicine8);
        if(medicine9 != null) medicine.add(medicine9);
        if(medicine10 != null) medicine.add(medicine10);
        if(medicine11 != null) medicine.add(medicine11);
        if(medicine12 != null) medicine.add(medicine12);
        if(medicine13 != null) medicine.add(medicine13);

        // 약 정보 데이터베이스 조회에 필요한 코드 목록 생성
        String[] medicineCode = new String[medicine.size()];
        for(int i=0; i<medicine.size(); i++) {
            String[] temp = medicine.get(i).split(" ");
            medicineCode[i] = temp[0];
        }
        
        // 처방전 내 인식된 정보를 ListView에 표시
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicine);
        lvInfo.setAdapter(adapter);

        // 처방전 양식이 아닌 경우 오류가 종료되는 것을 방지
        if(medicineCode.length>0) {
            loadDatabaseData(medicineCode);

            // 연령금기 관련
            sb.setLength(0); // StringBuilder 초기화
            sb.append("연령금기").append("\n");
            if(ageProhibitionData.size() == 0) sb.append("해당사항 없음").append("\n");
            else {
                for(int i=0; i<ageProhibitionData.size(); i++) {
                    String medicineName = ageProhibitionData.get(i).get(1).split("_")[0]; // 약 이름
                    String age = ageProhibitionData.get(i).get(2); // 연령
                    String unit1 = ageProhibitionData.get(i).get(3); // 세, 개월
                    String unit2 = ageProhibitionData.get(i).get(4); // 이상, 이하, 미만
                    sb.append(medicineName).append(" (").append(age).append(unit1).append(" ").append(unit2).append(" 복용금지)\n");
                }
            }
            tvAgeProhibition.setText(sb.toString());

            // 임부금기 관련
            sb.setLength(0); // StringBuilder 초기화
            sb.append("임부금기").append("\n");
            if(pregnantProhibitionData.size() == 0) sb.append("해당사항 없음").append("\n");
            else {
                for (int i=0; i<pregnantProhibitionData.size(); i++) {
                    String medicineName= pregnantProhibitionData.get(i).get(1).split("_")[0]; // 약 이름
                    sb.append(medicineName).append("\n");
                }
            }
            tvPregnantProhibition.setText(sb.toString());
        }

        // '확인' 버튼을 통해서 처방전관리 테이블에 데이터 삽입
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 처방전 내용을 데이터베이스화하는 메소드 호출
                ArrayList<String> prescriptionData = new ArrayList<String>();
                prescriptionData.add(visit_date); // 방문날짜 데이터
                prescriptionData.add(user_name); // 환자성명 데이터
                prescriptionData.add(user_age); // 환자나이 데이터
                prescriptionData.add(hospital_name); // 의료기관명칭 데이터
                prescriptionData.add(hospital_call); // 의료기관전화번호 데이터
                prescriptionData.add(doctor_name); // 처방의료인의성명 데이터

            }
        });

        // '재촬영' 버튼을 통해서 처방전을 다시 촬영
        btnRetake = findViewById(R.id.btnRetake);
        btnRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOCRResultActivity.this, PicturePrescriptionAndApplyOCRActivity.class);
                startActivity(intent);
            }
        });
    }

    // 취소 버튼 이벤트를 통해서 팝업창을 띄우고 초기 화면으로 돌아갈지 선택
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("초기 화면으로 돌아가기");
        alertBuilder.setMessage("처방전을 만들지 않고 초기 화면으로 돌아가시겠습니까?");
        alertBuilder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.setNegativeButton("초기 화면으로", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CheckOCRResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}