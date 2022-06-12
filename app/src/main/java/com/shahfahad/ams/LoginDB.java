package com.shahfahad.ams;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class LoginDB extends SQLiteOpenHelper {
    public static final String DBNAME = "Accounts.db";


    public LoginDB(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(fullname TEXT, username TEXT primary key, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData( String fullname, String username, String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);

        long result = MyDB.insert("users", null, contentValues);


        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    @SuppressLint("Range")
    public String getName(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        String fullname = "";
        if (cursor.moveToFirst()) {
            fullname = cursor.getString(cursor.getColumnIndex("fullname"));

        }
        return fullname;
    }

    @SuppressLint("Range")
    public Boolean checkEmail (String user, String enteredEmail) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{user});
        String email = "";
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndex("email"));

        }
        if (enteredEmail.equals(email))
            return true;
        else
            return false;
    }

    public Boolean updatePassword(String username, String email, String pass) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        String fullname = getName(username);
        Boolean correctEmail = checkEmail(username, email);


        contentValues.put("fullname", fullname);
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", pass);


        long result = -1;
        if (correctEmail) {
            result = MyDB.update("users", contentValues, "username = ?", new String[]{username});
        }

        if(result==-1) return false;
        else
            return true;
    }

}