package com.example.a23936.shoppingmall;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by 23936 on 2019/4/20.
 */

public class RewritePopwindow extends PopupWindow {
    private View view;

    public RewritePopwindow(Activity context,View.OnClickListener itemsOnClick){
        super(context);
        initView(context,itemsOnClick);
    }

    private void initView(final Activity context, View.OnClickListener itemsOnClick){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popupwindow_layout,null);
        LinearLayout gb1 = view.findViewById(R.id.popupWindow_linear_one);
        LinearLayout gb2 = view.findViewById(R.id.popupwindow_linear_two);

        TextView text1 = view.findViewById(R.id.popupwindow_text_color1);
        TextView text2 = view.findViewById(R.id.popupwindow_text_color2);
        TextView text3 = view.findViewById(R.id.popupwindow_text_color3);
        Button button = view.findViewById(R.id.popupWindow_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                backgroundAlpha(context,1f);
            }
        });

        gb1.setOnClickListener(itemsOnClick);
        gb2.setOnClickListener(itemsOnClick);
        text1.setOnClickListener(itemsOnClick);
        text2.setOnClickListener(itemsOnClick);
        text3.setOnClickListener(itemsOnClick);


        this.setContentView(view);
        this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context,0.5f);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context,1f);
            }
        });
    }

    public void backgroundAlpha(Activity context,float bgAlpha){
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
