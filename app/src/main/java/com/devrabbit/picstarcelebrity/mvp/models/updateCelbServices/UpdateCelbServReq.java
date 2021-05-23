package com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices;

import com.squareup.moshi.Json;

import java.util.List;



public class UpdateCelbServReq {

@Json(name = "service_ids")
private List<Integer> serviceIds = null;
@Json(name = "service_costs")
private List<Double> serviceCosts = null;
@Json(name = "celebrity_id")
private String celebrityId;

public List<Integer> getServiceIds() {
return serviceIds;
}

public void setServiceIds(List<Integer> serviceIds) {
this.serviceIds = serviceIds;
}

public List<Double> getServiceCosts() {
return serviceCosts;
}

public void setServiceCosts(List<Double> serviceCosts) {
this.serviceCosts = serviceCosts;
}

public String getCelebrityId() {
return celebrityId;
}

public void setCelebrityId(String celebrityId) {
this.celebrityId = celebrityId;
}

}