package com.hpm.sp.streaminfoportal.Events;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.R;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mahesh on 22/04/17.
 */

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter
        .DataObjectHolder> {

    private ArrayList<EventDataObject> eventDataset = new ArrayList<>();
    private RecyclerViewClickListener myClickListener;

    public EventRecyclerViewAdapter() {
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView nameLabel;
        TextView eventDate;
        TextView eventPlace;
        CardView mBranchView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            nameLabel = (TextView) itemView.findViewById(R.id.eventName);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            eventPlace = (TextView) itemView.findViewById(R.id.eventPlace);
            mBranchView = (CardView) itemView.findViewById(R.id.branch_view);
        }
    }

    public void swap(ArrayList<EventDataObject> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (eventDataset != null && eventDataset.size() > 0)
            eventDataset.clear();
        eventDataset.addAll(datas);
        notifyDataSetChanged();

    }

    public void setOnItemClickListener(RecyclerViewClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public EventRecyclerViewAdapter(ArrayList<EventDataObject> myDataset) {
        eventDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.nameLabel.setText(eventDataset.get(position).getNameText());
        holder.eventPlace.setText(eventDataset.get(position).getLocationText());
        holder.eventDate.setText(new SimpleDateFormat("dd-MMM-YYYY, hh:mm a").format(eventDataset.get(position).getDateText()));


        holder.mBranchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.onItemClick(position, eventDataset.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        int i = 0;
        try {
            i = eventDataset.size();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return i;
    }

    public interface MyClickListener {
        void onItemClick(EventDataObject dataObject, TextView v);
    }
}
