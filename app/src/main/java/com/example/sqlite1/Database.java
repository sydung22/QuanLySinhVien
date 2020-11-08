package com.example.sqlite1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "qlsv.sqlite";
    private static final int DATABASE_VERSION =1;
    public static final String TABLE_NAME="sinhvien";
    public static final String COL_ID="id";
    public static final String COL_NAME="name";
    public static final String COL_GT="gt";
    public static final String COL_SDT="sdt";
    public static final String COL_EMAIL="email";
    public static final String COL_LOP="lop";



    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT ,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT)"
                ,TABLE_NAME
                ,COL_ID
                ,COL_NAME
                ,COL_GT
                ,COL_SDT
                ,COL_EMAIL
                ,COL_LOP));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
