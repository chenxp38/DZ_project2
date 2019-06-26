package com.example.a11605.dz_project2;

import android.content.Intent;
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
        Toast.makeText(getApplicationContext(),"用户名为:"+ username1,Toast.LENGTH_SHORT).show();
        name_show.setText(username1);
        gotoApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title_s = title.getText().toString();
                String participants_s = participants.getText().toString();
                String start_time_s = start_time.getText().toString();
                String ending_time_s = ending_time.getText().toString();
                Toast.makeText(Meeting_add_Activity.this, title_s, Toast.LENGTH_LONG).show();

                //将会议信息存入会议数据库
                if (title_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "会议标题为空!", Toast.LENGTH_SHORT).show();
                } else if (participants_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "与会人员为空!", Toast.LENGTH_SHORT).show();
                } else if (start_time_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "未填写会议开始时间!", Toast.LENGTH_SHORT).show();
                } else if (ending_time_s.equals("")) {
                    Toast.makeText(Meeting_add_Activity.this, "未填写会议结束时间!", Toast.LENGTH_SHORT).show();
                } else {

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
