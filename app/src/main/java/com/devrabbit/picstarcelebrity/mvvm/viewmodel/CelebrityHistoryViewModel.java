package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

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
import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.History.Info;
import com.devrabbit.picstarcelebrity.mvp.models.twilioaccesstoken.GetTwilioAccessTokenResp;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoReq;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.CelebrityHistoryPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.CelebrityHistoryView;
import com.devrabbit.picstarcelebrity.mvvm.model.CelebrityHistoryModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.CelebrityHistoryActivity;
import com.devrabbit.picstarcelebrity.navigator.StockImagesNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.UploadToS3Interface;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.HistoryRequest;
import com.devrabbit.picstarcelebrity.videocalling.VideoActivity;

import java.util.ArrayList;
import java.util.List;

public class CelebrityHistoryViewModel extends BaseObservable implements CelebrityHistoryView, UploadToS3Interface {

    CelebrityHistoryPresenter presenter;
    CelebrityHistoryActivity activity;
    CelebrityHistoryModel model;
    PSR_PrefsManager psr_prefsManager;


    int nextPageNumber = 1;
    int perPage = 10;

    public void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
    }

    public void setNextPageNumber(int nextPageNumber) {
        this.nextPageNumber = nextPageNumber;
    }

    boolean loadMore = true;
    ArrayList<Uri> uris;
    ArrayList<String> uploadPaths;
    private int serviceReqId;
    StockImagesNavigator navigator;
    private String videoUploadedMsg = "";

    public CelebrityHistoryViewModel(PSR_PrefsManager psr_prefsManager, CelebrityHistoryActivity activity, StockImagesNavigator navigator) {
        this.activity = activity;
        this.psr_prefsManager = psr_prefsManager;
        this.navigator = navigator;
        model = new CelebrityHistoryModel();
        uploadPaths = new ArrayList<>();
        presenter = new CelebrityHistoryPresenter();
        presenter.attachMvpView(this);
        Log.v("CALLING_LOADMORE", "TRIGGERED");
        loadMore();
    }

    public MutableLiveData<CelebrityHistoryResponse> getHistoryData() {
        return model.getHistory();
    }

    @Bindable
    public String getMessage() {
        return model.getMessage();
    }

    public void setMessage(String message) {
        model.setMessage(message);
        notifyPropertyChanged(BR.message);
    }

    @Override
    public void onHistorySuccess(CelebrityHistoryResponse response) {
        PSRUtils.hideProgressDialog();
        loadMore = response.getInfo().size() == perPage;
        nextPageNumber = nextPageNumber + 1;
        model.getHistory().postValue(response);

        if (response.getInfo() == null || response.getInfo().size() == 0) {
            if (response.getMessage() != null) {
                setMessage(response.getMessage());
            }
        }
        if (!videoUploadedMsg.isEmpty()) {
            PSRUtils.showAlert(activity, videoUploadedMsg, null);
            videoUploadedMsg = "";
        }
    }


    public void setUris(ArrayList<Uri> uris) {
        this.uris = uris;
        this.uploadPaths.clear();
    }

    public void uploadtoS3(int index, int serviceReqID) {
        this.serviceReqId = serviceReqID;
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            PSRUtils.uploadVideo(index, uris.get(index), this, activity);
        } else {
            PSRUtils.hideProgressDialog();
            PSRUtils.showNoNetworkAlert(activity);
        }

    }

    @Override
    public void onHistoryFail(CelebrityHistoryResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void onUploadingVideoSuccess(UploadVideoResponse response) {
        videoUploadedMsg = response.getMessage();
        navigator.makeListEmpty();
        nextPageNumber = 1;
        loadMore();
    }

    @Override
    public void onUploadingVideoFailure(UploadVideoResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }


    @Override
    public void ongettingTokenSuccess(GetTwilioAccessTokenResp resp, int serviceReqId) {
        PSRUtils.hideProgressDialog();
        Intent intent = new Intent(activity, VideoActivity.class);
        intent.putExtra(PSRConstants.TWILIO_ACCESS_TOKEN, resp.getInfo().getAccessToken());
        intent.putExtra(PSRConstants.TWILIOROOMNAME, resp.getInfo().getRoom());
        intent.putExtra("SERVICEREQID", serviceReqId);
        activity.startActivity(intent);
    }

    @Override
    public void ongettingTokenFailure(GetTwilioAccessTokenResp response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage().toString(), null);
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

    public boolean loadMore() {
        if (loadMore) {
            if (nextPageNumber == 1) {
                PSRUtils.showProgressDialog(activity);
            }
            if (PSRUtils.isOnline(activity)) {
                HistoryRequest model = new HistoryRequest();
                model.setId(psr_prefsManager.get(PSRConstants.USER_ID));
                model.setStatus("all");
                model.setPage("" + nextPageNumber);
                model.setPerPage("" + perPage);
                presenter.getCelebrityHistory(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE), PSRUtils.getHeader(psr_prefsManager), model);
            } else if (nextPageNumber == 1) {
                PSRUtils.showNoNetworkAlert(activity);
            }

        }
        return loadMore;
    }

    @Override
    public void onImageUploaded(int index, String uploadedUrl, String videoThumb) {

        if (PSRUtils.isOnline(activity)) {
            UploadVideoReq req = new UploadVideoReq();
            req.setServiceRequestId(serviceReqId);
            req.setFilePath(uploadedUrl);
            req.setThumbNailPath(videoThumb);
            presenter.uploadVideo(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE), PSRUtils.getHeader(psr_prefsManager), req);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }
    }


    public void getTwilioAccessToken(int serviceReqId) {
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            presenter.getTwilioAccessToken(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE), PSRUtils.getHeader(psr_prefsManager), serviceReqId);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }

    }
}
