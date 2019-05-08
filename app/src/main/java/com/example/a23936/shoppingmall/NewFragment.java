package com.example.a23936.shoppingmall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 23936 on 2019/4/21.
 */

public class NewFragment extends Fragment {

    private ImageView imageView;

    private RecyclerView recyclerView;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private List<Commodity> commodityList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.newfragment_layout,null);
        initView(view);
        init();
        return view;
    }

    public void initView(View view){
        imageView = view.findViewById(R.id.newfragment_image_logo);

        recyclerView = view.findViewById(R.id.newfragment_recycler_goods);
        commodityList = new ArrayList<>();
        getImage("https://i1.mifile.cn/a4/xmad_15553254946305_vTBIw.jpg",imageView);

    }

    public void getImage(String url,ImageView imageView){
        Glide.with(this).load(url).into(imageView);
    }

    public void init(){
        Bitmap bitmap = getBitmapImage("https://i1.mifile.cn/a1/pms_1550642182.7527088!220x220.jpg");

        for (int i=0;i<3;i++){
            Commodity commodity1 = new Commodity(bitmap,"小米9");
            commodityList.add(commodity1);

            Commodity commodity2 = new Commodity(bitmap,"小米9王源定制版");
            commodityList.add(commodity2);

            Commodity commodity3 = new Commodity(bitmap,"小米9 SE");
            commodityList.add(commodity3);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        CommodityAdapter adapter = new CommodityAdapter(commodityList);
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
