package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.adapters.CelebrityHistoryAdapter;
import com.devrabbit.picstarcelebrity.camerapackage.VideoRecordingActivity;
import com.devrabbit.picstarcelebrity.databinding.ActivityHistoryBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.History.Info;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.CelebrityHistoryViewModel;
import com.devrabbit.picstarcelebrity.navigator.OnClickPhoto;
import com.devrabbit.picstarcelebrity.navigator.StockImagesNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import static com.devrabbit.picstarcelebrity.mvvm.viewmodel.StockImagesViewModel.PICK_IMAGES;
import static com.devrabbit.picstarcelebrity.utils.PSRConstants.VIDEOMSGDURATION;

public class CelebrityHistoryActivity extends BaseActivity implements Observer<CelebrityHistoryResponse>, OnClickPhoto, StockImagesNavigator {
    public static int PICK_VIDEOS = 21;
    private static final int VIDEO_CAPTURE = 101;
    public static final int BLOCK_USER = 30;
    CelebrityHistoryViewModel viewModel;
    ActivityHistoryBinding binding;
    ArrayList<Info> history = new ArrayList<>();
    CelebrityHistoryAdapter adapter;
    boolean isProgressBarShowing = false;
    private int serviceReqID;
    Uri videoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CelebrityHistoryViewModel(psr_prefsManager, this, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        Log.v("ONCREATE", "TRIGGERED");

        viewModel.getHistoryData().observe(this, this);
        binding.historyRV.setLayoutManager(new LinearLayoutManager(this));
        binding.historyRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    binding.loadmorePB.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isProgressBarShowing && history.size() > 0 && viewModel.loadMore()) {
                        binding.loadmorePB.setVisibility(View.VISIBLE);
                        isProgressBarShowing = true;
                    } else if (isProgressBarShowing) {
                        binding.loadmorePB.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        adapter = new CelebrityHistoryAdapter(history, this, this, 0);
        binding.historyRV.setAdapter(adapter);
        setRetakeUI();
    }

