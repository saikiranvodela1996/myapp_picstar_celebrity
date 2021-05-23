package com.devrabbit.picstarcelebrity.mvvm.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.PersonalisePhotoselfieBinding;
import com.devrabbit.picstarcelebrity.databinding.PersonaliseVideomessageBinding;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.PersonaliseLiveVideoViewModel;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.PersonalisePhotoSelfieViewModel;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.PersonaliseVideoMsgViewModel;
import com.devrabbit.picstarcelebrity.navigator.PersonaliseNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PersonaliseSingleton;

public  class PersonaliseVideoMessageFragment  extends BaseFragment implements PersonaliseNavigator {

    PersonaliseVideoMsgViewModel viewModel;
    PersonaliseVideomessageBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(viewModel==null){
           viewModel = new PersonaliseVideoMsgViewModel(this,this,psr_prefsManager.get(PSRConstants.VIDEOMSGCOST),psr_prefsManager.getBoolean(PSRConstants.VIDEOMSGACTIVE));
       }

        binding = DataBindingUtil.inflate(
                inflater, R.layout.personalise_videomessage, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(PersonaliseSingleton.getInstance().isVideoMsgChecked()){
            binding.checkbox.setImageDrawable(getResources().getDrawable(R.drawable.ic_checked));
        }else {
            binding.checkbox.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
        }
    }
    public PSR_PrefsManager getPreferences() {
        return psr_prefsManager;
    }
    @Override
    public void onClickCheckBox(boolean isChecked) {
        if (isChecked) {
            binding.checkbox.setImageDrawable(getResources().getDrawable(R.drawable.ic_checked));
        } else {
            binding.checkbox.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
        }
    }
}
