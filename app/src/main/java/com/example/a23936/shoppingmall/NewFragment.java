package com.example.a23936.shoppingmall;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private List<Commodity> commodityList;

    private SQLiteDatabase db;

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

//        for (int i=0;i<3;i++){
//            Commodity commodity1 = new Commodity("小米9");
//            commodityList.add(commodity1);
//
//            Commodity commodity2 = new Commodity("小米9王源定制版");
//            commodityList.add(commodity2);
//
//            Commodity commodity3 = new Commodity("小米9 SE");
//            commodityList.add(commodity3);
//        }

        String DATA_BASE_PATH = getContext().getApplicationInfo().dataDir+"/databases/data.db";
        db = SQLiteDatabase.openOrCreateDatabase(DATA_BASE_PATH,null);

        Cursor c = db.rawQuery("select * from phoneq",null);
        if (c!=null){
            while(c.moveToNext()){
               if (c.getString(0).equals("10")){
                   break;
               }else{
                   Commodity commodity = new Commodity(c.getString(1));
                   commodityList.add(commodity);
               }
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        CommodityAdapter adapter = new CommodityAdapter(commodityList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
