package com.example.graduationproject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class MedicineManagementAdapter {
    private SQLiteDatabase db;
    private final Context mContext;
    private final DBHelper dbHelper;

    public MedicineManagementAdapter(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    public MedicineManagementAdapter create() throws SQLException {
        try {
            dbHelper.createDB();
        } catch (IOException e) {
            throw new Error("Unable to create database.");
        }
        return this;
    }

    public MedicineManagementAdapter open() throws SQLException {
        try {
            dbHelper.openDB();
            dbHelper.close();
            db = dbHelper.getReadableDatabase();
        } catch(SQLException sqlException) {
            throw sqlException;
        }
        return this;
    }

    // '제품명' 또는 '제품코드'를 통해서 '약백과사전' 테이블로부터 해당 약의 데이터를 불러오는 메소드
    public ArrayList<String> getMedicineData(String medicineInput) {
        String sql = "SELECT * FROM 약백과사전 WHERE 제품명='"+medicineInput+"' OR 제품코드='"+medicineInput+"'";
        ArrayList<String> arr = new ArrayList<String>();

        // 약백과사전의 '제품명(1)', '제품코드(2)', '효능∙효과(3)', '사용법∙용량(4)', '주의사항(5)', '부작용(6)', '이미지(7)' 데이터를 불러옴
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            for(int i=1; i<cursor.getColumnCount(); i++)
                arr.add(cursor.getString(i));
        }
        return arr;
    }

    public boolean checkMedicineData(String medicineInput) {
        try {
            String sql = "SELECT * FROM 약백과사전 WHERE 제품명='"+medicineInput+"' OR 제품코드='"+medicineInput+"'";
            Cursor cursor = db.rawQuery(sql, null);
            return cursor.getCount() > 0;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }

    public void close() {
        dbHelper.close();
    }
}
