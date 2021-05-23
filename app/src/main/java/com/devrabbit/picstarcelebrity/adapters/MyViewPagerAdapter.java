package com.devrabbit.picstarcelebrity.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PersonaliseLiveSelfieFragment;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PersonaliseLiveVideoFragment;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PersonalisePhotoSelfieFragment;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PersonaliseVideoMessageFragment;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> frags = new ArrayList<>();


    public MyViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        frags.add(new PersonalisePhotoSelfieFragment());
        frags.add(new PersonaliseLiveSelfieFragment());
        frags.add(new PersonaliseVideoMessageFragment());
        frags.add(new PersonaliseLiveVideoFragment());


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       return frags.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}

