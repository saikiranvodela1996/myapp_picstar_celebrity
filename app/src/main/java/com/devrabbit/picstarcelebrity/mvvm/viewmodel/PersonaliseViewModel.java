package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import androidx.databinding.BaseObservable;
import com.devrabbit.picstarcelebrity.navigator.PersonaliseNavigator;

public  class PersonaliseViewModel extends BaseObservable {

    PersonaliseNavigator navigator;
    public PersonaliseViewModel() {
        this.navigator= navigator;
    }
}
