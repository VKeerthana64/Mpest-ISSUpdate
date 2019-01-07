package com.amana.mpest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amana.mpest.R;
import com.amana.mpest.model.MaterialPreviewModel;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class PreviewMaterialsAdapter extends RecyclerView.Adapter<PreviewMaterialsAdapter.ViewHolder> {

    ArrayList<MaterialPreviewModel> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    public PreviewMaterialsAdapter(Context context, ArrayList<MaterialPreviewModel> mList2) {
        this.mContext = context;
        this.mList= mList2;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_materials_preview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MaterialPreviewModel materialPreviewModel = getItem(position);


            holder.txt_pestType.setText(materialPreviewModel.getPestType());
            holder.txt_material.setText(materialPreviewModel.getMaterials());
            holder.txt_quantity.setText(materialPreviewModel.getQuantity());
            holder.txt_unit.setText(materialPreviewModel.getUnit());

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_pestType, txt_material,txt_quantity, txt_unit;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_pestType = (TextView) itemView.findViewById(R.id.txt_pesttype);
            txt_material = (TextView) itemView.findViewById(R.id.txt_materials);
            txt_quantity = (TextView) itemView.findViewById(R.id.txt_quantity);
            txt_unit = (TextView) itemView.findViewById(R.id.txt_units);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onMaterialPreviewModelItemClick(view, getAdapterPosition());
        }
    }

    public MaterialPreviewModel getItem(int pos) {
        return mList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onMaterialPreviewModelItemClick(View view, int position);
    }

}