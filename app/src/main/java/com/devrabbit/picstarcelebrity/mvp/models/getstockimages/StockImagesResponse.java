package com.devrabbit.picstarcelebrity.mvp.models.getstockimages;

import java.util.List;

public class StockImagesResponse {

@com.squareup.moshi.Json(name = "status")
private String status;
@com.squareup.moshi.Json(name = "message")
private String message;
@com.squareup.moshi.Json(name = "info")
private List<Info> info = null;

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

public List<Info> getInfo() {
return info;
}

public void setInfo(List<Info> info) {
this.info = info;
}

}