package com.devrabbit.picstarcelebrity.mvvm.model;


import androidx.lifecycle.MutableLiveData;

import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.Info;

import java.util.List;

public class StockImagesModel {
    MutableLiveData<List<Info>> images;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StockImagesModel() {
        images = new MutableLiveData<>();
    }

    public MutableLiveData<List<Info>> getImages() {
        return images;
    }

    public void setImages(MutableLiveData<List<Info>> images) {
        this.images = images;
    }
}
