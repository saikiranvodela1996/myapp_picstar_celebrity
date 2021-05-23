package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;
import com.devrabbit.picstarcelebrity.mvp.views.DashBoardView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DashBoardPresenter extends BasePresenter<DashBoardView> {


    public void getCelebrityServices(String language,String header, String celebId) {
        Disposable disposable = PSRService.getInstance(language,header).doGetCelbServices(celebId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<UpdateCelbServResponse>() {
                    @Override
                    public void onNext(UpdateCelbServResponse response) {
                        if (getMvpView() != null) {
                            if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().onGettingServiceDetailsSuccess(response);
                            } else if (response.getStatus().equalsIgnoreCase("Does_Not_Exist")) {
                                getMvpView().celebrityDoesNotExist();
                            } else {
                                getMvpView().onGettingServiceDetailsFailed(response);
                            }

                          /*  if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().onGettingServiceDetailsSuccess(response);
                            } else {
                                getMvpView().celebrityDoesNotExist();
                            }*/
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
