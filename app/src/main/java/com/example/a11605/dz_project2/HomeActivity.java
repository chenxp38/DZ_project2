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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
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
    Button re_login;
    TextView name_TV;
    RadioGroup choose_func_Btn;
    RadioButton had_applied_btn, need_to_join_btn;
    void findbyid(){
        head_image = findViewById(R.id.personal_image);
        name_TV = findViewById(R.id.usernameShow);
        gotoApply = findViewById(R.id.gotoApply);
        re_login = findViewById(R.id.re_login);
        choose_func_Btn = findViewById(R.id.choose_function_btn);
        had_applied_btn = findViewById(R.id.had_applied_btn);
        need_to_join_btn = findViewById(R.id.need_to_join_btn);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在setContentView之前添加,未添加的话home键监听无效，设置窗体属性
        this.getWindow().setFlags(0x80000000, 0x80000000);
        setContentView(R.layout.activity_home);
        Intent CurrentIntent = getIntent();//获取MainActivity.java传过来的值
        findbyid();
        username = CurrentIntent.getStringExtra("username");
        head_image_url = CurrentIntent.getStringExtra("head_img_url");
        Uri uri = Uri.parse((String) head_image_url);//转为uri
        //ContentResolver cr = this.getContentResolver();
        try {
            FileInputStream fis = new FileInputStream(head_image_url);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            head_image.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       /* Uri uri = Uri.parse(head_image_url);
        ContentResolver cr = this.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            /* 将Bitmap设定到ImageView */
        /*    head_image.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }*/
        name_TV.setText(username);
        Toast.makeText(getApplicationContext(),"用户名为:"+ head_image_url,Toast.LENGTH_SHORT).show();

        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        re_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        gotoApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,Meeting_add_Activity.class);
                intent.putExtra("username1",username);//把用户名传到主页面
                startActivity(intent);
            }
        });
        //下面是对两个单选按钮的显示的处理
        choose_func_Btn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.had_applied_btn:
                        Toast.makeText(HomeActivity.this, "已申请会议", 1).show();
                        break;
                    case R.id.need_to_join_btn:
                        Toast.makeText(HomeActivity.this, "要参加会议", 1).show();
                        break;
                }
            }
        });
    }
}
