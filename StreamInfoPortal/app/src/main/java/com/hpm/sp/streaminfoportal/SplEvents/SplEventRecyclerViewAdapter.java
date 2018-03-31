package com.hpm.sp.streaminfoportal.SplEvents;

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

public class SplEventRecyclerViewAdapter extends RecyclerView.Adapter<SplEventRecyclerViewAdapter
        .DataObjectHolder>  {

    private ArrayList<SplEventDataObject> eventDataset;
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
           // nameLabel = (TextView) itemView.findViewById(R.id.splEventName);
            eventDetails = (TextView) itemView.findViewById(R.id.splEventDetails);
          //  eventDate = (TextView) itemView.findViewById(R.id.splEventDate);
          //  eventPlace = (TextView) itemView.findViewById(R.id.splEventPlace);
       //     eventTime = (TextView) itemView.findViewById(R.id.splEventTime);
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

    public SplEventRecyclerViewAdapter(ArrayList<SplEventDataObject> myDataset) {
        eventDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.splevent_list_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        //holder.nameLabel.setText(eventDataset.get(position).getNameText());
        holder.eventDetails.setText(eventDataset.get(position).getDetailsText());
        //holder.eventPlace.setText("Location: " + eventDataset.get(position).getLocationText());
        //holder.eventDate.setText("Date: " + eventDataset.get(position).getDateText());
        //holder.eventTime.setText("Time: " + eventDataset.get(position).getTimeText());
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
