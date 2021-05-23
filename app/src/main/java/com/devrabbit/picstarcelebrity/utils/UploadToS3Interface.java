package com.devrabbit.picstarcelebrity.utils;

public interface UploadToS3Interface {
    void onImageUploaded(int index, String uploadedUrl,String thumbNailPath);
}
