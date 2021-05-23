package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;

public  interface UpdateCelbServicesView extends BaseMvpView {


    void onUpdatingCelbServicesSuccess(UpdateCelbServResponse response);
    void onUpdatingCelbServicesFailure(UpdateCelbServResponse response);
}
