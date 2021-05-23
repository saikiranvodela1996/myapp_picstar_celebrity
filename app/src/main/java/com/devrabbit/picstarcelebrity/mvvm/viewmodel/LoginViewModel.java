package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.jwt.JWT;
import com.auth0.android.result.Credentials;
import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginRequest;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginResponse;
import com.devrabbit.picstarcelebrity.mvp.models.login.ServicesOffering;
import com.devrabbit.picstarcelebrity.mvp.presenters.LoginPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.LoginView;
import com.devrabbit.picstarcelebrity.mvvm.model.LoginModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.BaseActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.ForgotPasswordActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.LoginActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.PersonaliseActivity;
import com.devrabbit.picstarcelebrity.navigator.LoginNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;


public class LoginViewModel extends BaseObservable implements LoginView {

    private final LoginPresenter loginPresenter;
    private LoginModel model;
    private LoginActivity activity;
    private String successMessage = "Login successful";
    private String errorMessage = "Email or Password is not valid";

    LoginNavigator navigator;
    @Bindable
    private String toastMessag = null;
    private boolean isPwdShown = false;

    public String getToastMessag() {
        return toastMessag;
    }

    private void setToastMessag(String toastMessag) {
        this.toastMessag = toastMessag;
        notifyPropertyChanged(BR.toastMessag);
    }

    @Bindable
    public String getUserEmail() {
        return model.getEmail();
    }

    public void setUserEmail(String email) {
        model.setEmail(email);
        notifyPropertyChanged(BR.userEmail);
    }

    @Bindable
    public String getUserPassword() {
        return model.getPassword();
    }

    public void setUserPassword(String password) {
        model.setPassword(password);
        notifyPropertyChanged(BR.userPassword);
    }

    public LoginViewModel(LoginActivity activity, LoginNavigator navigator) {
        model = new LoginModel("", "");
        this.navigator = navigator;
        loginPresenter = new LoginPresenter();
        loginPresenter.attachMvpView(this);
        this.activity = activity;
    }


    public void onClickPwdHideBtn() {
        if (isPwdShown) {
            isPwdShown = false;
            navigator.onClickEyeIcon(isPwdShown);
        } else {
            isPwdShown = true;
            navigator.onClickEyeIcon(isPwdShown);
        }

    }
    public  void onClickForgotPwd(){

        Intent intent = new Intent(activity, ForgotPasswordActivity.class);
        activity.startActivity(intent);

       // PSRUtils.showAlert(activity,"Work in progress",null);
    }

