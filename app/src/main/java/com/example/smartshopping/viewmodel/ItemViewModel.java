package com.example.smartshopping.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.smartshopping.model.AppRepository;
import com.example.smartshopping.model.AreaModel;
import com.example.smartshopping.model.ItemModel;
import com.example.smartshopping.model.UserModel;
import com.example.smartshopping.network.APIService;
import com.example.smartshopping.network.RetroInstance;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemViewModel extends AndroidViewModel {

    private MutableLiveData<ItemModel> itemModelMutableLiveData;
    private AppRepository appRepository;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);
        itemModelMutableLiveData = new MutableLiveData<>();
        loggedOutMutableLiveData = appRepository.getLoggedOutMutableLiveData();
    }

    public void getItemApiCall(){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<ItemModel> call = apiService.getItem();
        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                itemModelMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                itemModelMutableLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<ItemModel> getItemModelMutableLiveData() {
        return itemModelMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

}
