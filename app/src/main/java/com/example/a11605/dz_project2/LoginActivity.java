package com.example.a11605.dz_project2;

import android.content.ContentResolver;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class LoginActivity extends AppCompatActivity {
    Button gotoLogin;
    Button gotoManagelogin;
    ImageView image_head;
    EditText nameET;
    EditText pwdET;
    EditText pwd_confirm;
    String image_head_url;
    String username;
    String password;
    String password_confirm;
    private static final String TABLE_NAME = "Info";

    void findbyid(){
        gotoLogin = findViewById(R.id.login_in);
        image_head = findViewById(R.id.image_head);
        nameET = findViewById(R.id.name_input);
        pwdET = findViewById(R.id.pwd_input);
        pwd_confirm = findViewById(R.id.pwd_confirm_input);
        gotoManagelogin = findViewById(R.id.login_in_manage);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findbyid();

        //点击确认注册时进行检查并将检查通过的账户输入数据库
        //有一个gm账号，是通往DataViewActivity页面，普通用户通往home页面
        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = nameET.getText().toString();
                password = pwdET.getText().toString();
                password_confirm = pwd_confirm.getText().toString();
//先查询该用户是否已经注册在数据库中
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "用户名为空!", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "密码为空!", Toast.LENGTH_SHORT).show();
                } else if (!password_confirm.equals(password)) {
                    Toast.makeText(LoginActivity.this, "确认密码不一致!", Toast.LENGTH_SHORT).show();
                } else if (password_confirm.equals(password) && !username.equals("") && !password.equals("")) {
                    MyDataBase db = new MyDataBase(getBaseContext());
                    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                            TABLE_NAME + " where username like ?", new String[]{username});//查询数据库中用户名
                    if (cursor.moveToFirst() == true) {
                        // 当姓名编辑框与数据库中已存在的数据相同时，输出相应的Toast信息
                        Toast.makeText(LoginActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("username", username);//存
                        contentValues.put("password", password);
                        contentValues.put("image_heaad_url", image_head_url);
                        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
                        sqLiteDatabase.close();
                        Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        gotoManagelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = nameET.getText().toString();
                password = pwdET.getText().toString();
                password_confirm = pwd_confirm.getText().toString();
//先查询该用户是否已经注册在数据库中
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "用户名为空!", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "密码为空!", Toast.LENGTH_SHORT).show();
                } else if (!password_confirm.equals(password)) {
                    Toast.makeText(LoginActivity.this, "确认密码不一致!", Toast.LENGTH_SHORT).show();
                } else if (password_confirm.equals(password) && !username.equals("") && !password.equals("")) {
                    MyDataBase db = new MyDataBase(getBaseContext());
                    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                            TABLE_NAME + " where username like ?", new String[]{username});//查询数据库中用户名
                    if (cursor.moveToFirst() == true) {
                        // 当姓名编辑框与数据库中已存在的数据相同时，输出相应的Toast信息
                        Toast.makeText(LoginActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        if (username.equals("chenxp38")) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("username", username);//存
                            contentValues.put("password", password);
                            contentValues.put("image_heaad_url", image_head_url);
                            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
                            sqLiteDatabase.close();
                            Toast.makeText(LoginActivity.this, "管理员注册成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Sorry,您没有权限注册管理员！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        image_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });

    }
    //获取本地图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri1 = data.getData();
            image_head_url = uri1.getPath();//这是本机的图片路径
            image_head_url = uri1.toString();
            Uri uri2 = Uri.parse((String) image_head_url);//其实只是测试Uri和string的相互转换，没什么其他的意思，就是说下面Bitmap直接用uri1就行了
             System.out.println(image_head_url);
            //Toast.makeText(LoginActivity.this, img_url, Toast.LENGTH_SHORT).show();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri2));
                ImageView imageView = (ImageView) findViewById(R.id.image_head);
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
