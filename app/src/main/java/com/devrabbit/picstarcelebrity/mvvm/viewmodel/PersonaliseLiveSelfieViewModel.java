package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvvm.view.fragments.PersonaliseLiveSelfieFragment;
import com.devrabbit.picstarcelebrity.navigator.PersonaliseNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.PersonaliseSingleton;

public class PersonaliseLiveSelfieViewModel extends BaseObservable {

    PersonaliseNavigator navigator;
    PersonaliseLiveSelfieFragment fragment;

    public PersonaliseLiveSelfieViewModel(PersonaliseLiveSelfieFragment fragment, PersonaliseNavigator navigator, String serviceCost, boolean isLiveSelfieActive) {
        this.fragment = fragment;
        this.navigator = navigator;
        PersonaliseSingleton.getInstance().setLiveSelfiePrice(serviceCost);
        PersonaliseSingleton.getInstance().setLiveSelfieChecked(isLiveSelfieActive);
    }

    @Bindable
    public String getLiveSelfiePrice() {
        return PersonaliseSingleton.getInstance().getLiveSelfiePrice();
    }

    public void setLiveSelfiePrice(String price) {
        String pricEntered = price;
        pricEntered = price.isEmpty() ? "0" : pricEntered.trim();
        if (Double.parseDouble(pricEntered) == 0 && PersonaliseSingleton.getInstance().isLiveSelfieChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_servicecost_msg), null);
            PersonaliseSingleton.getInstance().setLiveSelfieChecked(!PersonaliseSingleton.getInstance().isLiveSelfieChecked());
            navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isLiveSelfieChecked());
        }

        if (pricEntered.length() == 3 && PersonaliseSingleton.getInstance().isLiveSelfieChecked()) {
            if (pricEntered.startsWith("00")) {
                PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_valid_price), null);
                PersonaliseSingleton.getInstance().setLiveSelfieChecked(!PersonaliseSingleton.getInstance().isLiveSelfieChecked());
                navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isLiveSelfieChecked());

            }
        }


        PersonaliseSingleton.getInstance().setLiveSelfiePrice(price);
        notifyPropertyChanged(BR.liveSelfiePrice);
    }

    public void onCheckboxClicked() {
        String pricEntered = PersonaliseSingleton.getInstance().getLiveSelfiePrice();
        pricEntered = pricEntered.isEmpty() ? "0" : pricEntered.trim();
        if (Double.parseDouble(pricEntered) == 0 && !PersonaliseSingleton.getInstance().isLiveSelfieChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_servicecost_msg), null);
            return;
        }


        if (pricEntered.length() == 3 && !PersonaliseSingleton.getInstance().isLiveSelfieChecked()) {
            if (pricEntered.startsWith("00")) {
                PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_valid_price), null);
                return;
            }
        }



      /*  if (Double.parseDouble(pricEntered) >= 1000 && !PersonaliseSingleton.getInstance().isLiveSelfieChecked()) {
            PSRUtils.showAlert(fragment.getActivity(), fragment.getActivity().getResources().getString(R.string.enter_valid_price), null);
            return;
        }*/


        PersonaliseSingleton.getInstance().setLiveSelfieChecked(!PersonaliseSingleton.getInstance().isLiveSelfieChecked());
        navigator.onClickCheckBox(PersonaliseSingleton.getInstance().isLiveSelfieChecked());

    }
}
