package com.devrabbit.picstarcelebrity.mvp.models.twilioaccesstoken;


public class Info {

    @com.squareup.moshi.Json(name = "room")
    private String room;
    @com.squareup.moshi.Json(name = "access_token_for_celebrity")
    private String accessToken;
    @com.squareup.moshi.Json(name = "access_token_for_user")
    private String userAccessToken;

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}