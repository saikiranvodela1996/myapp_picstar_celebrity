package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;
import com.devrabbit.picstarcelebrity.mvvm.model.DashBoardMenuModel;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.AlbumsActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.CelebrityHistoryActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.ContactUsActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.DashBoardCelebActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.FetchAllEventsActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.PrivacyPolicyActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.SettingsActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.StockImagesActivity;
import com.devrabbit.picstarcelebrity.mvvm.view.activities.UpdateProfilePic;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.devrabbit.picstarcelebrity.utils.PersonaliseSingleton;


import java.util.Observable;

import butterknife.BindView;

public class DasboardMenuViewModel extends BaseObservable {
    DashBoardCelebActivity dashBoardCelebActivity;
    PSR_PrefsManager psr_prefsManager;
    @BindView(R.id.user_id_pref)
    TextView user_name;
    @BindView(R.id.profile_pic)
    de.hdodenhof.circleimageview.CircleImageView image_profile_pic;
    DashBoardMenuModel menuModel;
    private String lang;

    public DasboardMenuViewModel(DashBoardCelebActivity dashBoardCelebActivity, PSR_PrefsManager psr_prefsManager) {
        this.dashBoardCelebActivity = dashBoardCelebActivity;
        this.psr_prefsManager = psr_prefsManager;
        menuModel = new DashBoardMenuModel();
    }

    @Bindable
    public String getUserName() {
        return menuModel.getUserName();
    }

    public void setUserName(@Nullable String userName) {
        menuModel.setUserName(userName);
        notifyPropertyChanged(BR.userName);

    }

    public void onCloseclicked() {
        dashBoardCelebActivity.finish();
    }

    public void onEventsClick() {
        Intent i = new Intent(dashBoardCelebActivity, FetchAllEventsActivity.class);
        dashBoardCelebActivity.startActivity(i);
    }

    public void onEditProfileClick() {
        Intent i = new Intent(dashBoardCelebActivity, UpdateProfilePic.class);
        dashBoardCelebActivity.startActivity(i);
    }

    public void onClickStockPhotos() {
        // PSRUtils.showAlert(dashBoardCelebActivity, "Work in Progress", null);
        dashBoardCelebActivity.startActivity(new Intent(dashBoardCelebActivity, StockImagesActivity.class));
    }

    public void onClickAlbum() {
        dashBoardCelebActivity.startActivity(new Intent(dashBoardCelebActivity, AlbumsActivity.class));
    }

    public void onClickHistory() {
        Intent i = new Intent(dashBoardCelebActivity, CelebrityHistoryActivity.class);
        dashBoardCelebActivity.startActivity(i);
    }


    public void onClickSettings() {
        dashBoardCelebActivity.startActivity(new Intent(dashBoardCelebActivity, SettingsActivity.class));
    }



    public  void onClickPrivacyPolicy(){
        dashBoardCelebActivity.startActivity(new Intent(dashBoardCelebActivity, PrivacyPolicyActivity.class));
    }
    public  void onClickContactUs(){
        dashBoardCelebActivity.startActivity(new Intent(dashBoardCelebActivity, ContactUsActivity.class));
    }




    public void onClickLanguage() {
        final String[] Language = {"English", "Spanish"};
        final int checkedItem;
        final boolean whichh;
        if (LocaleHelper.getLanguage(dashBoardCelebActivity).equals("en")) {
            checkedItem=0;
            whichh=true;
        }
        else
        {
            whichh=false;
            checkedItem=1;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(dashBoardCelebActivity);
        builder.setTitle(dashBoardCelebActivity.getResources().getString(R.string.select_language_txt))
                .setSingleChoiceItems(Language, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(Language[which].equals("English")&&checkedItem==1)
                        {
                            LocaleHelper.setLocale(dashBoardCelebActivity, "en");
                        }
                        //if user select prefered language as Hindi then
                        if(Language[which].equals("Spanish")&&checkedItem==0)
                        {
                            LocaleHelper.setLocale(dashBoardCelebActivity, "es");
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        System.exit(1);
                    }
                });
        builder.create().show();
    }

}
