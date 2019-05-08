package com.example.a23936.shoppingmall;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddresActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView image_back;
    private ImageView image_add;
    private SimpleAdapter adapter;

    private ListView listView;
    private SQLiteDatabase db;
    private List<Map<String,String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addres);

        //去除标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();
        remove();
    }

    private void initView(){
        image_back = findViewById(R.id.addres_image_back);
        image_add = findViewById(R.id.address_image_add);
        listView = findViewById(R.id.address_listview);

        image_back.setOnClickListener(this);
        image_add.setOnClickListener(this);

         //每个程序都要自己数据库，默认情况下各自互不干扰
        //创建一个数据库，并且打开；
        //通过下面这个方法，创建，如果存在就打开，不存在就创建并打开
        db = openOrCreateDatabase("data.db",MODE_PRIVATE,null);
        list = new ArrayList<Map<String,String>>();

        adapter = new SimpleAdapter(this,getData(),R.layout.addresslist_layout,new String[]{"name","number","address"},new int[]{R.id.addresslist_text_name,R.id.addresslist_text_number,R.id.addresslist_text_address});
        listView.setAdapter(adapter);

    }



    public List<Map<String,String>> getData(){
        if (isTableExist("address")) {
            Cursor c = db.rawQuery("select * from address", null);
            if (c != null) {
                while (c.moveToNext()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", c.getString(1));
                    map.put("number", c.getString(2));
                    map.put("address", c.getString(3)+c.getString(4));
                    list.add(map);
                }
            }
            c.close();

        }
        return list;
    }



    //判断table是否存在
    public boolean isTableExist(String table){
        Cursor c = db.rawQuery("select count(*) from sqlite_master where type='table' and name='"+table+"'",null);
        if (c!=null){
            while(c.moveToNext()){
                int count = c.getInt(0);
                if (count > 0){
                    c.close();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addres_image_back:
                finish();
                break;
            case R.id.address_image_add:
                startActivity(new Intent(AddresActivity.this,AddAddressActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    public void remove() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Map<String,String> map1 = new HashMap<>();
                map1 = list.get(position);
                Toast.makeText(AddresActivity.this, map1.get("name")+position, Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,  int position, long id) {
                listViewLongClick(position);
                return false;
            }
        });
    }

    public void listViewLongClick(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddresActivity.this);
        builder.setTitle("是否删除此地址?");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String,String> map = new HashMap<>();
                map = list.get(position);
                Log.e("name",map.get("name"));
                String s = new String("delete from address where name = '"+map.get("name")+"'");
                db.execSQL(s);
                startActivity(new Intent(AddresActivity.this,AddresActivity.class));
                finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    protected void onStop(){
        super.onStop();
        db.close();
    }
}
