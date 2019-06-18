package com.example.a23936.shoppingmall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 23936 on 2019/4/19.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder>{
    private List<Phone> mPhoneList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView phone_image;
        TextView phone_name;
        TextView phone_text;
        TextView phone_price;

        public ViewHolder(View itemView) {
            super(itemView);
            phone_image = itemView.findViewById(R.id.homepage_recyclerview_image);
            phone_name = itemView.findViewById(R.id.homepage_recyclerview_textname);
            phone_text = itemView.findViewById(R.id.homepage_recyclerview_text);
            phone_price = itemView.findViewById(R.id.homepage_recyclerview_textprice);
        }
    }
    public PhoneAdapter(Context mContext,List<Phone> phoneList){
        this.mContext = mContext;
        mPhoneList = phoneList;
    }

    /**
     * 这里的onCreateViewHolder()是用来创建ViewHolder实例，再将加载好的布局传入构造函数，最后返回ViewHolder的实例。
     *
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homerecycler_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        if (mOnItemClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v);
                }
            });
        }
        return holder;
    }


    /**
     * 用于对RecyclerView的子项进行赋值，会在每个子项滚动到屏幕内的时候执行。
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phone phone = mPhoneList.get(position);
        Glide.with(mContext).load(phone.getImageView()).into(holder.phone_image);
        holder.phone_name.setText(phone.getText_name());
        holder.phone_text.setText(phone.getText());
        holder.phone_price.setText(phone.getText_price());
        holder.itemView.setTag(position);
    }

    //就是返回RecyclerView有多少个子项
    @Override
    public int getItemCount() {
        return mPhoneList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }


}
