package com.devrabbit.picstarcelebrity.mvp.views;

public interface BaseMvpView extends com.chachapps.initialclasses.mvp.view.BaseMvpView {
    void onSessionExpired();
    void onServerError();
}
