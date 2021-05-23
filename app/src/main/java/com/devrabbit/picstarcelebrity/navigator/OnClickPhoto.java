package com.devrabbit.picstarcelebrity.navigator;

public interface OnClickPhoto {

    void onClickPhoto(int serviceReqTypeID,String image);
    void onClickUploadBtn(int serviceReqID,int serviceReqTypeID);
    void  onClickBlockBtn(String fanID);
}
