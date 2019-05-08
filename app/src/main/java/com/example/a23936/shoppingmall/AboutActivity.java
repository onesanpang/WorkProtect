package com.example.a23936.shoppingmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

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
        imageView = findViewById(R.id.about_image_back);
        textView = findViewById(R.id.about_text_text);

        imageView.setOnClickListener(this);

        String text = "\t\t您在注册成为大米商城平台用户，并接受大米商城平台服务时，您应该向大米提供真实有效的联系方式（包括您的电子邮件地址、联系电话、联系地址等），对于联系方式发生变更的，您有义务及时更新有关信息，并保持可被联系的状态。\t\t大米向您浏览界面推送的弹窗等通知形式，也作为向您发出的有效通知。\n\n" +
                "大米将向您的上述联系方式的其中之一或其中若干向您送达各类通知，而此类通知的内容可能对您的权利义务产生重大的有利或不利影响，请您务必及时关注。\n\n" +
                "\t\t您应当保证所提供的联系方式是准确、有效的，并进行实时更新。如果因提供的联系方式不确切，或不及时告知变更后的联系方式，使通知无法送达或未及时送达，由您自行承担由此可能产生的法律后果。";
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.about_image_back:
                finish();
                break;
            default:
                break;
        }
    }
}
