package com.example.graduationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class PrescriptionManagementAdapter {
    private SQLiteDatabase db;
    private final Context mContext;
    private final DBHelper dbHelper;

    public PrescriptionManagementAdapter(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    public PrescriptionManagementAdapter create() throws SQLException {
        try {
            dbHelper.createDB();
        } catch (IOException e) {
            throw new Error("Unable to create database.");
        }
        return this;
    }

    public PrescriptionManagementAdapter open() throws SQLException {
        try {
            dbHelper.openDB();
            dbHelper.close();
            db = dbHelper.getReadableDatabase();
        } catch(SQLException sqlException) {
            throw sqlException;
        }
        return this;
    }

    // 처방전 목록 불러오는 메소드
    public ArrayList<String> getPrescriptionListData() {
        try {
            String sql = "SELECT 처방전제목 FROM 처방전관리";
            ArrayList<String> arr = new ArrayList<String>();

            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            for(int i=0; i<cursor.getCount(); i++) {
                arr.add(cursor.getString(0)); // 처방전제목
                cursor.moveToNext();
            }
            return arr;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }

    // 처방전 목록 내의 각각의 데이터를 불러오는 메소드
    public ArrayList<String> getPrescriptionData(String prescription_title) {
        try {
            // 처방전제목이 중복되지 않도록 설계할 것이므로 하나의 튜플 데이터만 불러옴
            String sql = "SELECT * FROM 처방전관리 WHERE 처방전제목='"+prescription_title+"'";
            ArrayList<String> arr = new ArrayList<String>();
            
            // 처방전관리 테이블의 '처방전제목', '방문날짜', '환자성명', '환자나이', '의료기관명칭', '의료기관전화번호', '처방의료인의성명' 데이터를 불러옴
            // 2021.10.30 처방전관리 테이블에 저장된 처방전 데이터를 불러오는 과정에서 오류 발생
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            arr.add(prescription_title); // 처방전제목
            arr.add(cursor.getString(2)); // 방문날짜
            arr.add(cursor.getString(3)); // 환자성명
            arr.add(cursor.getString(4)); // 환자나이
            arr.add(cursor.getString(5)); // 의료기관명칭
            arr.add(cursor.getString(6)); // 의료기관전화번호
            arr.add(cursor.getString(7)); // 처방의료인의성명
            Log.d("PPAP", cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4)+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getString(7));
            return arr;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }

    // OCR 적용 결과로 생성된 데이터들을 처방전관리 테이블에 삽입
    public boolean insertPrescriptionData(String[] prescriptionData) {
        try {
            // 방법 1 (성공)
            //        StringBuilder sb = new StringBuilder();
            //        sb.append("INSERT INTO 처방전관리 VALUES(");
            //        sb.append((char[]) null).append(", '").append(prescriptionData[0]).append("', '");
            //        sb.append(prescriptionData[1]).append("', '").append(prescriptionData[2]).append("', '");
            //        sb.append(prescriptionData[3]).append("', '").append(prescriptionData[4]).append("', '");
            //        sb.append(prescriptionData[5]).append("', '").append(prescriptionData[6]).append("');");
            //        db.execSQL(sb.toString());

            // 방법 2 (성공)
            ContentValues values = new ContentValues();
            values.put("처방전번호", (byte[]) null);
            values.put("처방전제목", prescriptionData[0]);
            values.put("방문날짜", prescriptionData[1]);
            values.put("환자성명", prescriptionData[2]);
            values.put("환자나이", prescriptionData[3]);
            values.put("의료기관명칭", prescriptionData[4]);
            values.put("의료기관전화번호", prescriptionData[5]);
            values.put("처방의료인의성명", prescriptionData[6]);

            // 결과 성공/실패시 true/false 반환
            long result = db.insert("처방전관리", null, values);
            if (result == -1) {
                Toast.makeText(mContext.getApplicationContext(), "처방전 생성 실패", Toast.LENGTH_SHORT).show();
                return false;
            }
            Toast.makeText(mContext.getApplicationContext(), "처방전 생성 성공", Toast.LENGTH_SHORT).show();
            return true;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }

    public void close() {
        dbHelper.close();
    }
}
