package com.devrabbit.picstarcelebrity.mvp.models.celebrityevents;

import com.squareup.moshi.Json;

public class FetchAllEventsInfo {

    @Json(name="event_name")
    private String event_name="Damn";

    @Json(name="event_desc")
    private String event_desc;

    @Json(name="event_location")
    private String event_location;

    @Json(name="event_date")
    private String event_date;

    @Json(name="user_id")
    private String user_id;

    @Json(name="event_type")
    private String event_type;

    @Json(name="created_at")
    private String created_at;

    @Json(name="updated_at")
    private String updated_at;

    @Json(name="created_requests")
    private String created_requests;

    @Json(name="event_id")
    private int event_id;

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_requests() {
        return created_requests;
    }

    public void setCreated_requests(String created_requests) {
        this.created_requests = created_requests;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
