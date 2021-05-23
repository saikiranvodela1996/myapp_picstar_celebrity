package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;

import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsResponse;
import com.devrabbit.picstarcelebrity.mvp.views.FetchAllEventsView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FetchAllEventsPresenter extends BasePresenter<FetchAllEventsView> {


    public void doFetchAllEvents(String lang,String header, String userid, int page)
    {
        Disposable disposable = PSRService.getInstance(lang,header).getAllEventsOfCeleb(userid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<FetchAllEventsResponse>() {
                    @Override
                    public void onNext( FetchAllEventsResponse fetchAllEventsResponce) {
                        if (getMvpView() != null) {
                            if( fetchAllEventsResponce.getStatus().equals("SUCCESS"))
                            {
                                getMvpView().onFetchedDone(fetchAllEventsResponce);

                            }
                            else
                            {
                                getMvpView().onFetchedFailed(fetchAllEventsResponce);
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
