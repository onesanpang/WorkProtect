package com.example.a23936.shoppingmall;


import android.graphics.Bitmap;

/**
 * Created by 23936 on 2019/4/19.
 */

public class Phone {
    private Bitmap imageView;
    private String text_name;
    private String text;
    private String text_price;

    public Phone(Bitmap imageView, String text_name, String text, String text_price){
        this.imageView = imageView;
        this.text_name = text_name;
        this.text = text;
        this.text_price = text_price;
    }


    public Bitmap getImageView() {
        return imageView;
    }

    public String getText_name() {
        return text_name;
    }

    public String getText() {
        return text;
    }

    public String getText_price() {
        return text_price;
    }
}
