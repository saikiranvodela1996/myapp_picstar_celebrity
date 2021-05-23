package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.PhotosAlbumResponse;

public interface CelebrityAlbumView extends BaseMvpView {

    void onGettingPhotosAlbumSuccess(PhotosAlbumResponse response);

    void onGettingPhotosAlbumFailed(PhotosAlbumResponse response);

}
