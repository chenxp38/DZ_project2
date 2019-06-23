package com.example.a11605.dz_project2;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TABLE_NAME = "Info";//数据库表的名字
    public List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    ImageView head_image;
    String username;
    String password;
    String head_image_url;
    Button gotoApply;
    void findbyid(){
        head_image = findViewById(R.id.personal_image);
        gotoApply = findViewById(R.id.gotoApply);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在setContentView之前添加,未添加的话home键监听无效，设置窗体属性
        this.getWindow().setFlags(0x80000000, 0x80000000);
        setContentView(R.layout.activity_home);

        findbyid();

        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        gotoApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,Meeting_add_Activity.class);
                startActivity(intent);
                HomeActivity.this.finish();
            }
        });

    }
}
