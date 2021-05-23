package com.devrabbit.picstarcelebrity.mvp.models.celebrityevents;

import com.squareup.moshi.Json;

import java.util.List;

public class FetchAllEventsResponse
{
    @Json(name = "status")
    String status;
    @Json(name = "message")
    String message;
    @Json(name = "info")
    private List<FetchAllEventsInfo> info = null;

    public List<FetchAllEventsInfo> getInfo() {
        return info;
    }

    public void setInfo(List<FetchAllEventsInfo> info) {
        this.info = info;
    }

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
}
