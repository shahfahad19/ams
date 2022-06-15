package com.shahfahad.ams;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AttendanceDB extends SQLiteOpenHelper {

    public AttendanceDB(Context context, String DatabaseName, String tableName) {
        //Creating unique database for every subject
        super(context, DatabaseName+"-"+tableName.replaceAll("\\s", "")+"-att.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table attendance(date TEXT, total INTEGER, present INTEGER, absent INTEGER, leave INTEGER, absentlist TEXT, leavelist TEXT)");
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
}