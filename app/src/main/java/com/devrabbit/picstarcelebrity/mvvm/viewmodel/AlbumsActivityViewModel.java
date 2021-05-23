package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import com.devrabbit.picstarcelebrity.mvvm.view.activities.AlbumsActivity;

public class AlbumsActivityViewModel {
    AlbumsActivity activity;

    public AlbumsActivityViewModel(AlbumsActivity activity) {
        this.activity = activity;
    }


    public void onClickBack() {
        activity.finish();
    }
}