    public void onClickBack() {

        if (binding.recyclerViewLayout.getVisibility() == View.GONE) {
            binding.videoView.stopPlayback();
            binding.recyclerViewLayout.setVisibility(View.VISIBLE);
            binding.resetakeLL.setVisibility(View.GONE);
        } else {
            binding.videoView.stopPlayback();
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        onClickBack();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onChanged(CelebrityHistoryResponse response) {
        PSRUtils.hideProgressDialog();

        if (response.getInfo() != null && response.getInfo().size() != 0) {
            history.addAll(response.getInfo());
            adapter.notifyDataSetChanged();
        } else if (history.size() == 0 || history == null) {
            binding.historyRV.setVisibility(View.GONE);
            binding.noHistoryTv.setVisibility(View.VISIBLE);
        }

        binding.loadmorePB.setVisibility(View.GONE);
        isProgressBarShowing = false;


    }

    @Override
    public void onClickPhoto(int serviceTypeReqID, String image) {

        if (serviceTypeReqID == 3) {
            Intent intent = new Intent(this, FullScreenVideoView.class);
            intent.putExtra(PSRConstants.VIDEOURL, image);
            startActivity(intent);
        } else {
            PSRUtils.showImageAlert(this, image);
        }

    }

    @Override
    public void onClickUploadBtn(int serviceReqID, int serviceRequestTypeId) {
        this.serviceReqID = serviceReqID;
        if (serviceRequestTypeId == PSRConstants.VIDEOMSGS_SERVICE_REQ_ID) {
            showDialog();
        } else if (serviceRequestTypeId == PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID) {
            viewModel.getTwilioAccessToken(serviceReqID);
        }
    }

    @Override
    public void onClickBlockBtn(String fanID) {
        Intent intent = new Intent(this, BlockingFanActivity.class);
        intent.putExtra(PSRConstants.FANUSERID, fanID);
        startActivityForResult(intent, BLOCK_USER);


    }


    private void setRetakeUI() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBack();
            }
        });


        binding.retakeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recordVideo();
            }
        });
        binding.continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo use result here
                binding.videoView.stopPlayback();
                binding.recyclerViewLayout.setVisibility(View.VISIBLE);
                binding.resetakeLL.setVisibility(View.GONE);

                ArrayList<Uri> uris = new ArrayList<>();
                uris.add(videoUri);
                viewModel.setUris(uris);
                viewModel.uploadtoS3(0, serviceReqID);
                Log.v("VIDEO_CAPTURED", "");
            }
        });
        binding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.videoView.stopPlayback();
                binding.recyclerViewLayout.setVisibility(View.VISIBLE);
                binding.resetakeLL.setVisibility(View.GONE);
            }
        });
    }

    private void showDialog() {
        final String[] items = {getResources().getString(R.string.gallery_txt), getResources().getString(R.string.record_video_txt)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.chooseoption_txt));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int clickedPosition) {
                if (clickedPosition == 0) {
                    if (ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                    ) {
                        openGallery();
                    } else {

                        checkRunTimePermissions(clickedPosition);
                    }

                } else if (clickedPosition == 1) {
                    if (ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(CelebrityHistoryActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                    ) {
                        recordVideo();
                    } else {
                        checkRunTimePermissions(clickedPosition);
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(intent, PICK_VIDEOS);
    }

/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_CAPTURE) {
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> uris = new ArrayList<>();
                    uris.add(data.getData());
                    viewModel.setUris(uris);
                    viewModel.uploadtoS3(0, serviceReqID);
                    Log.v("VIDEO_CAPTURED", "");

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Video recording cancelled.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Failed to record video",
                            Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == PICK_IMAGES) {
                ArrayList<Uri> uris = new ArrayList<>();
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        uris.add(uri);
                    }
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    uris.add(uri);
                }
                if (uris.size() > 0) {
                    viewModel.setUris(uris);
                    viewModel.uploadtoS3(0, serviceReqID);
                }
            }
        }
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == VIDEO_CAPTURE) {
                binding.recyclerViewLayout.setVisibility(View.GONE);
                binding.resetakeLL.setVisibility(View.VISIBLE);
                videoUri = Uri.parse(data.getStringExtra("VIDEO_URI"));
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(binding.videoView);
                binding.videoView.setMediaController(mediaController);
                binding.videoView.setVideoURI(videoUri);
                binding.videoView.start();


            } else if (requestCode == PICK_VIDEOS) {
                ArrayList<Uri> uris = new ArrayList<>();
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        uris.add(uri);
                    }
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    uris.add(uri);
                }
                if (uris.size() > 0) {
                    viewModel.setUris(uris);
                    viewModel.uploadtoS3(0, serviceReqID);
                }
            } else if (requestCode == BLOCK_USER) {
                history.clear();
                adapter.notifyDataSetChanged();
                viewModel.setNextPageNumber(1);
                viewModel.setLoadMore(true);
                viewModel.loadMore();

            }
        }
    }

    @Override
    public void makeListEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                history.clear();
                adapter.notifyDataSetChanged();
                binding.loadmorePB.setVisibility(View.GONE);
                isProgressBarShowing = false;
            }
        });
    }


    public void checkRunTimePermissions(int pos) {

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (pos == 0) {
                                openGallery();
                            } else if (pos == 1) {
                                recordVideo();
                            }
                        } else {
                            PSRUtils.showToast(CelebrityHistoryActivity.this, getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            PSRUtils.showToast(CelebrityHistoryActivity.this, getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
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
                        PSRUtils.showToast(CelebrityHistoryActivity.this, getResources().getString(R.string.somethingwnt_wrong_txt));
                    }
                })
                .onSameThread()
                .check();
    }


    public void recordVideo() {
        videoUri = null;
        binding.videoView.stopPlayback();

        Intent intent = new Intent(this, VideoRecordingActivity.class);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, VIDEOMSGDURATION);
        startActivityForResult(intent, VIDEO_CAPTURE);
        binding.recyclerViewLayout.setVisibility(View.VISIBLE);
        binding.resetakeLL.setVisibility(View.GONE);
    }
}
