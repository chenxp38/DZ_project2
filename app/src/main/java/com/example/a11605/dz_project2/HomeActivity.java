package com.example.a11605.dz_project2;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    void findbyid(){
        head_image = findViewById(R.id.personal_image);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findbyid();

    }
}
