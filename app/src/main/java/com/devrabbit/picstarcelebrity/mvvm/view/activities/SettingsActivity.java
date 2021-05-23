package com.devrabbit.picstarcelebrity.mvvm.view.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.databinding.DataBindingUtil;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.databinding.ActivitySettingsBinding;
import com.devrabbit.picstarcelebrity.helpers.LocaleHelper;
import com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.ServicesOffering;
import com.devrabbit.picstarcelebrity.mvvm.viewmodel.Settings_View_Model;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.suke.widget.SwitchButton;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class SettingsActivity extends BaseActivity {

    Settings_View_Model settings_view_model;
    ActivitySettingsBinding activitySettingsBinding;

    boolean isLiveSelfieEnabled = false;
    boolean isPhotoSelfieEnabled = false;
    boolean isLiveVideoEnabled = false;
    boolean isVideoMsgEnabled = false;
    String liveselfieCost = "";
    String photoselfieCost = "";
    String livevideoCost = "";
    String videoMsgCost = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ServicesOffering> servicesOfferingList = getServicesOfCelebrity();
        if (servicesOfferingList != null) {
            for (ServicesOffering servicesOffering : servicesOfferingList) {
                if (servicesOffering.getServiceId() == PSRConstants.LIVESELFIE_SERVICE_REQ_ID) {
                    isLiveSelfieEnabled = true;
                    liveselfieCost = getServiceCost(servicesOffering.getServiceCost() + "");
                } else if (servicesOffering.getServiceId() == PSRConstants.PHOTOSELFIE_SERVICE_REQ_ID) {
                    isPhotoSelfieEnabled = true;
                    photoselfieCost = getServiceCost(servicesOffering.getServiceCost() + "");
                } else if (servicesOffering.getServiceId() == PSRConstants.LIVE_VIDEO_SERVICE_REQ_ID) {
                    isLiveVideoEnabled = true;
                    livevideoCost = getServiceCost(servicesOffering.getServiceCost() + "");
                } else if (servicesOffering.getServiceId() == PSRConstants.VIDEOMSGS_SERVICE_REQ_ID) {
                    isVideoMsgEnabled = true;
                    videoMsgCost = getServiceCost(servicesOffering.getServiceCost() + "");
                }
            }
        }
        settings_view_model = new Settings_View_Model(this, psr_prefsManager, isLiveSelfieEnabled, isLiveVideoEnabled, isPhotoSelfieEnabled, isVideoMsgEnabled);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        activitySettingsBinding.setViewModel(settings_view_model);
        activitySettingsBinding.executePendingBindings();

        settings_view_model.setLiveSelfiePrice(liveselfieCost);
        settings_view_model.setLiveVideoPrice(livevideoCost);
        settings_view_model.setPhotoSelfiePrice(photoselfieCost);
        settings_view_model.setVideoMsgPrice(videoMsgCost);

        if (isLiveSelfieEnabled) {
            activitySettingsBinding.liveselfieSwitch.setChecked(true);

        }
        if (isLiveVideoEnabled) {
            activitySettingsBinding.livevideoSwitch.setChecked(true);
        }

        if (isPhotoSelfieEnabled) {
            activitySettingsBinding.photoselfieSwitch.setChecked(true);
        }
        if (isVideoMsgEnabled) {
            activitySettingsBinding.videomsgSwitch.setChecked(true);
        }


        activitySettingsBinding.livevideoSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settings_view_model.onClickLiveVideoSwitch(isChecked);
            }
        });

        activitySettingsBinding.liveselfieSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settings_view_model.onClickLiveSelfieSwitch(isChecked);
            }
        });

        activitySettingsBinding.photoselfieSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settings_view_model.onClickPhotoSelfieSwitch(isChecked);
            }
        });

        activitySettingsBinding.videomsgSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                settings_view_model.onClickVideoMsgSwitch(isChecked);
            }
        });


        activitySettingsBinding.calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushAppointmentsToCalender(SettingsActivity.this, "PICSTAR EVENT", "EVENTS", "HYDERABAD", 1, 2);
                /*
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "A Test Event from android app");
                startActivity(intent);*/
            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }


    public static long pushAppointmentsToCalender(Activity curActivity, String title, String addInfo, String place, int status, long startDate) {
        /***************** Event: note(without alert) *******************/
        Calendar cal = Calendar.getInstance();

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);


      //  long endDate = startDate + 1000 * 60 * 60; // For next 1hr
        eventValues.put("dtstart", cal.getTimeInMillis()+180000);
        eventValues.put("dtend", cal.getTimeInMillis() + 1000 * 60 * 60);
        //  eventValues.put("duration","PT1H");

        // values.put("allDay", 1); //If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):
        eventValues.put("eventTimezone", TimeZone.getDefault().getID());
        /*Comment below visibility and transparency  column to avoid java.lang.IllegalArgumentException column visibility is invalid error */

    /*eventValues.put("visibility", 3); // visibility to default (0),
                                        // confidential (1), private
                                        // (2), or public (3):
    eventValues.put("transparency", 0); // You can control whether
                                        // an event consumes time
                                        // opaque (0) or transparent
                                        // (1).
      */
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

        Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        //  if (needReminder) {
        /***************** Event: Reminder(with alert) Adding reminder to event *******************/

        String reminderUriString = "content://com.android.calendar/reminders";
        ContentValues reminderValues = new ContentValues();
        reminderValues.put("event_id", eventID);
        reminderValues.put("minutes",  2); // Default value of the
        // system. Minutes is a
        // integer
        reminderValues.put("method", 1); // Alert Methods: Default(0),
        // Alert(1), Email(2),
        // SMS(3)
        Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        if (reminderUri != null) {
            PSRUtils.showToast(curActivity, "Event is created");
        }
        return eventID;

    }


    public ArrayList<ServicesOffering> getServicesOfCelebrity() {

        Gson gson = new Gson();
        String json = psr_prefsManager.get("SERVICESLIST");
        Type type = new TypeToken<ArrayList<com.devrabbit.picstarcelebrity.mvp.models.updateCelbServices.ServicesOffering>>() {
        }.getType();
        return gson.fromJson(json, type);
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
}