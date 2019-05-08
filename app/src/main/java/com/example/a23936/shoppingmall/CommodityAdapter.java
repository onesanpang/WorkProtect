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
 * Created by 23936 on 2019/4/22.
 */

public class CommodityAdapter  extends RecyclerView.Adapter<CommodityAdapter.ViewHodler>{

    private List<Commodity> commodityList;

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
        holder.imageView.setImageBitmap(commodity.getImageView());
        holder.textView.setText(commodity.getName());

    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }


}
