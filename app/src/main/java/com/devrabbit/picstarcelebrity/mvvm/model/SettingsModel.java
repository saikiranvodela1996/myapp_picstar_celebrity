package com.devrabbit.picstarcelebrity.mvvm.model;

import androidx.annotation.Nullable;

public class SettingsModel {
    @Nullable
    String liveSelfiePrice="";
    @Nullable
    String photoSelfiePrice="";
    @Nullable
    String videoMsgPrice="";
    @Nullable
    String liveVideoPrice="";



    boolean isLiveSelfieActive,isPhotoSelfieActive,isLiveVideoActive,isVideoMsgActive=false;

    public boolean isLiveSelfieActive() {
        return isLiveSelfieActive;
    }

    public void setLiveSelfieActive(boolean liveSelfieActive) {
        isLiveSelfieActive = liveSelfieActive;
    }

    public boolean isPhotoSelfieActive() {
        return isPhotoSelfieActive;
    }

    public void setPhotoSelfieActive(boolean photoSelfieActive) {
        isPhotoSelfieActive = photoSelfieActive;
    }

    public boolean isLiveVideoActive() {
        return isLiveVideoActive;
    }

    public void setLiveVideoActive(boolean liveVideoActive) {
        isLiveVideoActive = liveVideoActive;
    }

    public boolean isVideoMsgActive() {
        return isVideoMsgActive;
    }

    public void setVideoMsgActive(boolean videoMsgActive) {
        isVideoMsgActive = videoMsgActive;
    }

    public String getLiveSelfiePrice() {
        return liveSelfiePrice;
    }

    public void setLiveSelfiePrice(String liveSelfiePrice) {
        this.liveSelfiePrice = liveSelfiePrice;
    }

    public String getPhotoSelfiePrice() {
        return photoSelfiePrice;
    }

    public void setPhotoSelfiePrice(String photoSelfiePrice) {
        this.photoSelfiePrice = photoSelfiePrice;
    }

    public String getVideoMsgPrice() {
        return videoMsgPrice;
    }

    public void setVideoMsgPrice(String videoMsgPrice) {
        this.videoMsgPrice = videoMsgPrice;
    }

    public String getLiveVideoPrice() {
        return liveVideoPrice;
    }

    public void setLiveVideoPrice(String liveVideoPrice) {
        this.liveVideoPrice = liveVideoPrice;
    }
}
