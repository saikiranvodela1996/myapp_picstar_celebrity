package com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices;

import com.devrabbit.picstarcelebrity.mvp.models.login.ServiceDetails;
import com.squareup.moshi.Json;

public class ServicesOffering {

    @Json(name = "id")
    private Integer id;
    @Json(name = "user_id")
    private String userId;
    @Json(name = "service_id")
    private Integer serviceId;
    @Json(name = "service_cost")
    private Double serviceCost;
    @Json(name = "service_details")
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