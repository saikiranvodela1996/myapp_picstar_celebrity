package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityBlockingFanBinding;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.BlockingFanViewModel;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.CelebrityHistoryViewModel;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;

public class BlockingFanActivity extends BaseActivity {

    ActivityBlockingFanBinding binding;
    String fanID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
         fanID  =getIntent().getStringExtra(PSRConstants.FANUSERID);
        }
        BlockingFanViewModel viewModel = new BlockingFanViewModel(this, psr_prefsManager, fanID);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blocking_fan);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }






}