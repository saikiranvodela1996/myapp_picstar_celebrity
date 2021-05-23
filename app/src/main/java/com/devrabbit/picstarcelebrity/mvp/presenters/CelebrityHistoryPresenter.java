package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;
import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.twilioaccesstoken.GetTwilioAccessTokenResp;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoReq;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoResponse;
import com.devrabbit.picstarcelebrity.mvp.views.CelebrityHistoryView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.HistoryRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CelebrityHistoryPresenter extends BasePresenter<CelebrityHistoryView> {

    public void getCelebrityHistory(String lang,String header, HistoryRequest model) {
        Disposable disposable = PSRService.getInstance(lang,header).getCelebrityHistory(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<CelebrityHistoryResponse>() {
                    @Override
                    public void onNext(CelebrityHistoryResponse response) {
                        if (getMvpView() != null) {
                            if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().onHistorySuccess(response);
                            } else {
                                getMvpView().onHistoryFail(response);
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


    public void uploadVideo(String lang,String header,UploadVideoReq req) {
            Disposable disposable = PSRService.getInstance(lang,header).doUploadVideo(req)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new CustomDisposableObserver<UploadVideoResponse>() {
                        @Override
                        public void onNext(UploadVideoResponse response) {
                            if (getMvpView() != null) {
                                if (response.getStatus().equals("SUCCESS")) {
                                    getMvpView().onUploadingVideoSuccess(response);
                                } else {
                                    getMvpView().onUploadingVideoFailure(response);
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



    public void getTwilioAccessToken(String lang,String header, int req) {
        Disposable disposable = PSRService.getInstance(lang,header).getAccessToken(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<GetTwilioAccessTokenResp>() {
                    @Override
                    public void onNext(GetTwilioAccessTokenResp response) {
                        if (getMvpView() != null) {
                            if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().ongettingTokenSuccess(response,req);
                            } else {
                                getMvpView().ongettingTokenFailure(response);
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
