package com.amana.MpestISS.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amana.MpestISS.R;
import com.amana.MpestISS.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.MpestISS.model.realm.jobdetails.ServiceMaterialRMModel;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class PreviewServicesAdapter extends RecyclerView.Adapter<PreviewServicesAdapter.ViewHolder> {

    ArrayList<ServiceMaterialRMModel> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    public PreviewServicesAdapter(Context context, ArrayList<ServiceMaterialRMModel> mList2) {
        this.mContext = context;
        this.mList= mList2;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_service_preview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ServiceMaterialRMModel serviceMaterialRMModel = getItem(position);

        holder.txt_sno.setText(""+(position+1));
        holder.txt_pestType.setText(serviceMaterialRMModel.getPestType());

        Log.d("xiuchvjkxcvjkxcjkvbjk","cvbncv" + serviceMaterialRMModel.getPestType());
        Log.d("xiuchvjkxcvjkxcjkvbjk","cvbncv" + serviceMaterialRMModel.getSS_remarks());
        Log.d("xiuchvjkxcvjkxcjkvbjk","cvbncv" + serviceMaterialRMModel.getRM_Type());
        if(serviceMaterialRMModel.getOthers().length() >0 && serviceMaterialRMModel.getSS_remarks().length() > 0){
            holder.txt_service.setText(serviceMaterialRMModel.getSS_remarks()+","+serviceMaterialRMModel.getOthers());
        }else if(serviceMaterialRMModel.getOthers().length() ==0 && serviceMaterialRMModel.getSS_remarks().length() > 0) {
            holder.txt_service.setText(serviceMaterialRMModel.getSS_remarks());
        }else if(serviceMaterialRMModel.getOthers().length() >0 && serviceMaterialRMModel.getSS_remarks().length() == 0) {
            holder.txt_service.setText(serviceMaterialRMModel.getOthers());
        }else
        {
            holder.txt_service.setText("");
        }


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_sno,txt_pestType, txt_service;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_sno = (TextView) itemView.findViewById(R.id.txt_sno);
            txt_pestType = (TextView) itemView.findViewById(R.id.txt_pesttype);
            txt_service = (TextView) itemView.findViewById(R.id.txt_servcie);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onServiceMaterialRMModelItemClick(view, getAdapterPosition());
        }
    }

    public ServiceMaterialRMModel getItem(int pos) {
        return mList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onServiceMaterialRMModelItemClick(View view, int position);
    }

}