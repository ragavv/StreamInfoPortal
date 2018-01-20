package com.hpm.sp.streaminfoportal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.*;

/**
 * Created by mahesh on 22/04/17.
 */

public class PravachanaRecyclerViewAdapter extends RecyclerView.Adapter<PravachanaRecyclerViewAdapter
        .DataObjectHolder>  {

    private ArrayList<PravachanaDataObject> eventDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView videoLabel;
        TextView videoDetails;
        TextView videoLink;

        public DataObjectHolder(View itemView) {
            super(itemView);
            videoLabel = (TextView) itemView.findViewById(R.id.videoName);
            videoDetails = (TextView) itemView.findViewById(R.id.videoDetails);
            videoLink = (TextView) itemView.findViewById(R.id.videoLink);
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

    public PravachanaRecyclerViewAdapter(ArrayList<PravachanaDataObject> myDataset) {
        eventDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pravachana_card_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.videoLabel.setText(eventDataset.get(position).getTitle());
        holder.videoDetails.setText(eventDataset.get(position).getDescription());
        holder.videoLink.setText(eventDataset.get(position).getLink());
    }


    @Override
    public int getItemCount() {
        int i=0;
        try{
            i = eventDataset.size();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        return i;
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
