package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.UploadToS3Interface;
import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;
import com.sandrios.sandriosCamera.internal.ui.model.Media;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoSelectionActivity extends AppCompatActivity implements UploadToS3Interface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_selection);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.camera_btn)
    void onClickCamera(View view) {
        SandriosCamera
                .with()

                .setShowPicker(false)

                // .setShowPickerType(CameraConfiguration.VIDEO)
                .setVideoFileSize(20)

                .setMediaAction(CameraConfiguration.MEDIA_ACTION_VIDEO)
                .enableImageCropping(true)
                .launchCamera(VideoSelectionActivity.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == SandriosCamera.RESULT_CODE && data != null) {


            if (data.getSerializableExtra(SandriosCamera.MEDIA) instanceof Media) {

                    // =data.getData();
               // PSRUtils.uploadVideo(0,data.getData(), this, this);
                Media media = (Media) data.getSerializableExtra(SandriosCamera.MEDIA);

                Log.e("File", "" + media.getPath());
                Log.e("Type", "" + media.getType());
                Toast.makeText(getApplicationContext(), "Media captured.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onImageUploaded(int index, String uploadedUrl,String videoThumb) {

    }
}