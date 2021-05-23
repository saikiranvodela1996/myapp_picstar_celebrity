package com.devrabbit.picstarcelebrity.mvp.views;

public interface ForgotPasswordView extends BaseMvpView{
    void onSuccess(String message);
    void onFail(String message);
}