package com.devrabbit.picstarcelebrity.network;


import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventRequest;
import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventResponce;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockFanRequest;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockingFanResponse;
import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.createevent.CreateEventResponse;
import com.devrabbit.picstarcelebrity.mvp.models.forgotpassword.ForgotPasswordRequest;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.StockImagesResponse;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginRequest;
import com.devrabbit.picstarcelebrity.mvp.models.login.LoginResponse;
import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsResponse;
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

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PSRApi {

    @POST("login_celebrity")
    Observable<LoginResponse> doLogin(@Body LoginRequest loginRequest);


    @PUT("updateCelebrityServices")
    Observable<UpdateCelbServResponse> doUpdateCelbServices(@Body UpdateCelbServReq req);


    @GET("getCelebrityDashboard")
    Observable<UpdateCelbServResponse> doGetCelbServices(@Query("celebrity_id") String id);



    @GET("getAllEventsOfCelebrity?per_page=10")
    Observable<FetchAllEventsResponse> getAllEventsOfCelebrity(@Query("celebrity_id") String id, @Query("page") int param1);

    @POST("createEvent")
    Observable<CreateEventResponse> doCreate(@Body AddEventRequest req);



    @GET("getHistoryOfCelebrity")
    Observable<CelebrityHistoryResponse> getCelebrityHistory(@Query("celebrity_id") String id,
                                                             @Query("status") String status,
                                                             @Query("page") String page,
                                                             @Query("per_page") String perPage);





    @PUT("updateServiceRequestMedia")
    Observable<UploadVideoResponse> doUploadVideo(@Body UploadVideoReq req);




    @GET("getTwilioAccessToken")
    Observable<GetTwilioAccessTokenResp> getAccessToken(@Query("service_request_id") int serviceReqID);



    @GET("getStockPhotosOfCelebrity")
    Observable<StockImagesResponse> getStockPhotosOfCelebrity(@Query("user_id") String id,
                                                              @Query("page") String page,
                                                              @Query("per_page") String perPage);


    @POST("saveStockPhotosOfCelebrity")
    Observable<UploadStockImagesResponse> doUploadStockImages(@Body UploadStockImagesRequest request);



    @GET("getAlbumOfCelebrity")
    Observable<PhotosAlbumResponse> getAlbumOfCelebrity(@Query("celebrity_id") String id,
                                                        @Query("status") String status,
                                                        @Query("page") String page,
                                                        @Query("album_type") String albumType,
                                                        @Query("per_page") String perPage);

    @PUT("updateCelebrityDetails")
    Observable<UpdateProfileResponce> doUpdateProfilePic(@Body UpdateProfileRequest updateProfileRequest);


    @GET("getServiceRequestsOfCelebrity")
    Observable<CelebrityHistoryResponse> getServiceRequestsOfCelebrity(@Query("celebrity_id") String celebrity_id,
                                                                       @Query("service_request_type") int service_request_type,
                                                                       @Query("status") String status,
                                                                       @Query("page") int page,
                                                                       @Query("per_page") int perPage);

    @POST("https://dev-fwhugmus.us.auth0.com/dbconnections/change_password")
    Observable<ResponseBody> sendPasswordChangeRequest(@Body ForgotPasswordRequest request);


    @POST("blockUser")
    Observable<BlockingFanResponse> doBlockFan(@Body BlockFanRequest updateProfileRequest);






}
