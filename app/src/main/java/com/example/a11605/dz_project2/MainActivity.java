package com.example.a11605.dz_project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button gotologin;
    Button gotohome;
    Button gotoDataView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findbyId();
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        gotoDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DataViewActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });


    }
    void findbyId(){
        gotologin = findViewById(R.id.login_in);
        gotohome = findViewById(R.id.login_up);
        gotoDataView=findViewById(R.id.login_manage);
    }
}
