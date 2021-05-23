package com.devrabbit.picstarcelebrity.mvvm.model;

import androidx.lifecycle.MutableLiveData;

import com.devrabbit.picstarcelebrity.mvp.models.History.CelebrityHistoryResponse;
import com.devrabbit.picstarcelebrity.mvp.models.History.Info;

import java.util.List;

public class CelebrityHistoryModel {

    MutableLiveData<CelebrityHistoryResponse> history;
    String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CelebrityHistoryModel() {
        history = new MutableLiveData<>();
    }

    public MutableLiveData<CelebrityHistoryResponse> getHistory() {
        return history;
    }

    public void setHistory(MutableLiveData<CelebrityHistoryResponse> history) {
        this.history = history;
    }
}
