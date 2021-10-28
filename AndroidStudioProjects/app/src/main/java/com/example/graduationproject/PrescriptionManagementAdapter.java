package com.example.graduationproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.IOException;

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

    public void insert(String[] prescriptionData) {
        // 방법 1 (성공)
        //StringBuilder sb = new StringBuilder();
        //sb.append("INSERT INTO 처방전관리 VALUES(");
        //sb.append("6").append(", '").append(prescriptionData[0]).append("', '");
        //sb.append(prescriptionData[1]).append("', '").append(prescriptionData[2]).append("', '");
        //sb.append(prescriptionData[3]).append("', '").append(prescriptionData[4]).append("', '");
        //sb.append(prescriptionData[5]).append("', '").append(prescriptionData[6]).append("');");
        //db.execSQL(sb.toString());

        // 방법 2 (성공), 현재 8번째 튜플까지 생성
        ContentValues values = new ContentValues();
        values.put("처방전번호", "NULL");
        values.put("처방전이름", prescriptionData[0]);
        values.put("방문날짜", prescriptionData[1]);
        values.put("환자성명", prescriptionData[2]);
        values.put("환자나이", prescriptionData[3]);
        values.put("의료기관명칭", prescriptionData[4]);
        values.put("의료기관전화번호", prescriptionData[5]);
        values.put("처방의료인의성명", prescriptionData[6]);
        db.insert("처방전관리", null, values);

        // 결과 성공/실패시 true/false 반환 시도 예정
        //ContentValues values = new ContentValues();
        //values.put("처방전번호", 7);
        //values.put("처방전이름", "'"+prescriptionData[0]+"'");
        //values.put("방문날짜", "'"+prescriptionData[1]+"'");
        //values.put("환자성명", "'"+prescriptionData[2]+"'");
        //values.put("환자나이", "'"+prescriptionData[3]+"'");
        //values.put("의료기관명칭", "'"+prescriptionData[4]+"'");
        //values.put("의료기관전화번호", "'"+prescriptionData[5]+"'");
        //values.put("처방의료인의성명", "'"+prescriptionData[6]+"'");
        //long result = db.insert("처방전관리", null, values);
        //if(result==-1) return false;
        //else return true;

        Toast.makeText(mContext.getApplicationContext(), "처방전 생성 성공", Toast.LENGTH_SHORT).show();
    }

    public void close() {
        dbHelper.close();
    }
}
