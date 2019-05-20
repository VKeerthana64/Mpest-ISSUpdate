package com.amana.MpestISS.logs;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amana.MpestISS.R;
import com.amana.MpestISS.model.realm.logdetails.LogPhotoRemarkRMModel;
import com.amana.MpestISS.utils.AppConstants;
import com.amana.MpestISS.utils.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Naga babu on 06/06/18.
 */

public class LogPhotoAdapter extends RecyclerView.Adapter<LogPhotoAdapter.ViewHolder> {

    ArrayList<LogPhotoRemarkRMModel> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private File compressedImage;
    private File compressedImage2;
    public LogPhotoAdapter(Context context, ArrayList<LogPhotoRemarkRMModel> mList2) {
        this.mContext = context;
        this.mList= mList2;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_log_photoremarks, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LogPhotoRemarkRMModel photoWithRemarks = getItem(position);


        if(photoWithRemarks.getOthers().length() >0 && photoWithRemarks.getRemarks().length() >0){
            holder.tv_remarksText.setText(photoWithRemarks.getRemarks().toString()+","+photoWithRemarks.getOthers().toString());


        }else if(photoWithRemarks.getOthers().length() >0 && photoWithRemarks.getRemarks().length() ==0){
            holder.tv_remarksText.setText(photoWithRemarks.getOthers().toString());
        }else if(photoWithRemarks.getOthers().length() ==0 && photoWithRemarks.getRemarks().length() > 0){
            holder.tv_remarksText.setText(photoWithRemarks.getRemarks().toString());
        }else{
            holder.tv_remarksText.setText("");
        }

       // holder.iv_photo.setImageBitmap(Utils.convertBase64toBitmap(photoWithRemarks.getImgBase64().toString()));

        final File imgFile = new File(photoWithRemarks.getImgBase64().toString());


        //new ImageCompression(mContext).execute(photoWithRemarks.getImgBase64().toString());

        if(imgFile.exists()){

            try{
                Bitmap imgBitmap = Utils.compressImage(photoWithRemarks.getImgBase64().toString());
                holder.iv_photo.setImageBitmap(imgBitmap);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{

        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_remarks,tv_remarksText;
        public ImageView iv_photo, iv_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_remarks = (TextView) itemView.findViewById(R.id.remarkclick_tv);
            tv_remarksText = (TextView) itemView.findViewById(R.id.remarksText_tv);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);

            tv_remarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(), AppConstants.TYPE_CLCIK_REMARKS);
                }
            });
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(),AppConstants.TYPE_CLCIK_DELETE);
                }
            });

            iv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(),AppConstants.TYPE_CLCIK_IMAGE);
                }
            });
        }

//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onPhotoWithRemarksItemClick(view, getAdapterPosition());
//        }
    }

    public LogPhotoRemarkRMModel getItem(int pos) {
        return mList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, int ClickType);
    }

}