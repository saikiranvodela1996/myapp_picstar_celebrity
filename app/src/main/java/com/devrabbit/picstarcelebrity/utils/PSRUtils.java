package com.devrabbit.picstarcelebrity.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.amazonaws.AmazonServiceException;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;


import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.customui.ProgressHUD;
import com.devrabbit.picstarcelebrity.databinding.ActivityAlbumBinding;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.LoginActivity;
import com.devrabbit.picstarcelebrity.navigator.OnOkButtonClick;
import com.devrabbit.picstarcelebrity.videocompressor.VideoCompress;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.devrabbit.picstarcelebrity.mvvm.view.activities.CompressMainActivity.getSystemLocale;
import static com.devrabbit.picstarcelebrity.mvvm.view.activities.CompressMainActivity.getSystemLocaleLegacy;
import static com.devrabbit.picstarcelebrity.utils.PSRConstants.DATE_TIME_FORMAT_IN_FILE_NAMES;
import static com.devrabbit.picstarcelebrity.utils.PSRConstants.IMAGE_FILE_EXTENSION;


public class PSRUtils {

    private static ProgressHUD progressHUD;

    private static long startTime, endTime;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    static SimpleDateFormat createdDateFormat = new SimpleDateFormat("MM/dd/yy");
    static SimpleDateFormat evetDateFormat = new SimpleDateFormat("MM/dd/yyyy, h:mm a");
    static String[] monthName = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
            "AUG", "SEP", "OCT", "NOV",
            "DEC"};

    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getId(Activity activity) {

        String androidId = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public static String getHeader(PSR_PrefsManager psr_prefsManager) {

        return psr_prefsManager.get(PSRConstants.TOKEN);
    }

    public static boolean isOnline(Context activity) {
        if (activity != null) {
            NetworkInfo info = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return (info != null && info.getState() == NetworkInfo.State.CONNECTED);
        }
        return false;
    }


    public static void doLogout(Activity activity, PSR_PrefsManager psr_prefsManager) {
        psr_prefsManager.clearAllPrefs();
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }


    public static void showNoNetworkAlert(final Activity act) {
        if (act != null) {
            PSRUtils.alertMessage(act, act.getResources().getString(R.string.noInternet_txt), null);
        }
    }

    public static String sanitizeTime(int minuteOrHour) {
        if (minuteOrHour < 10) {
            return "0" + minuteOrHour;
        }
        return String.valueOf(minuteOrHour);
    }

    public static class InternetDialogViewHolder {

        @BindView(R.id.internet_alert_tv_message)
        TextView message;
        @BindView(R.id.singlebtn_alert_btn)
        TextView okayButtonm;

        InternetDialogViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public static void alertMessage(final Activity act, String message, Runnable runnable) {
        if (act != null) {
            try {
                int width = act.getResources().getDisplayMetrics().widthPixels;
                final Dialog dialog = new Dialog(act, R.style.DialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view = act.getLayoutInflater().inflate(R.layout.single_message_layout, null);
                InternetDialogViewHolder viewHolder = new InternetDialogViewHolder(view);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(false);


                viewHolder.message.setText(message);
                viewHolder.okayButtonm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog == null || dialog.getWindow() == null) {
                    return;
                }
                int dialogWidth = (int) (width * 0.80f);
               /* if (act.getResources().getBoolean(R.bool.isTablet)) {
                    dialogWidth = (int) (width * 0.75f);
                }*/
                int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(dialogWidth, dialogHeight);
                dialog.show();
                dialog.setOnDismissListener(dialog1 -> {
                    if (runnable != null) {
                        runnable.run();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void showProgressDialog(Activity activity) {
        if (activity != null) {
            try {
                if (progressHUD != null && progressHUD.isShowing()) {
                    progressHUD.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressHUD = ProgressHUD.show(activity, "", false, true, null);
            }
        }
    }


    public static void hideProgressDialog() {
        try {
            Log.d("ProgressBar", "hideProgressDialog");
            if (progressHUD != null && progressHUD.isShowing()) {
                progressHUD.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getServiceCost(String cost) {
        String serviceCost = "";
        if (cost.endsWith(".0")) {
            serviceCost = cost.replace(".0", "");
        } else {
            serviceCost = cost;
        }
        return serviceCost;

    }

    public static void showAlert(final Activity act, String message, Runnable runnable) {
        if (act != null) {
            try {
                int width = act.getResources().getDisplayMetrics().widthPixels;
                final Dialog dialog = new Dialog(act, R.style.DialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view = act.getLayoutInflater().inflate(R.layout.single_message_layout, null);
                InternetDialogViewHolder viewHolder = new InternetDialogViewHolder(view);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
                viewHolder.message.setText(message);
                viewHolder.okayButtonm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog == null || dialog.getWindow() == null) {
                    return;
                }
                int dialogWidth = (int) (width * 0.80f);

                int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(dialogWidth, dialogHeight);
                dialog.show();

                dialog.setOnDismissListener(dialog1 -> {
                    if (runnable != null) {
                        runnable.run();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void showButtonActionAlert(final Activity act, String message, Runnable runnable, OnOkButtonClick onOkButtonClick) {
        if (act != null) {
            try {
                int width = act.getResources().getDisplayMetrics().widthPixels;
                final Dialog dialog = new Dialog(act, R.style.DialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view = act.getLayoutInflater().inflate(R.layout.single_message_layout, null);
                InternetDialogViewHolder viewHolder = new InternetDialogViewHolder(view);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
                viewHolder.message.setText(message);
                viewHolder.okayButtonm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOkButtonClick.onClickOk();
                        dialog.dismiss();
                    }
                });
                if (dialog == null || dialog.getWindow() == null) {
                    return;
                }
                int dialogWidth = (int) (width * 0.80f);

                int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(dialogWidth, dialogHeight);
                dialog.show();

                dialog.setOnDismissListener(dialog1 -> {
                    if (runnable != null) {
                        runnable.run();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] getEventDate(String dtStart) {
        String[] values = new String[3];
        try {
            Date date = format.parse(dtStart.replaceAll("Z$", "+0000"));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            values[0] = evetDateFormat.format(date);
            values[1] = "" + calendar.get(Calendar.DAY_OF_MONTH);
            values[2] = monthName[calendar.get(Calendar.MONTH)];
            return values;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return values;
    }

    public static String getCreatedDate(String dtStart) {
        try {
            Date date = format.parse(dtStart.replaceAll("Z$", "+0000"));
            return createdDateFormat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }


    public static void uploadImage(int index, Uri uri, UploadToS3Interface callback, Activity activity) {
        Thread myUploadTask = new Thread(new Runnable() {
            AmazonS3Client s3Client;
            String pictureName;

            //  final String bucketName = "picstar/stock_photos";
            public void run() {
                try {
                    s3Client = new AmazonS3Client(new BasicAWSCredentials(PSRConstants.S3BUCKETACCESSKEYID, PSRConstants.S3BUCKETSECRETACCESSKEY));
                    s3Client.setRegion(RegionUtils.getRegion("us-west-2"));
                    pictureName = UUID.randomUUID().toString() + ".jpeg";
                    String filePath = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        filePath = PSRUtils.getPath(activity, uri);
                    }
                    File file = new File(filePath);
                    PutObjectRequest por = new PutObjectRequest(PSRConstants.STOCKPHOTOSBUCKETNAME, pictureName, file);
                    s3Client.putObject(por);
                    URL url = s3Client.getUrl(PSRConstants.STOCKPHOTOSBUCKETNAME, pictureName);
                    String uploadedPath = url.getProtocol() + "://" + url.getHost() + url.getPath();
                    callback.onImageUploaded(index, uploadedPath, "");
                } catch (Exception e) {
                    e.printStackTrace();
                    PSRUtils.hideProgressDialog();
                }

            }
        });
        myUploadTask.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static Calendar changeTimezoneOfDate(Date date, TimeZone fromTZ, TimeZone toTZ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long millis = calendar.getTimeInMillis();
        long fromOffset = fromTZ.getOffset(millis);
        long toOffset = toTZ.getOffset(millis);
        long convertedTime = millis - (fromOffset - toOffset);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(convertedTime);
        return c;
    }

    public static void checkStoragePermissionToProgress(Activity activity, Runnable runnable) {
        try {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (runnable != null)
                    runnable.run();
            } else {
                Dexter.withContext(activity)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                if (runnable != null)
                                    runnable.run();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                //Triggers when user denied the permission permanently
                                if (response.isPermanentlyDenied()) {
                                    Toast.makeText(activity, R.string.gallery_runtimepermission_alert_txt, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, R.string.gallery_runtimepermission_alert_txt, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void showImageAlert(Context context, String imageString) {
        try {

            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_image_fullview, null);
            LinearLayout frameLayout = (LinearLayout) dialogView.findViewById(R.id.linear_Layout);
            final ImageView imageView = (ImageView) dialogView.findViewById(R.id.imageFullView);

            ProgressBar progressBar = (ProgressBar) dialogView.findViewById(R.id.progressbar);
            ImageView closeBtn = (ImageView) dialogView.findViewById(R.id.buttonClose);


            Glide.with(context)
                    .load(imageString)
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_pholder))

                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
            Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setContentView(dialogView);

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });


            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File bitmapToFile(Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return file; // it will return null
        }
    }

    public static void uploadVideo(int index, Uri uri, UploadToS3Interface callback, Activity activity) {

        //  String path= getThumbnailPathForLocalFile(activity,uri);
        //Log.d("THUMBPATH", "" + path);

        String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + File.separator + "VID_" /*+ new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale(activity)).format(new Date()) */ + ".mp4";
        // String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "picstarvideo" + File.separator + "VID_" + ".mp4";
        // String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picstarvideo";

        File f = new File(destPath);
        if (f.exists()) {
            f.delete();
        }
        String inputPath = "";
        long fileSize = 0;
        try {
            inputPath = Util.getFilePath(activity, uri);
            if (inputPath != null) {

                fileSize = getFileSizeinMb(new File(inputPath));
                //  fileSize = Integer.parseInt(String.valueOf(new File(inputPath).length() / 1024));
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(inputPath, MediaStore.Video.Thumbnails.MINI_KIND);
                File thumnailFile = bitmapToFile(thumb, "thumnail");
                Log.d("THUMBPATH", "" + thumnailFile);
                uploadThumailToS3(callback, thumnailFile.getAbsolutePath(), activity, fileSize, destPath, inputPath, index);
            } else {
                PSRUtils.hideProgressDialog();
                PSRUtils.showAlert(activity, "Please try again", null);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            PSRUtils.hideProgressDialog();
        }


    }


    private static void compressingVideo(UploadToS3Interface callback, Activity activity, long fileSize, String inputPath, String destPath, int index, String thumbNailPath) {


        if (fileSize > 10) {
            VideoCompress.compressVideoLow(inputPath, destPath, new VideoCompress.CompressListener() {
                @Override
                public void onStart() {
                    int i = 0;
                    startTime = System.currentTimeMillis();
                    Util.writeFile(activity, "Start at: " + new SimpleDateFormat("HH:mm:ss", getLocale(activity)).format(new Date()) + "\n");
                }

                @Override
                public void onSuccess() {
                    endTime = System.currentTimeMillis();
                    Util.writeFile(activity, "End at: " + new SimpleDateFormat("HH:mm:ss", getLocale(activity)).format(new Date()) + "\n");
                    Util.writeFile(activity, "Total: " + ((endTime - startTime) / 1000) + "s" + "\n");
                    Util.writeFile(activity);
                    File f = new File(destPath);
                    if (f.exists()) {
                        int i = 0;

                    }
                    int i = 0;
                    Log.d("COMPRESSED", "" + destPath);

                    // int compressedVideoFileSize = Integer.parseInt(String.valueOf(new File(destPath).length() / 1024));
                    long compressedVideoFileSize= getFileSizeinMb(new File(destPath));
                    if (compressedVideoFileSize >= 40) {
                        PSRUtils.hideProgressDialog();
                        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.cant_upload_selectedVideo), null);
                        return;
                    } else {
                        uploadToS3(callback, destPath, index, thumbNailPath);
                    }


                }

                @Override
                public void onFail() {
                    int i = 0;
                    PSRUtils.hideProgressDialog();
                    PSRUtils.showToast(activity, "Video uploading failed");
                }

                @Override
                public void onProgress(float percent) {
                    int i = 0;
                }
            });
        } else if (fileSize < 10) {
            uploadToS3(callback, inputPath, index, thumbNailPath);
            Log.d("NOT_COMPRESSED", "" + inputPath);
        }
    }

    public static long getFileSizeinMb(File file) {

// Get length of file in bytes
        long fileSizeInBytes = file.length();
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInMB;
    }


    private static void uploadThumailToS3(UploadToS3Interface call, String filePath, Activity activity, long fileSize, String desPath, String inputPath, int index) {
        Thread myUploadTask = new Thread(new Runnable() {
            AmazonS3Client s3Client;
            String pictureName;

            public void run() {
                try {
                    s3Client = new AmazonS3Client(new BasicAWSCredentials(PSRConstants.S3BUCKETACCESSKEYID, PSRConstants.S3BUCKETSECRETACCESSKEY));
                    s3Client.setRegion(RegionUtils.getRegion("us-west-2"));
                    pictureName = UUID.randomUUID().toString() + ".jpeg";

                    File file = new File(filePath);
                    PutObjectRequest por = new PutObjectRequest(PSRConstants.THUMBNAILSBUCKETNAME, pictureName, file);
                    s3Client.putObject(por);
                    URL url = s3Client.getUrl(PSRConstants.THUMBNAILSBUCKETNAME, pictureName);
                    String thumbNailPath = url.getProtocol() + "://" + url.getHost() + url.getPath();
                    Log.v("THUMBNAIL_UPLOADED_TOS3", thumbNailPath);
                    compressingVideo(call, activity, fileSize, inputPath, desPath, index, thumbNailPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    /// Log.v("EXCEPTION_OCCURED_IN_AMAZON", e.getLocalizedMessage());
                    PSRUtils.hideProgressDialog();

                }
            }


        });
        myUploadTask.start();
    }


    private static void uploadToS3(UploadToS3Interface callback, String filePath, int index, String thumbNailPath) {

////S3 buket..
        Thread myUploadTask = new Thread(new Runnable() {
            AmazonS3Client s3Client;
            String pictureName;

            public void run() {
                try {
                    s3Client = new AmazonS3Client(new BasicAWSCredentials(PSRConstants.S3BUCKETACCESSKEYID, PSRConstants.S3BUCKETSECRETACCESSKEY));
                    s3Client.setRegion(RegionUtils.getRegion("us-west-2"));
                    pictureName = UUID.randomUUID().toString();
                  /*  String filePath = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        filePath = PSRUtils.getPath(activity, filePath1);
                    }*/
                    File file = new File(filePath);
                    PutObjectRequest por = new PutObjectRequest(PSRConstants.VIDEOMSGSBUCKETNAME, pictureName, file);
                    s3Client.putObject(por);
                    URL url = s3Client.getUrl(PSRConstants.VIDEOMSGSBUCKETNAME, pictureName);
                    String uploadedPath = url.getProtocol() + "://" + url.getHost() + url.getPath();
                    Log.v("VIDEO_UPLOADED_TOS3", uploadedPath);
                    callback.onImageUploaded(index, uploadedPath, thumbNailPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    /// Log.v("EXCEPTION_OCCURED_IN_AMAZON", e.getLocalizedMessage());
                    PSRUtils.hideProgressDialog();

                }
            }


        });
        myUploadTask.start();

    }

    private static Locale getLocale(Activity activity) {
        Configuration config = activity.getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }


    public static String getClickedPhotoPath(Context c) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return getAppDirPath(c) + File.separator + getNewFileName() + IMAGE_FILE_EXTENSION;
        }
        return null;
    }

    public static String getAppDirPath(Context c) {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    public static String getNewFileName() {
        return "picstarcelebrity" + getCurrentTime();
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat(DATE_TIME_FORMAT_IN_FILE_NAMES, Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    public static int getPhotoOrientation(String photoPath) {
        try {
            switch (new ExifInterface(photoPath).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String createFilePath(Context c) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return getAppDirPath(c) + File.separator;
        }
        return null;
    }



    public static void hideKeyboardIfOpened(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
           /* InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
