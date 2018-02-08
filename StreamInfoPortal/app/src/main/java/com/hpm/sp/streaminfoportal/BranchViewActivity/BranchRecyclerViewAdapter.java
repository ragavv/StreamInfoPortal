package com.hpm.sp.streaminfoportal.BranchViewActivity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hpm.sp.streaminfoportal.BranchDataObject;
import com.hpm.sp.streaminfoportal.Interfaces.RecyclerViewClickListener;
import com.hpm.sp.streaminfoportal.Models.Branch;
import com.hpm.sp.streaminfoportal.Models.EventDataObject;
import com.hpm.sp.streaminfoportal.R;

import java.util.*;

/**
 * Created by mahesh on 22/04/17.
 */

public class BranchRecyclerViewAdapter extends RecyclerView.Adapter<BranchRecyclerViewAdapter.DataObjectHolder> implements HeaderItemDecoration.StickyHeaderInterface {

    private List<Branch> mDataset;
    private RecyclerViewClickListener myClickListener;
    public static final String TAG = BranchRecyclerViewAdapter.class.getSimpleName();

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_recyclerview_sticky_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView mHeader = (TextView) header.findViewById(R.id.header);
        mHeader.setText(mDataset.get(headerPosition).getCity());
    }

    @Override
    public boolean isHeader(int itemPosition) {
        Log.d(TAG, "isHeader: " + itemPosition);
        return mDataset.get(itemPosition).getHeader();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView contact;
        TextView branchLoc;
        FloatingActionButton floatingActionButton;

        public DataObjectHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.branchName);
            contact = (TextView) itemView.findViewById(R.id.branchContact);
            branchLoc = (TextView) itemView.findViewById(R.id.branchLoc);
            floatingActionButton = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }
    }

    public void swap(List<Branch> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (mDataset != null && mDataset.size() > 0)
            mDataset.clear();
        mDataset.addAll(datas);
        notifyDataSetChanged();

    }

    public void setOnItemClickListener(RecyclerViewClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public BranchRecyclerViewAdapter(List<Branch> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.branch_list_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.contact.setText(mDataset.get(position).getContact());
        if (mDataset.get(position).getLoc() != null)
            holder.branchLoc.setText(mDataset.get(position).getLoc());
        else
            holder.branchLoc.setText(mDataset.get(position).getLocation());

        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.onItemClick(position, mDataset.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
