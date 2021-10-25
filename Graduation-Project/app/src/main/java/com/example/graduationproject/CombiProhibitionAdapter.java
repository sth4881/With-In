package com.example.graduationproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class CombiProhibitionAdapter {
    private SQLiteDatabase db;
    private final Context mContext;
    private final DBHelper dbHelper;

    protected static final String TABLE_NAME = "병용금기";
    protected static final String COLUMN_NAME = "제품코드";

    public CombiProhibitionAdapter(Context context) {
        this.mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    public CombiProhibitionAdapter create() throws SQLException {
        try {
            dbHelper.createDB();
        } catch (IOException e) {
            throw new Error("Unable to create database.");
        }
        return this;
    }

    public CombiProhibitionAdapter open() throws SQLException {
        try {
            dbHelper.openDB();
            dbHelper.close();
            db = dbHelper.getReadableDatabase();
        } catch(SQLException sqlException) {
            throw sqlException;
        }
        return this;
    }

//    public ArrayList<ArrayList<String> getCombiProhibitionData(ArrayList<String> medicineCode) {
//        try {
//            String sql ="SELECT * FROM 병용금기";
//            Cursor cursor = db.rawQuery(sql, null);
//            return cursor;
//        } catch (SQLException sqlException) {
//            throw sqlException;
//        }
//    }

    public void close() {
        dbHelper.close();
    }
}
