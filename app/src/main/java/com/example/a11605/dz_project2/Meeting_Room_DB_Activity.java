package com.example.a11605.dz_project2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Meeting_Room_DB_Activity extends SQLiteOpenHelper {
    private static final String DB_NAME = "MeetingRoom.db";
    private static final String TABLE_NAME = "MeetingRoom";
    private static final int DB_VERSION = 1;

    public Meeting_Room_DB_Activity(Context context, String username, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, username, factory, version);
    }
    public Meeting_Room_DB_Activity(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + "(title TEXT PRIMARY KEY,"//会议室的数据库表的主键是会议议题
                + "username TEXT,"//会议申请者
                + "participant TEXT,"//多个与会者用空格分隔名称
                + "start_time NUMERIC,"//开始时间以及结束时间
                + "ending_time NUMERIC)";//初定是date类型
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        } catch (SQLException e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
