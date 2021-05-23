package com.devrabbit.picstarcelebrity.mvvm.viewmodel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.devrabbit.picstarcelebrity.BR;
import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.PSR_PrefsManager;


import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventRequest;
import com.devrabbit.picstarcelebrity.mvp.models.AddEvents.AddEventResponce;
import com.devrabbit.picstarcelebrity.mvp.models.createevent.CreateEventResponse;
import com.devrabbit.picstarcelebrity.mvp.presenters.AddEventPresenter;
import com.devrabbit.picstarcelebrity.mvp.views.AddEventView;
import com.devrabbit.picstarcelebrity.mvvm.model.AddEventModel;

import com.devrabbit.picstarcelebrity.mvvm.view.activities.AddEventActivity;
import com.devrabbit.picstarcelebrity.navigator.OnOkButtonClick;
import com.devrabbit.picstarcelebrity.utils.PSRConstants;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddEventViewModel extends BaseObservable implements AddEventView, OnOkButtonClick {
    private String datetimestring, datetimestringUTC;
    private final AddEventPresenter addEventPresenter;
    private final AddEventModel model;
    private final AddEventActivity activity;
    PSR_PrefsManager psr_prefsManager;
    private String uiDate;

    @Bindable
    public String getVenueName() {
        return model.getVenueName();
    }

    public void setVenueName(String venueName) {
        model.setVenueName(venueName);
        notifyPropertyChanged(BR.venueName);
    }

    @Bindable
    public String getEventDescription() {
        return model.getEventDescription();
    }

    public void setEventDescription(String eventDescription) {
        model.setEventDescription(eventDescription);
        notifyPropertyChanged(BR.eventDescription);
    }

    @Bindable
    public String getEventDate() {
        return model.getEventDate();
    }

    public void setEventDate(String eventDate) {
        model.setEventDate(eventDate);
        notifyPropertyChanged(BR.eventDate);
    }


    public AddEventViewModel(AddEventActivity activity, PSR_PrefsManager psr_prefsManager) {
        model = new AddEventModel("", "", "", "");
        this.psr_prefsManager = psr_prefsManager;
        this.addEventPresenter = new AddEventPresenter();
        this.addEventPresenter.attachMvpView(this);
        this.activity = activity;
    }


    @Bindable
    public String getEventName() {
        return model.getEventName();
    }

    public void setEventName(String eventName) {
        model.setEventName(eventName);
        notifyPropertyChanged(BR.eventName);
    }

    public void createButtonClicked() {
        if (model.getEventName().trim().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_eventname_txt), null);
            return;
        }
        if (model.getVenueName().trim().isEmpty()) {
            PSRUtils.showAlert(activity,  activity.getResources().getString(R.string.enter_venue), null);
            return;
        }
        if (model.getEventDate().trim().isEmpty()) {
            PSRUtils.showAlert(activity,  activity.getResources().getString(R.string.enter_eventdate_txt), null);
            return;
        }
        if (model.getEventDescription().trim().isEmpty()) {
            PSRUtils.showAlert(activity, activity.getResources().getString(R.string.enter_descritption), null);
            return;
        }

        if (PSRUtils.isOnline(activity)) {
            PSRUtils.showProgressDialog(activity);
            AddEventRequest addEventRequest = new AddEventRequest();
            addEventRequest.setEvent_location(model.getVenueName());
            addEventRequest.setEvent_id(0);
            addEventRequest.setEvent_desc(model.getEventDescription());
            addEventRequest.setEvent_name(model.getEventName());
            addEventRequest.setCelebrity_id(psr_prefsManager.get(PSRConstants.USER_ID));
            addEventRequest.setLoggedin_user_id(psr_prefsManager.get(PSRConstants.USER_ID));
            addEventRequest.setEvent_date(datetimestringUTC + "+00:00");

            addEventPresenter.doCreate(psr_prefsManager.get(PSRConstants.SELECTED_LANGUAGE),PSRUtils.getHeader(activity.getPreferences()), addEventRequest);
        } else {
            PSRUtils.showNoNetworkAlert(activity);
        }
    }


    public void dateclicker() {
        datetimestring = "";
        final Calendar myCalendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                datetimestring = datetimestring.concat(PSRUtils.sanitizeTime(view.getHour()) + ":" + PSRUtils.sanitizeTime(view.getMinute()));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd,HH:mm", Locale.ENGLISH);

                try {
                    Log.d("datetimestring", datetimestring);
                    Date eventDate = df.parse(datetimestring);

                    String uiDate = new SimpleDateFormat("MM/dd/yyyy, hh:mm a", Locale.ENGLISH).format(eventDate);
                    model.setEventDate(uiDate);
                    setEventDate(uiDate);
                    TimeZone fromTZ = TimeZone.getDefault();
                    TimeZone toTZ = TimeZone.getTimeZone("UTC");
                    eventDate = PSRUtils.changeTimezoneOfDate(eventDate, fromTZ, toTZ).getTime();
                    datetimestringUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH).format(eventDate);
                    Log.d("datetimestringUTC", datetimestringUTC);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, timeSetListener, 12, 00, false);



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;

                datetimestring = view.getYear() + "-" + month + "-" + view.getDayOfMonth() + ",";
                timePickerDialog.show();

            }
        };
        DatePickerDialog dateDialog = new DatePickerDialog(activity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dateDialog.show();
    }

    @Override
    public void onCreatingEventSuccess(CreateEventResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showButtonActionAlert(activity, response.getMessage(), null, this);
        setEventName("");
        setVenueName("");
        setEventDate("");
        setEventDescription("");
    }

    @Override
    public void onCreatingEventFailed(CreateEventResponse response) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, response.getMessage(), null);
    }

    @Override
    public Context getMvpContext() {
        return null;
    }

    @Override
    public void onError(Throwable throwable) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onNoInternetConnection() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showNoNetworkAlert(activity);
    }

    @Override
    public void onErrorCode(String s) {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onSessionExpired() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onServerError() {
        PSRUtils.hideProgressDialog();
        PSRUtils.showAlert(activity, activity.getResources().getString(R.string.somethingwnt_wrong_txt), null);
    }

    @Override
    public void onClickOk() {
        activity.finish();
    }
}
