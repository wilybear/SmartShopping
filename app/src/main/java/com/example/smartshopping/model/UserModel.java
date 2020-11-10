package com.example.smartshopping.model;

import java.util.Date;

public class UserModel {
    private String birthday;
    private String gender;

    public UserModel( String birthday, String gender) {
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }
}
