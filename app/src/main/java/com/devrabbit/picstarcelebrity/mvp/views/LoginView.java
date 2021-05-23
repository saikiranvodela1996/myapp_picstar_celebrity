package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.login.LoginResponse;

public interface LoginView extends BaseMvpView {

    void onLoginSuccess(LoginResponse response);
    void  onLoginFailed(LoginResponse response);
}
