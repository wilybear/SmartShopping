package com.example.smartshopping.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class UserModel {

    private String birthday;
    private String gender;

    public UserModel(){}
    public UserModel( String birthday, String gender) {
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
