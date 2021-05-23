package com.devrabbit.picstarcelebrity.network;


import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventRequest;
import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventResponce;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockFanRequest;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockingFanResponse;
import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.allservices.CelebrityServiceRequest;
import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsResponse;
import com.devrabbit.picstarcelebrity.mvp.models.createevent.CreateEventResponse;
import com.devrabbit.picstarcelebrity.mvp.models.forgotpassword.ForgotPasswordRequest;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.StockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginRequest;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginResponse;

import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.PhotosAlbumResponse;
import com.devrabbit.picstarcelebrity.mvp.models.twilioaccesstoken.GetTwilioAccessTokenResp;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServReq;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;
import com.devrabbit.picstarcelebrity.mvp.models.updateprofile.UpdateProfileRequest;
import com.devrabbit.picstarcelebrity.mvp.models.updateprofile.UpdateProfileResponce;
import com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages.UploadStockImagesRequest;
import com.devrabbit.picstarcelebrity.mvp.models.uploadstockimages.UploadStockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoReq;
import com.devrabbit.picstarcelebrity.mvp.models.uploadvideo.UploadVideoResponse;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.HistoryRequest;
import com.devrabbit.picstarcelebrity.utils.getmethodmodels.StockImageRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class PSRService {

    private static PSRService instance;
    private static String headerValue = null;
    private static String language = "";

    private Retrofit retrofit;

    private PSRApi psrApi;

    private static final int TIMEOUT = 20;
    //    private static final int TIMEOUT = 600;
    private Request request;

    public static PSRService getInstance(String lang, String header) {
        if (instance == null) {
            instance = new PSRService();
        }
        language = lang;
        headerValue = header;
        return instance;
    }

    private PSRService() {
        if (retrofit == null || psrApi == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(PSRConstants.Base_API.BASE_URL)
                    .client(createOkHttpClientInterceptor())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();

            psrApi = retrofit.create(PSRApi.class);
        }
    }

    private OkHttpClient createOkHttpClientInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();

                if (headerValue != null && !headerValue.isEmpty()) {
                    builder.header(PSRConstants.APP_HEADER, headerValue);
                    builder.addHeader("Accept-Language",language);
                }
                builder.method(original.method(), original.body());
                request = builder.build();
                return chain.proceed(request);
            }
        };

        return new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT,
                TimeUnit.SECONDS).addInterceptor(interceptor)
                .addNetworkInterceptor(headerInterceptor).build();
    }


    public Observable<LoginResponse> doLogin(LoginRequest request) {
        return psrApi.doLogin(request);
    }

    public Observable<UpdateCelbServResponse> doUpdateCelbServices(UpdateCelbServReq request) {
        return psrApi.doUpdateCelbServices(request);
    }

    public Observable<UpdateCelbServResponse> doGetCelbServices(String id) {
        return psrApi.doGetCelbServices(id);
    }

    public Observable<FetchAllEventsResponse> getAllEventsOfCeleb(String user_id, int i) {
        return psrApi.getAllEventsOfCelebrity(user_id, i);
    }

    public Observable<CreateEventResponse> doCreate(AddEventRequest request) {
        return psrApi.doCreate(request);
    }

    public Observable<CelebrityHistoryResponse> getCelebrityHistory(HistoryRequest model) {
        return psrApi.getCelebrityHistory(model.getId(), model.getStatus(), model.getPage(), model.getPerPage());
    }


    public Observable<UploadVideoResponse> doUploadVideo(UploadVideoReq request) {
        return psrApi.doUploadVideo(request);
    }


    public Observable<GetTwilioAccessTokenResp> getAccessToken(int id) {
        return psrApi.getAccessToken(id);
    }


    public Observable<StockImagesResponse> getStockPhotosOfCelebrity(StockImageRequest model) {
        return psrApi.getStockPhotosOfCelebrity(model.getId(), model.getPage(), model.getPerPage());
    }

    public Observable<UploadStockImagesResponse> doUploadStockImages(UploadStockImagesRequest request) {
        return psrApi.doUploadStockImages(request);
    }


    public Observable<PhotosAlbumResponse> getAlbum(StockImageRequest model) {
        return psrApi.getAlbumOfCelebrity(model.getId(), model.getStatus(), model.getPage(), model.getAlbumType(), model.getPerPage());
    }


    public Observable<UpdateProfileResponce> doUpdateProfilePic(UpdateProfileRequest request) {
        return psrApi.doUpdateProfilePic(request);
    }


    public Observable<CelebrityHistoryResponse> getServiceRequestsOfCelebrity(CelebrityServiceRequest model) {
        return psrApi.getServiceRequestsOfCelebrity(model.getUserId(), model.getServiceRequestType(), model.getStatus(),
                model.getPage(), model.getPerPage());
    }


    public Observable<ResponseBody> sendPasswordChangeRequest(ForgotPasswordRequest request) {
        return psrApi.sendPasswordChangeRequest(request);
    }





    public Observable<BlockingFanResponse> doBlockFan(BlockFanRequest request) {
        return psrApi.doBlockFan(request);
    }

}
