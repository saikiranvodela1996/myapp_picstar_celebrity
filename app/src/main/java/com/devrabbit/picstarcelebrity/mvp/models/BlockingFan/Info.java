package com.devrabbit.picstarcelebrity.mvp.models.BlockingFan;

public class Info {

@com.squareup.moshi.Json(name = "id")
private Integer id;
@com.squareup.moshi.Json(name = "user_id")
private String userId;
@com.squareup.moshi.Json(name = "blocked_by")
private String blockedBy;
@com.squareup.moshi.Json(name = "block_comments")
private String blockComments;
@com.squareup.moshi.Json(name = "blocked_at")
private String blockedAt;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getBlockedBy() {
return blockedBy;
}

public void setBlockedBy(String blockedBy) {
this.blockedBy = blockedBy;
}

public String getBlockComments() {
return blockComments;
}

public void setBlockComments(String blockComments) {
this.blockComments = blockComments;
}

public String getBlockedAt() {
return blockedAt;
}

public void setBlockedAt(String blockedAt) {
this.blockedAt = blockedAt;
}

}