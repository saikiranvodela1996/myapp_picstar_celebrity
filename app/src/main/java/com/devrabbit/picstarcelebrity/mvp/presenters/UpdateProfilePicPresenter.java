package com.devrabbit.picstarcelebrity.mvp.presenters;

import android.util.Log;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;

import com.devrabbit.picstarcelebrity.mvp.models.updateprofile.UpdateProfileRequest;
import com.devrabbit.picstarcelebrity.mvp.models.updateprofile.UpdateProfileResponce;
import com.devrabbit.picstarcelebrity.mvp.views.UpdateProfilePicView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateProfilePicPresenter extends BasePresenter<UpdateProfilePicView> {

    public void doUpdateProfile(String lang,String header, UpdateProfileRequest updateProfileRequest) {
        Disposable disposable = PSRService.getInstance(lang,header).doUpdateProfilePic(updateProfileRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<UpdateProfileResponce>() {
                    @Override
                    public void onNext(UpdateProfileResponce updateProfileResponce) {
                        if (getMvpView() != null) {
                            if( updateProfileResponce.getStatus().equals("SUCCESS"))
                            {
                                getMvpView().onUpdateDone(updateProfileResponce);
                            }else
                            {
                                getMvpView().onUpdateFailed(updateProfileResponce);
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
