package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.adapters.StockImagesAdapter;
import com.devrabbit.picstarcelebrity.databinding.ActivityStockImagesBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.Info;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.StockImagesViewModel;
import com.devrabbit.picstarcelebrity.navigator.StockImagesNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import java.util.ArrayList;
import java.util.List;

import static com.devrabbit.picstarcelebrity.mvvm.viewmodel.StockImagesViewModel.PICK_IMAGES;

public class StockImagesActivity extends BaseActivity implements Observer<List<Info>>, StockImagesNavigator {

    StockImagesViewModel viewModel;
    ActivityStockImagesBinding binding;
    ArrayList<Info> images = new ArrayList<>();
    StockImagesAdapter adapter;
    boolean isProgressBarShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PSRUtils.showProgressDialog(this);
        viewModel = new StockImagesViewModel(psr_prefsManager, this, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_images);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        viewModel.getStockImagesData().observe(this, this);
        binding.stockimagesRV.setLayoutManager(new GridLayoutManager(this, 2));
        binding.stockimagesRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    binding.loadmorePB.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (PSRUtils.isOnline(StockImagesActivity.this)) {
                        if (!isProgressBarShowing && images.size() > 0 && viewModel.loadMore()) {
                            binding.loadmorePB.setVisibility(View.VISIBLE);
                            isProgressBarShowing = true;
                        } else if (isProgressBarShowing) {
                            binding.loadmorePB.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        adapter = new StockImagesAdapter(images, this);
        binding.stockimagesRV.setAdapter(adapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGES) {
                ArrayList<Uri> uris = new ArrayList<>();
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();

                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        uris.add(uri);
                    }
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    uris.add(uri);
                }
                if (uris.size() > 0) {
                    PSRUtils.showProgressDialog(this);
                    viewModel.setUris(uris);
                    viewModel.uploadtoS3(0);
                }
            }
        }
    }

    @Override
    public void onChanged(List<Info> infos) {
        PSRUtils.hideProgressDialog();
        images.addAll(infos);
        adapter.notifyDataSetChanged();
        binding.loadmorePB.setVisibility(View.GONE);
        isProgressBarShowing = false;

        if (infos.size() == 0 && images.size() == 0) {
            binding.stockimagesRV.setVisibility(View.GONE);
            binding.nostockImagesTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void makeListEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                images.clear();
                adapter.notifyDataSetChanged();
                binding.loadmorePB.setVisibility(View.GONE);
                isProgressBarShowing = false;
            }
        });
    }
}