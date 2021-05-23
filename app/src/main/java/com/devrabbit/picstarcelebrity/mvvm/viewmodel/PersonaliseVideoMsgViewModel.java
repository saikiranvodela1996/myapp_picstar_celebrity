package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.ServicesOffering;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServReq;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.UpdateCelbServicesPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.UpdateCelbServicesView;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.DashBoardActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PersonaliseVideoMessageFragment;
import com.devrabbit.picstarcelebrity.navigator.PersonaliseNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.PersonaliseSingleton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PersonaliseVideoMsgViewModel extends BaseObservable implements UpdateCelbServicesView {


    PersonaliseNavigator navigator;
    PersonaliseVideoMessageFragment fragment;
    UpdateCelbServicesPresenter updateCelbServicesPresenter;

    public PersonaliseVideoMsgViewModel(PersonaliseNavigator navigator, PersonaliseVideoMessageFragment fragment, String cost, boolean isActive) {
        this.navigator = navigator;
        this.fragment = fragment;
        updateCelbServicesPresenter = new UpdateCelbServicesPresenter();
        updateCelbServicesPresenter.attachMvpView(this);
        PersonaliseSingleton.getInstance().setVideoMsgPrice(cost);
        PersonaliseSingleton.getInstance().setVideoMsgChecked(isActive);
    }


    @Bindable
    public String getVideoMsgPrice() {
        return PersonaliseSingleton.getInstance().getVideoMsgPrice();
    }

    public void setVideoMsgPrice(String price) {

        String pricEntered = price;
        pricEntered = price.isEmpty() ? "0" : pricEntered.trim();
        if (Double.parseDouble(pricEntered) == 0 && PersonaliseSingleton.getInstance().isVideoMsgChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_servicecost_msg), null);
            PersonaliseSingleton.getInstance().setVideoMsgChecked(!PersonaliseSingleton.getInstance().isVideoMsgChecked());
            navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isVideoMsgChecked());
        }
        if (pricEntered.length() == 3 && PersonaliseSingleton.getInstance().isVideoMsgChecked()) {
            if (pricEntered.startsWith("00")) {
                PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_valid_price), null);
                PersonaliseSingleton.getInstance().setVideoMsgChecked(!PersonaliseSingleton.getInstance().isVideoMsgChecked());
                navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isVideoMsgChecked());
            }
        }


        PersonaliseSingleton.getInstance().setVideoMsgPrice(price);
        notifyPropertyChanged(BR.videoMsgPrice);
    }


    public void onCheckboxClicked() {

        String pricEntered = PersonaliseSingleton.getInstance().getVideoMsgPrice();
        pricEntered = pricEntered.isEmpty() ? "0" : pricEntered.trim();
        if (Double.parseDouble(pricEntered) == 0 && !PersonaliseSingleton.getInstance().isVideoMsgChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_servicecost_msg), null);
            return;
        }
        if (pricEntered.length()==3 && !PersonaliseSingleton.getInstance().isVideoMsgChecked()) {
            if(pricEntered.startsWith("00")) {
                PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_valid_price), null);
                return;
            }
        }
        PersonaliseSingleton.getInstance().setVideoMsgChecked(!PersonaliseSingleton.getInstance().isVideoMsgChecked());
        navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isVideoMsgChecked());
    }

    public void onClickStart() {

        if (!PersonaliseSingleton.getInstance().isLiveSelfieChecked() && !PersonaliseSingleton.getInstance().isPhotoSelfieChecked()
                && !PersonaliseSingleton.getInstance().isLiveVideoChecked() && !PersonaliseSingleton.getInstance().isVideoMsgChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), "Please choose atleast one service", null);
            return;
        }
/*
        if (PersonaliseSingleton.getInstance().isPhotoSelfieChecked()) {
            if (PersonaliseSingleton.getInstance().getPhotoSelfiePrice().isEmpty()) {
                PSRUtils.showAlert(fragment.getActivity(), "Please enter cost of service you are providing", null);
                return;
            }
        }
        if (PersonaliseSingleton.getInstance().isLiveVideoChecked()) {
            if (PersonaliseSingleton.getInstance().getLiveVideoPrice().isEmpty()) {
                PSRUtils.showAlert(fragment.getActivity(), "Please enter cost of service you are providing", null);
                return;
            }
        }
        if (PersonaliseSingleton.getInstance().isVideoMsgChecked()) {
            if (PersonaliseSingleton.getInstance().getVideoMsgPrice().isEmpty()) {
                PSRUtils.showAlert(fragment.getActivity(), "Please enter cost of service you are providing", null);
                return;
            }
        }*/

