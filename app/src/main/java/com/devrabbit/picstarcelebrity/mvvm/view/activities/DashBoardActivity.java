package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.devrabbit.picstarcelebrity.databinding.ActivityDashBoardBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.ServicesOffering;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.UpdateCelbServResponse;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.DashBoardViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.navigator.DashBoardNavigator;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardActivity extends BaseActivity implements DashBoardNavigator {

    DashBoardViewModel viewModel;
    ActivityDashBoardBinding binding;
    List<ServicesOffering> servicesOfferingList = new ArrayList<>();
    CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new DashBoardViewModel(psr_prefsManager, this, this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        ButterKnife.bind(this);
        imageView = findViewById(R.id.profile_pic_imgv);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PSRUtils.isOnline(this)) {
            viewModel.getServiceDetailsOfCelebrity();
            binding.layoutOne.setVisibility(View.GONE);
            binding.layoutTwo.setVisibility(View.GONE);
            binding.layoutThree.setVisibility(View.GONE);
            binding.layoutFour.setVisibility(View.GONE);
        } else {
            PSRUtils.showNoNetworkAlert(this);
        }


    }

    @OnClick(R.id.slide_menu)
    void onClickedslidemenu() {
        Intent i = new Intent(DashBoardActivity.this, DashBoardCelebActivity.class);
        DashBoardActivity.this.startActivity(i);
    }



    public PSR_PrefsManager getPreferences() {
        return psr_prefsManager;
    }


    @Override
    public void onGettingServicesSuccess(UpdateCelbServResponse response) {
        servicesOfferingList.clear();
        servicesOfferingList.addAll(response.getInfo().getServicesOffering());

        if (servicesOfferingList.size() != 0) {
            if (servicesOfferingList.size() == 1) {
                updateUiForSingleService(servicesOfferingList, response);
            } else if (servicesOfferingList.size() == 2) {
                updateUiForTwoServices(servicesOfferingList, response);
            } else if (servicesOfferingList.size() == 3) {
                updateUiForThreeServices(servicesOfferingList, response);
            } else if (servicesOfferingList.size() == 4) {
                updateUiForFourServices(servicesOfferingList, response);
            }
        }else{

            binding.noEventsTv.setVisibility(View.VISIBLE);
       }

        viewModel.setUserName(psr_prefsManager.get(PSRConstants.USERNAME));
        Glide
                .with(this)
                .load(psr_prefsManager.get(PSRConstants.PROFILEPIC))
                .placeholder(R.drawable.ic_profilepholder)
                .into(imageView);

    }

    private void updateUiForSingleService(List<ServicesOffering> services, UpdateCelbServResponse response) {
        binding.layoutOne.setVisibility(View.VISIBLE);
        binding.layoutOne.setTag(services.get(0).getServiceId());
        binding.layoutOne.setBackground(getServiceBg(services.get(0).getServiceId()));
        binding.layoutOneServiceName.setText(services.get(0).getServiceDetails().getServiceName());
        binding.layoutOneImgv.setImageDrawable(getServiceIcon(services.get(0).getServiceId()));
        binding.layoutOneTotalcount.setText(getServiceTotalCount(services.get(0).getServiceId(), response));
    }


    private void updateUiForTwoServices(List<ServicesOffering> services, UpdateCelbServResponse response) {
        binding.layoutOne.setVisibility(View.VISIBLE);
        binding.layoutOne.setTag(services.get(0).getServiceId());
        binding.layoutOne.setBackground(getServiceBg(services.get(0).getServiceId()));
        binding.layoutOneServiceName.setText(services.get(0).getServiceDetails().getServiceName());
        binding.layoutOneImgv.setImageDrawable(getServiceIcon(services.get(0).getServiceId()));
        binding.layoutOneTotalcount.setText(getServiceTotalCount(services.get(0).getServiceId(), response));

        binding.layoutTwo.setVisibility(View.VISIBLE);
        binding.layoutTwo.setTag(services.get(1).getServiceId());
        binding.layoutTwo.setBackground(getServiceBg(services.get(1).getServiceId()));
        binding.layoutTwoServicename.setText(services.get(1).getServiceDetails().getServiceName());
        binding.layoutTwoImgv.setImageDrawable(getServiceIcon(services.get(1).getServiceId()));
        binding.layoutTwoTotalcount.setText(getServiceTotalCount(services.get(1).getServiceId(), response));


    }

    private void updateUiForThreeServices(List<ServicesOffering> services, UpdateCelbServResponse response) {
        binding.layoutOne.setVisibility(View.VISIBLE);
        binding.layoutOne.setTag(services.get(0).getServiceId());
        binding.layoutOne.setBackground(getServiceBg(services.get(0).getServiceId()));
        binding.layoutOneServiceName.setText(services.get(0).getServiceDetails().getServiceName());
        binding.layoutOneImgv.setImageDrawable(getServiceIcon(services.get(0).getServiceId()));
        binding.layoutOneTotalcount.setText(getServiceTotalCount(services.get(0).getServiceId(), response));

        binding.layoutTwo.setVisibility(View.VISIBLE);
        binding.layoutTwo.setTag(services.get(1).getServiceId());
        binding.layoutTwo.setBackground(getServiceBg(services.get(1).getServiceId()));
        binding.layoutTwoServicename.setText(services.get(1).getServiceDetails().getServiceName());
        binding.layoutTwoImgv.setImageDrawable(getServiceIcon(services.get(1).getServiceId()));
        binding.layoutTwoTotalcount.setText(getServiceTotalCount(services.get(1).getServiceId(), response));

        binding.layoutThree.setVisibility(View.VISIBLE);
        binding.layoutThree.setTag(services.get(2).getServiceId());
        binding.layoutThree.setBackground(getServiceBg(services.get(2).getServiceId()));
        binding.layoutThreeServicename.setText(services.get(2).getServiceDetails().getServiceName());
        binding.layoutThreeImgv.setImageDrawable(getServiceIcon(services.get(2).getServiceId()));
        binding.layoutThreeTotalcount.setText(getServiceTotalCount(services.get(2).getServiceId(), response));


    }

    private void updateUiForFourServices(List<ServicesOffering> services, UpdateCelbServResponse response) {
        binding.layoutOne.setVisibility(View.VISIBLE);
        binding.layoutOne.setTag(services.get(0).getServiceId());
        binding.layoutOne.setBackground(getServiceBg(services.get(0).getServiceId()));
        binding.layoutOneServiceName.setText(services.get(0).getServiceDetails().getServiceName());
        binding.layoutOneImgv.setImageDrawable(getServiceIcon(services.get(0).getServiceId()));
        binding.layoutOneTotalcount.setText(getServiceTotalCount(services.get(0).getServiceId(), response));

        binding.layoutTwo.setVisibility(View.VISIBLE);
        binding.layoutTwo.setTag(services.get(1).getServiceId());
        binding.layoutTwo.setBackground(getServiceBg(services.get(1).getServiceId()));
        binding.layoutTwoServicename.setText(services.get(1).getServiceDetails().getServiceName());
        binding.layoutTwoImgv.setImageDrawable(getServiceIcon(services.get(1).getServiceId()));
        binding.layoutTwoTotalcount.setText(getServiceTotalCount(services.get(1).getServiceId(), response));

        binding.layoutThree.setVisibility(View.VISIBLE);
        binding.layoutThree.setTag(services.get(2).getServiceId());
        binding.layoutThree.setBackground(getServiceBg(services.get(2).getServiceId()));
        binding.layoutThreeServicename.setText(services.get(2).getServiceDetails().getServiceName());
        binding.layoutThreeImgv.setImageDrawable(getServiceIcon(services.get(2).getServiceId()));
        binding.layoutThreeTotalcount.setText(getServiceTotalCount(services.get(2).getServiceId(), response));

        binding.layoutFour.setVisibility(View.VISIBLE);
        binding.layoutFour.setTag(services.get(3).getServiceId());
        binding.layoutFour.setBackground(getServiceBg(services.get(3).getServiceId()));
        binding.layoutFourServicename.setText(services.get(3).getServiceDetails().getServiceName());
        binding.layoutFourImgv.setImageDrawable(getServiceIcon(services.get(3).getServiceId()));
        binding.layoutFourTotalcount.setText(getServiceTotalCount(services.get(3).getServiceId(), response));


    }

    private Drawable getServiceBg(int serviceId) {
        Drawable drawable = null;
        if (serviceId == 1) {
            drawable = getResources().getDrawable(R.drawable.dashboard_photo_selfie_bg);

        } else if (serviceId == 2) {
            drawable = getResources().getDrawable(R.drawable.dashboard_live_selfie_bg);
        } else if (serviceId == 3) {
            drawable = getResources().getDrawable(R.drawable.dashboard_video_msg_bg);

        } else if (serviceId == 4) {
            drawable = getResources().getDrawable(R.drawable.dashboard_live_video_bg);

        }
        return drawable;
    }

    Drawable getServiceIcon(int serviceId) {
        Drawable drawable = null;
        if (serviceId == 1) {
            drawable = getResources().getDrawable(R.drawable.ic_photoselfie_logo);

        } else if (serviceId == 2) {
            drawable = getResources().getDrawable(R.drawable.ic_liveselfies_logo);
        } else if (serviceId == 3) {
            drawable = getResources().getDrawable(R.drawable.ic_videomsg_logo);

        } else if (serviceId == 4) {
            drawable = getResources().getDrawable(R.drawable.ic_livevideo_logo);

        }
        return drawable;
    }

    public String getServiceName(int serviceId) {
        String name = null;
        if (serviceId == 1) {
            name = getResources().getString(R.string.photoSelfie_txt);
        } else if (serviceId == 2) {
            name = getResources().getString(R.string.liveselfie_txt);
        } else if (serviceId == 3) {
            name = getResources().getString(R.string.videomsg_txt);
        } else if (serviceId == 4) {
            name = getResources().getString(R.string.live_video_txt);
        }
        return name;


    }

    private String getServiceTotalCount(int serviceId, UpdateCelbServResponse response) {
        String serviceTotalCount = "";
        if (serviceId == 1) {
            serviceTotalCount = response.getInfo().getPhotoSelfiesCount().toString();

        } else if (serviceId == 2) {
            serviceTotalCount = response.getInfo().getLiveSelfiesCount().toString();

        } else if (serviceId == 3) {
            serviceTotalCount = response.getInfo().getVideoMessagesCount().toString();

        } else if (serviceId == 4) {
            serviceTotalCount = response.getInfo().getLiveVideosCount().toString();
        }
        return serviceTotalCount;

    }

}