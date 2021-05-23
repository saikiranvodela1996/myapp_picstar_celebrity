package com.devrabbit.picstarcelebrity.mvp.models.uploadvideo;


public class UploadVideoReq {

    @com.squareup.moshi.Json(name = "service_request_id")
    private Integer serviceRequestId;
    @com.squareup.moshi.Json(name = "file_path")
    private String filePath;

    @com.squareup.moshi.Json(name = "thumbnail_path")
    private String thumbNailPath;

    public String getThumbNailPath() {
        return thumbNailPath;
    }

    public void setThumbNailPath(String thumbNailPath) {
        this.thumbNailPath = thumbNailPath;
    }

    public Integer getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Integer serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}