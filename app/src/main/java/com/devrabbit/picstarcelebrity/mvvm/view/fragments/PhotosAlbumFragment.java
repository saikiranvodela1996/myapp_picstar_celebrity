package com.devrabbit.picstarcelebrity.mvvm.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.adapters.PhotosAlbumAdapter;
import com.devrabbit.picstarcelebrity.adapters.StockImagesAdapter;
import com.devrabbit.picstarcelebrity.databinding.LayoutPhotosAlbumBinding;

import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.Info;
import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.PhotosAlbumResponse;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.AlbumsActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.FetchAllEventsActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.VideoPlayerActivity;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.AlbumsViewModel;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.PersonaliseLiveSelfieViewModel;
import com.devrabbit.picstarcelebrity.navigator.OnClickPhoto;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotosAlbumFragment extends BaseFragment implements Observer<PhotosAlbumResponse>, OnClickPhoto {
    AlbumsViewModel viewModel;
    LayoutPhotosAlbumBinding binding;
    ArrayList<Info> imagesList = new ArrayList<>();
    PhotosAlbumAdapter adapter;
    boolean isLoading = false;
    boolean isAllPagesShown = false;

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setAllPagesShown(boolean allPagesShown) {
        isAllPagesShown = allPagesShown;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModel = new AlbumsViewModel(psr_prefsManager, getActivity(), "photos");


        binding = DataBindingUtil.inflate(
                inflater, R.layout.layout_photos_album, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        binding.setViewModel(viewModel);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCelebrityPhotosAlbum().observe(getActivity(), this);
        binding.photosRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
                    if (!isLoading && !isAllPagesShown) {
                        if (PSRUtils.isOnline(getActivity())) {
                            binding.loadmorePB.setVisibility(View.VISIBLE);
                            isLoading = true;
                            viewModel.getPhotosAlbum();
                        }
                    }
                }
            }
        });
        adapter = new PhotosAlbumAdapter(imagesList, getActivity(), this, false);
        binding.photosRV.setAdapter(adapter);


    }

    @Override
    public void onChanged(PhotosAlbumResponse response) {
        PSRUtils.hideProgressDialog();
        isLoading = false;
        binding.loadmorePB.setVisibility(View.GONE);
        if (response.getInfo() == null || response.getInfo().size() == 0) {
            isAllPagesShown = true;
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

    @Override
    public void onClickPhoto(int serviceTypeReqID, String image) {


        PSRUtils.showImageAlert(getActivity(), image);
    }

    @Override
    public void onClickUploadBtn(int serviceReqID,int serviceReqTypeID) {

    }

    @Override
    public void onClickBlockBtn(String fanID) {

    }

}
