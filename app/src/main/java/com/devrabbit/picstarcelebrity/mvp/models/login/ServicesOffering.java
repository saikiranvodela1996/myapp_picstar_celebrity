package com.devrabbit.picstarcelebrity.mvp.models.login;

import com.squareup.moshi.Json;

public class ServicesOffering {

    @Json(name = "id")
    private Integer id;
    @com.squareup.moshi.Json(name = "user_id")
    private String userId;
    @com.squareup.moshi.Json(name = "service_id")
    private Integer serviceId;
    @com.squareup.moshi.Json(name = "service_cost")
    private Double serviceCost;
    @com.squareup.moshi.Json(name = "service_details")
    private ServiceDetails serviceDetails;

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

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public ServiceDetails getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(ServiceDetails serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

}