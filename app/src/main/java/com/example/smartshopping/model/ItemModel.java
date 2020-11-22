package com.example.smartshopping.model;

import com.google.gson.annotations.SerializedName;

public class ItemModel {

    @SerializedName(value = "title")
    private String title;

    //@SerializedName(value = "thumbnailUrl")

    @SerializedName(value = "image")
    private String imageUrl;
    private int d;
    //private int price;

    public ItemModel(String title, String imageUrl, int no) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.d = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
