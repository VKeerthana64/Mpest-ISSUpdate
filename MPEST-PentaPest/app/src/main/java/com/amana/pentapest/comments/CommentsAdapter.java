package com.amana.pentapest.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amana.pentapest.R;
import com.amana.pentapest.model.comments.Datum;
import com.amana.pentapest.utils.AppPreferences;
import com.amana.pentapest.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<Datum> arrayLogsTable;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;
    CommentsAdapter(Context context, ArrayList<Datum> arrayLogsTable) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayLogsTable= arrayLogsTable;
        this.mContext = context;
        _appPrefs = new AppPreferences(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_comments, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Datum LogsTable = arrayLogsTable.get(position);

      /*  holder.tvServiceId.setText(LogsTable.getServiceId());
        holder.tvStatus.setText(LogsTable.getStatus());
        holder.tvLogDate.setText(LogsTable.getLogDate());
        holder.tvLogTime.setText(LogsTable.getLogTime());*/

        holder.tvCommentedBy.setText(LogsTable.getCommentedBy());
        holder.tvComment.setText(LogsTable.getCmt());
        holder.tvDate.setText(Utils.getRequiredDateTime(LogsTable.getCreatedDate()));

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayLogsTable.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCommentedBy, tvDate, tvComment;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCommentedBy = (TextView) itemView.findViewById(R.id.commmentedBy_txt);
            tvDate = (TextView) itemView.findViewById(R.id.Date_txt);
            tvComment = (TextView) itemView.findViewById(R.id.comment_txt);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return arrayLogsTable.get(id).getId();
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


