package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;

import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventRequest;
import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventResponce;
import com.devrabbit.picstarcelebrity.mvp.models.createevent.CreateEventResponse;
import com.devrabbit.picstarcelebrity.mvp.views.AddEventView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddEventPresenter extends BasePresenter<AddEventView> {


    public void doCreate(String language,String header, AddEventRequest addEventRequest) {
        Disposable disposable = PSRService.getInstance(language,header).doCreate(addEventRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<CreateEventResponse>() {
                    @Override
                    public void onNext(CreateEventResponse response) {
                        if (getMvpView() != null) {
                            if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().onCreatingEventSuccess(response);

                            } else {
                                getMvpView().onCreatingEventFailed(response);

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
                            getMvpView().onSessionExpired();
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
