package com.devrabbit.picstarcelebrity.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvp.models.getstockimages.Info;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockImagesAdapter extends RecyclerView.Adapter<StockImagesAdapter.ViewHolder> {

    ArrayList<Info> images;
    Activity activity;
    int screenWidth = 0;

    public StockImagesAdapter(ArrayList<Info> history, Activity activity) {
        this.images = history;
        this.activity = activity;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_images_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewGroup.LayoutParams llparams = holder.parent_Layout.getLayoutParams();
        llparams.height = (screenWidth / 2) - 45;
        llparams.width = (screenWidth / 2) - 45;
        Info info = images.get(position);

        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(activity).load(info.getPhotoUrl())
                .placeholder(activity.getResources().getDrawable(R.drawable.ic_selfiepholder))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageview);



      /*  Glide.with(activity)
                .load(info.getPhotoUrl())
                .placeholder(R.drawable.ic_selfiepholder)
                .into(holder.imageview);*/
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {//   parent_Layout
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.imageview)
        ImageView imageview;
        @BindView(R.id.parent_Layout)
        LinearLayout parent_Layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
