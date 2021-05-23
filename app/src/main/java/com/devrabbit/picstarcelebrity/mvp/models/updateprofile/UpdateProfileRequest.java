package com.devrabbit.picstarcelebrity.mvp.models.updateprofile;

public class UpdateProfileRequest
{

    @com.squareup.moshi.Json(name = "username")
    private String username;
    @com.squareup.moshi.Json(name = "profile_pic")
    private String profilePic;
    @com.squareup.moshi.Json(name = "gender")
    private String gender;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @com.squareup.moshi.Json(name = "user_id")
    private String user_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
