package com.devrabbit.picstarcelebrity.mvp.models.BlockingFan;

public class BlockingFanResponse {

@com.squareup.moshi.Json(name = "status")
private String status;
@com.squareup.moshi.Json(name = "message")
private String message;
@com.squareup.moshi.Json(name = "info")
private Info info;
@com.squareup.moshi.Json(name = "count")
private Integer count;

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

public Integer getCount() {
return count;
}

public void setCount(Integer count) {
this.count = count;
}

}
