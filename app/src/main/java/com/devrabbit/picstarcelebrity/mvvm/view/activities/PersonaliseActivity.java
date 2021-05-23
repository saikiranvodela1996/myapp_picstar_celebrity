package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivityPersonaliseBinding;
import com.devrabbit.picstarcelebrity.adapters.MyViewPagerAdapter;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;

public class PersonaliseActivity extends BaseActivity {

    ActivityPersonaliseBinding activityPersonaliseBinding;
//    PersonaliseViewModel personaliseViewModel;
    private MyViewPagerAdapter myvpAdapter;
    int[] screens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPersonaliseBinding = DataBindingUtil.setContentView(this, R.layout.activity_personalise);
//        activityPersonaliseBinding.setViewModel(personaliseViewModel);
        activityPersonaliseBinding.executePendingBindings();
        screens = new int[]{R.layout.personalise_liveselfie,
                R.layout.personalise_photoselfie, R.layout.personalise_videomessage, R.layout.personalise_livevideo};
        myvpAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        activityPersonaliseBinding.viewPager.setAdapter(myvpAdapter);
        activityPersonaliseBinding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        ColoredBars(0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    private void ColoredBars(int position) {
        activityPersonaliseBinding.indicatorOne.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        activityPersonaliseBinding.indicatorTwo.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        activityPersonaliseBinding.indicatorThree.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        activityPersonaliseBinding.indicatorFour.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (position == 0) {
            activityPersonaliseBinding.indicatorOne.setBackground(getResources().getDrawable(R.drawable.bg_radius));
        } else if (position == 1) {
            activityPersonaliseBinding.indicatorTwo.setBackground(getResources().getDrawable(R.drawable.bg_radius));
        } else if (position == 2) {
            activityPersonaliseBinding.indicatorThree.setBackground(getResources().getDrawable(R.drawable.bg_radius));
        } else if (position == 3) {
            activityPersonaliseBinding.indicatorFour.setBackground(getResources().getDrawable(R.drawable.bg_radius));
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            ColoredBars(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

}
