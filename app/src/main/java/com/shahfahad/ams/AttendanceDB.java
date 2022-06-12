package com.shahfahad.ams;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AttendanceDB extends SQLiteOpenHelper {

    private String TABLE_NAME;

    public AttendanceDB(Context context, String DatabaseName, String tableName) {
        //Creating unique database for every user
        super(context, DatabaseName+"_attendances.db", null, 1);
        this.TABLE_NAME = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table "+TABLE_NAME+"(id INTEGER primary key autoincrement, date TEXT, total INTEGER, present INTEGER, absent INTEGER, leave INTEGER, absentlist TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists "+TABLE_NAME);
    }

    public Boolean insertData(String date, int total, int present, int absent, int leave, String absentList){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("date", date);
        contentValues.put("present", present);
        contentValues.put("absent", absent);
        contentValues.put("leave", leave);
        contentValues.put("absentlist", absentList);


        long result = MyDB.insert(TABLE_NAME, null, contentValues);


        if(result==-1) return false;
        else
            return true;
    }


    public Cursor getAttendances() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

//    public Boolean checkusernamepassword(String username, String password){
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
//        if(cursor.getCount()>0)
//            return true;
//        else
//            return false;
//    }
//
//    @SuppressLint("Range")
//    public String getName(String username) {
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
//        String fullname = "";
//        if (cursor.moveToFirst()) {
//            fullname = cursor.getString(cursor.getColumnIndex("fullname"));
//
//        }
//        return fullname;
//    }
//
//    @SuppressLint("Range")
//    public Boolean checkEmail (String user, String enteredEmail) {
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{user});
//        String email = "";
//        if (cursor.moveToFirst()) {
//            email = cursor.getString(cursor.getColumnIndex("email"));
//
//        }
//        if (enteredEmail.equals(email))
//            return true;
//        else
//            return false;
//    }
//
//    public Boolean updatePassword(String username, String email, String pass) {
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        ContentValues contentValues= new ContentValues();
//        String fullname = getName(username);
//        Boolean correctEmail = checkEmail(username, email);
//
//
//        contentValues.put("fullname", fullname);
//        contentValues.put("username", username);
//        contentValues.put("email", email);
//        contentValues.put("password", pass);
//
//
//        long result = -1;
//        if (correctEmail) {
//            result = MyDB.update("users", contentValues, "username = ?", new String[]{username});
//        }
//
//        if(result==-1) return false;
//        else
//            return true;
//    }

}