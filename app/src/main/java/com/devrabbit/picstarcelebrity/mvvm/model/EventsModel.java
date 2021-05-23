package com.devrabbit.picstarcelebrity.mvvm.model;

import androidx.lifecycle.MutableLiveData;

import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsInfo;
import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsResponse;

import java.util.List;

public class EventsModel {

    MutableLiveData<FetchAllEventsResponse> events;
    String  message="";

    public EventsModel() {
        events= new MutableLiveData<>();
    }

    public MutableLiveData<FetchAllEventsResponse> getEvents() {
        return events;
    }

    public void setEvents(MutableLiveData<FetchAllEventsResponse> events) {
        this.events = events;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
