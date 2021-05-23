package com.devrabbit.picstarcelebrity.mvvm.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.PersonaliseLiveselfieBinding;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.AlbumsViewModel;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.PersonaliseLiveSelfieViewModel;
import com.devrabbit.picstarcelebrity.navigator.PersonaliseNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PersonaliseSingleton;

public class PersonaliseLiveSelfieFragment extends BaseFragment implements PersonaliseNavigator {

    PersonaliseLiveSelfieViewModel viewModel;
    PersonaliseLiveselfieBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewModel == null) {
            viewModel = new PersonaliseLiveSelfieViewModel(this, this, psr_prefsManager.get(PSRConstants.LIVESELFIE_COST), psr_prefsManager.getBoolean(PSRConstants.LIVESELIFEACTIVE));
        }
        binding = DataBindingUtil.inflate(
                inflater, R.layout.personalise_liveselfie, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        binding.setViewModel(viewModel);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (PersonaliseSingleton.getInstance().isLiveSelfieChecked()) {
            binding.checkbox.setImageDrawable(getResources().getDrawable(R.drawable.ic_checked));
        } else {
            binding.checkbox.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
        }
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
