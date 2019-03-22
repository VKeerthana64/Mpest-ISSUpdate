package com.amana.MpestISS.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amana.MpestISS.R;

/**
 * Created by Naga babu on 23/05/18.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private String[] mData = new String[0];
    private int[] mData_icons = new int[0];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int METTINGS_COUNT = 0;
    private boolean is_unread_count_have = false;

    public DashboardAdapter(Context context, String[] data, int[] mData_icons, int METTINGS_COUNT, boolean is_unread_count_have) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mData_icons = mData_icons;
        this.METTINGS_COUNT = METTINGS_COUNT;
        this.is_unread_count_have = is_unread_count_have;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_dashboard, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String label = mData[position];
        holder.myTextView.setText(label);
        holder.imgIcon.setImageResource(mData_icons[position]);

        if(label.equalsIgnoreCase("ANNOUNCEMENT")){
            holder.count_lnr.setVisibility(View.VISIBLE);
            holder.tvCount.setText(""+METTINGS_COUNT);
        }else {
            holder.count_lnr.setVisibility(View.GONE);
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView, tvCount;
        public ImageView imgIcon, imgMsgUnRead;
        public LinearLayout count_lnr;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCount = itemView.findViewById(R.id.count_txt);
            count_lnr = itemView.findViewById(R.id.count_lnr);
            myTextView = itemView.findViewById(R.id.info_text);
            imgIcon = itemView.findViewById(R.id.imgIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}