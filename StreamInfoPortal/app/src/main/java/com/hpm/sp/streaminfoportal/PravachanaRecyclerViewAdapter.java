package com.hpm.sp.streaminfoportal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.Models.Video;
import com.squareup.picasso.Picasso;

import java.util.*;

/**
 * Created by mahesh on 22/04/17.
 */

public class PravachanaRecyclerViewAdapter extends RecyclerView.Adapter<PravachanaRecyclerViewAdapter
        .DataObjectHolder> {

    private static final long FADE_DURATION = 500;
    private ArrayList<Video> eventDataset;
    private static MyClickListener myClickListener;
    private Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView videoLabel;
        TextView videoDetails;
        TextView videoLink;
        ImageView mThumbnailView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            videoLabel = (TextView) itemView.findViewById(R.id.videoName);
            videoDetails = (TextView) itemView.findViewById(R.id.videoDetails);
            videoLink = (TextView) itemView.findViewById(R.id.videoLink);
            mThumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail_image);
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

    public PravachanaRecyclerViewAdapter(Context context, ArrayList<Video> myDataset) {
        eventDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pravachana_card_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.videoLabel.setText(eventDataset.get(position).getSnippet().getTitle());
        holder.videoDetails.setText(eventDataset.get(position).getSnippet().getDescription());
        holder.videoLink.setText(eventDataset.get(position).getVideoLink());

        Picasso.with(context)
                .load(eventDataset.get(position).getSnippet().getThumbnails().getHigh().getUrl())
                .resize(eventDataset.get(position).getSnippet().getThumbnails().getHigh().getWidth(), eventDataset.get(position).getSnippet().getThumbnails().getHigh().getHeight())
                .centerCrop()
                .into(holder.mThumbnailView);

        setFadeAnimation(holder.itemView);
//        setScaleAnimation(holder.itemView);

    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.4f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
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
        void onItemClick(int position, View v);
    }
}
