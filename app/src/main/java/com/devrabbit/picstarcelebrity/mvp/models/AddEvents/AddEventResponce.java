package com.devrabbit.picstarcelebrity.mvp.models.AddEvents;

import com.squareup.moshi.Json;

import java.util.List;

public class AddEventResponce {
    @Json(name = "status")
    public String status;
    @Json(name = "message")
    public String message;
    @Json(name = "info")
    public List<AddEventInfo> info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AddEventInfo> getInfo() {
        return info;
    }

    public void setInfo(List<AddEventInfo> info) {
        this.info = info;
    }
}
