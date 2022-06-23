package com.shahfahad.ams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AttendanceDB extends SQLiteOpenHelper {

    private String tableName;

    public AttendanceDB(Context context, String DatabaseName, String tableName) {
        //Creating unique database for every subject
        super(context, DatabaseName+"-"+tableName.replaceAll("\\s", "")+"-att.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table attendance(date TEXT primary key, total INTEGER, present INTEGER, absent INTEGER, leave INTEGER, absentlist TEXT, leavelist TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists attendance");
    }

    public Boolean insertData(String date, int total, int present, int absent, int leave, String absentList, String leaveList){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("date", date);
        contentValues.put("total", total);
        contentValues.put("present", present);
        contentValues.put("absent", absent);
        contentValues.put("leave", leave);
        contentValues.put("absentlist", absentList);
        contentValues.put("leavelist", leaveList);

        long result = MyDB.insert("attendance", null, contentValues);


        if(result==-1)
            return false;
        else
            return true;
    }


    public Cursor getAttendances() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from attendance", null);
        return res;
    }

    public Integer deleteAtt(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("attendance", "date = ?", new String[]{date});
    }
}