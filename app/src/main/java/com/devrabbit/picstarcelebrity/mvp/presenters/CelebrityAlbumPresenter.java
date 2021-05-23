package com.devrabbit.picstarcelebrity.mvp.presenters;

import com.chachapps.initialclasses.mvp.presenter.BasePresenter;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.StockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.PhotosAlbumResponse;
import com.devrabbit.picstarcelebrity.mvp.views.CelebrityAlbumView;
import com.devrabbit.picstarcelebrity.network.CustomDisposableObserver;
import com.devrabbit.picstarcelebrity.network.PSRService;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.StockImageRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CelebrityAlbumPresenter extends BasePresenter<CelebrityAlbumView> {


    public void getCelebrityPhotosAlbum(String lang,String header, StockImageRequest request) {
        Disposable disposable = PSRService.getInstance(lang,header).getAlbum(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableObserver<PhotosAlbumResponse>() {
                    @Override
                    public void onNext(PhotosAlbumResponse response) {
                        if (getMvpView() != null) {
                            if (response.getStatus().equals("SUCCESS")) {
                                getMvpView().onGettingPhotosAlbumSuccess(response);
                            } else {
                                getMvpView().onGettingPhotosAlbumFailed(response);
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
