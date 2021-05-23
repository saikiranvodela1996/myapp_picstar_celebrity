package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.adapters.FetchAllEventsAdapter;
import com.devrabbit.picstarcelebrity.databinding.ActivityFetchEventsBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;

import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsInfo;
import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsResponse;

import com.devrabbit.picstarcelebrity.mvvm.viewmodel.FetchAllEventsViewModel;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import java.util.ArrayList;
import java.util.List;

public class FetchAllEventsActivity extends BaseActivity implements Observer<FetchAllEventsResponse> {
    FetchAllEventsAdapter fetchAllEventsAdapter;
    ActivityFetchEventsBinding activityFetchEventsBinding;


    private boolean isLoading = false;


    private boolean isAllPagesShown = false;
    TextView noeventTextView;
    FetchAllEventsViewModel viewModel;
    List<FetchAllEventsInfo> fetchAllEventsInfoArrayList = new ArrayList<>();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new FetchAllEventsViewModel(this, psr_prefsManager);
        activityFetchEventsBinding = DataBindingUtil.setContentView(this, R.layout.activity_fetch_events);
        activityFetchEventsBinding.setViewModel(viewModel);
        activityFetchEventsBinding.executePendingBindings();

        viewModel.getCelebEvents().observe(this, this);

        activityFetchEventsBinding.eventsRecyclerviews.setLayoutManager(new LinearLayoutManager(this));
        fetchAllEventsAdapter = new FetchAllEventsAdapter(fetchAllEventsInfoArrayList, FetchAllEventsActivity.this);
        activityFetchEventsBinding.eventsRecyclerviews.setAdapter(fetchAllEventsAdapter);
        noeventTextView = activityFetchEventsBinding.noeventsTv;


        activityFetchEventsBinding.eventsRecyclerviews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading && !isAllPagesShown) {
                        if (PSRUtils.isOnline(FetchAllEventsActivity.this)) {
                            isLoading = true;
                            viewModel.getAllEvents();

                        }
                    }
                }
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    public PSR_PrefsManager getPreferences() {
        return psr_prefsManager;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }


    public void setAllPagesShown(boolean allPagesShown) {
        isAllPagesShown = allPagesShown;
    }


    @Override
    protected void onResume() {
        super.onResume();
        isLoading = false;
        isAllPagesShown = false;
        viewModel.setPageNo(1);
        fetchAllEventsInfoArrayList.clear();
        fetchAllEventsAdapter.notifyDataSetChanged();
        viewModel.getAllEvents();

    }

    @Override
    public void onChanged(FetchAllEventsResponse response) {
        PSRUtils.hideProgressDialog();
        isLoading = false;
        if (response.getInfo() == null || response.getInfo().size() == 0) {
            isAllPagesShown = true;
        }
        if (response.getInfo().size() != 0) {
            fetchAllEventsInfoArrayList.addAll(response.getInfo());
            fetchAllEventsAdapter.notifyDataSetChanged();
        } else if (fetchAllEventsInfoArrayList.size() == 0) {
            activityFetchEventsBinding.eventsRecyclerviews.setVisibility(View.GONE);
            activityFetchEventsBinding.noeventsTv.setVisibility(View.VISIBLE);
        }
    }


}