package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PersonalisePhotoSelfieFragment;
import com.devrabbit.picstarcelebrity.navigator.PersonaliseNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.PersonaliseSingleton;

public class PersonalisePhotoSelfieViewModel extends BaseObservable {


    PersonaliseNavigator navigator;
    PersonalisePhotoSelfieFragment fragment;

    public PersonalisePhotoSelfieViewModel(PersonalisePhotoSelfieFragment fragment, PersonaliseNavigator navigator, String cost, boolean isActive) {
        this.fragment = fragment;
        this.navigator = navigator;
        PersonaliseSingleton.getInstance().setPhotoSelfiePrice(cost);
        PersonaliseSingleton.getInstance().setPhotoSelfieChecked(isActive);
    }


    @Bindable
    public String getPhotoSelfiePrice() {
        return PersonaliseSingleton.getInstance().getPhotoSelfiePrice();
    }

    public void setPhotoSelfiePrice(String price) {

        String pricEntered = price;
        pricEntered = price.isEmpty() ? "0" : pricEntered.trim();
        if (Double.parseDouble(pricEntered) == 0 && PersonaliseSingleton.getInstance().isPhotoSelfieChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_servicecost_msg), null);
            PersonaliseSingleton.getInstance().setPhotoSelfieChecked(!PersonaliseSingleton.getInstance().isPhotoSelfieChecked());
            navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isPhotoSelfieChecked());
        }

        if (pricEntered.length() == 3 && PersonaliseSingleton.getInstance().isPhotoSelfieChecked()) {
            if (pricEntered.startsWith("00")) {
                PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_valid_price), null);
                PersonaliseSingleton.getInstance().setPhotoSelfieChecked(!PersonaliseSingleton.getInstance().isPhotoSelfieChecked());
                navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isPhotoSelfieChecked());
            }
        }


        PersonaliseSingleton.getInstance().setPhotoSelfiePrice(price);
        notifyPropertyChanged(BR.photoSelfiePrice);
    }


    public void onCheckboxClicked() {
        String pricEntered = PersonaliseSingleton.getInstance().getPhotoSelfiePrice();
        pricEntered = pricEntered.isEmpty() ? "0" : pricEntered.trim();
        if (Double.parseDouble(pricEntered) == 0 && !PersonaliseSingleton.getInstance().isPhotoSelfieChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_servicecost_msg), null);
            return;
        }
        if (pricEntered.length() == 3 && !PersonaliseSingleton.getInstance().isPhotoSelfieChecked()) {
            if (pricEntered.startsWith("00")) {
                PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_valid_price), null);
                return;
            }
        }

        PersonaliseSingleton.getInstance().setPhotoSelfieChecked(!PersonaliseSingleton.getInstance().isPhotoSelfieChecked());
        navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isPhotoSelfieChecked());
    }
}
