package com.diya.apps.khatuni.Utils;

import android.graphics.Bitmap;



public class Item {
    Bitmap image;
    String title;
    String applink;

    public Item(Bitmap image, String title, String applink) {
        super();
        this.image = image;
        this.title = title;
        this.applink = applink;
    }



    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getApplink() {
        return applink;
    }
    public void setApplink(String applink) {
        this.applink = applink;
    }
}
