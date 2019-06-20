package com.example.a11605.dz_project2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;


public class MyDataBase extends SQLiteOpenHelper {
    // 创建类
    private static final String DB_NAME = "MyDB.db";
    private static final String TABLE_NAME = "Info";
    private static final int DB_VERSION = 1;

    public MyDataBase(Context context, String username, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, username, factory, version);
    }

    public MyDataBase(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    // 创建数据库: 可直接执行创建数据库的 SQl 语句
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + "(username TEXT PRIMARY KEY,"
                + "password TEXT,"
                + "image_heaad_url TEXT)";
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        } catch (SQLException e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Cursor query(String tableName, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
//查询语句还需要重写
        return  null;
    }
}

