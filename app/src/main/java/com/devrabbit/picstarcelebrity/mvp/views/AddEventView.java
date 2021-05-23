package com.devrabbit.picstarcelebrity.mvp.views;


import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventResponce;
import com.devrabbit.picstarcelebrity.mvp.models.createevent.CreateEventResponse;


public interface AddEventView extends BaseMvpView {

    void onCreatingEventSuccess(CreateEventResponse response);
    void  onCreatingEventFailed(CreateEventResponse response);


}
