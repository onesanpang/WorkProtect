package com.example.a23936.shoppingmall;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static boolean isExit = false;//标识是否退出

    private Fragment homeFragment = new HomePage();
    private Fragment classFragment = new Classification();
    private Fragment shoppingFragment = new ShoppingCart();
    private Fragment ownFragment = new Own();

    private ImageView homeImage;
    private ImageView classImage;
    private ImageView shoppingImage;
    private ImageView ownImage;

    private TextView homeText;
    private TextView classText;
    private TextView shoppingText;
    private TextView ownText;

    private LinearLayout homeLinear;
    private LinearLayout classLinear;
    private LinearLayout shoppingLinear;
    private LinearLayout ownLinear;


    private static Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            isExit = false;
        }
    };


    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去除标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        // 初始化组件
        initView();

        // 初始化所有fragment
        initFragment();

    }


    // 初始化组件
    public void initView(){
        homeLinear = findViewById(R.id.main_linear_homepage);
        classLinear = findViewById(R.id.main_linear_classification);
        shoppingLinear = findViewById(R.id.main_linear_shoppingcart);
        ownLinear = findViewById(R.id.main_linear_own);

        homeImage = findViewById(R.id.main_image_homepage);
        classImage = findViewById(R.id.main_image_classifition);
        shoppingImage = findViewById(R.id.main_image_shoppingcart);
        ownImage = findViewById(R.id.main_image_own);

        homeText = findViewById(R.id.main_text_homepage);
        classText = findViewById(R.id.main_text_classifition);
        shoppingText = findViewById(R.id.main_text_shoppingcart);
        ownText = findViewById(R.id.main_text_own);

        homeLinear.setOnClickListener(this);
        classLinear.setOnClickListener(this);
        shoppingLinear.setOnClickListener(this);
        ownLinear.setOnClickListener(this);
    }

    // 初始化所有fragment
    public void initFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (!homeFragment.isAdded()){
            fragmentTransaction.add(R.id.mian_fragment,homeFragment);
            fragmentTransaction.hide(homeFragment);
        }
        if (!classFragment.isAdded()){
            fragmentTransaction.add(R.id.mian_fragment,classFragment);
            fragmentTransaction.hide(classFragment);
        }
        if (!shoppingFragment.isAdded()){
            fragmentTransaction.add(R.id.mian_fragment,shoppingFragment);
            fragmentTransaction.hide(shoppingFragment);
        }
        if (!ownFragment.isAdded()){
            fragmentTransaction.add(R.id.mian_fragment,ownFragment);
            fragmentTransaction.hide(ownFragment);
        }
        hideAllFragment(fragmentTransaction);

        // 默认显示第一个fragment
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();
    }

    // 隐藏所有fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.hide(classFragment);
        fragmentTransaction.hide(shoppingFragment);
        fragmentTransaction.hide(ownFragment);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_linear_homepage:
                clickTab(homeFragment);
                break;
            case R.id.main_linear_classification:
                clickTab(classFragment);
                break;
            case R.id.main_linear_shoppingcart:
                clickTab(shoppingFragment);
                break;
            case R.id.main_linear_own:
                clickTab(ownFragment);
                break;
            default:
                break;
        }
    }

    //点击下面的Tab按钮
    private void clickTab(Fragment tabFragment){
        // 清除上次选中状态
        clearSelect();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 隐藏所有fragment
        hideAllFragment(transaction);
        // 显示该Fragment
        transaction.show(tabFragment);
        // 提交事务
        transaction.commit();
        // 改变tab的样式,设置为选中状态
        changeTabStyle(tabFragment);
    }

    //清除上次选中状态
    private void clearSelect(){
        if (!homeFragment.isHidden()){
            homeImage.setImageResource(R.mipmap.homepageone);
            homeText.setTextColor(Color.parseColor("#bfbfbf"));
        }
        if (!classFragment.isHidden()){
            classImage.setImageResource(R.mipmap.classone);
            classText.setTextColor(Color.parseColor("#bfbfbf"));
        }
        if (!shoppingFragment.isHidden()){
            shoppingImage.setImageResource(R.mipmap.shoppingone);
            shoppingText.setTextColor(Color.parseColor("#bfbfbf"));
        }
        if (!ownFragment.isHidden()){
            ownImage.setImageResource(R.mipmap.ownone);
            ownText.setTextColor(Color.parseColor("#bfbfbf"));
        }

    }

    //根据Fragment的状态改变样式
    private void changeTabStyle(Fragment tabFragment){
        if (tabFragment instanceof HomePage){
            homeImage.setImageResource(R.mipmap.homepagetwo);
            homeText.setTextColor(Color.parseColor("#0da0f2"));
        }
        if (tabFragment instanceof Classification){
            classImage.setImageResource(R.mipmap.classtwo);
            classText.setTextColor(Color.parseColor("#0da0f2"));
        }
        if (tabFragment instanceof ShoppingCart){
            shoppingImage.setImageResource(R.mipmap.shoppingtwo);
            shoppingText.setTextColor(Color.parseColor("#0da0f2"));
        }
        if (tabFragment instanceof Own){
            ownImage.setImageResource(R.mipmap.onwtwo);
            ownText.setTextColor(Color.parseColor("#0da0f2"));
        }
    }


    @Override
    public void onBackPressed(){
        exit();
    }

    private void exit(){
        if (!isExit){
            isExit = true;
            Toast.makeText(this, "再按一次后退出程序", Toast.LENGTH_SHORT).show();
            mhandler.sendEmptyMessageDelayed(0,2000);
        }else{
            this.finish();
        }
    }
}
