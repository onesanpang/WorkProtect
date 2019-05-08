package com.example.a23936.shoppingmall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 23936 on 2019/4/19.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder>{
    private List<Phone> mPhoneList;



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
    public PhoneAdapter(List<Phone> phoneList){
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
        return holder;
    }


    /**
     * 用于对RecyclerView的子项进行赋值，会在每个子项滚动到屏幕内的时候执行。
     *
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phone phone = mPhoneList.get(position);
        holder.phone_image.setImageBitmap(phone.getImageView());
        holder.phone_name.setText(phone.getText_name());
        holder.phone_text.setText(phone.getText());
        holder.phone_price.setText(phone.getText_price());
    }

    //就是返回RecyclerView有多少个子项
    @Override
    public int getItemCount() {
        return mPhoneList.size();
    }
}
