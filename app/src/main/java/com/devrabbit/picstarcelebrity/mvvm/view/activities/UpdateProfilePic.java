package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityUpdateProfilePicBinding;
import com.devrabbit.picstarcelebrity.helpers.BitmapHelper;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.UpdateProfilePicViewModel;
import com.devrabbit.picstarcelebrity.utils.FileUtils;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.net.URI;
import java.util.List;

import static com.devrabbit.picstarcelebrity.utils.PSRConstants.LOAD_PIC_BITMAP_WIDTH_HEIGHT;

public class UpdateProfilePic extends BaseActivity {

    UpdateProfilePicViewModel updateProfilePicViewModel;
    ActivityUpdateProfilePicBinding activityUpdateProfilePicBinding;
    ImageView imageView;
    TextView holder_name;
    TextView male_textView;
    String gender = "";
    TextView female_textView;
    private static final int CLICK_PHOTO = 5;
    String localImageFileFath = "";
    ImageView cameraImageView;
    private static final int CAMERA_REQUEST = 1888;
    public static final int REQUEST_GALLERY = 1;
    Button updateprofilebutton;
    TextView others_textView;
    String clickedPhotoPath = "";

    public void setClickedPhotoPath(String clickedPhotoPath) {
        this.clickedPhotoPath = clickedPhotoPath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateProfilePicBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile_pic);
        updateProfilePicViewModel = new UpdateProfilePicViewModel(this, psr_prefsManager);

        activityUpdateProfilePicBinding.setViewmodell(updateProfilePicViewModel);
        activityUpdateProfilePicBinding.executePendingBindings();

        imageView = activityUpdateProfilePicBinding.profilePic;
        updateprofilebutton = activityUpdateProfilePicBinding.uploadButton;
        male_textView = activityUpdateProfilePicBinding.maleTextView;
        cameraImageView = activityUpdateProfilePicBinding.cameraPic;
        female_textView = activityUpdateProfilePicBinding.femaleTextView;
        others_textView = activityUpdateProfilePicBinding.otherMaleTextView;
        holder_name = activityUpdateProfilePicBinding.userNameEdit;
        Log.v("ONCREATE_CALLED", "ONCREATE");

        holder_name.setText(psr_prefsManager.get(PSRConstants.USERNAME));
        if(savedInstanceState==null) {
            Glide.with(this).
                    load(psr_prefsManager.get(PSRConstants.PROFILEPIC)).placeholder(R.drawable.ic_profilepholder).into(imageView);
        }

        if (psr_prefsManager.get(PSRConstants.GENDER).equalsIgnoreCase("M")) {
            onMaleClicked();
        } else if (psr_prefsManager.get(PSRConstants.GENDER).equalsIgnoreCase("F")) {
            onFeMaleClicked();
        } else if (psr_prefsManager.get(PSRConstants.GENDER).equalsIgnoreCase("O")) {
            onOtherClicked();
        }

        if(savedInstanceState!=null){
            clickedPhotoPath=savedInstanceState.getString("PHOTOPATH");
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    public void onMaleClicked() {
        gender = "M";
        male_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked_radio_button, 0, 0, 0);
        female_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_uncheck, 0, 0, 0);
        others_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_uncheck, 0, 0, 0);
    }

    public void onFeMaleClicked() {
        gender = "F";
        male_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_uncheck, 0, 0, 0);
        female_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked_radio_button, 0, 0, 0);
        others_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_uncheck, 0, 0, 0);

    }

    public void onOtherClicked() {
        gender = "O";
        male_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_uncheck, 0, 0, 0);
        female_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_uncheck, 0, 0, 0);
        others_textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked_radio_button, 0, 0, 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("ONACTIVITYRESULT_CALLED", "ONACTIVYTRESULT");
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(result.getUri());
                updateProfilePicViewModel.photoPath(FileUtils.getPath(UpdateProfilePic.this, result.getUri()));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                PSRUtils.showToast(this, "error");
                Exception error = result.getError();
            }
        } else if (requestCode == CLICK_PHOTO && resultCode == Activity.RESULT_OK) {

            CropImage.activity(Uri.fromFile(new File(clickedPhotoPath)))
                    .setAllowFlipping(false)
                    .setAspectRatio(1, 1)
                    .setCropMenuCropButtonTitle("Done")
                    .start(this);
        }
    }
/*
    public void launchCameranew(int clickPhotoRequestCode) {
        try {
           // String path="/storage/emulated/0/Pictures/picstarcelebrity20210508_172311.jpeg";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            clickedPhotoPath = PSRUtils.getClickedPhotoPath(this.getApplicationContext());

            if (clickedPhotoPath == null) {
                //   showToast("");
                PSRUtils.showAlert(this, "Something went wrong.", null);
                return;
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(clickedPhotoPath)));
            startActivityForResult(Intent.createChooser(intent, "Click Photo"), clickPhotoRequestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("PHOTOPATH",clickedPhotoPath);
    }
}