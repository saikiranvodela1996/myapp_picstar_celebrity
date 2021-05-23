package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoPlayerActivity extends BaseActivity {
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    String videoUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            videoUrl = getIntent().getStringExtra(PSRConstants.VIDEOURL);
            //getVideoFromHttp(videoUrl);
        }



        MediaController mediacontroller = new MediaController(
                this);
        mediacontroller.setAnchorView(videoView);
        //  videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(videoUrl));
        // videoView.setVideoPath(videoUrl);
        videoView.setMediaController(mediacontroller);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                        if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            // video started; hide the placeholder.
                            progressBar.setVisibility(View.GONE);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });




    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @OnClick(R.id.back_btn)
    void onClickBack(View view) {
        finish();
    }

    public void getVideoFromHttp(String urlPath) {

        try {
// Start the MediaController
            MediaController mediacontroller = new MediaController(VideoPlayerActivity.this);
            mediacontroller.setAnchorView(videoView);
// Get the URL from String VideoURL
            Uri mVideo = Uri.parse(urlPath);
            videoView.setMediaController(mediacontroller);
            videoView.setVideoURI(mVideo);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                videoView.start();

            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {

            }
        });

    }
}