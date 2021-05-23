package com.devrabbit.picstarcelebrity.mvp.models.updateprofile;

public class Info {

    @com.squareup.moshi.Json(name = "username")
    private String username;
    @com.squareup.moshi.Json(name = "gender")
    private String gender;
    @com.squareup.moshi.Json(name = "profile_pic")
    private String profilePic;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


}
