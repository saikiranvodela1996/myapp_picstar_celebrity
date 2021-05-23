package com.devrabbit.picstarcelebrity.mvp.models.login;


import com.squareup.moshi.Json;

public class ServiceDetails {

@Json(name = "service_id")
private Integer serviceId;
@com.squareup.moshi.Json(name = "service_name")
private String serviceName;
@com.squareup.moshi.Json(name = "active")
private Boolean active;

public Integer getServiceId() {
return serviceId;
}

public void setServiceId(Integer serviceId) {
this.serviceId = serviceId;
}

public String getServiceName() {
return serviceName;
}

public void setServiceName(String serviceName) {
this.serviceName = serviceName;
}

public Boolean getActive() {
return active;
}

public void setActive(Boolean active) {
this.active = active;
}

}