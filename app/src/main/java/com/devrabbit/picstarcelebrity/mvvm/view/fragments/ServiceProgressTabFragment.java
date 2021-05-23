package com.devrabbit.picstarcelebrity.mvvm.view.fragments;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.adapters.CelebrityHistoryAdapter;

import com.devrabbit.picstarcelebrity.camerapackage.VideoRecordingActivity;
import com.devrabbit.picstarcelebrity.databinding.FragmentServiceProgressTabBinding;
import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.History.Info;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.BlockingFanActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.FullScreenVideoView;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.ServiceProgressActivity;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.ServiceProgressViewModel;
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

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.devrabbit.picstarcelebrity.mvvm.viewmodel.StockImagesViewModel.PICK_IMAGES;
import static com.devrabbit.picstarcelebrity.utils.PSRConstants.VIDEOMSGDURATION;

public class ServiceProgressTabFragment extends BaseFragment implements Observer<CelebrityHistoryResponse>, OnClickPhoto, StockImagesNavigator {
    public static int PICK_VIDEOS = 21;
    private static final int VIDEO_CAPTURE = 101;
    public static final int BLOCK_USER = 30;
    int index = 0;
    private int serviceRequestTypeId = 0;
    ServiceProgressViewModel viewModel;
    FragmentServiceProgressTabBinding binding;
    ArrayList<Info> imagesList = new ArrayList<>();
    CelebrityHistoryAdapter adapter;
    boolean isLoading = false;
    private int serviceReqID;
    Uri videoUri = null;

    public ServiceProgressTabFragment(int index, int serviceRequestTypeId) {
        this.index = index;
        this.serviceRequestTypeId = serviceRequestTypeId;
    }

