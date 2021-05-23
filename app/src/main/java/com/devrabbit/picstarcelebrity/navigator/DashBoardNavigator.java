package com.devrabbit.picstarcelebrity.navigator;

import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;

public interface DashBoardNavigator {
    void onGettingServicesSuccess(UpdateCelbServResponse response);
}
