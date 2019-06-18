package com.example.a23936.shoppingmall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 23936 on 2019/4/22.
 */

public class CommodityAdapter  extends RecyclerView.Adapter<CommodityAdapter.ViewHodler>{

    private List<Commodity> commodityList;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    Bitmap bitmap = getBitmapImage("https://i1.mifile.cn/a1/pms_1550642182.7527088!220x220.jpg");

    static class ViewHodler extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHodler(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.classifitionrecycler_image_logo);
            textView = itemView.findViewById(R.id.classifitionrecycler_text_name);
        }
    }


    public CommodityAdapter(List<Commodity> commodities){
        this.commodityList = commodities;
    }



    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.classifition_recycler_layout,parent,false);
        ViewHodler holder = new ViewHodler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        Commodity commodity = commodityList.get(position);
        holder.imageView.setImageBitmap(bitmap);
        holder.textView.setText(commodity.getName());

    }

    @Override
    public int getItemCount() {
        return commodityList.size();
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
