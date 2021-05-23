package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.ServiceProgressActivity;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ServiceTabsViewModel extends BaseObservable {
    ServiceProgressActivity activity;
    String title;

    public ServiceTabsViewModel(ServiceProgressActivity activity, int serviceRequestTypeId) {
        this.activity = activity;
        if (serviceRequestTypeId == PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID)
            title = activity.getResources().getString(R.string.photoSelfie_txt);
        else if (serviceRequestTypeId == PSRConstants.LIVESELFIE_SERVICE_REQ_ID)
            title = activity.getResources().getString(R.string.liveselfie_txt);
        else if (serviceRequestTypeId == PSRConstants.VIDEOMSGS_SERVICE_REQ_ID)
            title = activity.getResources().getString(R.string.videomsg_txt);
        else if (serviceRequestTypeId == PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID)
            title = activity.getResources().getString(R.string.live_video_txt);
    }




    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

}