package com.example.a23936.shoppingmall;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener, CityPickerListener {

    private ImageView image_back;
    private TextView text_add;

    private EditText edit_name;
    private EditText edit_number;
    private TextView text_address1;
    private EditText edit_address2;

    private CityPicker cityPicker;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        //去除标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();

    }


    private void initView(){

        image_back = findViewById(R.id.addaddres_image_back);
        text_add = findViewById(R.id.addaddress_text_add);
        edit_name = findViewById(R.id.addaddress_edit_name);
        edit_number = findViewById(R.id.addaddress_edit_number);
        text_address1 = findViewById(R.id.addaddress_text_address1);
        edit_address2 = findViewById(R.id.addaddress_edit_address2);

        cityPicker = new CityPicker(AddAddressActivity.this,this);

        image_back.setOnClickListener(this);
        text_add.setOnClickListener(this);
        text_address1.setOnClickListener(this);

        //每个程序都要自己数据库，默认情况下各自互不干扰
        //创建一个数据库，并且打开；
        //通过下面这个方法，创建，如果存在就打开，不存在就创建并打开
        db = openOrCreateDatabase("data.db",MODE_PRIVATE,null);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addaddres_image_back:
                finish();
                break;
            case R.id.addaddress_text_add:
                insertData();
                break;
            case R.id.addaddress_text_address1:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_number.getWindowToken(),0);
                cityPicker.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void getCity(String s) {
        text_address1.setText(s);
    }
    public void onBackPressed(){
        if (cityPicker.isShow()){
            cityPicker.close();
            return;
        }
        super.onBackPressed();
    }

    private void insertData(){
        String name = edit_name.getText().toString();
        String number = edit_number.getText().toString();
        String address1 = text_address1.getText().toString();
        String address2 = edit_address2.getText().toString();


        if (name.isEmpty() || number.isEmpty()
                ||address1.isEmpty()||address2.isEmpty()){
            Toast.makeText(this, "请输入完整数据", Toast.LENGTH_SHORT).show();
        }else{
            String sql = "insert into address(name,number,address1,address2) values('"+name+"','"+number+"','"+address1+"','"+address2+"')";
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            db.execSQL(sql);
            startActivity(new Intent(this,AddresActivity.class));
            finish();
        }

    }
}
