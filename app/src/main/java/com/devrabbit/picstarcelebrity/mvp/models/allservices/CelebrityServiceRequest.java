package com.devrabbit.picstarcelebrity.mvp.models.allservices;

public class CelebrityServiceRequest {

    String userId, status;
    int serviceRequestType, page, perPage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getServiceRequestType() {
        return serviceRequestType;
    }

    public void setServiceRequestType(int serviceRequestType) {
        this.serviceRequestType = serviceRequestType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}