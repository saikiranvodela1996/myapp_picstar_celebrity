package com.devrabbit.picstarcelebrity.utils;

public class PersonaliseSingleton {

    boolean isLiveSelfieChecked, isPhotoSelfieChecked, isVideoMsgChecked, isLiveVideoChecked;
    String liveSelfiePrice, photoSelfiePrice, liveVideoPrice, videoMsgPrice;
    static PersonaliseSingleton mInstance;


    public static PersonaliseSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new PersonaliseSingleton();
        }
        return mInstance;
    }

    public boolean isLiveSelfieChecked() {
        return isLiveSelfieChecked;
    }

    public void setLiveSelfieChecked(boolean liveSelfieChecked) {
        isLiveSelfieChecked = liveSelfieChecked;
    }

    public boolean isPhotoSelfieChecked() {
        return isPhotoSelfieChecked;
    }

    public void setPhotoSelfieChecked(boolean photoSelfieChecked) {
        isPhotoSelfieChecked = photoSelfieChecked;
    }

    public boolean isVideoMsgChecked() {
        return isVideoMsgChecked;
    }

    public void setVideoMsgChecked(boolean videoMsgChecked) {
        isVideoMsgChecked = videoMsgChecked;
    }

    public boolean isLiveVideoChecked() {
        return isLiveVideoChecked;
    }

    public void setLiveVideoChecked(boolean liveVideoChecked) {
        isLiveVideoChecked = liveVideoChecked;
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

    public String getLiveVideoPrice() {
        return liveVideoPrice;
    }

    public void setLiveVideoPrice(String liveVideoPrice) {
        this.liveVideoPrice = liveVideoPrice;
    }

    public String getVideoMsgPrice() {
        return videoMsgPrice;
    }

    public void setVideoMsgPrice(String videoMsgPrice) {
        this.videoMsgPrice = videoMsgPrice;
    }


}
