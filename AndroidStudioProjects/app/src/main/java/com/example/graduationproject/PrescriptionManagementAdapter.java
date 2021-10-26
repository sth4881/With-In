package com.example.graduationproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
        
    }

    public void close() {
        dbHelper.close();
    }
}
