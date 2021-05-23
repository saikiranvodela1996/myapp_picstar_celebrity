package com.devrabbit.picstarcelebrity.utils;


import com.devrabbit.picstarcelebrity.BuildConfig;

public interface PSRConstants {
       String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    String S3BUCKETACCESSKEYID = "AKIAZ2HMNTJVTXUKXGVN";
    String S3BUCKETSECRETACCESSKEY = "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";

    String PROFILEPICSBUCKETNAME = "picstar/profile_pics";

    String STOCKPHOTOSBUCKETNAME = "picstar/stock_photos";

    int VIDEOMSGDURATION = 10;
    String VIDEOMSGSBUCKETNAME = "picstar/video_messages";

    String THUMBNAILSBUCKETNAME = "picstar/thumbnails_new";
    //String THUMBNAILSBUCKETNAME = "picstar/thumbnails";
    int LIVE_VIDEO_SERVICE_REQ_ID = 4;
    int VIDEOMSGS_SERVICE_REQ_ID = 3;
    int PHOTOSELFIE_SERVICE_REQ_ID = 1;
    int LIVESELFIE_SERVICE_REQ_ID = 2;
    String SERVICE_REQUEST_TYPE_ID = "service_request_type_id";
    String LIVE_SELFIES_COUNT = "liveSelfiesCount";
    String PHOTO_SELFIES_COUNT = "photoSelfiesCount";
    String VIDEO_MSGS_COUNT = "videoMsgsCount";
    String LIVE_VIDEOS_COUNT = "liveVideosCount";
    String LIVESELIFEACTIVE = "liveSelfieActive";
    String CELEBRITYROLE = "celebrityRole";
    int LOAD_PIC_BITMAP_WIDTH_HEIGHT = 512;
    String VIDEOURL = "videoUrl";
    String LIVESELFIE_COST = "liveSelfieCost";
    String PHOTOSELFIE_COST = "photoSelfieCost";
    String LIVEVIDEOCOST = "liveVideoCost";
    String VIDEOMSGCOST = "videoMsgCost";
    String ISLOGGEDIN = "isLoggedIn";
    String ISSERVICESSELECTED = "isServicesSelected";
    String PHOTOSELIFEACTIVE = "photoSelfieActive";
    String VIDEOMSGACTIVE = "videoMsgActive";
    String LIVEVIDEOACTIVE = "liveVideoActive";

    String SERVICE_ID = "service_id";
    String FANUSERID="fanUserId";

    String IMAGE_FILE_EXTENSION = ".jpeg";
    String TWILIO_ACCESS_TOKEN="twilioAccessToken";
    String TWILIOROOMNAME="twilioRoomName";

    String APP_HEADER = "Authorization";
    String TOKEN = "bearerToken";
    String USER_ID = "userID";
    String USERNAME = "userName";
    String COVERPIC = "coverPic";
    String PROFILEPIC = "profilePic";
    String GENDER = "gender";

    String PRIVACY_POLICY_URL = "privacy_policy_url";
    String CONTACT_US_PHONE_NO = "contact_us_phone_no";
    String CONTACT_US_EMAIL = "contact_us_email";
    String CONTACT_US_ADDRESS = "contact_us_address";

    String DATE_TIME_FORMAT_IN_FILE_NAMES = "yyyyMMdd_HHmmss";
    class Base_API {
        public static final String BASE_URL = BuildConfig.BASE_URL;
    }

}
