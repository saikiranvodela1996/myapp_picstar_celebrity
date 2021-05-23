package com.devrabbit.picstarcelebrity.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvp.models.photosalbum.Info;
import com.devrabbit.picstarcelebrity.navigator.OnClickPhoto;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotosAlbumAdapter extends RecyclerView.Adapter<PhotosAlbumAdapter.ViewHolder> {

    ArrayList<Info> photosAlbum;
    Activity activity;
    int screenWidth = 0;
    OnClickPhoto onClickPhoto;
    boolean isVideosAlbum;

    public PhotosAlbumAdapter(ArrayList<Info> photosAlbum, Activity activity, OnClickPhoto onClickPhoto, boolean isVideosAlbum) {
        this.photosAlbum = photosAlbum;
        this.activity = activity;
        this.onClickPhoto = onClickPhoto;
        this.isVideosAlbum = isVideosAlbum;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_images_item, parent, false);
        PhotosAlbumAdapter.ViewHolder viewHolder = new PhotosAlbumAdapter.ViewHolder(mView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewGroup.LayoutParams llparams = holder.parent_Layout.getLayoutParams();
        llparams.height = (screenWidth / 2) - 45;
        llparams.width = (screenWidth / 2) - 45;
        Info info = photosAlbum.get(position);
        holder.playBtn.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.VISIBLE);
        if (isVideosAlbum) {
            Glide.with(activity).load(info.getThumbNailPath())
                    .placeholder(activity.getResources().getDrawable(R.drawable.ic_videopholder))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.playBtn.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(holder.imageview);


            holder.imageview.setForeground(activity.getResources().getDrawable(R.drawable.ic_foreground_img));
        } else {
            Glide.with(activity).load(info.getFilePath())
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
                    .load(info.getFilePath())
                    .placeholder(R.drawable.ic_selfiepholder)
                    .into(holder.imageview);*/
        }

    }

    @Override
    public int getItemCount() {
        return photosAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {//   parent_Layout

        @BindView(R.id.play_btn)
        ImageView playBtn;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;


        @BindView(R.id.imageview)
        ImageView imageview;
        @BindView(R.id.parent_Layout)
        LinearLayout parent_Layout;

        @OnClick(R.id.parent_Layout)
        void onClick(View v) {
            Info info = photosAlbum.get(getAdapterPosition());
            if (info.getFilePath() != null && !info.getFilePath().isEmpty()) {
                onClickPhoto.onClickPhoto(info.getServiceRequestTypeId(), info.getFilePath());
            }

        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
