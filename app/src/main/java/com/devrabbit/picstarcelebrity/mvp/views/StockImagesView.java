package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.StockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages.UploadStockImagesResponse;

public interface StockImagesView extends BaseMvpView {

    void onStockImagesSuccess(StockImagesResponse response);

    void onStockImagesFail(StockImagesResponse response);

    void uploadStockImagesSuccess(UploadStockImagesResponse response);

    void uploadStockImagesFail(UploadStockImagesResponse response);

}
