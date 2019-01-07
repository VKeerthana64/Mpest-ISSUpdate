package com.amana.mpest.myschedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amana.mpest.R;
import com.amana.mpest.model.schedule.Datum;
import com.amana.mpest.utils.AppPreferences;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<Datum> arrayDatum;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;

    ScheduleAdapter(Context context, ArrayList<Datum> arrayDatum) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayDatum = arrayDatum;
        this.mContext = context;
        _appPrefs = new AppPreferences(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_schedule, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Datum datum = arrayDatum.get(position);

        holder.tvServiceId.setText(datum.getServiceID());
        try {
            if(datum.getAdhocType().length() > 0){
                holder.tvCustomer.setText(datum.getAdhocdata().get(0).getCompanyName());
            }else{
                holder.tvCustomer.setText(datum.getCustomerdetails().get(0).getCompanyName());
            }

        } catch (Exception e) {
            holder.tvCustomer.setText(datum.getCustomerdetails().get(0).getCompanyName());
            e.printStackTrace();
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayDatum.size();
    }

    public Datum getItem(int pos) {
        return arrayDatum.get(pos);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvServiceId, tvCustomer;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServiceId = (TextView) itemView.findViewById(R.id.service_txt);
            tvCustomer = (TextView) itemView.findViewById(R.id.customer_txt);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
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


