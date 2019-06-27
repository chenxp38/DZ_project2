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
import android.widget.ListView;
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
    public SimpleAdapter adapter;
    public ListView LV;
    ImageView head_image;
    String username;
    String password;
    String head_image_url;
    Button gotoApply;
    Button re_login;
    TextView name_TV;
    RadioGroup choose_func_Btn;
    RadioButton had_applied_btn, need_to_join_btn;
    Map<String, String> map = new HashMap<String, String>();
    void findbyid(){
        head_image = findViewById(R.id.personal_image);
        name_TV = findViewById(R.id.usernameShow);
        gotoApply = findViewById(R.id.gotoApply);
        re_login = findViewById(R.id.re_login);
        choose_func_Btn = findViewById(R.id.choose_function_btn);
        had_applied_btn = findViewById(R.id.had_applied_btn);
        need_to_join_btn = findViewById(R.id.need_to_join_btn);
        LV = findViewById(R.id.had_applied_meeting_display);
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

        had_applied_display();//因为单选按钮要按了才更新列表，一开始没有显示，所以改成函数，进入类时先加载一次

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
                    case R.id.had_applied_btn: {
                        had_applied_display();
                        break;
                    }
                    case R.id.need_to_join_btn: {
                        Toast.makeText(HomeActivity.this,"要参加会议！",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }
    void had_applied_display(){
        //忽然发现会有一个重复点击的问题，所以要做一个限制，就是已经存在了不能添加显示
        //在MeetRoom_DB的数据库里面关于第二列的username查询，输出显示
            Meeting_Room_DB_Activity db = new Meeting_Room_DB_Activity(getBaseContext());
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                    "MeetingRoom" + " where username like ?", new String[]{username});
            if (cursor == null) {
            }
            else {
                while (cursor.moveToNext()) {
                    //
                    SQLiteDatabase db1 = openOrCreateDatabase("MeetingRoom.db", MODE_PRIVATE, null);
                    db1.execSQL("CREATE TABLE IF NOT EXISTS password (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "title NVARCHAR, " +
                            "username NVARCHAR, " +
                            "participant NVARCHAR, " +
                            "start_time NVARCHAR, " +
                            "ending_time NVARCHAR)");
                    Cursor cursor1 = db1.query("MeetingRoom", new String[]{"title, username, participant, start_time, ending_time"},
                            "username=?", new String[]{username}, null, null, null);
                    //  cursor1.moveToFirst();                  //上条语句应该查询的是数据库中用户名所在的行，并提取密码等数据
                    //
                    if (cursor1.moveToFirst() == true) {//注意这里不能用上一行的cursor1.moveToFirst();命令（其他类应该能看到），因为用了只会得到第一个符合条件的数据                                    String receive_title = cursor.getString(cursor.getColumnIndex("title")).toString();//获取该用户名对应的议题
                        String receive_title = cursor.getString(cursor.getColumnIndex("title")).toString();
                        String receive_start_time = cursor.getString(cursor.getColumnIndex("start_time")).toString();//获取会议开始时间
                        String receive_ending_time = cursor.getString(cursor.getColumnIndex("ending_time")).toString();//获取会议结束时间
                        //尝试显示
                        //在这里判断有没有重复点击
                        // 遍历Map
                        boolean is_add = true;
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            if (key.equals("title_h") && value.equals(receive_title)){
                                is_add = false;//意思就是Map中的title（唯一）已经存在时，不添加该条
                            }
                        }
                        if (is_add == true) {
                            map.put("meeting_room_h", "1");
                            map.put("title_h", receive_title);
                            map.put("name_h", username);
                            datas.add(map);
                        }
                    }
                }
                adapter = new SimpleAdapter(HomeActivity.this, datas, R.layout.meeting_item,
                        new String[]{"meeting_room_h", "title_h", "name_h"},
                        new int[]{R.id.meeting_room, R.id.title_TV, R.id.username_TV});//item
                LV.setAdapter(adapter);
            }
    }
}
