package com.example.sqlite1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TuongTacDatabase {
    Database db;
    SQLiteDatabase sdb;
    String arr[]=new String[]{db.COL_ID,db.COL_NAME,db.COL_EMAIL,db.COL_GT,db.COL_LOP,db.COL_SDT};
    public TuongTacDatabase(Context context){
        db =new Database(context);

    }
    public void open(){
        sdb=db.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
    public long themSV(SinhVien sv){
        ContentValues cv =new ContentValues();
        cv.put(db.COL_NAME,sv.getTen());
        cv.put(db.COL_EMAIL,sv.getEmail());
        cv.put(db.COL_GT,sv.getGioitinh());
        cv.put(db.COL_SDT,sv.getSdt());
        cv.put(db.COL_LOP,sv.getLophoc());
        return sdb.insert(db.TABLE_NAME,null,cv);
    }
    public long suaSV(SinhVien sv){
        ContentValues cv =new ContentValues();
        cv.put(db.COL_NAME,sv.getTen());
        cv.put(db.COL_EMAIL,sv.getEmail());
        cv.put(db.COL_GT,sv.getGioitinh());
        cv.put(db.COL_SDT,sv.getSdt());
        cv.put(db.COL_LOP,sv.getLophoc());
        return sdb.update(db.TABLE_NAME,cv,db.COL_ID+" = "+sv.getId(),null);
    }
    public long xoaSV(int id){
        return sdb.delete(db.TABLE_NAME,db.COL_ID+" = "+id,null);
    }

    public ArrayList<SinhVien> getAlldata(){
        Cursor cursor =sdb.rawQuery(" SELECT * FROM " + db.TABLE_NAME +" ;",null);
        ArrayList<SinhVien> arr=new ArrayList<>();

        while (cursor.moveToNext()){
            SinhVien sv=new SinhVien();

            sv.setId(cursor.getInt(cursor.getColumnIndex(db.COL_ID)));
            sv.setTen(cursor.getString(cursor.getColumnIndex(db.COL_NAME)));
            sv.setEmail(cursor.getString(cursor.getColumnIndex(db.COL_EMAIL)));
            sv.setSdt(cursor.getString(cursor.getColumnIndex(db.COL_SDT)));
            sv.setGioitinh(cursor.getString(cursor.getColumnIndex(db.COL_GT)));
            sv.setLophoc(cursor.getString(cursor.getColumnIndex(db.COL_LOP)));

            arr.add(sv);
        }
        return arr;
    }
}
