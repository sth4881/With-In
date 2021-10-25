package com.example.graduationproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqlite;
    private final Context context;

    private static String DB_PATH;
    private static final String DB_NAME = "carecaredatabase.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
    }

    // If the database does not exist, copy it from the assets.
    public void createDB() throws IOException {
        boolean result = checkDB();
        if (!result) {
            this.getReadableDatabase(); // Create and/or open database.
            this.close(); // Close any open database object.
            try {
                copyDB();
            } catch (IOException e) {
                throw new Error("Error copying dataBase.");
            }
        }
    }

    // Check that the database file exists in databases folder.
    public boolean checkDB() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    // Copy the database from assets.
    public void copyDB() throws IOException {
        String outputFileName = DB_PATH + DB_NAME;
        InputStream inputStream = context.getAssets().open(DB_NAME);
        OutputStream outputStream = new FileOutputStream(outputFileName);

        int size;
        byte[] buffer = new byte[1024];
        while((size = inputStream.read(buffer)) != 0) {
            outputStream.write(buffer, 0, size);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    // Open the database, so we can query it.
    public void openDB() throws SQLException {
        String dbFile = DB_PATH + DB_NAME;
        sqlite = SQLiteDatabase.openDatabase(dbFile, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public synchronized void close() {
        if(sqlite != null) sqlite.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to write the create table query.
        // As we are using pre-built database.
        // Which is ReadOnly.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No need to write the update table query.
        // As we are using pre-built database.
        // Which is ReadOnly.
        // We should not update it as requirements of application.
    }
}