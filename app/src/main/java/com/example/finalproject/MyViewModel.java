package com.example.finalproject;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private UserModel userModel;

    public MyViewModel() {
        userModel = new UserModel();
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
