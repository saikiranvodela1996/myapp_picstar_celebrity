package com.devrabbit.picstarcelebrity.mvp.views;


import com.devrabbit.picstarcelebrity.mvp.models.updateprofile.UpdateProfileResponce;

public interface UpdateProfilePicView extends BaseMvpView {


    void onUpdateDone(UpdateProfileResponce response);
    void  onUpdateFailed(UpdateProfileResponce response);
}
