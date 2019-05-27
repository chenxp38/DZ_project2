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
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    // 初始化控件
    public Button addBtn;
    public Button clear;
    public Button BtnC;

    public EditText usernameET;
    public EditText passwordET;
    public EditText head_iamge_ET;

    private void findViewById() {
        addBtn = (Button) findViewById(R.id.BtnAdd);
        clear = (Button) findViewById(R.id.BtnClear);
        BtnC = (Button) findViewById(R.id.BtnC);
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        head_iamge_ET = (EditText) findViewById(R.id.head_image_urlET);
    }

    // 创建类
    private static final String TABLE_NAME = "Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_info);

        findViewById();

        // 增加条目的编辑界面增加按钮事件
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById();
                if (usernameET.length() == 0) {
                    // 当编辑框为空时，输出相应的Toast信息
                    Toast.makeText(AddActivity.this, "名字为空啦，请完善一下哦~", Toast.LENGTH_SHORT).show();
                } else {
                    findViewById();

                    String usernamet = usernameET.getText().toString();
                    String passwordt = passwordET.getText().toString();
                    String head_image_t = head_iamge_ET.getText().toString();

                    MyDataBase db = new MyDataBase(getBaseContext());
                    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                            TABLE_NAME + " where username like ?", new String[]{usernamet});

                    if (cursor.moveToFirst() == true) {
                        // 当姓名编辑框与数据库中已存在的数据相同时，输出相应的Toast信息
                        Toast.makeText(AddActivity.this, "名字重复啦，请核查一下哦~", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("username", usernamet);//存
                        contentValues.put("password", passwordt);
                        contentValues.put("image_heaad_url", head_image_t);
                        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
                        sqLiteDatabase.close();
                        Intent intent = new Intent(AddActivity.this, DataViewActivity.class);
                        setResult(99, intent);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        // 清除输入框中的内容事件
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById();
                usernameET.setText("");
                passwordET.setText("");
                head_iamge_ET.setText("");
            }
        });

        // 取消当前编辑按钮事件
        BtnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                AddActivity.this.finish();
            }
        });
    }
}