package com.amana.MPESTPestoff.logs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.model.realm.LogsTable;
import com.amana.MPESTPestoff.restApi.ApiInterface;
import com.amana.MPESTPestoff.utils.AppPreferences;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<LogsTable> arrayLogsTable;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;
    LogsAdapter(Context context, ArrayList<LogsTable> arrayLogsTable) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayLogsTable= arrayLogsTable;
        this.mContext = context;
        _appPrefs = new AppPreferences(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_logs, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LogsTable LogsTable = arrayLogsTable.get(position);

        String[] arrServiceID =(LogsTable.getServiceId().toString()).split("@@");

        holder.tvServiceId.setText(arrServiceID[0]);
        holder.tvStatus.setText(LogsTable.getStatus());
        holder.tvLogDate.setText(LogsTable.getLogDate());
        holder.tvLogTime.setText(LogsTable.getLogTime());

        holder.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ApiInterface.REPORT_URL+ LogsTable.getId()+"&Type=Download";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                (v.getContext()).startActivity(i);
            }
        });

        holder.tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), LogPreviewActivity.class);
                mIntent.putExtra("ServiceId", LogsTable.getServiceId());
                (v.getContext()).startActivity(mIntent);
                // ((Activity) mContext).finish();
            }
        });

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayLogsTable.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvServiceId, tvStatus, tvLogDate, tvLogTime, tvView, tvPreview;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServiceId = (TextView) itemView.findViewById(R.id.service_txt);
            tvStatus = (TextView) itemView.findViewById(R.id.status_txt);
            tvLogDate = (TextView) itemView.findViewById(R.id.logdate_txt);
            tvLogTime = (TextView) itemView.findViewById(R.id.logtime_txt);
            tvView = (TextView) itemView.findViewById(R.id.preview_txt);
            tvPreview = (TextView) itemView.findViewById(R.id.view_txt);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return arrayLogsTable.get(id).getServiceId();
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


