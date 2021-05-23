package com.devrabbit.picstarcelebrity.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.mvp.models.History.Info;
import com.devrabbit.picstarcelebrity.mvp.models.History.LiveVideo;
import com.devrabbit.picstarcelebrity.mvp.models.History.RequestedUser;
import com.devrabbit.picstarcelebrity.mvp.models.History.VideoEvent;
import com.devrabbit.picstarcelebrity.navigator.OnClickPhoto;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.github.siyamed.shapeimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CelebrityHistoryAdapter extends RecyclerView.Adapter<CelebrityHistoryAdapter.ViewHolder> {

    ArrayList<Info> history;
    Activity activity;
    TimeZone toTZ = TimeZone.getDefault();
    TimeZone fromTZ = TimeZone.getTimeZone("UTC");
    static SimpleDateFormat createdDateFormat = new SimpleDateFormat("MM/dd/yy");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
    static SimpleDateFormat videoeventDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat date = new SimpleDateFormat("dd");
    SimpleDateFormat month = new SimpleDateFormat("MMM");
    String todaysDate = "";
    OnClickPhoto onClickPhoto;
    int index;


    public CelebrityHistoryAdapter(ArrayList<Info> history, Activity activity, OnClickPhoto onClickPhoto, int index) {
        this.history = history;
        this.activity = activity;
        this.onClickPhoto = onClickPhoto;
        this.index = index;
        Calendar cal = Calendar.getInstance();
        todaysDate = videoeventDateFormat.format(cal.getTime());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Info info = history.get(position);
            holder.eventPrice.setText("$" + getServiceCost(info.getAmount().toString()));

            Date serverDate = null;
            try {
                serverDate = df.parse(info.getCreatedAt());
                Calendar calInLocal = PSRUtils.changeTimezoneOfDate(serverDate, fromTZ, toTZ);
                Date dateLocal = calInLocal.getTime();
                String createdAt = "Created: " + createdDateFormat.format(dateLocal);
                holder.createdatTV.setText(createdAt);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.statusTV.setText(info.getStatus());
            holder.playBtnImgv.setVisibility(View.GONE);
            holder.eventdateTV.setVisibility(View.VISIBLE);
            holder.uploadVideoBtn.setVisibility(View.INVISIBLE);
            holder.shortdateLL.setVisibility(View.VISIBLE);
            holder.eventlocationTV.setVisibility(View.VISIBLE);
            holder.eventdescTV.setVisibility(View.VISIBLE);
            holder.photoImgv.setForeground(null);
            holder.fanNameTv.setText(info.getRequestedUser().getUsername());

            if (info.getRequestedUser().isBlocked()) {
                holder.blockUserBtn.setText(activity.getResources().getString(R.string.blocked_txt));
            } else {
                holder.blockUserBtn.setText(activity.getResources().getString(R.string.block_txt));
            }


            if (info.getServiceRequestTypeId() == 1) {
                handlePhotoSelfie(holder, info, info.getRequestedUser());
            } else if (info.getServiceRequestTypeId() == 2) {
                handleLiveSelfie(holder, info, info.getRequestedUser());
            } else if (info.getServiceRequestTypeId() == 3) {
                handleVideoMsg(holder, info, info.getRequestedUser());
            } else if (info.getServiceRequestTypeId() == 4) {
                handleLiveVideo(holder, info, info.getRequestedUser());
            }
            //expired,completed, payment failed, created, payment success,
            if (info.getStatus() != null) {
                if (info.getStatus().toLowerCase().contains("completed")
                        || info.getStatus().toLowerCase().contains("success")
                        || info.getStatus().toLowerCase().contains("created")) {
                    Drawable img = activity.getResources().getDrawable(R.drawable.ic_green_ticks);
                    holder.statusTV.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    holder.statusTV.setBackground(ContextCompat.getDrawable(activity, R.drawable.status_success_bg));
                    holder.statusTV.setTextColor(activity.getResources().getColor(R.color.completed_txt_color_green));
                } else if (info.getStatus().toLowerCase().contains("expire")
                        || info.getStatus().toLowerCase().contains("fail")) {
                    Drawable img = activity.getResources().getDrawable(R.drawable.ic_close_red);
                    holder.statusTV.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    holder.statusTV.setBackground(ContextCompat.getDrawable(activity, R.drawable.status_fail_bg));
                    holder.statusTV.setTextColor(activity.getResources().getColor(R.color.fail_txt_color));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //getServiceRequestTypeId = 1
    private void handlePhotoSelfie(@NotNull ViewHolder holder, Info info, RequestedUser user) {
        holder.eventIV.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_photoselfie_circular));
        try {
           /* serverDate = df.parse(info.getPhoto().getCreatedAt());
            Calendar calInLocal = PSRUtils.changeTimezoneOfDate(serverDate, fromTZ, toTZ);
            Date dateLocal = calInLocal.getTime();
            String[] dateValues = PSRUtils.getEventDate(df.format(dateLocal));
            holder.eventdateTV.setText(dateValues[0]);
            holder.dateTV.setText(dateValues[1]);
            holder.monthTV.setText(dateValues[2]);*/

            if (info.getFilePath() != null) {
                holder.photoImgv.setVisibility(View.VISIBLE);
                holder.progressBar.setVisibility(View.VISIBLE);
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
                        .into(holder.photoImgv);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.shortdateLL.setVisibility(View.GONE);
        holder.eventdateTV.setVisibility(View.GONE);
        holder.eventNameTv.setVisibility(View.GONE);
        holder.eventlocationTV.setVisibility(View.GONE);
        holder.eventdescTV.setVisibility(View.GONE);
    }

    //getServiceRequestTypeId = 2
    private void handleLiveSelfie(ViewHolder holder, Info info, RequestedUser user) {
        holder.eventIV.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_liveselfie_circular));

        if (info.getFilePath() != null && !info.getFilePath().isEmpty()) {
            holder.photoImgv.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
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
                    .into(holder.photoImgv);
        }


        if (info.getLiveEvent() != null) {
            Date serverDate;
            try {
                serverDate = df.parse(info.getLiveEvent().getEventDate());
                Calendar calInLocal = PSRUtils.changeTimezoneOfDate(serverDate, fromTZ, toTZ);
                Date dateLocal = calInLocal.getTime();
                String[] dateValues = PSRUtils.getEventDate(df.format(dateLocal));
                holder.eventdateTV.setText(dateValues[0]);
                holder.dateTV.setText(dateValues[1]);
                holder.monthTV.setText(dateValues[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.eventNameTv.setVisibility(View.VISIBLE);
            holder.eventNameTv.setText(info.getLiveEvent().getEventName().trim());
            holder.eventlocationTV.setText(info.getLiveEvent().getEventLocation());
            holder.eventdescTV.setText(info.getLiveEvent().getEventDesc().trim());

        } else {
            holder.eventNameTv.setVisibility(View.GONE);
            holder.shortdateLL.setVisibility(View.GONE);
            holder.eventdateTV.setVisibility(View.GONE);
            holder.eventlocationTV.setVisibility(View.GONE);
            holder.eventdescTV.setVisibility(View.GONE);
        }
    }

    //getServiceRequestTypeId = 3
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void handleVideoMsg(ViewHolder holder, Info info, RequestedUser user) {
        holder.photoImgv.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.GONE);
        holder.eventNameTv.setVisibility(View.VISIBLE);
        holder.eventIV.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_videomsg_circular));

        if (info.getFilePath() == null || info.getFilePath().isEmpty()) {
            if (index != 2 && !info.getStatus().toLowerCase().contains("expire")) {
                holder.uploadVideoBtn.setVisibility(View.VISIBLE);
                holder.uploadVideoBtn.setText(activity.getResources().getString(R.string.uploadvideo_txt));
            }

        } else if (info.getFilePath() != null && !info.getFilePath().isEmpty()) {
            ///show imageView
            holder.photoImgv.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.photoImgv.setForeground(activity.getResources().getDrawable(R.drawable.ic_foreground_img));
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
                            holder.playBtnImgv.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(holder.photoImgv);


        }

        if (info.getVideoEvent() != null) {

            VideoEvent videoEvent = info.getVideoEvent();
            holder.eventNameTv.setText(videoEvent.getVideoEventName().trim());


            Date serverDate;
            try {
                serverDate = df.parse(videoEvent.getVideoEventDate());
                holder.eventdateTV.setText(videoeventDateFormat.format(serverDate));
                holder.dateTV.setText(date.format(serverDate));
                holder.monthTV.setText(month.format(serverDate).toUpperCase());

            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.eventlocationTV.setVisibility(View.GONE);
            holder.eventdescTV.setText(videoEvent.getVideoEventDesc().trim());
        }
    }

    //getServiceRequestTypeId = 4
    private void handleLiveVideo(ViewHolder holder, Info info, RequestedUser user) {
        holder.photoImgv.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.GONE);
        holder.eventNameTv.setVisibility(View.VISIBLE);
        holder.eventIV.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_livevideo_circular));
        holder.eventNameTv.setText(info.getLiveVideo().getLiveVideoName().trim());
        Date serverDate;
        try {
            serverDate = df.parse(info.getLiveVideo().getLiveVideoDatetime());
            holder.eventdateTV.setText(videoeventDateFormat.format(serverDate));
            if (todaysDate.equalsIgnoreCase(videoeventDateFormat.format(serverDate)) && index != 2 && !info.getStatus().toLowerCase().contains("expire") && info.getStatus().toLowerCase().contains("success")) {
                holder.uploadVideoBtn.setVisibility(View.VISIBLE);
                holder.uploadVideoBtn.setText(activity.getResources().getString(R.string.start_live_video_txt));

            }

            holder.dateTV.setText(date.format(serverDate));
            holder.monthTV.setText(month.format(serverDate).toUpperCase());

          /*  serverDate = df.parse(video.getLiveVideoDatetime());
            Calendar calInLocal = PSRUtils.changeTimezoneOfDate(serverDate, fromTZ, toTZ);
            Date dateLocal = calInLocal.getTime();
            String[] dateValues = PSRUtils.getEventDate(df.format(dateLocal));
            holder.eventdateTV.setText(dateValues[0]);
            holder.dateTV.setText(dateValues[1]);
            holder.monthTV.setText(dateValues[2]);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  String[] dateValues = PSRUtils.getEventDate(video.getLiveVideoDatetime());
        holder.eventdateTV.setText(dateValues[0]);
        holder.dateTV.setText(dateValues[1]);
        holder.monthTV.setText(dateValues[2]);*/


        holder.eventlocationTV.setVisibility(View.GONE);
        holder.eventdescTV.setText(info.getLiveVideo().getLiveVideoDesc().trim());

    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public static String getServiceCost(String cost) {
        String serviceCost = "";
        if (cost.endsWith(".0")) {
            serviceCost = cost.replace(".0", "");
        } else {
            serviceCost = cost;
        }
        return serviceCost;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.play_btn)
        ImageView playBtnImgv;
        @BindView(R.id.upload_video_button)
        Button uploadVideoBtn;
        @BindView(R.id.event_name_TV)
        TextView eventNameTv;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.photo_selfie_imgV)
        RoundedImageView photoImgv;
        @BindView(R.id.eventIV)
        ImageView eventIV;
        @BindView(R.id.eventPrice)
        TextView eventPrice;
        @BindView(R.id.fan_nameTV)
        TextView fanNameTv;
        @BindView(R.id.eventdateTV)
        TextView eventdateTV;
        @BindView(R.id.eventlocationTV)
        TextView eventlocationTV;
        @BindView(R.id.eventdescTV)
        TextView eventdescTV;
        @BindView(R.id.createdatTV)
        TextView createdatTV;
        @BindView(R.id.dateTV)
        TextView dateTV;
        @BindView(R.id.monthTV)
        TextView monthTV;
        @BindView(R.id.shortdateLL)
        View shortdateLL;
        @BindView(R.id.statusTV)
        TextView statusTV;
        @BindView(R.id.blockuser_btn)
        Button blockUserBtn;

        @OnClick(R.id.photo_selfie_imgV)
        void onClick(View v) {
            Info info = history.get(getAdapterPosition());
            if (info.getFilePath() != null && !info.getFilePath().isEmpty()) {
                onClickPhoto.onClickPhoto(info.getServiceRequestTypeId(), info.getFilePath());
            }

        }

        @OnClick(R.id.upload_video_button)
        void onClickBTn(View v) {
            Info info = history.get(getAdapterPosition());
            /// onClickPhoto.onClickPhoto(info.getFilePath());
            onClickPhoto.onClickUploadBtn(info.getServiceRequestId(), info.getServiceRequestTypeId());
        }


        @OnClick(R.id.blockuser_btn)
        void onClickBlock(View v) {
            Info info = history.get(getAdapterPosition());
            if (!info.getRequestedUser().isBlocked()) {
                onClickPhoto.onClickBlockBtn(info.getRequestedUser().getUserId());
            }
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
