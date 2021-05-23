package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.StockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages.UploadStockImagesRequest;
import com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages.UploadStockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.views.StockImagesView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.StockImageRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StockImagesPresenter extends BasePresenter<StockImagesView> {

    public void getStockPhotosOfCelebrity(String lang,String header, StockImageRequest request) {

        Disposable disposable = PSRService.getInstance(lang,header).getStockPhotosOfCelebrity(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<StockImagesResponse>() {
                    @Override
                    public void onNext(StockImagesResponse response) {
                        if (getMvpView() != null) {
                            if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().onStockImagesSuccess(response);
                            } else {
                                getMvpView().onStockImagesFail(response);
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

    public void doUploadStockImages(String lang,String header, UploadStockImagesRequest request) {
        Disposable disposable = PSRService.getInstance(lang,header).doUploadStockImages(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<UploadStockImagesResponse>() {
                    @Override
                    public void onNext(UploadStockImagesResponse response) {
                        if (getMvpView() != null) {
                            if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().uploadStockImagesSuccess(response);
                            } else {
                                getMvpView().uploadStockImagesFail(response);
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
