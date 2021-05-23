package com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages;

import java.util.List;

public class UploadStockImagesRequest {

@com.squareup.moshi.Json(name = "celebrity_id")
private String celebrityId;
@com.squareup.moshi.Json(name = "photo_urls")
private List<String> photoUrls = null;
@com.squareup.moshi.Json(name = "photo_type")
private Integer photoType;

public String getCelebrityId() {
return celebrityId;
}

public void setCelebrityId(String celebrityId) {
this.celebrityId = celebrityId;
}

public List<String> getPhotoUrls() {
return photoUrls;
}

public void setPhotoUrls(List<String> photoUrls) {
this.photoUrls = photoUrls;
}

public Integer getPhotoType() {
return photoType;
}

public void setPhotoType(Integer photoType) {
this.photoType = photoType;
}

}