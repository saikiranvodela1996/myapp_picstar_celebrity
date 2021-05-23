package com.devrabbit.picstarcelebrity.mvvm.view.activities;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityAlbumBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PhotosAlbumFragment;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.VideosAlbumFragment;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.AlbumsActivityViewModel;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.AlbumsViewModel;
import com.google.android.material.tabs.TabLayout;

public class AlbumsActivity extends BaseActivity {


    AlbumsActivityViewModel viewModel;
    ActivityAlbumBinding binding;
    PhotosAlbumFragment photosAlbumFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new AlbumsActivityViewModel(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getResources().getString(R.string.photos_txt)).setTag(0));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getResources().getString(R.string.videos_txt)).setTag(1));

        photosAlbumFragment = new PhotosAlbumFragment();
        FragmentTransaction transaction = AlbumsActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, photosAlbumFragment);
        transaction.commit();


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tag = Integer.parseInt(tab.getTag().toString());
                switch (tag) {
                    case 0:
                        photosAlbumFragment = new PhotosAlbumFragment();
                        FragmentTransaction transaction = AlbumsActivity.this.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, photosAlbumFragment);
                        transaction.commit();
                        break;
                    case 1:
                        VideosAlbumFragment videosAlbumFragment = new VideosAlbumFragment();
                        FragmentTransaction transaction1 = AlbumsActivity.this.getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.frame_layout, videosAlbumFragment);
                        transaction1.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}