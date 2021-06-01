package com.example.graduationproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
    private static String TAG = "DataBaseHelper"; //Logcat에 출력할 태그이름

    // TODO : assets 폴더에 있는 경우 "", 그 외 경로기입
    private static String DB_PATH = "";
    // TODO : assets 폴더에 있는 DB명 또는 별도의 데이터베이스 파일이름
    private static String DB_NAME ="carecaredatabase.db";

    private SQLiteDatabase mDataBase;

}
