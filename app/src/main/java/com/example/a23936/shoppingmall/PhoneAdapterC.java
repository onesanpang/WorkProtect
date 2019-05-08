package com.example.a23936.shoppingmall;

import android.graphics.Bitmap;
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

public class PhoneAdapterC extends RecyclerView.Adapter<PhoneAdapterC.ViewHolder>{

    private List<Phone> mphoneList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener  onitemLongClickListener;


    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView phoneImage;
        TextView phone_name;
        TextView phone_text;
        TextView phone_price;


        public ViewHolder(View itemView) {
            super(itemView);
            phoneImage = itemView.findViewById(R.id.collection_recyclerview_image);
            phone_name = itemView.findViewById(R.id.collection_recyclerview_textname);
            phone_text = itemView.findViewById(R.id.collection_recyclerview_text);
            phone_price = itemView.findViewById(R.id.collection_recyclerview_textprice);


        }
    }


    public PhoneAdapterC(List<Phone> phoneList){
        mphoneList = phoneList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Phone phone = mphoneList.get(position);
        holder.phoneImage.setImageBitmap(phone.getImageView());
        holder.phone_name.setText(phone.getText_name());
        holder.phone_text.setText(phone.getText());
        holder.phone_price.setText(phone.getText_price());




    }

    @Override
    public int getItemCount() {
        return mphoneList.size();
    }

    public void removeData(int position){
        mphoneList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener{
        void onClick(int position);

    }
    public interface OnItemLongClickListener{
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnitemLongClickListener(OnItemLongClickListener onitemLongClickListener){
        this.onitemLongClickListener = onitemLongClickListener;
    }

}
