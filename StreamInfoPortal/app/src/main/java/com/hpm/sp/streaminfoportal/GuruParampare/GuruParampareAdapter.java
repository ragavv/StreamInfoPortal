package com.hpm.sp.streaminfoportal.GuruParampare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.Models.Guru;
import com.hpm.sp.streaminfoportal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by kumardivyarajat on 04/02/18.
 */

public class GuruParampareAdapter extends RecyclerView.Adapter<GuruParampareAdapter.ViewHolder> {
    private static final String TAG = GuruParampareAdapter.class.getSimpleName();
    private List<Guru> guruList;
    private Context context;

    public GuruParampareAdapter(Context context, List<Guru> gurus) {
        this.context = context;
        this.guruList = gurus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guru_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setFilter(List<Guru> gurus) {
        this.guruList = new ArrayList<>();
        this.guruList.addAll(gurus);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Guru guru = guruList.get(position);
        // holder.mText.setText();

        holder.mName.setText(guru.getName());
        holder.mAradhna.setText("Aradhana : " + guru.getAradhana());
        holder.mBrindavan.setText("Brindavan : " + guru.getBrindavan());
        holder.mPeriod.setText("Period : " + guru.getPeriod());

        Picasso.with(context)
                .load(guru.getProfileImage())
                .transform(new CropCircleTransformation())
                .resize(192, 192)
                .centerCrop()
                .into(holder.mProfileImage);

    }

    @Override
    public int getItemCount() {
        return guruList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView mName;

        @BindView(R.id.aradhana)
        TextView mAradhna;

        @BindView(R.id.brindavan)
        TextView mBrindavan;

        @BindView(R.id.period)
        TextView mPeriod;

        @BindView(R.id.profileImage)
        ImageView mProfileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

