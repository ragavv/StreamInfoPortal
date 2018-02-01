package com.hpm.sp.streaminfoportal.EventsActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.R;

import java.util.*;

/**
 * Created by mahesh on 22/04/17.
 */

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter
        .DataObjectHolder>  {

    private ArrayList<EventDataObject> eventDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView nameLabel;
        TextView eventDetails;
        TextView eventDate;
        TextView eventPlace;
        TextView eventTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            nameLabel = (TextView) itemView.findViewById(R.id.eventName);
            eventDetails = (TextView) itemView.findViewById(R.id.eventDetails);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            eventPlace = (TextView) itemView.findViewById(R.id.eventPlace);
            eventTime = (TextView) itemView.findViewById(R.id.eventTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
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
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.nameLabel.setText(eventDataset.get(position).getNameText());
        holder.eventDetails.setText(eventDataset.get(position).getDetailsText());
        holder.eventPlace.setText("Location: " + eventDataset.get(position).getLocationText());
        holder.eventDate.setText("Date: " + eventDataset.get(position).getDateText());
    }


    @Override
    public int getItemCount() {
        int i=0;
        try{
            i=eventDataset.size();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return i;
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
