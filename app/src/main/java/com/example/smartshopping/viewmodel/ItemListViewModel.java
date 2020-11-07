package com.example.smartshopping.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartshopping.model.ItemModel;
import com.example.smartshopping.network.APIService;
import com.example.smartshopping.network.RetroInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListViewModel extends ViewModel {

    //can update recyclerview
    private MutableLiveData<List<ItemModel>> itemsList;

    public ItemListViewModel(){
        itemsList = new MutableLiveData<>();
    }

    public MutableLiveData<List<ItemModel>> getItemsListObserver(){
        return itemsList;
    }

    public void makeApiCall(){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<List<ItemModel>> call = apiService.getItemList();
        call.enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                itemsList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ItemModel>> call, Throwable t) {
                itemsList.postValue(null);
            }
        });
    }
}
