package com.example.smartshopping.network;

import com.example.smartshopping.model.ItemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    //@GET("photos")
    @GET("/item_list")
    Call<List<ItemModel>> getItemList(@Query("area") char area,@Query("user_id")String id);

//    @GET("volley_array.json")
//    Call<List<ItemModel>> getItemList();

    @GET("something")
    Call<ItemModel> getItem();
}
