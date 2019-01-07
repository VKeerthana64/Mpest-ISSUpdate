package com.amana.MPESTPestoff.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.model.realm.Materials;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class MaterialsListAdapter extends RecyclerView.Adapter<MaterialsListAdapter.ViewHolder> {

    ArrayList<Materials> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    public MaterialsListAdapter(Context context, ArrayList<Materials> mList2) {
        this.mContext = context;
        this.mList= mList2;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_checkbox, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Materials materials = getItem(position);

        if(materials.isSelected()){
            holder.cb_item.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
        }else{
            holder.cb_item.setTextColor(ContextCompat.getColor(mContext,R.color.jumbo));
        }
        holder.cb_item.setText(materials.getMaterialsName());

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView cb_item;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_item = (TextView) itemView.findViewById(R.id.cb_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onMaterialsItemClick(view, getAdapterPosition());
        }
    }

    public Materials getItem(int pos) {
        return mList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onMaterialsItemClick(View view, int position);
    }

}