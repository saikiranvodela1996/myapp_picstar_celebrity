package com.devrabbit.picstarcelebrity.mvp.models.History;

public class VideoEvent {

    @com.squareup.moshi.Json(name = "video_event_id")
    private Integer videoEventId;
    @com.squareup.moshi.Json(name = "user_id")
    private String userId;
    @com.squareup.moshi.Json(name = "celebrity_id")
    private String celebrityId;
    @com.squareup.moshi.Json(name = "video_event_name")
    private String videoEventName;
    @com.squareup.moshi.Json(name = "video_event_desc")
    private String videoEventDesc;
    @com.squareup.moshi.Json(name = "video_event_date")
    private String videoEventDate;
    @com.squareup.moshi.Json(name = "video_event_status")
    private Integer videoEventStatus;

    public Integer getVideoEventId() {
        return videoEventId;
    }

    public void setVideoEventId(Integer videoEventId) {
        this.videoEventId = videoEventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCelebrityId() {
        return celebrityId;
    }

    public void setCelebrityId(String celebrityId) {
        this.celebrityId = celebrityId;
    }

    public String getVideoEventName() {
        return videoEventName;
    }

    public void setVideoEventName(String videoEventName) {
        this.videoEventName = videoEventName;
    }

    public String getVideoEventDesc() {
        return videoEventDesc;
    }

    public void setVideoEventDesc(String videoEventDesc) {
        this.videoEventDesc = videoEventDesc;
    }

    public String getVideoEventDate() {
        return videoEventDate;
    }

    public void setVideoEventDate(String videoEventDate) {
        this.videoEventDate = videoEventDate;
    }

    public Integer getVideoEventStatus() {
        return videoEventStatus;
    }

    public void setVideoEventStatus(Integer videoEventStatus) {
        this.videoEventStatus = videoEventStatus;
    }
}
