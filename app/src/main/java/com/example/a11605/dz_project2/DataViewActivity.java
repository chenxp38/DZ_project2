package com.example.a11605.dz_project2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataViewActivity extends AppCompatActivity {
        // 初始化控件
        public Button add;
        public Button clear;
        public Button quit;

        public ListView LV;
        public SimpleAdapter adapter;

        public TextView nameTV;
        public EditText birthET;
        public EditText giftET;
        public TextView phone;
        private static final String TABLE_NAME = "Info";//数据库表的名字
        public List<Map<String, String>> datas = new ArrayList<Map<String, String>>();

        public void deleteDb(){
            //删除数据库
            MyDataBase db = new MyDataBase(getBaseContext());
            db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        }
        // 将保存在数据库中的数据更新到UI中的函数
        private void dataUpdate() {
            try {
                MyDataBase db = new MyDataBase(getBaseContext());
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                        + TABLE_NAME, null);
                datas  = new ArrayList<Map<String, String>>();
                if (cursor == null) {
                } else {
                    while (cursor.moveToNext()) {
                        String name1 = cursor.getString(0);
                        String birth1 = cursor.getString(1);
                        String gift1 = cursor.getString(2);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name", name1);
                        map.put("birth", birth1);
                        map.put("gift", gift1);
                        datas .add(map);
                    }
                    adapter = new SimpleAdapter(DataViewActivity.this, datas, R.layout.item,
                            new String[]{"name", "birth", "gift"},
                            new int[]{R.id.nameLV, R.id.birthLV, R.id.giftLV});
                    LV.setAdapter(adapter);
                }
            } catch (SQLException e) {

            }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dataview);
            LV = (ListView) findViewById(R.id.Start);

            //deleteDb();因为属性是一条一条加的，数据库要重新改变，测试用，否则不要删除表，删除了只能重新安装软件
            // 在刚打开时将保存在数据库中的数据更新到UI中
            dataUpdate();

            // listview的单击事件，单击以进入增加条目的编辑界面
            LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // 自定义对话框的实现——使用 LayoutInflater 类
                    LayoutInflater factory = LayoutInflater.from(DataViewActivity.this);
                    View newView = factory.inflate(R.layout.dialoglayout, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(DataViewActivity.this);

                    nameTV = (TextView) newView.findViewById(R.id.nameXG1);
                    birthET = (EditText) newView.findViewById(R.id.birthXG1);
                    giftET = (EditText) newView.findViewById(R.id.giftXG1);
                    phone = (TextView) newView.findViewById(R.id.phone);

                    nameTV.setText(datas.get(position).get("name"));
                    birthET.setText(datas.get(position).get("birth"));
                    giftET.setText(datas.get(position).get("gift"));

                    // 通过询问同学，在读取通信录之前进行api23的动态权限申请
                    if (ContextCompat.checkSelfPermission(DataViewActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DataViewActivity.this, new String[]{Manifest.permission.READ_CONTACTS},0);
                    }

                    // 使用getContentResolver方法读取联系人列表
                    String str1 = "";
                    Cursor cursor1 = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                            null, null, null, null);
                    str1 = "";
                    while (cursor1.moveToNext()) {
                        String str2 = cursor1.getString(cursor1.getColumnIndex("_id"));
                        if (cursor1.getString(cursor1.getColumnIndex("display_name"))
                                .equals(nameTV.getText().toString())) {
                            // 判断某条联系人的信息中，是否有电话号码
                            if (Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("has_phone_number"))) > 0) {
                                // 取出该条联系人信息中的电话号码
                                Cursor cursor2 = getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        "contact_id = " + str2,
                                        null,
                                        null
                                );
                                while (cursor2.moveToNext()) {
                                    str1 = str1 + cursor2.getString(cursor2.getColumnIndex("data1")) + "\n";
                                }
                                cursor2.close();
                            }
                        }
                    }
                    cursor1.close();
                    // 如果手机通讯录中没有对应的联系人则将手机设为无
                    if (str1.equals("")) str1 = "无";
                    phone.setText(str1);

                    // 自定义对话框的实现
                    builder.setView(newView);
                    builder.setTitle("（*-.-*）主人想修改哪些条目呢~");
                    builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (birthET.length() != 0) {
                                MyDataBase db = new MyDataBase(getBaseContext());
                                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                                sqLiteDatabase.execSQL("update " + TABLE_NAME +
                                        " set birth = ? where name = ?", new Object[]{
                                        birthET.getText().toString(), nameTV.getText().toString()});
                                sqLiteDatabase.close();
                            }
                            if (giftET.length() != 0) {
                                MyDataBase db = new MyDataBase(getBaseContext());
                                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                                sqLiteDatabase.execSQL("update " + TABLE_NAME +
                                        " set gift = ? where name = ?", new Object[]{
                                        giftET.getText().toString(), nameTV.getText().toString()});
                                sqLiteDatabase.close();
                            }
                            dataUpdate();
                        }
                    });
                    builder.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            });

            add = (Button) findViewById(R.id.add);
            quit = (Button) findViewById(R.id.quit);

            // 增加条目的按钮事件（跳转到编辑界面）
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DataViewActivity.this, AddActivity.class);
                    startActivityForResult(intent, 9);
                }
            });

            // 退出的按钮事件
            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DataViewActivity.this.finish();
                    } catch (Exception e) {

                    }
                }
            });

            // listview的长按事件，弹出对话框，是否删除联系人
            LV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder message = new AlertDialog.Builder(DataViewActivity.this);
                    message.setTitle("删除联系人");
                    message.setMessage("确定删除联系人");
                    message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 在数据库中进行delete数据
                            MyDataBase db = new MyDataBase(getBaseContext());
                            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                            sqLiteDatabase.execSQL("delete from " + TABLE_NAME
                                    + " where name = ?", new String[]{datas.get(position).get("name")});
                            sqLiteDatabase.close();
                            // 删除listview中的对应数据
                            datas.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    message.create().show();
                    return true;
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 9 && resultCode == 99) {
                dataUpdate();
            }
        }
    }
