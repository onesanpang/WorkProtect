package com.example.a23936.shoppingmall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by 23936 on 2019/4/16.
 */

public class ShoppingCart extends Fragment{
    private TextView text_price;
    private TextView text_sum;
    private CheckBox checkbox;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.shoppingcart_layout,null);

        initView(view);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()){
                    text_sum.setText(text_price.getText());
                }else{
                    text_sum.setText("");
                }
            }
        });
        return view;
    }

    private void initView(View view){
        text_sum  = view.findViewById(R.id.shoppingcart_text_sum);
        text_price = view.findViewById(R.id.shoppingcart_text_price);
        checkbox = view.findViewById(R.id.shoppingcart_checkbox);
    }
}
