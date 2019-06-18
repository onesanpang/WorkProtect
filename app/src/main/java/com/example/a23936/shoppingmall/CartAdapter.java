package com.example.a23936.shoppingmall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by onesanpang on 19-6-14.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private List<Phone> phoneList;

    public CartAdapter(Context mContext,List<Phone> phoneList){
        this.mContext = mContext;
        this.phoneList = phoneList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textname;
        TextView textguige;
        TextView textprice;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartcycler_image_pic);
            textname = itemView.findViewById(R.id.cartcycler_text_name);
            textguige = itemView.findViewById(R.id.cartcycler_text_guige);
            textprice = itemView.findViewById(R.id.cartcycler_text_price);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartrecycler_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Phone phone = phoneList.get(position);
        Glide.with(mContext).load(phone.getImageView()).into(holder.imageView);
        holder.textname.setText(phone.getText_name());
        holder.textguige.setText(phone.getText());
        holder.textprice.setText(phone.getText_price());
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }
}
