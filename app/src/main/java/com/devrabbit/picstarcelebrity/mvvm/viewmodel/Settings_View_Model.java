package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.ServicesOffering;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServReq;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.UpdateCelbServicesPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.UpdateCelbServicesView;
import com.devrabbit.picstarcelebrity.mvvm.model.SettingsModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.SettingsActivity;
import com.devrabbit.picstarcelebrity.navigator.OnOkButtonClick;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings_View_Model extends BaseObservable implements UpdateCelbServicesView, OnOkButtonClick {
    SettingsActivity activity;
    PSR_PrefsManager psr_prefsManager;
    SettingsModel model;
    UpdateCelbServicesPresenter presenter;

    public Settings_View_Model(SettingsActivity settings_activity, PSR_PrefsManager psr_prefsManager, boolean isLiveselfieEnable, boolean isLivevideoEnabled, boolean isPhotoselfie, boolean isVideoMsg) {

        this.activity = settings_activity;
        this.psr_prefsManager = psr_prefsManager;
        presenter = new UpdateCelbServicesPresenter();
        presenter.attachMvpView(this);
        model = new SettingsModel();
        model.setLiveSelfieActive(isLiveselfieEnable);
        model.setLiveVideoActive(isLivevideoEnabled);
        model.setPhotoSelfieActive(isPhotoselfie);
        model.setVideoMsgActive(isVideoMsg);

    }


    @Bindable
    public String getLiveSelfiePrice() {
        return model.getLiveSelfiePrice();
    }

    public void setLiveSelfiePrice(String cost) {
        model.setLiveSelfiePrice(cost);
        notifyPropertyChanged(BR.liveSelfiePrice);
    }


    @Bindable
    public String getPhotoSelfiePrice() {
        return model.getPhotoSelfiePrice();
    }

    public void setPhotoSelfiePrice(String cost) {
        model.setPhotoSelfiePrice(cost);
        notifyPropertyChanged(BR.photoSelfiePrice);
    }


    @Bindable
    public String getLiveVideoPrice() {
        return model.getLiveVideoPrice();
    }

    public void setLiveVideoPrice(String cost) {
        model.setLiveVideoPrice(cost);
        notifyPropertyChanged(BR.liveVideoPrice);
    }


    @Bindable
    public String getVideoMsgPrice() {
        return model.getVideoMsgPrice();
    }

    public void setVideoMsgPrice(String cost) {
        model.setVideoMsgPrice(cost);
        notifyPropertyChanged(BR.videoMsgPrice);
    }


    public void onBackClicked() {
        activity.finish();
    }

    public void onClickVideoMsgSwitch(boolean b) {
        model.setVideoMsgActive(b);

    }

    public void onClickLiveVideoSwitch(boolean b) {
        model.setLiveVideoActive(b);
    }

    public void onClickPhotoSelfieSwitch(boolean b) {
        model.setPhotoSelfieActive(b);
    }

    public void onClickLiveSelfieSwitch(boolean b) {
        model.setLiveSelfieActive(b);
    }


    public void onClickSaveSettings() {
        if (!model.isLiveSelfieActive() && !model.isPhotoSelfieActive() && !model.isLiveVideoActive() && !model.isVideoMsgActive()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.select_atleast_oneservice), null);
            return;
        }

        if (model.isPhotoSelfieActive() && model.getPhotoSelfiePrice().isEmpty()) {
            PSRUtils.showAlert(activity,  activity.getResources().getString(R.string.enter_photoselfie_price), null);
            return;
        }
        if (model.isPhotoSelfieActive() && Double.parseDouble(model.getPhotoSelfiePrice()) == 0) {
            PSRUtils.showAlert(activity,  activity.getResources().getString(R.string.price_should_be_greaterthan_zero),null);
            return;
        }
       /* if (!isPriceValid(model.getPhotoSelfiePrice())) {
            PSRUtils.showAlert(activity, "Please enter valid Photoselfie price", null);
        }*/


        if (model.isLiveSelfieActive() && model.getLiveSelfiePrice().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_liveselfie_price), null);
            return;
        }
        if (model.isLiveSelfieActive() && Double.parseDouble(model.getLiveSelfiePrice()) == 0) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.price_should_be_greaterthan_zero),null);
            return;
        }


        if (model.isVideoMsgActive() && model.getVideoMsgPrice().isEmpty()) {
            PSRUtils.showAlert(activity,  activity.getResources().getString(R.string.enter_videomsg_price), null);
            return;
        }

        if (model.isVideoMsgActive() && Double.parseDouble(model.getVideoMsgPrice()) == 0) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.price_should_be_greaterthan_zero),null);
            return;
        }

        if (model.isLiveVideoActive() && model.getLiveVideoPrice().isEmpty()) {
            PSRUtils.showAlert(activity,  activity.getResources().getString(R.string.enter_livevideo_price), null);
            return;
        }
        if (model.isLiveVideoActive() && Double.parseDouble(model.getLiveVideoPrice()) == 0) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.price_should_be_greaterthan_zero),null);
            return;
        }


        List<Integer> serviceIds = new ArrayList<>();
        List<Double> serviceCosts = new ArrayList<>();
        if (model.isLiveSelfieActive()) {
            serviceIds.add(PSRConstants.LIVESELFIE_SERVICE_REQ_ID);
            if (model.getLiveSelfiePrice() != null && !model.getLiveSelfiePrice().isEmpty())
                serviceCosts.add(Double.parseDouble(model.getLiveSelfiePrice()));

        }
        if (model.isLiveVideoActive()) {
            serviceIds.add(PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID);
            serviceCosts.add(Double.parseDouble(model.getLiveVideoPrice()));

        }
        if (model.isPhotoSelfieActive()) {
            serviceIds.add(PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID);
            serviceCosts.add(Double.parseDouble(model.getPhotoSelfiePrice()));
        }
        if (model.isVideoMsgActive()) {
            serviceIds.add(PSRConstants.VIDEOMSGS_SERVICE_REQ_ID);
            serviceCosts.add(Double.parseDouble(model.getVideoMsgPrice()));
        }


        if (PSRUtils.isOnline(activity)) {

            Log.d("PRICES", serviceCosts.toString());
            Log.d("SERVICEREQID", serviceIds.toString());
            PSRUtils.showProgressDialog(activity);
            UpdateCelbServReq updateCelbServReq = new UpdateCelbServReq();
            updateCelbServReq.setCelebrityId(psr_prefsManager.get(PSRConstants.USER_ID));
            updateCelbServReq.setServiceCosts(serviceCosts);
            updateCelbServReq.setServiceIds(serviceIds);
            presenter.updateServices(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(psr_prefsManager), updateCelbServReq);

        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }


    }


    private boolean isPriceValid(String input) {
        // String input = "1024.22";
        //  String pattern = "([0-9]{4})(\.)([0-2]{2})"; // 4 digits followe by . followed by 2 digits
        String pattern = "(?=.*?\\d)^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2})?$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        if (m.matches()) {
            System.out.println("Validated");
            return true;

        } else {
            System.out.println("Not Validated");
            return false;

        }
    }


    @Override
    public void onUpdatingCelbServicesSuccess(UpdateCelbServResponse response) {
        PSRUtils.hideProgressDialog();
        if (response.getInfo().getServicesOffering() != null) {
            Gson gson = new Gson();
            String json = gson.toJson(response.getInfo().getServicesOffering());
            psr_prefsManager.save("SERVICESLIST", json);
        }

        PSRUtils.showButtonActionAlert(activity, response.getMessage(), null, this);
    }

    @Override
    public void onUpdatingCelbServicesFailure(UpdateCelbServResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
        PSRUtils.doLogout(activity, psr_prefsManager);
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
    public void onClickOk() {
        activity.finish();
    }
}
