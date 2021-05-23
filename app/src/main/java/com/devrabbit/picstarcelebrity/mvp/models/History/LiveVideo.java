package com.devrabbit.picstarcelebrity.mvp.models.History;

public class LiveVideo {

@com.squareup.moshi.Json(name = "live_video_id")
private Integer liveVideoId;
@com.squareup.moshi.Json(name = "user_id")
private String userId;
@com.squareup.moshi.Json(name = "celebrity_id")
private String celebrityId;
@com.squareup.moshi.Json(name = "live_video_name")
private String liveVideoName;
@com.squareup.moshi.Json(name = "live_video_desc")
private String liveVideoDesc;
@com.squareup.moshi.Json(name = "live_video_datetime")
private String liveVideoDatetime;
@com.squareup.moshi.Json(name = "live_video_status")
private Integer liveVideoStatus;

public Integer getLiveVideoId() {
return liveVideoId;
}

public void setLiveVideoId(Integer liveVideoId) {
this.liveVideoId = liveVideoId;
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

public String getLiveVideoName() {
return liveVideoName;
}

public void setLiveVideoName(String liveVideoName) {
this.liveVideoName = liveVideoName;
}

public String getLiveVideoDesc() {
return liveVideoDesc;
}

public void setLiveVideoDesc(String liveVideoDesc) {
this.liveVideoDesc = liveVideoDesc;
}

public String getLiveVideoDatetime() {
return liveVideoDatetime;
}

public void setLiveVideoDatetime(String liveVideoDatetime) {
this.liveVideoDatetime = liveVideoDatetime;
}

public Integer getLiveVideoStatus() {
return liveVideoStatus;
}

public void setLiveVideoStatus(Integer liveVideoStatus) {
this.liveVideoStatus = liveVideoStatus;
}

}