    public void onButtonClicked() {
        if (model.getEmail().trim().isEmpty()&& model.getPassword().trim().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enteremail_pwd_msgalert), null);
            return;
        }



        if (model.getEmail().trim().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_email_txt), null);
            return;
        }
      /*  if (!Patterns.EMAIL_ADDRESS.matcher(model.getEmail().trim()).matches()) {
            PSRUtils.showAlert(activity, "Please enter valid email", null);
            return;
        }*/


        if (model.getPassword().trim().isEmpty()) {
            PSRUtils.showAlert(activity,  activity.getResources().getString(R.string.enter_pwd_alert_txt), null);
            return;
        }

        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            AuthenticationAPIClient authenticationAPIClient = new AuthenticationAPIClient(getAccount());
            authenticationAPIClient.login(model.getEmail(), model.getPassword(), "Username-Password-Authentication")
                    .setScope("openid profile email username")
                    .start(new com.auth0.android.callback.AuthenticationCallback<Credentials>() {
                        @Override
                        public void onSuccess(@Nullable Credentials payload) {
                            JWT jwt2 = new JWT(payload.getIdToken());
                            activity.getPreferences().save(PSRConstants.TOKEN, "Bearer" + " " + payload.getIdToken());
                            Log.e("idtoken", payload.getIdToken());

                            String userId = jwt2.getClaim("sub").asObject(String.class);
                            String userName = jwt2.getClaim("name").asObject(String.class);
                            String lastName = jwt2.getClaim("family_name").asObject(String.class);
                            String givenName = jwt2.getClaim("given_name").asObject(String.class);
                            String profilePic = jwt2.getClaim("picture").asObject(String.class);
                            String email = jwt2.getClaim("email").asObject(String.class);
                            String auth0Username = "";
                            if (jwt2.getClaim("username").asObject(String.class) != null) {
                                auth0Username = jwt2.getClaim("username").asObject(String.class);
                            }
                            LoginRequest loginRequest = new LoginRequest();
                            loginRequest.setUserId(userId);
                            loginRequest.setUsername(userName);
                            loginRequest.setEmail(email);
                            loginRequest.setAuth0Username(auth0Username);
                            loginRequest.setFcmRegToken("dsdjskdjsd");
                            loginRequest.setDeviceType("android");
                            loginRequest.setDeviceId(PSRUtils.getId(activity));
                            loginRequest.setLoginType(0);
                            loginRequest.setFirstName(givenName);
                            loginRequest.setLastName(lastName);
                            loginRequest.setProfilePic(profilePic);
                            loginRequest.setDob("");
                            loginPresenter.doLogin(activity.getPreferences().get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(activity.getPreferences()), loginRequest);
                        }

                        @Override
                        public void onFailure(@NonNull AuthenticationException error) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    PSRUtils.hideProgressDialog();
                                    PSRUtils.showAlert(activity, error.getDescription(), null);
                                }
                            });

                        }
                    });
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(getUserEmail()) && Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches()
                && getUserPassword().length() > 5;
    }

    private Auth0 getAccount() {
        Auth0 account = new Auth0(activity.getString(R.string.com_auth0_client_id), activity.getString(R.string.com_auth0_domain));
        account.setOIDCConformant(true);
        account.setLoggingEnabled(true);
        return account;
    }

    @Override
    public void onLoginSuccess(LoginResponse response) {
        activity.getPreferences().save(PSRConstants.USER_ID, response.getInfo().getUserId());
        activity.getPreferences().save(PSRConstants.USERNAME, response.getInfo().getUsername());
        activity.getPreferences().save(PSRConstants.COVERPIC, response.getInfo().getCoverPic());
        activity.getPreferences().save(PSRConstants.PROFILEPIC, response.getInfo().getProfilePic());
        if(response.getInfo().getGender()!=null) {
            activity.getPreferences().save(PSRConstants.GENDER, response.getInfo().getGender().toString());
        }
        activity.getPreferences().save(PSRConstants.LIVE_SELFIES_COUNT, response.getInfo().getLiveSelfiesCount().toString());
        activity.getPreferences().save(PSRConstants.PHOTO_SELFIES_COUNT, response.getInfo().getPhotoSelfiesCount().toString());
        activity.getPreferences().save(PSRConstants.VIDEO_MSGS_COUNT, response.getInfo().getVideoMessagesCount().toString());
        activity.getPreferences().save(PSRConstants.LIVE_VIDEOS_COUNT, response.getInfo().getLiveVideosCount().toString());

        String categories = new Gson().toJson(response.getInfo().getCategoriesOfCelebrity());
        activity.getPreferences().save(PSRConstants.CELEBRITYROLE,categories);

        if (response.getInfo().getServicesOffering().size() != 0) {

            Gson gson = new Gson();
            String json = gson.toJson(response.getInfo().getServicesOffering());
            activity.getPreferences().save("SERVICESLIST", json);

            for (ServicesOffering servicesOffering : response.getInfo().getServicesOffering()) {
                if (servicesOffering.getServiceId() == PSRConstants.LIVESELFIE_SERVICE_REQ_ID) {
                    activity.getPreferences().save(PSRConstants.LIVESELIFEACTIVE, true);
                    activity.getPreferences().save(PSRConstants.LIVESELFIE_COST, PSRUtils.getServiceCost(servicesOffering.getServiceCost().toString()));
                } else if (servicesOffering.getServiceId() == PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID) {
                    activity.getPreferences().save(PSRConstants.PHOTOSELIFEACTIVE, true);
                    activity.getPreferences().save(PSRConstants.PHOTOSELFIE_COST, PSRUtils.getServiceCost(servicesOffering.getServiceCost().toString()));
                } else if (servicesOffering.getServiceId() == PSRConstants.VIDEOMSGS_SERVICE_REQ_ID) {
                    activity.getPreferences().save(PSRConstants.VIDEOMSGACTIVE, true);
                    activity.getPreferences().save(PSRConstants.VIDEOMSGCOST, PSRUtils.getServiceCost(servicesOffering.getServiceCost().toString()));
                } else if (servicesOffering.getServiceId() == PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID) {
                    activity.getPreferences().save(PSRConstants.LIVEVIDEOACTIVE, true);
                    activity.getPreferences().save(PSRConstants.LIVEVIDEOCOST, PSRUtils.getServiceCost(servicesOffering.getServiceCost().toString()));
                }
            }

        }

        activity.getPreferences().save(PSRConstants.PRIVACY_POLICY_URL, response.getInfo().getPrivacyPolicyUrl());
        activity.getPreferences().save(PSRConstants.CONTACT_US_PHONE_NO, response.getInfo().getContactUsPhoneNum());
        activity.getPreferences().save(PSRConstants.CONTACT_US_EMAIL, response.getInfo().getContactUsEmail());
        activity.getPreferences().save(PSRConstants.CONTACT_US_ADDRESS, response.getInfo().getContactUsAddress());



        activity.getPreferences().save(PSRConstants.ISLOGGEDIN, true);
        Intent intent = new Intent(activity, PersonaliseActivity.class);
        activity.startActivity(intent);
        activity.finishAffinity();
        PSRUtils.hideProgressDialog();
    }

    @Override
    public void onLoginFailed(LoginResponse response) {
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
}