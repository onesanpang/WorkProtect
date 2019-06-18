package com.example.a23936.shoppingmall;


import android.graphics.Bitmap;

/**
 * Created by 23936 on 2019/4/19.
 */

public class Phone {
    private String imageView;
    private String text_name;
    private String text;
    private String text_price;
    private String image;
    private String goodsId;
    private String nums;

    public Phone(String imageView, String text_name, String text, String text_price){
        this.imageView = imageView;
        this.text_name = text_name;
        this.text = text;
        this.text_price = text_price;
    }
    public Phone( String text_name, String text, String text_price){
        this.text_name = text_name;
        this.text = text;
        this.text_price = text_price;
    }


    public String getImageView() {
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

    public String getImage() {
        return image;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getNums() {
        return nums;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }
}
