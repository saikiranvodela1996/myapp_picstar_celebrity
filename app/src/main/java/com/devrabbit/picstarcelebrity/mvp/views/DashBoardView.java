package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;

public  interface DashBoardView extends BaseMvpView {
    void onGettingServiceDetailsSuccess(UpdateCelbServResponse response);
    void onGettingServiceDetailsFailed(UpdateCelbServResponse response);
    void celebrityDoesNotExist();
}
