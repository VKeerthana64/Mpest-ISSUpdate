package com.amana.pentapest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.amana.pentapest.R;
import com.amana.pentapest.model.realm.jobdetails.MaterialsCapturesRmModel;
import com.amana.pentapest.utils.AppConstants;

import java.util.ArrayList;

/**
 * Created by Naga babu on 06/06/18.
 */

public class MaterialQuantityAdapter extends RecyclerView.Adapter<MaterialQuantityAdapter.ViewHolder> {

    ArrayList<MaterialsCapturesRmModel> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    public MaterialQuantityAdapter(Context context, ArrayList<MaterialsCapturesRmModel> mList2) {
        this.mContext = context;
        this.mList= mList2;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_materiallist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MaterialsCapturesRmModel materialsCapturesRmModel = getItem(position);

        holder.tv_text.setText(materialsCapturesRmModel.getMaterialName().toString());


        if(!TextUtils.isEmpty(materialsCapturesRmModel.getQuantity())){
            holder.edt_quantity.setText(materialsCapturesRmModel.getQuantity().toString());
        }

        if(!TextUtils.isEmpty(materialsCapturesRmModel.getUnit())){
            holder.edt_unit.setText(materialsCapturesRmModel.getUnit().toString());
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_text;
        public EditText edt_quantity,edt_unit;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.cb_item);
            edt_quantity = (EditText) itemView.findViewById(R.id.Qty_edt);
            edt_unit = (EditText) itemView.findViewById(R.id.unit_edt);

            edt_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(), AppConstants.TYPE_CLCIK_QUANTITY);
                }
            });
            edt_unit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(), AppConstants.TYPE_CLCIK_UNIT);
                }
            });
        }

       /* @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }*/

    }

    public MaterialsCapturesRmModel getItem(int pos) {
        return mList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, int ClickType);

       // void onItemClick(View view, int adapterPosition);
    }

}