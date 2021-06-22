package com.example.graduationproject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class CombiTabooAdapter {
    private SQLiteDatabase db;
    private final Context context;
    private final DBHelper dbHelper;


    protected static final String TABLE_NAME = "병용금기";
    protected static final String COLUMN_NAME = "제품코드";

    public CombiTabooAdapter(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public CombiTabooAdapter create() throws SQLException {
        try {
            dbHelper.createDB();
        } catch (IOException e) {
            throw new Error("Unable to create database.");
        }
        return this;
    }

    public CombiTabooAdapter open() throws SQLException {
        try {
            dbHelper.openDB();
            dbHelper.close();
            db = dbHelper.getReadableDatabase();
        } catch(SQLException sqlException) {
            throw sqlException;
        }
        return this;
    }

    public Cursor getCombiTabooData(ArrayList<String> code) {
        try {
            String sql ="SELECT * FROM 병용금기";
            Cursor cursor = db.rawQuery(sql, null);
            return cursor;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void close() {
        dbHelper.close();
    }
}
