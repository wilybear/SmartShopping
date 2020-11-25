package com.example.smartshopping.model;

import com.google.gson.annotations.SerializedName;

public class ItemModel {

    @SerializedName(value = "product_name")
    private String productName;

    //@SerializedName(value = "thumbnailUrl")

    @SerializedName(value = "image_addr")
    private String imageUrl;

    @SerializedName(value = "price")
    private int price;

    //private int price;

    public ItemModel(String title, String imageUrl, int price) {
        this.productName = title;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
