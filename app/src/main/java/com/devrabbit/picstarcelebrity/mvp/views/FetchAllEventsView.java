package com.devrabbit.picstarcelebrity.mvp.views;

import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsResponse;


public interface FetchAllEventsView extends BaseMvpView {
    void onFetchedDone(FetchAllEventsResponse response);
    void  onFetchedFailed(FetchAllEventsResponse response);
}
