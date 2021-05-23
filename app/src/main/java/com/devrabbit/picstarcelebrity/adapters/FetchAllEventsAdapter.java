package com.devrabbit.picstarcelebrity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devrabbit.picstarcelebrity.R;
import com.devrabbit.picstarcelebrity.helpers.EventsViewHolder;
import com.devrabbit.picstarcelebrity.mvp.models.celebrityevents.FetchAllEventsInfo;

import java.text.ParseException;
import java.util.List;

public class FetchAllEventsAdapter extends RecyclerView.Adapter<EventsViewHolder> {
    List<FetchAllEventsInfo> arrayList;

    Context context;

    public FetchAllEventsAdapter(List<FetchAllEventsInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_events, viewGroup, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int i) {
        FetchAllEventsInfo current = arrayList.get(i);
        try {
            holder.setData(context, current.getEvent_name(), current.getEvent_date(), current.getEvent_location(),
                    current.getEvent_desc(), current.getCreated_at(), getItemCount(), arrayList);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
