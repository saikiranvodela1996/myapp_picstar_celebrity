package com.devrabbit.picstarcelebrity.mvp.models.photosalbum;


public class Photo {

@com.squareup.moshi.Json(name = "photoId")
private Integer photoId;
@com.squareup.moshi.Json(name = "user_id")
private String userId;
@com.squareup.moshi.Json(name = "photo_url")
private String photoUrl;
@com.squareup.moshi.Json(name = "photo_type")
private Integer photoType;
@com.squareup.moshi.Json(name = "created_at")
private String createdAt;
@com.squareup.moshi.Json(name = "updated_at")
private String updatedAt;

public Integer getPhotoId() {
return photoId;
}

public void setPhotoId(Integer photoId) {
this.photoId = photoId;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getPhotoUrl() {
return photoUrl;
}

public void setPhotoUrl(String photoUrl) {
this.photoUrl = photoUrl;
}

public Integer getPhotoType() {
return photoType;
}

public void setPhotoType(Integer photoType) {
this.photoType = photoType;
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

}