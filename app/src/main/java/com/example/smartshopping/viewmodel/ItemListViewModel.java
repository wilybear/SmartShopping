package com.example.smartshopping.viewmodel;

import android.app.Application;
import android.util.Log;

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

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListViewModel extends AndroidViewModel {

    //can update recyclerview
    private MutableLiveData<List<ItemModel>> itemsList;
    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private MutableLiveData<UserModel> userModelMutableLiveData;
    private MutableLiveData<AreaModel> areaModelMutableLiveData;
    private MutableLiveData<Boolean> autoThread;

    public ItemListViewModel(@NonNull Application application){
        super(application);
        itemsList = new MutableLiveData<>();
        this.appRepository = new AppRepository(application);
        areaModelMutableLiveData =  appRepository.getAreaModelMutableLiveData();
        userMutableLiveData = appRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = appRepository.getLoggedOutMutableLiveData();
        userModelMutableLiveData = appRepository.getUserModelMutableLiveData();
        autoThread = appRepository.getAutoThread();
    }

    public MutableLiveData<List<ItemModel>> getItemsListObserver(){
        return itemsList;
    }
    public void logOut(){
        appRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        return userModelMutableLiveData;
    }


    public MutableLiveData<AreaModel> getAreaModelMutableLiveData(){
        return areaModelMutableLiveData;
    }

    public MutableLiveData<Boolean> getAutoThread() {
        return autoThread;
    }

    public void makeApiCall(){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        //해당에 파라미터 추가
        UserModel userModel = userModelMutableLiveData.getValue();

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

    public void makeApiCallWithParm(){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        UserModel userModel = userModelMutableLiveData.getValue();
        AreaModel areaModel = areaModelMutableLiveData.getValue();
        if(userModel == null || areaModel == null){
            return;
        }
        int age =20;
        try {
            age = userModel.calculateAge();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Call<List<ItemModel>> call = apiService.getItemList(userModel.getGender(),age,areaModel.getArea());

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
