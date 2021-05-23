package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityAddEventBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.presenters.AddEventPresenter;


import com.devrabbit.picstarcelebrity.mvvm.viewmodel.AddEventViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import butterknife.BindView;

public class AddEventActivity extends BaseActivity {

    AddEventViewModel addEventViewModel;
    AddEventPresenter addEventPresenter;

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    ActivityAddEventBinding activityAddEventBinding;
    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addEventViewModel = new AddEventViewModel(this, psr_prefsManager);
        activityAddEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_event);
        activityAddEventBinding.setViewmodel(addEventViewModel);
        activityAddEventBinding.executePendingBindings();


        layoutBottomSheet = activityAddEventBinding.bottomSheet;
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {
                    case BottomSheetBehavior.PEEK_HEIGHT_AUTO: {
                        Toast.makeText(getApplicationContext(), "auto", Toast.LENGTH_SHORT).show();
                    }

                    case BottomSheetBehavior.STATE_HIDDEN: {
                        finish();
                    }
                    break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_SETTLING: {

                    }
                    break;
                    default:

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

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

    public PSR_PrefsManager getPreferences() {
        return psr_prefsManager;
    }
}