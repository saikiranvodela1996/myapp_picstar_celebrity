package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockFanRequest;
import com.devrabbit.picstarcelebrity.mvp.models.BlockingFan.BlockingFanResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.BlockingFanPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.BlockingFanView;
import com.devrabbit.picstarcelebrity.mvvm.model.CommentsModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.CelebrityHistoryActivity;
import com.devrabbit.picstarcelebrity.navigator.OnOkButtonClick;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import static android.app.Activity.RESULT_OK;

public class BlockingFanViewModel extends BaseObservable implements BlockingFanView, OnOkButtonClick {

    CommentsModel model;
    Activity activity;
    PSR_PrefsManager psr_prefsManager;
    String fanID;
    BlockingFanPresenter presenter;

    public BlockingFanViewModel(Activity activity, PSR_PrefsManager psr_prefsManager, String fanID) {
        model = new CommentsModel("");
        presenter = new BlockingFanPresenter();
        presenter.attachMvpView(this);
        this.activity = activity;
        this.psr_prefsManager = psr_prefsManager;
        this.fanID = fanID;
    }


    @Bindable
    public String getCelebComments() {
        return model.getComments();
    }

    public void setCelebComments(String comments) {
        model.setComments(comments);
        notifyPropertyChanged(BR.celebComments);
    }


    public void onClickBack() {
        PSRUtils.hideKeyboardIfOpened(activity);
        activity.finish();
    }

    public void onClickSubmitComments() {
        PSRUtils.hideKeyboardIfOpened(activity);
        if (model.getComments().trim().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_urcomments_alertmsg), null);
            return;
        }
        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            BlockFanRequest request = new BlockFanRequest();
            request.setUserID(fanID);
            request.setBlockedBy(psr_prefsManager.get(PSRConstants.USER_ID));
            request.setComments(model.getComments());
            presenter.blockUser(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE), PSRUtils.getHeader(psr_prefsManager), request);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }

    }

    @Override
    public void onBlockingSuccess(BlockingFanResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showButtonActionAlert(activity, response.getMessage(), null, this);
    }

    @Override
    public void onBlockingFailure(BlockingFanResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
        PSRUtils.doLogout(activity, psr_prefsManager);
    }

    @Override
    public void onServerError() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
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
    public void onClickOk() {
        Intent intent = new Intent();
        activity.setResult(RESULT_OK, intent);
        activity.finish();
    }
}
