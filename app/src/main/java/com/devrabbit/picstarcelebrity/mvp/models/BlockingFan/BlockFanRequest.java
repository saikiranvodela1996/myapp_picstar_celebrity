package com.devrabbit.picstarcelebrity.mvp.models.BlockingFan;

import com.squareup.moshi.Json;

public class BlockFanRequest {

    @Json(name = "user_id")
    String userID;

    @Json(name = "blocked_by")
    String blockedBy;

    @Json(name = "block_comments")
    String comments;

    public String getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(String blockedBy) {
        this.blockedBy = blockedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
