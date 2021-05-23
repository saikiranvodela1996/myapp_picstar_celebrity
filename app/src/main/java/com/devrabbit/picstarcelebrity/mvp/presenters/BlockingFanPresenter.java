package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockFanRequest;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockingFanResponse;
import com.devrabbit.picstarcelebrity.mvp.models.forgotpassword.ForgotPasswordRequest;
import com.devrabbit.picstarcelebrity.mvp.views.BlockingFanView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BlockingFanPresenter  extends BasePresenter<BlockingFanView> {

    public void blockUser(String lang,String header, BlockFanRequest request) {
        Disposable disposable = PSRService.getInstance(lang,header).doBlockFan(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<BlockingFanResponse>() {
                    @Override
                    public void onNext(BlockingFanResponse response) {
                        if (getMvpView() != null) {
                            if( response.getStatus().equals("SUCCESS")){
                                getMvpView().onBlockingSuccess(response);
                            }else
                            {
                                getMvpView().onBlockingFailure(response);
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
