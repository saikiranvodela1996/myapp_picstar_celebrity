package com.devrabbit.picstarcelebrity.helpers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devrabbit.picstarcelebrity.R;

import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.EventsInsideInfo;
import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsInfo;
import com.devrabbit.picstarcelebrity.utils.PSRUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EventsViewHolder extends RecyclerView.ViewHolder {
    Context context;
    public EventsViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    public void setData(Context application, String event_name, String event_date, String event_location, String event_description, String created_at, int size, List<FetchAllEventsInfo> arrayList) throws ParseException {
        this.context = application;


        TextView textname = itemView.findViewById(R.id.event_name);
        TextView textdate = itemView.findViewById(R.id.event_date);
        TextView textlocation = itemView.findViewById(R.id.event_location);
        TextView textdescriptrion = itemView.findViewById(R.id.event_description);
        TextView textcreatedby = itemView.findViewById(R.id.event_created_at);
        TextView textdateformated = itemView.findViewById(R.id.event_date_formated);
        TextView textmonthformated = itemView.findViewById(R.id.event_month_formatted);


        textname.setText(event_name.trim());
        textdescriptrion.setText(event_description.trim());
        textlocation.setText(event_location.trim());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
        df.parse(event_date);
        TimeZone toTZ = TimeZone.getDefault();
        TimeZone fromTZ = TimeZone.getTimeZone("UTC");


        Calendar calendar = PSRUtils.changeTimezoneOfDate(df.parse(event_date), fromTZ, toTZ);
        Date dateLocal = calendar.getTime();
        textdate.setText(new SimpleDateFormat("MM/dd/yyyy, hh:mm a").format(dateLocal).toUpperCase());
        textdateformated.setText(new SimpleDateFormat("dd").format(dateLocal).toUpperCase());
        textmonthformated.setText(new SimpleDateFormat("MMM").format(dateLocal).toUpperCase());

        Calendar calendar2 = PSRUtils.changeTimezoneOfDate(df.parse(created_at), fromTZ, toTZ);
        Date dateLocal1 = calendar2.getTime();
        textcreatedby.setText("Created: " + new SimpleDateFormat("MM/dd/yy").format(dateLocal1).toUpperCase());


    }

}
