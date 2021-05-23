package com.devrabbit.picstarcelebrity.mvvm.model;

public class UPdateProfileModel

{

    public String username;
    public String gender;
    public String profilePic;
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public UPdateProfileModel(String username, String gender, String profilePic) {
        this.gender=gender;
        this.username=username;
        this.profilePic=profilePic;
    }

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
}
