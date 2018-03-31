package com.hpm.sp.streaminfoportal.Announcements;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.R;

import java.util.ArrayList;

/**
 * Created by mahesh on 22/04/17.
 */

public class AnnouncementRecyclerViewAdapter extends RecyclerView.Adapter<AnnouncementRecyclerViewAdapter
        .DataObjectHolder>  {

    private ArrayList<AnnouncementDataObject> eventDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView eventDetails;

        public DataObjectHolder(View itemView) {
            super(itemView);
            eventDetails = (TextView) itemView.findViewById(R.id.announcementEventDetails);
            eventDetails.setMovementMethod(new ScrollingMovementMethod());
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

    public AnnouncementRecyclerViewAdapter(ArrayList<AnnouncementDataObject> myDataset) {
        eventDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_list_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.eventDetails.setText(eventDataset.get(position).getDetailsText());
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
