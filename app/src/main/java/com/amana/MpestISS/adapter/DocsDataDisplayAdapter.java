package com.amana.MpestISS.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.amana.MpestISS.R;
import com.amana.MpestISS.model.RemarskModel;

import java.util.ArrayList;


public class DocsDataDisplayAdapter extends RecyclerView.Adapter<DocsDataDisplayAdapter.ViewHolder> {

    ArrayList<RemarskModel> arrList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public DocsDataDisplayAdapter(Context context, ArrayList<RemarskModel> arrList) {
        this.mInflater = LayoutInflater.from(context);
        this.arrList = arrList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_premarks, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RemarskModel model = arrList.get(position);

        holder.title.setText(model.getName());

        if (model.getSelected()) {
            holder.title.setChecked(true);
        } else {
            holder.title.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chk_doc_name);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.OnItemClick(view, getAdapterPosition());
        }
    }

    public RemarskModel getItem(int id) {
        return arrList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void OnItemClick(View view, int position);
    }
}