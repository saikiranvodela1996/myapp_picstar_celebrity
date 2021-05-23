package com.devrabbit.picstarcelebrity.mvvm.model;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;


import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.Info;
import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.PhotosAlbumResponse;

import java.util.List;

public class PhotosAlbumModel   {

    MutableLiveData<PhotosAlbumResponse> photosAlbum;
    @Nullable
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MutableLiveData<PhotosAlbumResponse> getPhotosAlbum() {
        return photosAlbum;
    }

    public void setPhotosAlbumsList(MutableLiveData<PhotosAlbumResponse> photosAlbumsList) {
        this.photosAlbum = photosAlbumsList;
    }

    public PhotosAlbumModel() {

        photosAlbum = new MutableLiveData<>();
    }
}
