package com.jeiel.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;

/**
 * Created by Jeiel on 2015/9/2.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_BOOK =  "create table Book("+
            "id integer primary key autoincrement,"+
            "author text,"+
            "price real,"+
            "pages integer,"+
            "name text)";

    private static final String CREATE_CATEGORY = "create table Category("+
            "id integer primary key autoincrement,"+
            "category_name text,"+
            "category_code integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Log.d("MyDatabaseHelper", "Create succeeded");
        //Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion){
            case 1:
                db.execSQL(CREATE_CATEGORY);
            case 2:
                db.execSQL("alter table Book add column category_id integer");
            default:
        }
    }
}
