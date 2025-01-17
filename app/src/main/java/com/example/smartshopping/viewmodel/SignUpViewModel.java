package com.example.smartshopping.viewmodel;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.smartshopping.model.AppRepository;
import com.example.smartshopping.model.UserModel;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<UserModel> userModelMutableLiveData;


    public SignUpViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
    }

    public void signUp(String email, String password,String birthday, String gender){
        appRepository.signUp(email,password,birthday,gender);
    }

//    public void signIn(String email, String password){
//        appRepository.signIn(email,password);
//    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        return userModelMutableLiveData;
    }

    public boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
