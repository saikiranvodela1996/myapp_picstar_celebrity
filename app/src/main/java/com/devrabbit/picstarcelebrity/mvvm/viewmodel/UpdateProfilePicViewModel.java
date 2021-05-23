package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;

import com.devrabbit.picstarcelebrity.mvp.models.updateprofile.UpdateProfileRequest;
import com.devrabbit.picstarcelebrity.mvp.models.updateprofile.UpdateProfileResponce;
import com.devrabbit.picstarcelebrity.mvp.presenters.UpdateProfilePicPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.UpdateProfilePicView;
import com.devrabbit.picstarcelebrity.mvvm.model.UPdateProfileModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.UpdateProfilePic;
import com.devrabbit.picstarcelebrity.navigator.OnOkButtonClick;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import br.com.packapps.retropicker.callback.CallbackPicker;
import br.com.packapps.retropicker.config.Retropicker;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateProfilePicViewModel extends BaseObservable implements UpdateProfilePicView, OnOkButtonClick {
    UpdateProfilePic updateProfilePic;
    Activity activity;
    PSR_PrefsManager psr_prefsManager;
    UPdateProfileModel model;
    UpdateProfilePicPresenter updateProfilePicPresenter;
    private static final int CAMERA_REQUEST = 1888;
    @BindView(R.id.male_text_view)
    TextView male_textView;
    @BindView(R.id.female_text_view)
    TextView female_textView;
    @BindView(R.id.other_male_text_view)
    TextView others_textView;
    private String selectedPhotoPath = "";
    private String clickedPhotoPath;

    public UpdateProfilePicViewModel(UpdateProfilePic updateProfilePic, PSR_PrefsManager psr_prefsManager) {

        this.updateProfilePic = updateProfilePic;
        this.psr_prefsManager = psr_prefsManager;
        this.activity = updateProfilePic;
        updateProfilePicPresenter = new UpdateProfilePicPresenter();
        ButterKnife.bind(updateProfilePic);
        this.model = new UPdateProfileModel("", "", "");
        setUsername(psr_prefsManager.get(PSRConstants.USERNAME));
        setGender(psr_prefsManager.get(PSRConstants.GENDER));
        updateProfilePicPresenter.attachMvpView(this);
        checkRunTimePermissions();
    }

    @Bindable
    public String getUsername() {
        return model.username;


    }

    public void setUsername(String username) {
        model.setUsername(username);
        notifyPropertyChanged(BR.username);

    }

    @Bindable
    public String getGender() {
        return model.gender;


    }

    public void onClickCameraIcon() {
        showDialog();
    }

    public void setGender(String gender) {
        model.setGender(gender);
        notifyPropertyChanged(BR.gender);
    }


    public void onMaleClicked() {
        model.setGender("M");
        updateProfilePic.onMaleClicked();
    }

    public void onFeMaleClicked() {
        model.setGender("F");
        updateProfilePic.onFeMaleClicked();
    }

    public void onOtherClicked() {
        model.setGender("O");
        updateProfilePic.onOtherClicked();
    }

    public void onBackClicked() {
        updateProfilePic.finish();
    }

    public void onUpdateProfilePicClicked() {

        if (model.getUsername().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_username_txt), null);
            return;
        }
        if (model.getGender().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.select_gender_txt), null);
            return;
        }


        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            if (!selectedPhotoPath.isEmpty() && selectedPhotoPath != null) {
                Thread myUploadTask = new Thread(new Runnable() {
                    public void run() {
                        if (PSRUtils.isOnline(updateProfilePic)) {

                            AmazonS3Client S3Client;
                            String pictureName;
                            S3Client = new AmazonS3Client(new BasicAWSCredentials(PSRConstants.S3BUCKETACCESSKEYID, PSRConstants.S3BUCKETSECRETACCESSKEY));
                            S3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
                            pictureName = UUID.randomUUID().toString();
                            PutObjectRequest por = new PutObjectRequest(PSRConstants.PROFILEPICSBUCKETNAME, pictureName, new java.io.File(selectedPhotoPath));
                            S3Client.putObject(por);
                            URL url = S3Client.getUrl(PSRConstants.PROFILEPICSBUCKETNAME, pictureName);
                            Log.d("S3 PROFILEPIC", url + "");
                            updateProfile(url.toString());
                        }
                    }
                });
                myUploadTask.start();
            } else {
                updateProfile(psr_prefsManager.get(PSRConstants.PROFILEPIC));
                Log.d("PROFILEPIC", "UPLOADING OLD PROFILE PIC");
            }

        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }

    }


    public void updateProfile(String url) {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setGender(model.gender);
        updateProfileRequest.setProfilePic(url);
        updateProfileRequest.setUsername(model.getUsername());
        updateProfileRequest.setUser_id(psr_prefsManager.get(PSRConstants.USER_ID));
        updateProfilePicPresenter.doUpdateProfile(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(psr_prefsManager), updateProfileRequest);

    }


    @Override
    public void onUpdateDone(UpdateProfileResponce response) {
        selectedPhotoPath = "";
        PSRUtils.hideProgressDialog();
        psr_prefsManager.save(PSRConstants.USERNAME, response.getInfo().getUsername());
        psr_prefsManager.save(PSRConstants.PROFILEPIC, response.getInfo().getProfilePic());
        psr_prefsManager.save(PSRConstants.GENDER, response.getInfo().getGender());
        PSRUtils.showButtonActionAlert(activity, response.getMessage(), null, this);
    }

    @Override
    public void onUpdateFailed(UpdateProfileResponce response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
        PSRUtils.doLogout(activity, psr_prefsManager);
    }

    @Override
    public void onServerError() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public Context getMvpContext() {
        return null;
    }

    @Override
    public void onError(Throwable throwable) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onNoInternetConnection() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showNoNetworkAlert(activity);
    }

    @Override
    public void onErrorCode(String s) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }


    public void checkRunTimePermissions() {
        Dexter.withContext(activity)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted

                        if (report.areAllPermissionsGranted()) {
                            //runnable.run();
                        } else {
                            PSRUtils.showToast(activity, activity.getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            PSRUtils.showToast(activity, activity.getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        PSRUtils.showToast(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt));
                    }
                })
                .onSameThread()
                .check();
    }

    private static final int CLICK_PHOTO = 5;

    private void showDialog() {
        final String[] items = {activity.getResources().getString(R.string.gallery_txt), activity.getResources().getString(R.string.camera_txt)};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.chooseoption_txt));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int clickedPosition) {
                if (clickedPosition == 0) {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        openGalleryApp();
                    } else {
                        PSRUtils.showToast(activity, activity.getResources().getString(R.string.gallery_runtimepermission_alert_txt));
                    }

                } else if (clickedPosition == 1) {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //  launchCamera();
                       launchCameranew(CLICK_PHOTO);
                    } else {
                        PSRUtils.showToast(activity, activity.getResources().getString(R.string.cam_Permision_deny_txt));
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void openGalleryApp() {
        try {
            Retropicker.Builder builder = new Retropicker.Builder(activity)
                    .setTypeAction(Retropicker.GALLERY_PICKER) //Para abrir a galeria passe Retropicker.GALLERY_PICKER
                    .checkPermission(true);

            builder.enquee(new CallbackPicker() {
                @Override
                public void onSuccess(Bitmap bitmap, String uri) {
                    if (bitmap != null) {
                        //  Uri uri = getImageUri(MyProfileActivity.this, bitmap);
                        CropImage.activity(Uri.parse(uri))
                                .setAllowFlipping(false)
                                .setAspectRatio(1, 1)
                                .setCropMenuCropButtonTitle("Done")
                                .start(activity);

                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    PSRUtils.showToast(activity, e.getLocalizedMessage());
                }
            });
            Retropicker retropicker = builder.create();
            retropicker.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void launchCamera() {
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
/*
            Retropicker.Builder builder = new Retropicker.Builder(activity)
                    .setTypeAction(Retropicker.CAMERA_PICKER)
                    .checkPermission(true);

            builder.enquee(new CallbackPicker() {
                @Override
                public void onSuccess(Bitmap bitmap, String imagePath) {
                    try {
                        if (bitmap != null) {
                            Uri uri = null;
                            ExifInterface exif = new ExifInterface(imagePath);
                            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                            int rotationInDegrees = exifToDegrees(rotation);
                            if (rotationInDegrees == 0) {
                                uri = getImageUri(activity, bitmap);
                            } else {
                                bitmap = rotateImage(bitmap, rotationInDegrees);
                                uri = getImageUri(activity, bitmap);
                            }

                            CropImage.activity(uri)
                                    .setAllowFlipping(false)
                                    .setAspectRatio(1, 1)
                                    .setCropMenuCropButtonTitle("Done")
                                    .start(activity);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Throwable e) {
                    PSRUtils.showToast(activity, e.getLocalizedMessage());
                }
            });

            Retropicker retropicker = builder.create();
            retropicker.open();*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    /*public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = Environment.getExternalStorageDirectory().toString();
        File imageFile = new File(path, "picstar_profile.jpg");
        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(imageFile);


      *//*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);*//*


    }*/
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void photoPath(String photoPath) {
        this.selectedPhotoPath = photoPath;
    }


    @Override
    public void onClickOk() {
        activity.finish();
    }


    public void launchCameranew(int clickPhotoRequestCode) {
        try {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            clickedPhotoPath = PSRUtils.getClickedPhotoPath(this.activity.getApplicationContext());

            if (clickedPhotoPath == null) {
                //   showToast("");
                PSRUtils.showAlert(activity, "Something went wrong.", null);
                return;
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(clickedPhotoPath)));
            updateProfilePic.setClickedPhotoPath(clickedPhotoPath);
            activity.startActivityForResult(Intent.createChooser(intent, "Click Photo"), clickPhotoRequestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
