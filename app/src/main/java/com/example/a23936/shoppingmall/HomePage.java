package com.example.a23936.shoppingmall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import com.youth.banner.listener.OnBannerListener;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * Created by 23936 on 2019/4/16.
 */

public class HomePage extends Fragment implements OnBannerListener{
    private Banner banner;
    private ArrayList<String> images;
    private EditText editText;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private ImageView image_select1;
    private ImageView image_select2;
    private ImageView image_select3;
    private ImageView image_select4;

    private RecyclerView recyclerView;
    private List<Phone> phoneList = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.homepage_layout,null);

        initView(view);
        init();
        return view;
    }

    private void initView(View view){


        banner = view.findViewById(R.id.homepage_banner);
        editText = view.findViewById(R.id.homepage_edit_find);
        images = new ArrayList<>();

        image_select1 = view.findViewById(R.id.homepage_image_select1);
        image_select2 = view.findViewById(R.id.homepage_image_select2);
        image_select3 = view.findViewById(R.id.homepage_image_select3);
        image_select4 = view.findViewById(R.id.homepage_image_select4);

        getImage("https://i1.mifile.cn/a4/xmad_15523945788008_wGKWT.jpg",image_select1);
        getImage("https://i1.mifile.cn/a4/xmad_15543882240313_PHKGN.jpg",image_select2);
        getImage("https://i1.mifile.cn/a4/xmad_15438026207445_MFOpC.jpg",image_select3);
        getImage("https://i1.mifile.cn/a4/xmad_15553176536198_QfsoW.jpg",image_select4);

        recyclerView = view.findViewById(R.id.homepage_recyclerview);

        //edittext不可编辑
        editText.setFocusable(false);

        images.add("https://i1.mifile.cn/a4/xmad_15554080824205_AMwsQ.jpg");
        images.add("https://i1.mifile.cn/a4/xmad_15532682813162_ihsHZ.jpg");
        images.add("https://i1.mifile.cn/a4/xmad_15531616096346_mMwvn.jpg");

        //设置图片加载器
        banner.setImageLoader(new MyLoader());
        banner.setImages(images);
        //轮播图的监听
        banner .setOnBannerListener(this);
        //开始调用的方法，启动轮播图。
        banner .start();
    }

    //获取网络图片
    public void getImage(String imageUrl,ImageView imageView){
        Glide.with(this).load(imageUrl).into(imageView);
    }

    /**
     * 轮播监听
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position){
        switch (position){
            case 0:
                startActivity(new Intent(getActivity(),GoodsActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(),GoodsActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(),GoodsActivity.class));
                break;
            default:
                break;
        }
    }

    //recyclerview的初始化
    public void init(){
        Bitmap bitmaps = getBitmapImage("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/d729b57d7eadb4d672cfdc7e7b05648e.jpg?w=1212&h=716");


        for (int i=0;i<5;i++){
            Phone phone1 = new Phone(bitmaps,"Redmi 7","4000Ah超长续航","¥699起");
            phoneList.add(phone1);
            Phone phone2 = new Phone(bitmaps,"Redmi Note 7","4800万拍照千元机","¥999起");
            phoneList.add(phone2);
            Phone phone3 = new Phone(bitmaps,"Redmi Note 7 Pro","索尼4800万超清拍照","¥1599");
            phoneList.add(phone3);
            Phone phone4 = new Phone(bitmaps,"小米9 SE","索尼4800万三摄，骁龙712","¥1999起");
            phoneList.add(phone4);
        }
        //创建一个layoutManager，这里使用LinearLayoutManager指定为线性，也就可以有ListView这样的效果
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        //完成layoutManager设置
        recyclerView.setLayoutManager(gridLayoutManager);
        //创建PhoneAdapter的实例同时将iconList传入其构造函数
        PhoneAdapter adapter = new PhoneAdapter(phoneList);
        recyclerView.setAdapter(adapter);

    }
    //Glide加载网络图片(Bitmap)
    private Bitmap getBitmapImage(final String path) {
        final Bitmap[] bitmap = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    //获取网络连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为0.5秒
                    connection.setConnectTimeout(500);
                    //获取返回码
                    if (connection.getResponseCode() == 200){
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        bitmap[0] = BitmapFactory.decodeStream(inputStream);
                        countDownLatch.countDown();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap[0];
    }
}
