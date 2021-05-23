package com.devrabbit.picstarcelebrity.mvp.models.createevent;


public class Info {

@com.squareup.moshi.Json(name = "event_id")
private Integer eventId;
@com.squareup.moshi.Json(name = "user_id")
private String userId;
@com.squareup.moshi.Json(name = "event_name")
private String eventName;
@com.squareup.moshi.Json(name = "event_desc")
private String eventDesc;
@com.squareup.moshi.Json(name = "event_location")
private String eventLocation;
@com.squareup.moshi.Json(name = "event_date")
private String eventDate;
@com.squareup.moshi.Json(name = "event_type")
private String eventType;
@com.squareup.moshi.Json(name = "created_at")
private String createdAt;
@com.squareup.moshi.Json(name = "updated_at")
private String updatedAt;
@com.squareup.moshi.Json(name = "created_by")
private String createdBy;
@com.squareup.moshi.Json(name = "updated_by")
private String updatedBy;
@com.squareup.moshi.Json(name = "created_requests")
private Integer createdRequests;

public Integer getEventId() {
return eventId;
}

public void setEventId(Integer eventId) {
this.eventId = eventId;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getEventName() {
return eventName;
}

public void setEventName(String eventName) {
this.eventName = eventName;
}

public String getEventDesc() {
return eventDesc;
}

public void setEventDesc(String eventDesc) {
this.eventDesc = eventDesc;
}

public String getEventLocation() {
return eventLocation;
}

public void setEventLocation(String eventLocation) {
this.eventLocation = eventLocation;
}

public String getEventDate() {
return eventDate;
}

public void setEventDate(String eventDate) {
this.eventDate = eventDate;
}

public String getEventType() {
return eventType;
}

public void setEventType(String eventType) {
this.eventType = eventType;
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

public String getCreatedBy() {
return createdBy;
}

public void setCreatedBy(String createdBy) {
this.createdBy = createdBy;
}

public String getUpdatedBy() {
return updatedBy;
}

public void setUpdatedBy(String updatedBy) {
this.updatedBy = updatedBy;
}

public Integer getCreatedRequests() {
return createdRequests;
}

public void setCreatedRequests(Integer createdRequests) {
this.createdRequests = createdRequests;
}

}