package com.devrabbit.picstarcelebrity.mvp.models.photosalbum;


public class Info {

    @com.squareup.moshi.Json(name = "service_request_id")
    private Integer serviceRequestId;
    @com.squareup.moshi.Json(name = "userId")
    private String userId;
    @com.squareup.moshi.Json(name = "celebrity_id")
    private String celebrityId;
    @com.squareup.moshi.Json(name = "service_request_type_id")
    private Integer serviceRequestTypeId;
    @com.squareup.moshi.Json(name = "status")
    private String status;
    @com.squareup.moshi.Json(name = "file_path")
    private String filePath;

    @com.squareup.moshi.Json(name = "thumbnail_path")
    private String thumbNailPath;


    @com.squareup.moshi.Json(name = "event_id")
    private Integer eventId;
    @com.squareup.moshi.Json(name = "photo_id")
    private Integer photoId;
    @com.squareup.moshi.Json(name = "video_event_id")
    private Integer videoEventId;
    @com.squareup.moshi.Json(name = "live_video_id")
    private Integer liveVideoId;
    @com.squareup.moshi.Json(name = "amount")
    private Double amount;
    @com.squareup.moshi.Json(name = "created_at")
    private String createdAt;
    @com.squareup.moshi.Json(name = "updated_at")
    private String updatedAt;
    @com.squareup.moshi.Json(name = "celebrity_user")
    private Object celebrityUser;
    @com.squareup.moshi.Json(name = "live_event")
    private Object liveEvent;
    @com.squareup.moshi.Json(name = "video_event")
    private VideoEvent videoEvent;
    @com.squareup.moshi.Json(name = "live_video")
    private Object liveVideo;
    @com.squareup.moshi.Json(name = "photo")
    private Photo photo;
    @com.squareup.moshi.Json(name = "requested_user")
    private Object requestedUser;

    public Integer getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Integer serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
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

    public Integer getServiceRequestTypeId() {
        return serviceRequestTypeId;
    }

    public void setServiceRequestTypeId(Integer serviceRequestTypeId) {
        this.serviceRequestTypeId = serviceRequestTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public Integer getVideoEventId() {
        return videoEventId;
    }

    public void setVideoEventId(Integer videoEventId) {
        this.videoEventId = videoEventId;
    }

    public Integer getLiveVideoId() {
        return liveVideoId;
    }

    public void setLiveVideoId(Integer liveVideoId) {
        this.liveVideoId = liveVideoId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getCelebrityUser() {
        return celebrityUser;
    }

    public void setCelebrityUser(Object celebrityUser) {
        this.celebrityUser = celebrityUser;
    }

    public Object getLiveEvent() {
        return liveEvent;
    }

    public void setLiveEvent(Object liveEvent) {
        this.liveEvent = liveEvent;
    }

    public VideoEvent getVideoEvent() {
        return videoEvent;
    }

    public void setVideoEvent(VideoEvent videoEvent) {
        this.videoEvent = videoEvent;
    }

    public Object getLiveVideo() {
        return liveVideo;
    }

    public void setLiveVideo(Object liveVideo) {
        this.liveVideo = liveVideo;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Object getRequestedUser() {
        return requestedUser;
    }

    public void setRequestedUser(Object requestedUser) {
        this.requestedUser = requestedUser;
    }

    public String getThumbNailPath() {
        return thumbNailPath;
    }

    public void setThumbNailPath(String thumbNailPath) {
        this.thumbNailPath = thumbNailPath;
    }
}