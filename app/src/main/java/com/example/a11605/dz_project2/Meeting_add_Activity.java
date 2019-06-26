package com.example.a11605.dz_project2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Meeting_add_Activity extends AppCompatActivity {
    String username1;
    TextView name_show;
    EditText title;
    EditText participants;
    EditText start_time;
    EditText ending_time;
    Button gotoApply;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        Intent CurrentIntent = getIntent();//获取MainActivity.java传过来的值
        findbyid();
        username1 = CurrentIntent.getStringExtra("username1");
        name_show.setText(username1);
        gotoApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title_s = title.getText().toString();
                String participants_s = participants.getText().toString();
                String start_time_s = start_time.getText().toString();
                String ending_time_s = ending_time.getText().toString();

                //将会议信息存入会议数据库
                if (title_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "会议标题为空!", Toast.LENGTH_SHORT).show();
                } else if (participants_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "与会人员为空!", Toast.LENGTH_SHORT).show();
                } else if (start_time_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "未填写会议开始时间!", Toast.LENGTH_SHORT).show();
                } else if (ending_time_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "未填写会议结束时间!", Toast.LENGTH_SHORT).show();
                } else {//先判断会议室有没有空，有空才能添加到会议室和个人会议的数据库
                    Meeting_Room_DB_Activity db = new Meeting_Room_DB_Activity(getBaseContext());
                    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                            "MeetingRoom" + " where title like ?", new String[]{title_s});//查询会议室的议题名
                    if (cursor.moveToFirst() == true) {
                        // 当议题与数据库中已存在的数据相同时，输出相应的Toast信息
                        Toast.makeText(Meeting_add_Activity.this, "请使用其他议题名称！", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("title", title_s);//存
                        contentValues.put("username", username1);
                        contentValues.put("participant", participants_s);
                        contentValues.put("start_time", start_time_s);
                        contentValues.put("ending_time", ending_time_s);
                        sqLiteDatabase.insert("MeetingRoom", null, contentValues);
                        sqLiteDatabase.close();
                        Toast.makeText(Meeting_add_Activity.this, "会议申请成功！", Toast.LENGTH_SHORT).show();

                        //跳转到Meeting_Room_Activity.java测试一下
                        Intent intent = new Intent(Meeting_add_Activity.this, Meeting_Room_Activity.class);
                        intent.putExtra("meeting_title",title_s);//把用户名传到主页面
                        startActivity(intent);
                    }
                }
            }
        });
    }
    void findbyid(){
        title = findViewById(R.id.title_input);
        participants = findViewById(R.id.participants_input);
        start_time = findViewById(R.id.start_time_input);
        ending_time = findViewById(R.id.ending_time_input);
        gotoApply = findViewById(R.id.add_meeting_apply);
        name_show = findViewById(R.id.name_show);
    }
}
