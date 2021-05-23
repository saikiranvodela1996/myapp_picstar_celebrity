package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Patterns;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.forgotpassword.ForgotPasswordRequest;
import com.devrabbit.picstarcelebrity.mvp.presenters.ForgotPasswordPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.ForgotPasswordView;
import com.devrabbit.picstarcelebrity.navigator.OnOkButtonClick;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ForgotPasswordViewmodel extends BaseObservable implements ForgotPasswordView, OnOkButtonClick {

    String email;
    Activity activity;
    ForgotPasswordPresenter presenter;
    PSR_PrefsManager psr_prefsManager;


    public ForgotPasswordViewmodel(Activity activity, PSR_PrefsManager psr_prefsManager) {
        email = "";
        this.activity = activity;
        this.psr_prefsManager = psr_prefsManager;
        presenter = new ForgotPasswordPresenter();
        presenter.attachMvpView(this);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.userPassword);
    }


    public  void onClickBack(){
        activity.finish();
    }














    public void onClickSubmitBtn() {

        if (email.trim().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_email_alert), null);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_validemail_msg), null);
            return;
        }
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            ForgotPasswordRequest request = new ForgotPasswordRequest();
            request.setClientId(activity.getResources().getString(R.string.com_auth0_client_id));
            request.setConnection("Username-Password-Authentication");
            request.setEmail(email);
            presenter.sendPasswordChangeRequest(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(psr_prefsManager), request);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }

    }

    @Override
    public void onSuccess(String message) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showButtonActionAlert(activity, message, null, this);

    }

    @Override
    public void onFail(String message) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, message, null);
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