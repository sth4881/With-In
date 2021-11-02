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
            
            // 처방전관리 테이블의 '처방전제목(1)', '방문날짜(2)', '환자성명(3)', '환자나이(4)', '의료기관명칭(5)', '의료기관전화번호(6)', '처방의료인의성명(7)', '약(8~20)' 데이터를 불러옴
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            // '처방전번호' 칼럼을 제외한 나머지 데이터들을 모두 가져옴
            for(int i=1; i<cursor.getColumnCount(); i++)
                // 불러온 데이터의 값이 null이 아니라면 arr에 삽입
                if(cursor.getString(i) != null) arr.add(cursor.getString(i));
            return arr;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }

    // OCR 적용 결과로 생성된 데이터들을 처방전관리 테이블에 삽입
    public boolean insertPrescriptionData(ArrayList<String> prescriptionData, ArrayList<String> medicineData) {
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
            values.put("처방전제목", prescriptionData.get(0));
            values.put("방문날짜", prescriptionData.get(1));
            values.put("환자성명", prescriptionData.get(2));
            values.put("환자나이", prescriptionData.get(3));
            values.put("의료기관명칭", prescriptionData.get(4));
            values.put("의료기관전화번호", prescriptionData.get(5));
            values.put("처방의료인의성명", prescriptionData.get(6));
            for(int i=1; i<=medicineData.size(); i++)
                values.put("약"+i, medicineData.get(i-1));

            // 결과 성공/실패시 true/false 반환
            long result = db.insert("처방전관리", null, values);
            return result != -1;
        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }

    public void close() {
        dbHelper.close();
    }
}
