package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;


import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.Info;
import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.PhotosAlbumResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.CelebrityAlbumPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.CelebrityAlbumView;
import com.devrabbit.picstarcelebrity.mvvm.model.PhotosAlbumModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.AlbumsActivity;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.StockImageRequest;

import java.util.List;

public class AlbumsViewModel extends BaseObservable implements CelebrityAlbumView {
    Activity activity;
    PhotosAlbumModel model;
    CelebrityAlbumPresenter presenter;
    PSR_PrefsManager prefsManager;
    int pageNo = 1;
    String albumType;

    public AlbumsViewModel(PSR_PrefsManager preferenceManager, Activity activity, String albumType) {
        this.activity = activity;
        model = new PhotosAlbumModel();
        this.prefsManager = preferenceManager;
        this.albumType = albumType;
        presenter = new CelebrityAlbumPresenter();
        presenter.attachMvpView(this);
        getPhotosAlbum();
    }

    @Bindable
    public String getMessage() {
        return model.getMessage();
    }

    public void setMessage(String message) {
        model.setMessage(message);
        notifyPropertyChanged(BR.message);
    }


    public void getPhotosAlbum() {

        if (PSRUtils.isOnline(activity)) {
            if (pageNo == 1) {
                PSRUtils.showProgressDialog(activity);
            }

            StockImageRequest request = new StockImageRequest();
            request.setId(prefsManager.get(PSRConstants.USER_ID));
            request.setAlbumType(albumType);

            request.setPage(pageNo + "");
            request.setStatus("all");
            request.setPerPage("10");
            presenter.getCelebrityPhotosAlbum(prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(prefsManager), request);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }
    }


    public MutableLiveData<PhotosAlbumResponse> getCelebrityPhotosAlbum() {
        return model.getPhotosAlbum();
    }

    public void onClickBack() {
        activity.finish();
    }

    @Override
    public void onGettingPhotosAlbumSuccess(PhotosAlbumResponse response) {
        PSRUtils.hideProgressDialog();
        pageNo++;
        model.getPhotosAlbum().postValue(response);

        if (response.getInfo() == null || response.getInfo().size() == 0) {
            if (response.getMessage() != null) {
                setMessage(response.getMessage().toString());
            }
        }

    }

    @Override
    public void onGettingPhotosAlbumFailed(PhotosAlbumResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage().toString(), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
        PSRUtils.doLogout(activity, prefsManager);
    }

    @Override
    public void onServerError() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public Context getMvpContext() {
        return null;
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
}
