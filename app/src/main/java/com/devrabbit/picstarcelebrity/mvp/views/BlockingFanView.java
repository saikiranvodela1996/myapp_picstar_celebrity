package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockingFanResponse;

public interface BlockingFanView extends BaseMvpView {
    void onBlockingSuccess(BlockingFanResponse response);

    void onBlockingFailure(BlockingFanResponse response);
}
