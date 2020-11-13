package com.example.smartshopping.network;

import com.example.smartshopping.model.ItemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    //@GET("photos")
    @GET("/lists")
    Call<List<ItemModel>> getItemList(@Query("gender")String gender, @Query("birthday")String birthday,@Query("area") char area);

    @GET("volley_array.json")
    Call<List<ItemModel>> getItemList();



    @GET("something")
    Call<ItemModel> getItem();
}
