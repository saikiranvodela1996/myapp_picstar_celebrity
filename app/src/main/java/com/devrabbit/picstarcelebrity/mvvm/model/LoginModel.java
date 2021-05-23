package com.devrabbit.picstarcelebrity.mvvm.model;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class LoginModel {

    @Nullable
    String email, password;

    public LoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

}
