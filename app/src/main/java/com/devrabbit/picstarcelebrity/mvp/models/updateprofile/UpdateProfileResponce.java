package com.devrabbit.picstarcelebrity.mvp.models.updateprofile;

public class UpdateProfileResponce

{
    @com.squareup.moshi.Json(name = "status")
    private String status;
    @com.squareup.moshi.Json(name = "message")
    private String message;
    @com.squareup.moshi.Json(name = "info")
    private com.devrabbit.picstarcelebrity.mvp.models.updateprofile.Info info;

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

       public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
