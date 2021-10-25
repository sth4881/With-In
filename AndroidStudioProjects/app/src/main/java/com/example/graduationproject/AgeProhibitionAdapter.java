package com.example.graduationproject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class AgeProhibitionAdapter {
    private SQLiteDatabase db;
    private final Context mContext;
    private final DBHelper dbHelper;

    protected static final String TABLE_NAME = "연령금기";
    protected static final String COLUMN_NAME = "제품코드";

    public AgeProhibitionAdapter(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    public AgeProhibitionAdapter create() throws SQLException {
        try {
            dbHelper.createDB();
        } catch (IOException e) {
            throw new Error("Unable to create database.");
        }
        return this;
    }

    public AgeProhibitionAdapter open() throws SQLException {
        try {
            dbHelper.openDB();
            dbHelper.close();
            db = dbHelper.getReadableDatabase();
        } catch(SQLException sqlException) {
            throw sqlException;
        }
        return this;
    }

    public ArrayList<ArrayList<String>> getAgeProhibitionData(String[] medicineCode) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM 연령금기 WHERE 제품코드 = ").append(medicineCode[0]);
            for(int i=1; i<medicineCode.length; i++)
                sql.append(" OR 제품코드 = ").append(medicineCode[i]);
            Cursor cursor = db.rawQuery(sql.toString(), null);

            // 처방전에 적혀있는 약 중에서 연령금기에 해당하는 약의 개수만큼 생성
            ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
            for(int i=0; i<cursor.getCount(); i++)
                arr.add(new ArrayList<String>());

            // 연령금기 테이블의 '제품코드', '제품명', '특정연령', '특정연령단위-1', '특정연령단위-2' 칼럼 데이터를 불러옴
            cursor.moveToFirst();
            for(int i=0; i<arr.size(); i++) {
                arr.get(i).add(cursor.getString(0)); // 제품코드
                arr.get(i).add(cursor.getString(1)); // 제품명
                arr.get(i).add(cursor.getString(2)); // 특정연령
                arr.get(i).add(cursor.getString(3)); // 특정연령단위-1
                arr.get(i).add(cursor.getString(4)); // 특정연령단위-2
                cursor.moveToNext();
            }
            return arr;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }

    public void close() {
        dbHelper.close();
    }
}