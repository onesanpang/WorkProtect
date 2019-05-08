package com.example.a23936.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by 23936 on 2019/4/16.
 */

public class Own extends Fragment implements View.OnClickListener{

    private LinearLayout linear_order;
    private LinearLayout linear_address;
    private LinearLayout linear_about;
    private LinearLayout linear_collection;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.own_layout,null);
        initView(view);
        return view;
    }
    private void initView(View view){
        linear_order = view.findViewById(R.id.own_linear_order);
        linear_address = view.findViewById(R.id.own_linear_address);
        linear_about = view.findViewById(R.id.own_linear_about);
        linear_collection = view.findViewById(R.id.own_linear_shoucnag);

        linear_order.setOnClickListener(this);
        linear_address.setOnClickListener(this);
        linear_about.setOnClickListener(this);
        linear_collection.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.own_linear_order:
                startActivity(new Intent(getActivity(),OrderActivity.class));
                break;
            case R.id.own_linear_address:
                startActivity(new Intent(getActivity(),AddresActivity.class));
                break;
            case R.id.own_linear_about:
                startActivity(new Intent(getActivity(),AboutActivity.class));
                break;
            case R.id.own_linear_shoucnag:
                startActivity(new Intent(getActivity(),CollectionActivity.class));
                break;
            case R.id.own_linear_setting:
                break;
            default:
                break;
        }
    }
}
