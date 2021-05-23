package com.devrabbit.picstarcelebrity.mvvm.model;


import androidx.annotation.Nullable;

public class AddEventModel {


    public AddEventModel(@Nullable String eventName, @Nullable String venueName, @Nullable String eventDescription, @Nullable String eventDate) {
        this.eventName = eventName;
        this.venueName = venueName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
    }

    @Nullable
    public String getEventName() {
        return eventName;
    }

    public void setEventName(@Nullable String eventName) {
        this.eventName = eventName;
    }

    @Nullable
    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(@Nullable String venueName) {
        this.venueName = venueName;
    }

    @Nullable
    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(@Nullable String eventDescription) {
        this.eventDescription = eventDescription;
    }

    @Nullable
    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(@Nullable String eventDate) {
        this.eventDate = eventDate;
    }

    @Nullable
    String eventName;
    @Nullable
    String venueName;
    @Nullable
    String eventDescription;
    @Nullable
    String eventDate;

}
