package com.example.smartshopping.network;

import com.example.smartshopping.model.ItemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    //@GET("photos")
    @GET("volley_array.json")
    Call<List<ItemModel>> getItemList();
    Call<ItemModel> getItem();
}
