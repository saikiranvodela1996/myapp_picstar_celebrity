package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.DashBoardPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.DashBoardView;
import com.devrabbit.picstarcelebrity.mvvm.model.DashBoardModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.AddEventActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.DashBoardActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.ServiceProgressActivity;
import com.devrabbit.picstarcelebrity.navigator.DashBoardNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.google.gson.Gson;

public class DashBoardViewModel extends BaseObservable implements DashBoardView {
    DashBoardModel model;
    DashBoardActivity activity;
    DashBoardPresenter dashBoardPresenter;
    DashBoardNavigator navigator;

    public DashBoardViewModel(PSR_PrefsManager psr_prefsManager, DashBoardActivity activity, DashBoardNavigator navigator) {
        this.activity = activity;
        model = new DashBoardModel();
        dashBoardPresenter = new DashBoardPresenter();
        this.navigator = navigator;
        dashBoardPresenter.attachMvpView(this);


    }

    /*@Bindable
    public String getLiveSelfieCount() {
        return model.getLiveSelfieCount();
    }*/


    @Bindable
    public String getUserName() {
        return model.getUserName();
    }

    public void setUserName(@Nullable String userName) {
        model.setUserName(userName);
        notifyPropertyChanged(BR.userName);

    }



    @Bindable
    public String getMessage() {
        return model.getMessage();
    }

    public void setMessage(@Nullable String msg) {
        model.setMessage(msg);
        notifyPropertyChanged(BR.message);

    }




    public void getServiceDetailsOfCelebrity() {
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            dashBoardPresenter.getCelebrityServices(activity.getPreferences().get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(activity.getPreferences()), activity.getPreferences().get(PSRConstants.USER_ID));

        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }
    }

    public void onLiveSelfieClicked(View view) {
        Intent intent = new Intent(activity, ServiceProgressActivity.class);
        intent.putExtra(PSRConstants.SERVICE_REQUEST_TYPE_ID, (Integer) view.getTag());
        activity.startActivity(intent);
    }


    public void onPhotoSelfieClicked() {
        PSRUtils.showAlert(activity, "Work in progress", null);

    }

    public void onCreateEventClicked() {
        Intent intent = new Intent(activity, AddEventActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onGettingServiceDetailsSuccess(UpdateCelbServResponse response) {
        PSRUtils.hideProgressDialog();
        activity.getPreferences().save(PSRConstants.PROFILEPIC, response.getInfo().getProfilePic());
        activity.getPreferences().save(PSRConstants.USERNAME, response.getInfo().getUsername());
        if(response.getInfo().getGender()!=null) {
            activity.getPreferences().save(PSRConstants.GENDER, response.getInfo().getGender().toString());
        }

        if (response.getInfo().getServicesOffering().size() != 0) {

            Gson gson = new Gson();
            String json = gson.toJson(response.getInfo().getServicesOffering());
            activity.getPreferences().save("SERVICESLIST", json);
        }else  if(response.getMessage()!=null){
            setMessage(response.getMessage());
        }
        navigator.onGettingServicesSuccess(response);
    }






    @Override
    public void onGettingServiceDetailsFailed(UpdateCelbServResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void celebrityDoesNotExist() {
        PSRUtils.hideProgressDialog();
        PSRUtils.doLogout(activity,activity.getPreferences());
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
}