/*
        if (PSRUtils.isOnline(fragment.getActivity())) {
            PSRUtils.showProgressDialog(fragment.getActivity());
            List<Double> serviceCosts = new ArrayList<>();
            List<Integer> services = new ArrayList<>();
            if (PersonaliseSingleton.getInstance().isLiveSelfieChecked()) {
                services.add(PSRConstants.LIVESELFIE_SERVICE_REQ_ID);
                if (!PersonaliseSingleton.getInstance().getLiveSelfiePrice().isEmpty())
                    serviceCosts.add(Double.parseDouble(PersonaliseSingleton.getInstance().getLiveSelfiePrice()));
            }
            if (PersonaliseSingleton.getInstance().isPhotoSelfieChecked()) {
                services.add(PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID);
                if (!PersonaliseSingleton.getInstance().getPhotoSelfiePrice().isEmpty())
                    serviceCosts.add(Double.parseDouble(PersonaliseSingleton.getInstance().getPhotoSelfiePrice()));
            }
            if (PersonaliseSingleton.getInstance().isLiveVideoChecked()) {
                services.add(PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID);
                if (!PersonaliseSingleton.getInstance().getLiveVideoPrice().isEmpty())
                    serviceCosts.add(Double.parseDouble(PersonaliseSingleton.getInstance().getLiveVideoPrice()));
            }

            if (PersonaliseSingleton.getInstance().isVideoMsgChecked()) {
                services.add(PSRConstants.VIDEOMSGS_SERVICE_REQ_ID);
                if (!PersonaliseSingleton.getInstance().getVideoMsgPrice().isEmpty())
                    serviceCosts.add(Double.parseDouble(PersonaliseSingleton.getInstance().getVideoMsgPrice()));
            }
            UpdateCelbServReq updateCelbServReq = new UpdateCelbServReq();
            updateCelbServReq.setServiceCosts(serviceCosts);
            updateCelbServReq.setServiceIds(services);
            updateCelbServReq.setCelebrityId(fragment.getPreferences().get(PSRConstants.USER_ID));
            updateCelbServicesPresenter.updateServices(PSRUtils.getHeader(fragment.getPreferences()), updateCelbServReq);
        } else {
            PSRUtils.showNoNetworkAlert(fragment.getActivity());
        }*/

    }

    @Override
    public void onUpdatingCelbServicesSuccess(UpdateCelbServResponse response) {
        fragment.getPreferences().save(PSRConstants.LIVE_SELFIES_COUNT, response.getInfo().getLiveSelfiesCount().toString());
        fragment.getPreferences().save(PSRConstants.PHOTO_SELFIES_COUNT, response.getInfo().getPhotoSelfiesCount().toString());
        fragment.getPreferences().save(PSRConstants.LIVE_VIDEOS_COUNT, response.getInfo().getLiveVideosCount().toString());
        fragment.getPreferences().save(PSRConstants.VIDEO_MSGS_COUNT, response.getInfo().getVideoMessagesCount().toString());

        boolean isLiveSelfieEnabled, isPhotoSelfieEnabled, isVideoMsgEnabled, isLiveVideoEnabled;
        if (response.getInfo().getServicesOffering() != null) {
            Gson gson = new Gson();
            String json = gson.toJson(response.getInfo().getServicesOffering());
            fragment.getPreferences().save("SERVICESLIST", json);


            for (ServicesOffering servicesOffering : response.getInfo().getServicesOffering()) {
                if (servicesOffering.getServiceId() == PSRConstants.LIVESELFIE_SERVICE_REQ_ID) {
                    isLiveSelfieEnabled = true;
                    fragment.getPreferences().save(PSRConstants.LIVESELIFEACTIVE, isLiveSelfieEnabled);
                } else if (servicesOffering.getServiceId() == PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID) {
                    isPhotoSelfieEnabled = true;
                    fragment.getPreferences().save(PSRConstants.PHOTOSELIFEACTIVE, isPhotoSelfieEnabled);
                } else if (servicesOffering.getServiceId() == PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID) {
                    isLiveVideoEnabled = true;
                    fragment.getPreferences().save(PSRConstants.LIVEVIDEOACTIVE, isLiveVideoEnabled);
                } else if (servicesOffering.getServiceId() == PSRConstants.VIDEOMSGS_SERVICE_REQ_ID) {
                    isVideoMsgEnabled = true;
                    fragment.getPreferences().save(PSRConstants.VIDEOMSGACTIVE, isVideoMsgEnabled);
                }
            }
        }


        PSRUtils.hideProgressDialog();
        Intent intent = new Intent(fragment.getActivity(), DashBoardActivity.class);
        fragment.getActivity().startActivity(intent);
        fragment.getActivity().finish();

    }

    @Override
    public void onUpdatingCelbServicesFailure(UpdateCelbServResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(fragment.getActivity(), response.getMessage(), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
    }

    @Override
    public void onServerError() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(fragment.getActivity(), fragment.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public Context getMvpContext() {
        return null;
    }

    @Override
    public void onError(Throwable throwable) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(fragment.getActivity(), fragment.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onNoInternetConnection() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showNoNetworkAlert(fragment.getActivity());
    }

    @Override
    public void onErrorCode(String s) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(fragment.getActivity(), fragment.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }
}
