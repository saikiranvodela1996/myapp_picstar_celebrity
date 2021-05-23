package com.devrabbit.picstarcelebrity.mvvm.viewmodel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.Info;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.StockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages.UploadStockImagesRequest;
import com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages.UploadStockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.StockImagesPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.StockImagesView;
import com.devrabbit.picstarcelebrity.mvvm.model.StockImagesModel;
import com.devrabbit.picstarcelebrity.navigator.StockImagesNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.UploadToS3Interface;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.StockImageRequest;

import java.util.ArrayList;
import java.util.List;

public class StockImagesViewModel extends BaseObservable implements StockImagesView, UploadToS3Interface {

    Activity activity;
    StockImagesPresenter presenter;
    StockImagesModel model;
    PSR_PrefsManager psr_prefsManager;
    int nextPageNumber = 1;
    int perPage = 10;
    boolean loadMore = true;
    public static int PICK_IMAGES = 21;
    ArrayList<Uri> uris;
    ArrayList<String> uploadPaths;
    StockImagesNavigator navigator;
    String imagesUploadedStatus = "";

    public StockImagesViewModel(PSR_PrefsManager psr_prefsManager, Activity activity, StockImagesNavigator navigator) {
        this.activity = activity;
        this.psr_prefsManager = psr_prefsManager;
        this.navigator = navigator;
        model = new StockImagesModel();
        presenter = new StockImagesPresenter();
        presenter.attachMvpView(this);
        uploadPaths = new ArrayList<>();
        loadMore();
    }

    @Bindable
    public String getMessage() {
        return model.getMessage();
    }

    public void setMessage(String message) {
        model.setMessage(message);
        notifyPropertyChanged(BR.message);
    }

    public MutableLiveData<List<Info>> getStockImagesData() {
        return model.getImages();
    }

    public boolean loadMore() {
        if (loadMore) {
            StockImageRequest model = new StockImageRequest();
            model.setId(psr_prefsManager.get(PSRConstants.USER_ID));
            model.setPage("" + nextPageNumber);
            model.setPerPage("" + perPage);
            presenter.getStockPhotosOfCelebrity(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(psr_prefsManager), model);
        }
        return loadMore;
    }


    public void onClickBack() {
        activity.finish();
    }

    public void openGallery() {
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.checkStoragePermissionToProgress(activity, new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    activity.startActivityForResult(intent, PICK_IMAGES);
                }
            });
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }
    }

    public void setUris(ArrayList<Uri> uris) {
        this.uris = uris;
        this.uploadPaths.clear();
    }

    public void uploadtoS3(int index) {
        Log.d("UPLOADEDINDEX::", "uploadtoS3=" + index);
        PSRUtils.uploadImage(index, uris.get(index), this, activity);
    }

    @Override
    public void onStockImagesSuccess(StockImagesResponse response) {
        PSRUtils.hideProgressDialog();
        loadMore = response.getInfo().size() == perPage;
        nextPageNumber = nextPageNumber + 1;
        model.getImages().postValue(response.getInfo());
        if (imagesUploadedStatus != null && !imagesUploadedStatus.isEmpty()) {
            PSRUtils.showAlert(activity, imagesUploadedStatus, null);
            imagesUploadedStatus = "";
        }

        if (response.getInfo() == null || response.getInfo().size() == 0) {
            if (response.getMessage() != null) {
                setMessage(response.getMessage());
            }
        }

    }

    @Override
    public void onStockImagesFail(StockImagesResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void uploadStockImagesSuccess(UploadStockImagesResponse response) {
        imagesUploadedStatus = response.getMessage();
        loadMore = true;
        nextPageNumber = 1;
        loadMore();
    }

    @Override
    public void uploadStockImagesFail(UploadStockImagesResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
    }

    @Override
    public void onServerError() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public Context getMvpContext() {
        return activity;
    }

    @Override
    public void onError(Throwable throwable) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onNoInternetConnection() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showNoNetworkAlert(activity);
    }

    @Override
    public void onErrorCode(String s) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onImageUploaded(int index, String uploadedUrl,String videothumb) {
        try {
            uploadPaths.add(uploadedUrl);
            index = index + 1;
            if (uris.size() > index) {
                uploadtoS3(index);
            } else {
                navigator.makeListEmpty();
                UploadStockImagesRequest request = new UploadStockImagesRequest();
                request.setCelebrityId(psr_prefsManager.get(PSRConstants.USER_ID));
                request.setPhotoType(1);
                request.setPhotoUrls(uploadPaths);
                presenter.doUploadStockImages(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(psr_prefsManager), request);
            }
        } catch (Exception e) {
            Log.d("UPLOADEDINDEX::", "onImageUploaded=" + e.getMessage());
            e.printStackTrace();
        }
    }
}
