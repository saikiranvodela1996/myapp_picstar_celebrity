package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.allservices.CelebrityServiceRequest;
import com.devrabbit.picstarcelebrity.mvp.models.twilioaccesstoken.GetTwilioAccessTokenResp;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoReq;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.ServiceProgressPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.ServiceProgressView;
import com.devrabbit.picstarcelebrity.mvvm.model.CelebrityHistoryModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.CompressMainActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.ServiceProgressActivity;
import com.devrabbit.picstarcelebrity.navigator.StockImagesNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.UploadToS3Interface;
import com.devrabbit.picstarcelebrity.utils.Util;
import com.devrabbit.picstarcelebrity.videocalling.VideoActivity;
import com.devrabbit.picstarcelebrity.videocompressor.VideoCompress;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.devrabbit.picstarcelebrity.mvvm.view.activities.CompressMainActivity.getSystemLocale;
import static com.devrabbit.picstarcelebrity.mvvm.view.activities.CompressMainActivity.getSystemLocaleLegacy;

public class ServiceProgressViewModel extends BaseObservable implements ServiceProgressView, UploadToS3Interface {

    ServiceProgressActivity activity;
    PSR_PrefsManager prefsManager;
    ServiceProgressPresenter presenter;
    CelebrityHistoryModel model;
    int index;
    private int serviceRequestTypeId = 0;
    int nextPageNo = 1;
    int perPage = 10;


    boolean hasMoreToLoad;
    ArrayList<Uri> uris;
    ArrayList<String> uploadPaths;
    private int serviceReqId;
    StockImagesNavigator navigator;
    private String uploadingVideosSuccessMsg = "";
    private long startTime, endTime;

    public ServiceProgressViewModel(PSR_PrefsManager preferenceManager, ServiceProgressActivity activity, int index, StockImagesNavigator navigator) {
        this.activity = activity;
        this.index = index;
        this.prefsManager = preferenceManager;
        this.navigator = navigator;
        presenter = new ServiceProgressPresenter();
        model = new CelebrityHistoryModel();
        presenter.attachMvpView(this);
        hasMoreToLoad = true;
    }

    public void setUris(ArrayList<Uri> uris) {
        this.uris = uris;
        // this.uploadPaths.clear();
    }

    public void uploadtoS3(int index, int serviceReqID) {
        this.serviceReqId = serviceReqID;
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);

            PSRUtils.uploadVideo(index, uris.get(index), this, activity);
/*
            String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + ".mp4";
            // String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "picstarvideo" + File.separator + "VID_" + ".mp4";
            // String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picstarvideo";

            File f = new File(destPath);
            if (f.exists()) {
                f.delete();
            }
            String inputPath = "";
            try {
                inputPath = Util.getFilePath(activity, uris.get(index));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            VideoCompress.compressVideoLow(inputPath, destPath, new VideoCompress.CompressListener() {
                @Override
                public void onStart() {
                    int i = 0;
                    startTime = System.currentTimeMillis();
                    Util.writeFile(activity, "Start at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()) + "\n");
                }

                @Override
                public void onSuccess() {
                    endTime = System.currentTimeMillis();
                    Util.writeFile(activity, "End at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()) + "\n");
                    Util.writeFile(activity, "Total: " + ((endTime - startTime) / 1000) + "s" + "\n");
                    Util.writeFile(activity);
                    File f = new File(destPath);
                    if (f.exists()) {
                        int i = 0;

                    }
                    int i = 0;
                    Log.d("COMPRESSED", "" + destPath);
                    upload(destPath);
                }

                @Override
                public void onFail() {
                    int i = 0;
                    PSRUtils.hideProgressDialog();
                }

                @Override
                public void onProgress(float percent) {
                    int i = 0;
                }
            });*/
//            PSRUtils.showProgressDialog(activity);

        } else {
            PSRUtils.hideProgressDialog();
            PSRUtils.showNoNetworkAlert(activity);
        }

    }

    /* void  upload(String path) {
        PSRUtils.uploadVideo(index,path, this, activity);
     }*/
    public void setServiceRequestTypeId(int serviceRequestTypeId) {
        this.serviceRequestTypeId = serviceRequestTypeId;
        getLiveSelfies();
    }

    private Locale getLocale() {
        Configuration config = activity.getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    public boolean isHasMoreToLoad() {
        return hasMoreToLoad;
    }
    public void setHasMoreToLoad(boolean hasMoreToLoad) {
        this.hasMoreToLoad = hasMoreToLoad;
    }

    public void setNextPageNo(int nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public void onClickBack() {
        activity.finish();
    }

    public void getLiveSelfies() {
        if (!hasMoreToLoad) {
            return;
        }
        if (PSRUtils.isOnline(activity)) {
            if (nextPageNo == 1) {
                PSRUtils.showProgressDialog(activity);
            }
            CelebrityServiceRequest request = new CelebrityServiceRequest();
            request.setUserId(prefsManager.get(PSRConstants.USER_ID));
            if (index == 0)
                request.setStatus("upcoming");
            else if (index == 1)
                request.setStatus("pending");
            else if (index == 2)
                request.setStatus("completed");
            request.setServiceRequestType(serviceRequestTypeId);
            request.setPage(nextPageNo);
            request.setPerPage(perPage);
            presenter.getServiceRequestsOfCelebrity(prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(prefsManager), request);
        } else {
            PSRUtils.hideProgressDialog();
            PSRUtils.showNoNetworkAlert(activity);
        }
    }

    @Override
    public void onSuccess(CelebrityHistoryResponse response) {
        PSRUtils.hideProgressDialog();
        nextPageNo++;
        model.getHistory().postValue(response);
        if (response.getMessage() != null) {
            setMessage(response.getMessage());
        }
        hasMoreToLoad = response.getInfo().size() == perPage;

        if (!uploadingVideosSuccessMsg.isEmpty()) {
            PSRUtils.showAlert(activity, uploadingVideosSuccessMsg, null);
            uploadingVideosSuccessMsg = "";
        }
    }

    public MutableLiveData<CelebrityHistoryResponse> getListObservable() {
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
    public void onFail(CelebrityHistoryResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void onUploadingVideoSuccess(UploadVideoResponse response) {
        navigator.makeListEmpty();
        nextPageNo = 1;
        hasMoreToLoad = true;
        getLiveSelfies();
        uploadingVideosSuccessMsg = response.getMessage();

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
       // intent.putExtra(PSRConstants.TWILIO_ACCESS_TOKEN,resp.getInfo().getAccessToken());
        intent.putExtra("USERACCESSTOKEN",resp.getInfo().getUserAccessToken());
       // intent.putExtra(PSRConstants.TWILIOROOMNAME,resp.getInfo().getRoom());
        intent.putExtra("SERVICEREQID",serviceReqId);
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

    @Override
    public void onImageUploaded(int index, String uploadedUrl, String videoThumb) {
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.hideProgressDialog();
            UploadVideoReq req = new UploadVideoReq();
            req.setServiceRequestId(serviceReqId);
            req.setFilePath(uploadedUrl);
            req.setThumbNailPath(videoThumb);
            presenter.uploadVideo(prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(prefsManager), req);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }

    }


    public void getTwilioAccessToken(int serviceReqId) {
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            presenter.getTwilioAccessToken(prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(prefsManager),serviceReqId);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }

    }
}