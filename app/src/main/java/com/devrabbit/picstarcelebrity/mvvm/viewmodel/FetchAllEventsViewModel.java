package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;

import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsInfo;
import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.FetchAllEventsPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.FetchAllEventsView;

import com.devrabbit.picstarcelebrity.mvvm.model.EventsModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.AddEventActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.FetchAllEventsActivity;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import java.util.List;

public class FetchAllEventsViewModel extends BaseObservable implements FetchAllEventsView {
    private final FetchAllEventsActivity activity;
    PSR_PrefsManager psr_prefsManager;
    FetchAllEventsPresenter fetchAllEventsPresenter;
    RecyclerView recyclerView;
    EventsModel model;

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    private int pageNo = 1;

    public RecyclerView getRecyclerview() {
        return recyclerView;
    }

    public FetchAllEventsViewModel(FetchAllEventsActivity ac, PSR_PrefsManager psr_prefsManager) {
        model = new EventsModel();
        this.activity = ac;
        this.psr_prefsManager = psr_prefsManager;
        this.fetchAllEventsPresenter = new FetchAllEventsPresenter();
        this.fetchAllEventsPresenter.attachMvpView(this);


    }

    @Bindable
    public String getMessage() {
        return model.getMessage();
    }

    public void setMessage(String message) {
        model.setMessage(message);
        notifyPropertyChanged(BR.message);
    }


    public void getAllEvents() {
        if (PSRUtils.isOnline(activity)) {
            if (pageNo == 1) {
                PSRUtils.showProgressDialog(activity);
            }
            activity.setLoading(true);
            fetchAllEventsPresenter.doFetchAllEvents(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE),
                    PSRUtils.getHeader(activity.getPreferences()), psr_prefsManager.
                            get(PSRConstants.USER_ID), pageNo);

        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }

    }

    public void onAddClick() {
        Intent intent = new Intent(activity, AddEventActivity.class);
        activity.startActivity(intent);
    }

    public void onClickBack() {
        activity.finish();
    }

    public MutableLiveData<FetchAllEventsResponse> getCelebEvents() {
        return model.getEvents();
    }


    @Override
    public Context getMvpContext() {
        return null;
    }

    @Override
    public void onError(Throwable throwable) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onNoInternetConnection() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showNoNetworkAlert(activity);
    }

    @Override
    public void onErrorCode(String s) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onFetchedDone(FetchAllEventsResponse response) {
        PSRUtils.hideProgressDialog();
        pageNo++;
        model.getEvents().postValue(response);
        if (response.getInfo() == null || response.getInfo().size() == 0) {
            if (response.getMessage() != null) {
                setMessage(response.getMessage());
            }
        }

    }

    @Override
    public void onFetchedFailed(FetchAllEventsResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
    }

    @Override
    public void onServerError() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }
}
