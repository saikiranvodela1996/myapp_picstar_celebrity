package com.devrabbit.picstarcelebrity.mvvm.model;

import androidx.annotation.Nullable;

public class DashBoardMenuModel {

    @Nullable
    String userName;

    @Nullable
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nullable String userName) {
        this.userName = userName;
    }
}
