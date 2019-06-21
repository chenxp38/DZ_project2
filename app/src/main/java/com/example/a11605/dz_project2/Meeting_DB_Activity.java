package com.example.a11605.dz_project2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Meeting_DB_Activity extends SQLiteOpenHelper {
    private static final String DB_NAME = "Meeting.db";
    private static final String TABLE_NAME = "Meeting";
    private static final int DB_VERSION = 1;

    public Meeting_DB_Activity(Context context, String username, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, username, factory, version);
    }

    public Meeting_DB_Activity(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    // 创建数据库: 可直接执行创建数据库的 SQl 语句
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + "(username TEXT PRIMARY KEY,"
                + "title TEXT,"//会议议题
                + "participant TEXT,"//多个与会者用空格分隔名称
                + "start_time NUMERIC,"//开始时间以及结束时间
                + "ending_time NUMERIC)";//初定是date类型
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        } catch (SQLException e) {

        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
