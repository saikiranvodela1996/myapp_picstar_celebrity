package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginRequest;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginResponse;
import com.devrabbit.picstarcelebrity.mvp.views.LoginView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public  class LoginPresenter  extends BasePresenter<LoginView> {

    public void doLogin(String lang,String header, LoginRequest loginRequest) {
        Disposable disposable = PSRService.getInstance(lang,header).doLogin(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        if (getMvpView() != null) {
                            if( loginResponse.getStatus().equals("SUCCESS"))
                            {
                                getMvpView().onLoginSuccess(loginResponse);
                            }else
                            {
                                getMvpView().onLoginFailed(loginResponse);
                            }
                        }
                    }


                    @Override
                    public void onConnectionLost() {
                        if (getMvpView() != null) {
                            getMvpView().onNoInternetConnection();
                        }
                    }

                    @Override
                    public void onSessionExpired() {
                        if (getMvpView() != null) {
                            getMvpView().onSessionExpired();
                        }
                    }
                    @Override
                    public void onServerError() {
                        if (getMvpView() != null) {
                            getMvpView().onServerError();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (getMvpView() != null) {
                            getMvpView().onError(t);
                        }
                    }
                });
        compositeSubscription.add(disposable);
    }
}
