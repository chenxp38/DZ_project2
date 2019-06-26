package com.example.a11605.dz_project2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Meeting_Room_Activity extends AppCompatActivity {
    Button testBt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingroom);
        findbyid();

        testBt.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                SQLiteDatabase db1 = openOrCreateDatabase("MeetingRoom.db", MODE_PRIVATE, null);
                db1.execSQL("CREATE TABLE IF NOT EXISTS password (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "title TEXT, " +
                        "username TEXT, " +
                        "participant TEXT, " +
                        "start_time NVARCHAR, " +
                        "ending_time NVARCHAR)");

                Cursor cursor1 = db1.query("MeetingRoom", new String[]{"title, username, participant, start_time, ending_time"}, "title=?", new String[]{"First Meeting"}, null, null, null);
                cursor1.moveToFirst();
                String receive_title = cursor1.getString(cursor1.getColumnIndex("password")).toString();//获取该用户名对应的用户密码
                String receive_img_url = cursor1.getString(cursor1.getColumnIndex("image_heaad_url")).toString();//获取该用户名对应的用户密码
            }
        });
    }
    void findbyid(){
        testBt = findViewById(R.id.testBt);
    }
}
