package com.devrabbit.picstarcelebrity.mvvm.view.activities;


import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityForgotPasswordBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.ForgotPasswordViewmodel;

public class ForgotPasswordActivity extends BaseActivity {

    ForgotPasswordViewmodel viewmodel;
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ForgotPasswordViewmodel(this,psr_prefsManager);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        binding.setViewModel(viewmodel);
        binding.executePendingBindings();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
}