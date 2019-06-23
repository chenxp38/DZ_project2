package com.example.a11605.dz_project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Meeting_add_Activity extends AppCompatActivity {
    EditText title;
    EditText participants;
    EditText start_time;
    EditText ending_time;
    Button gotoApply;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        findbyid();
        gotoApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title_s = title.getText().toString();
                Toast.makeText(Meeting_add_Activity.this, title_s, Toast.LENGTH_LONG).show();
            }
        });
    }
    void findbyid(){
        title = findViewById(R.id.title_input);
        participants = findViewById(R.id.participants_input);
        start_time = findViewById(R.id.start_time_input);
        ending_time = findViewById(R.id.ending_time_input);
        gotoApply = findViewById(R.id.add_meeting_apply);
    }
}
