package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityDashBoardCelebBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.DasboardMenuViewModel;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DashBoardCelebActivity extends BaseActivity {
    private String celebrityId;
    private String celebrityName;
    private String bToken;
    ActivityDashBoardCelebBinding activityDashBoardCelebBinding;
    DasboardMenuViewModel dasboardMenuViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dasboardMenuViewModel = new DasboardMenuViewModel(this, psr_prefsManager);
        activityDashBoardCelebBinding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board_celeb);
        activityDashBoardCelebBinding.setViewmodell(dasboardMenuViewModel);
        activityDashBoardCelebBinding.executePendingBindings();

        activityDashBoardCelebBinding.userIdPref.setText(psr_prefsManager.get(PSRConstants.USERNAME));

        String jsonString = psr_prefsManager.get(PSRConstants.CELEBRITYROLE);
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> categriesList = new Gson().fromJson(jsonString, type);
        activityDashBoardCelebBinding.celebrityRole.setText(TextUtils.join(",", categriesList.size() != 0 ? categriesList : null));
    }


    @Override
    protected void onResume() {
        super.onResume();
        dasboardMenuViewModel.setUserName(psr_prefsManager.get(PSRConstants.USERNAME));
        Glide.with(this)
                .load(psr_prefsManager.get(PSRConstants.PROFILEPIC))
                .placeholder(R.drawable.ic_profilepholder)
                .override(100, 100)
                .dontAnimate()
                .into(activityDashBoardCelebBinding.profilePic);
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
