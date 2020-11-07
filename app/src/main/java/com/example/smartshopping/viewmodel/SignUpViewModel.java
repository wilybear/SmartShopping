package com.example.smartshopping.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.smartshopping.model.AppRepository;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
    }

    public void signUp(String email, String password){
        appRepository.signUp(email,password);
    }

//    public void signIn(String email, String password){
//        appRepository.signIn(email,password);
//    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
