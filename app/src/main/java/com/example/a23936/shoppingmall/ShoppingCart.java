package com.example.a23936.shoppingmall;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 23936 on 2019/4/16.
 */

public class ShoppingCart extends Fragment{
    private RecyclerView recyclerView;
    private List<Phone> phoneList;

    private CartAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.shoppingcart_layout,null);

        initView(view);

        getMessage(Const.URL+"/cart");



        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.shoppingcart_recyler);


        phoneList = new ArrayList<>();


    }


    private void getMessage(final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .addHeader("cookie",getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("cookie",""))
                            .url(path)
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    String data = new JSONObject(responseData).getString("data");
                    Log.e("cart",data);
                    JSONArray items = new JSONObject(data).getJSONArray("items");
                    JSONObject jsonObject = (JSONObject)items.get(0);
                    JSONObject item = jsonObject.getJSONObject("item");
                    String image = item.getString("image");
                    String title = item.getString("title");
                    String sellPoint = item.getString("sellPoint");
                    String price = item.getString("price");

                    Phone phone = new Phone(image,title,sellPoint,price);
                    phoneList.add(phone);

                } catch (IOException e) {
                    e.printStackTrace();
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(manager);

        adapter = new CartAdapter(getContext().getApplicationContext(),phoneList);
        recyclerView.setAdapter(adapter);
    }
}
