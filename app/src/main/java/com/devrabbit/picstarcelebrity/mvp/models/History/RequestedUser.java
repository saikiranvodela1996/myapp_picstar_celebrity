package com.devrabbit.picstarcelebrity.mvp.models.History;

public class RequestedUser {

    @com.squareup.moshi.Json(name = "userId")
    private String userId;
    @com.squareup.moshi.Json(name = "role_id")
    private Integer roleId;
    @com.squareup.moshi.Json(name = "email")
    private String email;
    @com.squareup.moshi.Json(name = "username")
    private String username;
    @com.squareup.moshi.Json(name = "auth0_username")
    private String auth0Username;
    @com.squareup.moshi.Json(name = "first_name")
    private String firstName;
    @com.squareup.moshi.Json(name = "last_name")
    private String lastName;
    @com.squareup.moshi.Json(name = "gender")
    private String gender;
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
    private Object coverPic;
    @com.squareup.moshi.Json(name = "user_location")
    private Object userLocation;
    @com.squareup.moshi.Json(name = "user_desc")
    private Object userDesc;
    @com.squareup.moshi.Json(name = "phone_number")
    private String phoneNumber;
    @com.squareup.moshi.Json(name = "dob")
    private String dob;
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
    @com.squareup.moshi.Json(name = "total_transactions_amount")
    private Double totalTransactionsAmount;
    @com.squareup.moshi.Json(name = "categories_of_celebrity")
    private Object categoriesOfCelebrity;
    @com.squareup.moshi.Json(name = "services_offering")
    private Object servicesOffering;
    @com.squareup.moshi.Json(name = "live_selfies_count")
    private Integer liveSelfiesCount;
    @com.squareup.moshi.Json(name = "photo_selfies_count")
    private Integer photoSelfiesCount;
    @com.squareup.moshi.Json(name = "live_videos_count")
    private Integer liveVideosCount;
    @com.squareup.moshi.Json(name = "video_messages_count")
    private Integer videoMessagesCount;
    @com.squareup.moshi.Json(name = "last_request")
    private Object lastRequest;
    @com.squareup.moshi.Json(name = "is_blocked")
    private boolean isBlocked;

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public Object getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(Object coverPic) {
        this.coverPic = coverPic;
    }

    public Object getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Object userLocation) {
        this.userLocation = userLocation;
    }

    public Object getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(Object userDesc) {
        this.userDesc = userDesc;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
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

    public Double getTotalTransactionsAmount() {
        return totalTransactionsAmount;
    }

    public void setTotalTransactionsAmount(Double totalTransactionsAmount) {
        this.totalTransactionsAmount = totalTransactionsAmount;
    }

    public Object getCategoriesOfCelebrity() {
        return categoriesOfCelebrity;
    }

    public void setCategoriesOfCelebrity(Object categoriesOfCelebrity) {
        this.categoriesOfCelebrity = categoriesOfCelebrity;
    }

    public Object getServicesOffering() {
        return servicesOffering;
    }

    public void setServicesOffering(Object servicesOffering) {
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

    public Object getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Object lastRequest) {
        this.lastRequest = lastRequest;
    }

}