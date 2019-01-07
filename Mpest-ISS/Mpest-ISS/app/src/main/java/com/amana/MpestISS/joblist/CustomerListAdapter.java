package com.amana.MpestISS.joblist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amana.MpestISS.R;
import com.amana.MpestISS.model.customerlist.Datum;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<Datum> arrayDatum;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;
    CustomerListAdapter(Context context, ArrayList<Datum> arrayDatum) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayDatum= arrayDatum;
        this.mContext = context;
        _appPrefs = new AppPreferences(mContext);

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_customer_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Datum datum = arrayDatum.get(position);

        holder.tvServiceId.setText(datum.getServiceID());
        holder.tvDateTime.setText(Utils.getRequiredDateTime(datum.getUpdatedDate()));
        holder.tvStartTime.setText(Utils.getRequiredTime(datum.getStartsat()));
        holder.tvEndTime.setText(Utils.getRequiredTime(datum.getEndsat()));
        holder.tvActualStartTime.setText(Utils.getRequiredTime(datum.getTabletSchedulerrecordDetails().get(0).getStartsat()));
        holder.tvActualEndTiem.setText(Utils.getRequiredTime(datum.getTabletSchedulerrecordDetails().get(0).getEndsat()));

        holder.tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String url = "http://www.amanaasia.net:8080/CLEANSolutions.Reports/Reports/Service.aspx?ID=" + datum.getId();

                String url = ApiInterface.REPORT_URL+ datum.getId()+"&Type=Download";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                (v.getContext()).startActivity(i);
            }
        });

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayDatum.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvServiceId, tvDateTime, tvStartTime, tvEndTime, tvActualStartTime, tvActualEndTiem, tvPreview;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServiceId = (TextView) itemView.findViewById(R.id.service_txt);
            tvDateTime = (TextView) itemView.findViewById(R.id.datetime_txt);
            tvStartTime = (TextView) itemView.findViewById(R.id.startTime_txt);
            tvEndTime = (TextView) itemView.findViewById(R.id.endTime_txt);
            tvActualStartTime = (TextView) itemView.findViewById(R.id.ActualStarttime_txt);
            tvActualEndTiem = (TextView) itemView.findViewById(R.id.ActualendTime_txt);
            tvPreview = (TextView) itemView.findViewById(R.id.preview_txt);

            tvPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
                }
            });
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


