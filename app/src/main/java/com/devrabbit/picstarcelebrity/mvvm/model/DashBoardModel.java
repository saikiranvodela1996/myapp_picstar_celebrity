package com.devrabbit.picstarcelebrity.mvvm.model;

import androidx.annotation.Nullable;

public  class DashBoardModel {
    @Nullable
    String  liveSelfieCount,photoSelfieCount,videoMsgsCount,liveVideosCount,userName,message;

    @Nullable
    public String getMessage() {
        return message;
    }

    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    @Nullable
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nullable String userName) {
        this.userName = userName;
    }

    @Nullable
    public String getPhotoSelfieCount() {
        return photoSelfieCount;
    }

    public void setPhotoSelfieCount(@Nullable String photoSelfieCount) {
        this.photoSelfieCount = photoSelfieCount;
    }

    @Nullable
    public String getVideoMsgsCount() {
        return videoMsgsCount;
    }

    public void setVideoMsgsCount(@Nullable String videoMsgsCount) {
        this.videoMsgsCount = videoMsgsCount;
    }

    @Nullable
    public String getLiveVideosCount() {
        return liveVideosCount;
    }

    public void setLiveVideosCount(@Nullable String liveVideosCount) {
        this.liveVideosCount = liveVideosCount;
    }

    @Nullable
    public String getLiveSelfieCount() {
        return liveSelfieCount;
    }

    public void setLiveSelfieCount(@Nullable String liveSelfieCount) {
        this.liveSelfieCount = liveSelfieCount;
    }


}
