package com.shahfahad.ams;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassesDB extends SQLiteOpenHelper {



    public ClassesDB(Context context, String DatabaseName) {
        //Creating unique database for every user
        super(context, DatabaseName+"_classes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table classes(name TEXT primary key, description TEXT, students INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData( String name, String description, int students){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("students", students);

        long result = MyDB.insert("classes", null, contentValues);

        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkClass(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from classes where name = ?", new String[]{name});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from classes", null);
        return res;
    }

    public Integer deleteClass(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("classes", "name = ?", new String[]{name});
    }

}