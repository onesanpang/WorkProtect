package com.example.a23936.shoppingmall;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegriesterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView image_back;
    private EditText edit_username;
    private EditText edit_number;
    private EditText edit_email;
    private EditText edit_password;
    private Button but_regriester;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regriester);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();
    }

    private void initView(){
        image_back = findViewById(R.id.regriester_image_back);
        edit_username = findViewById(R.id.regriester_edit_username);
        edit_number = findViewById(R.id.regriester_edit_number);
        edit_email = findViewById(R.id.regriester_edit_email);
        edit_password = findViewById(R.id.regriester_edit_passward);
        but_regriester = findViewById(R.id.regriester_but_zhuce);

        image_back.setOnClickListener(this);
        but_regriester.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.regriester_image_back:
                finish();
                break;
            case R.id.regriester_but_zhuce:
                regirester(Const.URL+"/user/register");
                break;
            default:break;
        }
    }

    private void regirester(final String path){
        final String username = edit_username.getText().toString();
        final String number = edit_number.getText().toString();
        final String email = edit_email.getText().toString();
        final String password = edit_password.getText().toString();
        if (username.isEmpty() || number.isEmpty()|| email.isEmpty()||password.isEmpty()){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegriesterActivity.this, "请输入正确注册信息", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        OkHttpClient client = new OkHttpClient();

                        FormBody formBody = new FormBody.Builder()
                                .add("username",username)
                                .add("phone",number)
                                .add("email",email)
                                .add("password",password)
                                .build();
                        Request request = new Request.Builder()
                                .url(path)
                                .post(formBody)
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();

                        JSONObject jsonObject = new JSONObject(responseData);
                        String header = jsonObject.getString("header");

                        JSONObject jsonObject1 = new JSONObject(header);
                        final String msg = jsonObject1.getString("msg");
                        if (response.code() == 200){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegriesterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(RegriesterActivity.this,LoginActivity.class));

                            finish();
                        }else{
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegriesterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    edit_username.setText("");
                                    edit_number.setText("");
                                    edit_email.setText("");
                                    edit_password.setText("");
                                }
                            });
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }
}
