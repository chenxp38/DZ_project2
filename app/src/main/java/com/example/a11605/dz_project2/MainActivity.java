package com.example.a11605.dz_project2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button gotologin;
    Button gotohome;
    Button gotoDataView;
    EditText login_name;
    EditText password;
    private static final String TABLE_NAME = "Info";
    private static final String DB_NAME = "MyDB.db";
    private static final int REQUEST_CODE = 1;
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
            public void onClick(View v) {
                String name = login_name.getText().toString();
                String pwd = password.getText().toString();
                if(name.equals("")){
                    Toast.makeText(getApplicationContext(),"用户名为空！",Toast.LENGTH_SHORT).show();
                }else if(pwd.equals("")){
                    Toast.makeText(getApplicationContext(),"密码为空！",Toast.LENGTH_SHORT).show();
                }else if(!name.equals("") && !pwd.equals("")) {
                    MyDataBase db = new MyDataBase(getBaseContext());
                    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                            TABLE_NAME + " where username like ?", new String[]{name});
                    if (cursor.moveToFirst() == true) {
                        //
                        SQLiteDatabase db1 = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                        db1.execSQL("CREATE TABLE IF NOT EXISTS password (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "username NVARCHAR, " +
                                "password NVARCHAR, " +
                                "image_heaad_url NVARCHAR)");
                        Cursor cursor1 = db1.query(TABLE_NAME, new String[]{"username, password, image_heaad_url"}, "username=?", new String[]{name}, null, null, null);
                        cursor1.moveToFirst();                  //上条语句应该查询的是数据库中用户名所在的行，并提取密码等数据
                        //String receive_name = cursor.getString(cursor.getColumnIndex("username")).toString();
                        String receive_pwd = cursor1.getString(cursor1.getColumnIndex("password")).toString();//获取该用户名对应的用户密码
                        String receive_img_url = cursor1.getString(cursor1.getColumnIndex("image_heaad_url")).toString();//获取该用户名对应的用户密码
                        if (receive_pwd.equals(pwd)){//密码正确
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("username",name);//把用户名传到主页面
                            intent.putExtra("head_img_url", receive_img_url);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }else{
                            Toast.makeText(MainActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 当姓名编辑框与数据库中已存在的数据不相同时，输出相应的Toast信息
                        Toast.makeText(MainActivity.this, "该用户不存在，请注册！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        gotoDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = login_name.getText().toString();
                String pwd = password.getText().toString();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this,"用户名为空！",Toast.LENGTH_SHORT).show();
                }else if(pwd.equals("")){
                    Toast.makeText(MainActivity.this,"密码为空！",Toast.LENGTH_SHORT).show();
                }else if(!name.equals("") && !pwd.equals("")) {
                    if (name.equals("chenxp38")) {
                        if(pwd.equals("chenxp38")) {
                            Intent intent = new Intent(MainActivity.this, DataViewActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }else{
                            Toast.makeText(MainActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Sorry,您不是管理员！"+ name, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    void findbyId(){
        gotologin = findViewById(R.id.login_in);
        gotohome = findViewById(R.id.login_up);
        gotoDataView=findViewById(R.id.login_manage);
        login_name = findViewById(R.id.name_input);
        password = findViewById(R.id.pwd_input);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        //
        if(requestCode==REQUEST_CODE){
            if(requestCode==RESULT_OK){
                String name = data.getStringExtra("name");
                System.out.println("onActivityResult().name="+name);
            }
        }
    };
}
