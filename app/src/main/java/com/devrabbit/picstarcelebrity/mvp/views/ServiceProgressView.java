package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.twilioaccesstoken.GetTwilioAccessTokenResp;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoResponse;


public interface ServiceProgressView extends BaseMvpView {

    void onSuccess(CelebrityHistoryResponse response);

    void onFail(CelebrityHistoryResponse response);

    void onUploadingVideoSuccess(UploadVideoResponse response);
    void onUploadingVideoFailure(UploadVideoResponse response);

    void ongettingTokenSuccess(GetTwilioAccessTokenResp resp, int id);
    void ongettingTokenFailure(GetTwilioAccessTokenResp resp);
}