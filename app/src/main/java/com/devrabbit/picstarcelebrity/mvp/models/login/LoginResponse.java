package com.devrabbit.picstarcelebrity.mvp.models.login;


import com.squareup.moshi.Json;

public class LoginResponse {

    @Json(name = "status")
    private String status;
    @com.squareup.moshi.Json(name = "message")
    private Object message;
    @com.squareup.moshi.Json(name = "info")
    private Info info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}