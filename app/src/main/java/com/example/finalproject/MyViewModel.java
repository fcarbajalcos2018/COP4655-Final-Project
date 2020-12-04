package com.example.finalproject;
import android.arch.lifecycle.view
public class ViewModel extends ViewModel{
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
