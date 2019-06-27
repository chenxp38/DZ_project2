package com.example.a11605.dz_project2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meeting_Room_Activity extends AppCompatActivity {
    private static final String TABLE_NAME = "MeetingRoom";//数据库表的名字
    public List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    public SimpleAdapter adapter;
    public ListView LV;
    public Button goto_return;
    String username1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingroom);
        findbyid();
        Intent CurrentIntent = getIntent();//获取MainActivity.java传过来的值
        username1 = CurrentIntent.getStringExtra("username1");

        goto_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Meeting_Room_Activity.this, Meeting_add_Activity.class);
                intent.putExtra("username1",username1);//把用户名传到下一页面
                startActivity(intent);
            }
        });
        try {
            Meeting_Room_DB_Activity db = new Meeting_Room_DB_Activity(getBaseContext());
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                    + TABLE_NAME, null);
            datas  = new ArrayList<Map<String, String>>();
            if (cursor == null) {
            } else {
                while (cursor.moveToNext()) {
                    String title_s = cursor.getString(0);
                    String name_s = cursor.getString(1);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("meeting_room", "1");
                    map.put("title_m", title_s);
                    map.put("name_m", name_s);
                    datas .add(map);
                }
                adapter = new SimpleAdapter(Meeting_Room_Activity.this, datas, R.layout.meeting_item,
                        new String[]{"meeting_room", "title_m", "name_m"},
                        new int[]{R.id.meeting_room, R.id.title_TV, R.id.username_TV});//item
                LV.setAdapter(adapter);
            }
        } catch (SQLException e) {

        }
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //由position获取会议名，然后从数据库里面调出时间表,其实当初列表存值的时候就可以顺便存了，现在写到这里懒得改了
                String title_click = datas.get(position).get("title_m").toString();
                Meeting_Room_DB_Activity db = new Meeting_Room_DB_Activity(getBaseContext());
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " +
                        TABLE_NAME + " where title like ?", new String[]{title_click});
                if (cursor.moveToFirst() == true) {
                    //
                    SQLiteDatabase db1 = openOrCreateDatabase("MeetingRoom.db", MODE_PRIVATE, null);
                    db1.execSQL("CREATE TABLE IF NOT EXISTS password (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "title NVARCHAR, " +
                            "username NVARCHAR, " +
                            "participant NVARCHAR, " +
                            "start_time NVARCHAR, " +
                            "ending_time NVARCHAR)");
                    Cursor cursor1 = db1.query(TABLE_NAME, new String[]{"title, username, participant, start_time, ending_time"}, "title=?", new String[]{title_click}, null, null, null);
                    cursor1.moveToFirst();                  //上条语句应该查询的是数据库中用户名所在的行，并提取密码等数据
                    //String receive_name = cursor.getString(cursor.getColumnIndex("username")).toString();
                    String receive_start_time = cursor1.getString(cursor1.getColumnIndex("start_time")).toString();//获取该用户名对应的用户密码
                    String receive_ending_time = cursor1.getString(cursor1.getColumnIndex("ending_time")).toString();//获取该用户名对应的用户密码
                    //单击时弹出会议时间
                    AlertDialog alertDialog2 = new AlertDialog.Builder(Meeting_Room_Activity.this)
                            .setTitle("会议时间")
                            .setMessage("开始时间：" + receive_start_time + "\n结束时间：" + receive_ending_time)
                            .setIcon(R.drawable.time)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).create();
                    alertDialog2.show();
                }
            }
        });
        // listview的长按事件，弹出对话框，是否删除联系人
        LV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder message = new AlertDialog.Builder(Meeting_Room_Activity.this);
                message.setTitle("删除会议");
                message.setMessage("确定删除会议");
                message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 在数据库中进行delete数据
                        Meeting_Room_DB_Activity db = new Meeting_Room_DB_Activity(getBaseContext());
                        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                        sqLiteDatabase.execSQL("delete from " + "MeetingRoom"
                                + " where title = ?", new String[]{datas.get(position).get("title_m")});
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


    void findbyid(){
        LV = (ListView) findViewById(R.id.meeting_display);
        goto_return = findViewById(R.id.re_last);
    }
}
