package com.example.a23936.shoppingmall;

import android.graphics.Bitmap;

/**
 * Created by 23936 on 2019/4/22.
 */

public class Commodity {
    private Bitmap imageView;
    private String name;

    public Commodity(Bitmap imageView, String name) {
        this.imageView = imageView;
        this.name = name;
    }

    public Bitmap getImageView() {
        return imageView;
    }

    public void setImageView(Bitmap imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
