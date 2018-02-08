package com.hpm.sp.streaminfoportal.Articles;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Models.Article;
import com.hpm.sp.streaminfoportal.Models.Branch;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

/**
 * Created by mahesh on 22/04/17.
 */

public class TatvaRecyclerViewAdapter extends RecyclerView.Adapter<TatvaRecyclerViewAdapter
        .DataObjectHolder> {
    private static final long FADE_DURATION = 500;
    private static final String TAG = TatvaRecyclerViewAdapter.class.getSimpleName();
    private List<Article> articles;
    private RecyclerViewClickListener myClickListener;
    private Context context;
    private Utils utils = new Utils();

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView nameLabel;
        TextView dateTime;
        TextView artLoc;
        TextView desc;
        TextView tatvaPDFLabel;
        CardView mParent;
        ImageView mImage;

        public DataObjectHolder(View itemView) {
            super(itemView);
            nameLabel = (TextView) itemView.findViewById(R.id.tatvaName);
            dateTime = (TextView) itemView.findViewById(R.id.tatvaAuthor);
            artLoc = (TextView) itemView.findViewById(R.id.artLoc);
            mParent = (CardView) itemView.findViewById(R.id.card_view);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            desc = (TextView) itemView.findViewById(R.id.tatvaDesc);
            tatvaPDFLabel = (TextView) itemView.findViewById(R.id.tatvaPDFLabel);
        }

    }

    public void setOnItemClickListener(RecyclerViewClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public TatvaRecyclerViewAdapter(Context context, List<Article> myDataset) {
        articles = myDataset;
        this.context = context;
    }

    public void swap(List<Article> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (articles != null && articles.size() > 0)
            articles.clear();
        articles.addAll(datas);
        notifyDataSetChanged();

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tatva_card_list_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.nameLabel.setText(articles.get(position).getTitle());
        CharSequence ago = utils.getRelativeDateSeq(articles.get(position).getPublishedDate());
        holder.dateTime.setText(ago);
        holder.artLoc.setText(articles.get(position).getPdfLink());
        if(articles.get(position).getShowOnlyPdf() && articles.get(position).getPdfLink() != null) {
            holder.desc.setVisibility(View.GONE);
            holder.tatvaPDFLabel.setVisibility(View.VISIBLE);
            holder.tatvaPDFLabel.setText("PDF");
        } else {
            holder.desc.setVisibility(View.VISIBLE);
            holder.tatvaPDFLabel.setVisibility(View.GONE);
            holder.desc.setText(articles.get(position).getDetails());
        }
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                TatvaRecyclerViewAdapter.this.myClickListener.onItemClick(position, articles.get(position));
            }
        });


        Picasso.with(this.context)
                .load(articles.get(position).getImageUrl())
                .transform(new CropSquareTransformation())
                .into(holder.mImage);

        setFadeAnimation(holder.itemView);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.4f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


    @Override
    public int getItemCount() {
        int i = 0;
        try {
            i = articles.size();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return i;
    }
}
