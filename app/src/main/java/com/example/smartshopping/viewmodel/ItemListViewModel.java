package com.example.smartshopping.viewmodel;

import android.app.Application;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartshopping.model.AppRepository;
import com.example.smartshopping.model.AreaModel;
import com.example.smartshopping.model.ItemModel;
import com.example.smartshopping.model.UserModel;
import com.example.smartshopping.network.APIService;
import com.example.smartshopping.network.RetroInstance;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.auth.User;

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

    public ItemListViewModel(@NonNull Application application){
        super(application);
        itemsList = new MutableLiveData<>();
        this.appRepository = new AppRepository(application);
        areaModelMutableLiveData = new MutableLiveData<>();
        userMutableLiveData = appRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = appRepository.getLoggedOutMutableLiveData();
        userModelMutableLiveData = appRepository.getUserModelMutableLiveData();
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

    public void makeApiCall(){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        //해당에 파라미터 추가
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
        Call<List<ItemModel>> call = apiService.getItemList(userModel.getGender(),userModel.getBirthday(),areaModel.getArea());

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

    public void changeArea(AreaModel areaModel){
        areaModelMutableLiveData.postValue(areaModel);
    }
}
