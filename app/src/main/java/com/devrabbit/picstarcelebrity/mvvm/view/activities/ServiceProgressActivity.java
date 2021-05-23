package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.devrabbit.picstarcelebrity.R;

import com.devrabbit.picstarcelebrity.databinding.ActivityServiceProgressBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.ServiceProgressTabFragment;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.ServiceTabsViewModel;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.google.android.material.tabs.TabLayout;

public class ServiceProgressActivity extends BaseActivity {

    ActivityServiceProgressBinding binding;
    ServiceTabsViewModel viewModel;
    private ServiceProgressTabFragment fragment;
    private int serviceRequestTypeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceRequestTypeId = getIntent().getExtras().getInt(PSRConstants.SERVICE_REQUEST_TYPE_ID);
        viewModel = new ServiceTabsViewModel(this, serviceRequestTypeId);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_progress);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        if (serviceRequestTypeId != PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID)
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getResources().getString(R.string.upcoming)).setTag(0));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getResources().getString(R.string.pending)).setTag(1));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getResources().getString(R.string.completed)).setTag(2));
        int startingIndex = 0;
        if (serviceRequestTypeId == PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID) {
            startingIndex = startingIndex + 1;
        }
        fragment = new ServiceProgressTabFragment(startingIndex, serviceRequestTypeId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.live_selfies_container, fragment);
        transaction.commit();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tag = Integer.parseInt(tab.getTag().toString());
                switch (tag) {
                    case 0:
                        fragment = new ServiceProgressTabFragment(0, serviceRequestTypeId);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.live_selfies_container, fragment);
                        transaction.commit();
                        break;
                    case 1:
                        fragment = new ServiceProgressTabFragment(1, serviceRequestTypeId);
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.live_selfies_container, fragment);
                        transaction1.commit();
                        break;
                    case 2:
                        fragment = new ServiceProgressTabFragment(2, serviceRequestTypeId);
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.live_selfies_container, fragment);
                        transaction2.commit();
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


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onClickBack();
            }
        });

    }

    @Override
    public void onBackPressed() {
        fragment.onClickBack();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }


    public void handleTabs(boolean show) {
        binding.tabLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}