package com.hpm.sp.streaminfoportal.AradhaneActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.Constants;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Models.AradhaneDataObject;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.R;
import com.hpm.sp.streaminfoportal.Utils;

import java.util.ArrayList;

/**
 *
 */

public class AradhaneRecyclerViewAdapter extends RecyclerView.Adapter<AradhaneRecyclerViewAdapter
        .DataObjectHolder> {

    private ArrayList<AradhaneDataObject> aradhaneDataObjects = new ArrayList<>();
    private RecyclerViewClickListener myClickListener;
    private int mode = Constants.HORIZONTAL;

    public AradhaneRecyclerViewAdapter() {
    }

    public AradhaneRecyclerViewAdapter(int mode) {
        this.mode = mode;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView nameLabel;
        TextView maasaLabel;
        TextView pakshaLabel;
        TextView thithiLabel;
        TextView brindavanaLabel;
        TextView dateLabel;
        CardView mAradhaneView;        



        public DataObjectHolder(View itemView) {
            super(itemView);
            nameLabel = (TextView) itemView.findViewById(R.id.name);
            maasaLabel = (TextView) itemView.findViewById(R.id.maasa);
            pakshaLabel = (TextView) itemView.findViewById(R.id.paksha);
            thithiLabel = (TextView) itemView.findViewById(R.id.thithi);
            brindavanaLabel = (TextView) itemView.findViewById(R.id.brindavana);
            dateLabel = (TextView) itemView.findViewById(R.id.aradhaneDate);
            mAradhaneView = (CardView) itemView.findViewById(R.id.aradhane_view);
        }
    }

    public void swap(ArrayList<AradhaneDataObject> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (aradhaneDataObjects != null && aradhaneDataObjects.size() > 0)
            aradhaneDataObjects.clear();
        aradhaneDataObjects.addAll(datas);
        notifyDataSetChanged();

    }

    public void setOnItemClickListener(RecyclerViewClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public AradhaneRecyclerViewAdapter(ArrayList<AradhaneDataObject> myDataset, int mode) {
        aradhaneDataObjects = myDataset;
        this.mode = mode;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = mode == Constants.HORIZONTAL ? R.layout.aradhane_card_list : R.layout.aradhane_card_list;
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.nameLabel.setText(aradhaneDataObjects.get(position).getNameText());
        holder.maasaLabel.setText(aradhaneDataObjects.get(position).getMaasaText());
        holder.pakshaLabel.setText(aradhaneDataObjects.get(position).getPakshaText());
        holder.thithiLabel.setText(aradhaneDataObjects.get(position).getThithiText());
        holder.brindavanaLabel.setText(aradhaneDataObjects.get(position).getBrindavanaText());
        holder.dateLabel.setText(aradhaneDataObjects.get(position).getDateText());

        holder.mAradhaneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.onItemClick(position, aradhaneDataObjects.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        int i = 0;
        try {
            i = aradhaneDataObjects.size();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return i;
    }

    public interface MyClickListener {
        void onItemClick(AradhaneDataObject dataObject, TextView v);
    }


}