    public ServiceProgressTabFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ServiceProgressViewModel(psr_prefsManager, (ServiceProgressActivity) getActivity(), index, this);
        viewModel.setServiceRequestTypeId(serviceRequestTypeId);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service_progress_tab, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getListObservable().observe(getActivity(), this);
        binding.photosRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.photosRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    binding.loadmorePB.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading && viewModel.isHasMoreToLoad()) {
                        if (PSRUtils.isOnline(getActivity())) {
                            binding.loadmorePB.setVisibility(View.VISIBLE);
                            isLoading = true;
                            viewModel.getLiveSelfies();
                        }
                    }
                }
            }
        });
        adapter = new CelebrityHistoryAdapter(imagesList, getActivity(), this, index);
        binding.photosRV.setAdapter(adapter);
        setRetakeUI();
    }

    @Override
    public void onChanged(CelebrityHistoryResponse response) {
        PSRUtils.hideProgressDialog();
        isLoading = false;
        binding.loadmorePB.setVisibility(View.GONE);
        if (response.getInfo() == null || response.getInfo().size() == 0) {
            if (imagesList.size() == 0) {
                binding.photosRV.setVisibility(View.GONE);
                binding.noPhotosTv.setVisibility(View.VISIBLE);
            }
        }
        if (response.getInfo().size() != 0) {
            imagesList.addAll(response.getInfo());
            adapter.notifyDataSetChanged();
        }
    }


    public void onClickBack() {

        if (binding.recyclerViewLayout.getVisibility() == View.GONE) {
            binding.videoView.stopPlayback();
            binding.recyclerViewLayout.setVisibility(View.VISIBLE);
            binding.resetakeLL.setVisibility(View.GONE);
            ((ServiceProgressActivity) getActivity()).handleTabs(true);
        } else {
            binding.videoView.stopPlayback();
            getActivity().finish();
        }

    }

    private void setRetakeUI() {


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
                ((ServiceProgressActivity) getActivity()).handleTabs(true);
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
                ((ServiceProgressActivity) getActivity()).handleTabs(true);
            }
        });
    }

    @Override
    public void onClickPhoto(int serviceTypeReqID, String image) {

        if (serviceTypeReqID == 3) {
            Intent intent = new Intent(getActivity(), FullScreenVideoView.class);
            intent.putExtra(PSRConstants.VIDEOURL, image);
            startActivity(intent);
        } else {
            PSRUtils.showImageAlert(getActivity(), image);
        }

    }

    @Override
    public void onClickUploadBtn(int serviceReqID, int serviceRequestTypeId) {
        this.serviceReqID = serviceReqID;
        if (serviceRequestTypeId == PSRConstants.VIDEOMSGS_SERVICE_REQ_ID) {
            showDialog();
        } else if (serviceRequestTypeId == PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
            ) {
                viewModel.getTwilioAccessToken(serviceReqID);

            } else {
                checkPermissionForVideo(serviceReqID);
            }



          /*  Intent intent = new Intent(getActivity(), VideoActivity.class);
            startActivity(intent);*/
        }
    }

    @Override
    public void onClickBlockBtn(String fanID) {
        Intent intent = new Intent(getActivity(), BlockingFanActivity.class);
        intent.putExtra(PSRConstants.FANUSERID, fanID);
        startActivityForResult(intent, BLOCK_USER);
    }

    @Override
    public void makeListEmpty() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imagesList.clear();
                adapter.notifyDataSetChanged();
                binding.loadmorePB.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }


    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_VIDEOS);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == VIDEO_CAPTURE) {
                ((ServiceProgressActivity) getActivity()).handleTabs(false);
                binding.recyclerViewLayout.setVisibility(View.GONE);
                binding.resetakeLL.setVisibility(View.VISIBLE);
                videoUri = Uri.parse(data.getStringExtra("VIDEO_URI"));
                MediaController mediaController = new MediaController(getActivity());
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
                imagesList.clear();
                adapter.notifyDataSetChanged();
                viewModel.setHasMoreToLoad(true);
                viewModel.setNextPageNo(1);
                isLoading = false;
                viewModel.getLiveSelfies();

            }
        }
    }


    public void checkRunTimePermissions(int index) {

        Dexter.withContext(getActivity())
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
                            if (index == 0) {
                                openGallery();
                            } else if (index == 1) {
                                recordVideo();
                            }

                        } else {
                            PSRUtils.showToast(getActivity(), getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            PSRUtils.showToast(getActivity(), getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
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
                        PSRUtils.showToast(getActivity(), getResources().getString(R.string.somethingwnt_wrong_txt));
                    }
                })
                .onSameThread()
                .check();
    }


    private void showDialog() {
        final String[] items = {getResources().getString(R.string.gallery_txt), getResources().getString(R.string.record_video_txt)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.chooseoption_txt));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int clickedPosition) {
                if (clickedPosition == 0) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                    ) {
                        openGallery();
                    } else {

                        checkRunTimePermissions(clickedPosition);
                    }

                } else if (clickedPosition == 1) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
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


    public void recordVideo() {
        videoUri = null;
        binding.videoView.stopPlayback();
        Intent intent = new Intent(getActivity(), VideoRecordingActivity.class);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, VIDEOMSGDURATION);
        startActivityForResult(intent, VIDEO_CAPTURE);
        binding.recyclerViewLayout.setVisibility(View.VISIBLE);
        binding.resetakeLL.setVisibility(View.GONE);
        ((ServiceProgressActivity) getActivity()).handleTabs(true);
    }

    public void checkPermissionForVideo(int serviceReqID) {

        Dexter.withContext(getActivity())
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
                            viewModel.getTwilioAccessToken(serviceReqID);

                        } else {
                            PSRUtils.showToast(getActivity(), getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            PSRUtils.showToast(getActivity(), getResources().getString(R.string.galleryandcamera_runtimepermission_alert_txt));
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
                        PSRUtils.showToast(getActivity(), getResources().getString(R.string.somethingwnt_wrong_txt));
                    }
                })
                .onSameThread()
                .check();
    }


}