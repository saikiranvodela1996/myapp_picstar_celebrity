package com.devrabbit.picstarcelebrity.mvp.models.login;


import com.squareup.moshi.Json;

import java.util.List;

public class Info {

    @Json(name = "userId")
    private String userId;
    @Json(name = "role_id")
    private Integer roleId;
    @Json(name = "email")
    private String email;
    @Json(name = "username")
    private String username;
    @com.squareup.moshi.Json(name = "auth0_username")
    private String auth0Username;
    @com.squareup.moshi.Json(name = "first_name")
    private String firstName;
    @com.squareup.moshi.Json(name = "last_name")
    private String lastName;
    @com.squareup.moshi.Json(name = "gender")
    private Object gender;
    @com.squareup.moshi.Json(name = "fcm_reg_token")
    private String fcmRegToken;
    @com.squareup.moshi.Json(name = "device_type")
    private String deviceType;
    @com.squareup.moshi.Json(name = "device_id")
    private String deviceId;
    @com.squareup.moshi.Json(name = "login_status")
    private Integer loginStatus;
    @com.squareup.moshi.Json(name = "profile_pic")
    private String profilePic;
    @com.squareup.moshi.Json(name = "cover_pic")
    private String coverPic;
    @com.squareup.moshi.Json(name = "user_location")
    private String userLocation;
    @com.squareup.moshi.Json(name = "user_desc")
    private String userDesc;
    @com.squareup.moshi.Json(name = "phone_number")
    private Object phoneNumber;
    @com.squareup.moshi.Json(name = "dob")
    private Object dob;
    @com.squareup.moshi.Json(name = "created_at")
    private String createdAt;
    @com.squareup.moshi.Json(name = "updated_at")
    private String updatedAt;
    @com.squareup.moshi.Json(name = "login_type")
    private Integer loginType;
    @com.squareup.moshi.Json(name = "is_deleted")
    private Boolean isDeleted;
    @com.squareup.moshi.Json(name = "category_id")
    private Integer categoryId;
    @com.squareup.moshi.Json(name = "likes_count")
    private Integer likesCount;
    @com.squareup.moshi.Json(name = "favs_count")
    private Integer favsCount;
    @com.squareup.moshi.Json(name = "categories_of_celebrity")
    private Object categoriesOfCelebrity;
    @com.squareup.moshi.Json(name = "services_offering")
    private List<ServicesOffering> servicesOffering = null;
    @com.squareup.moshi.Json(name = "live_selfies_count")
    private Integer liveSelfiesCount;
    @com.squareup.moshi.Json(name = "photo_selfies_count")
    private Integer photoSelfiesCount;
    @com.squareup.moshi.Json(name = "live_videos_count")
    private Integer liveVideosCount;
    @com.squareup.moshi.Json(name = "video_messages_count")
    private Integer videoMessagesCount;

    @com.squareup.moshi.Json(name = "privacy_policy_url")
    private String privacyPolicyUrl;
    @com.squareup.moshi.Json(name = "contactus_phone_num")
    private String contactUsPhoneNum;

    public String getPrivacyPolicyUrl() {
        return privacyPolicyUrl;
    }

    public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
        this.privacyPolicyUrl = privacyPolicyUrl;
    }

    public String getContactUsPhoneNum() {
        return contactUsPhoneNum;
    }

    public void setContactUsPhoneNum(String contactUsPhoneNum) {
        this.contactUsPhoneNum = contactUsPhoneNum;
    }

    public String getContactUsEmail() {
        return contactUsEmail;
    }

    public void setContactUsEmail(String contactUsEmail) {
        this.contactUsEmail = contactUsEmail;
    }

    public String getContactUsAddress() {
        return contactUsAddress;
    }

    public void setContactUsAddress(String contactUsAddress) {
        this.contactUsAddress = contactUsAddress;
    }

    @com.squareup.moshi.Json(name = "contactus_email")
    private String contactUsEmail;
    @com.squareup.moshi.Json(name = "contactus_address")
    private String contactUsAddress;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuth0Username() {
        return auth0Username;
    }

    public void setAuth0Username(String auth0Username) {
        this.auth0Username = auth0Username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getFcmRegToken() {
        return fcmRegToken;
    }

    public void setFcmRegToken(String fcmRegToken) {
        this.fcmRegToken = fcmRegToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
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

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getFavsCount() {
        return favsCount;
    }

    public void setFavsCount(Integer favsCount) {
        this.favsCount = favsCount;
    }

    public Object getCategoriesOfCelebrity() {
        return categoriesOfCelebrity;
    }

    public void setCategoriesOfCelebrity(Object categoriesOfCelebrity) {
        this.categoriesOfCelebrity = categoriesOfCelebrity;
    }

    public List<ServicesOffering> getServicesOffering() {
        return servicesOffering;
    }

    public void setServicesOffering(List<ServicesOffering> servicesOffering) {
        this.servicesOffering = servicesOffering;
    }

    public Integer getLiveSelfiesCount() {
        return liveSelfiesCount;
    }

    public void setLiveSelfiesCount(Integer liveSelfiesCount) {
        this.liveSelfiesCount = liveSelfiesCount;
    }

    public Integer getPhotoSelfiesCount() {
        return photoSelfiesCount;
    }

    public void setPhotoSelfiesCount(Integer photoSelfiesCount) {
        this.photoSelfiesCount = photoSelfiesCount;
    }

    public Integer getLiveVideosCount() {
        return liveVideosCount;
    }

    public void setLiveVideosCount(Integer liveVideosCount) {
        this.liveVideosCount = liveVideosCount;
    }

    public Integer getVideoMessagesCount() {
        return videoMessagesCount;
    }

    public void setVideoMessagesCount(Integer videoMessagesCount) {
        this.videoMessagesCount = videoMessagesCount;
    }
}