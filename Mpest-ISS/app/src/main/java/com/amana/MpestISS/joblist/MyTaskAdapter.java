package com.amana.MpestISS.joblist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amana.MpestISS.R;
import com.amana.MpestISS.MapsActivity;
import com.amana.MpestISS.comments.CommentsActivity;
import com.amana.MpestISS.myjob.MyJobActivity;
import com.amana.MpestISS.myjob.PreviewActivity;
import com.amana.MpestISS.myjob.ServiceDetailsActivity;
import com.amana.MpestISS.model.realm.taskdetail.ListData;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Naga babu on 28/05/18.
 */

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<ListData> arrayDatum;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;

    MyTaskAdapter(Context context, ArrayList<ListData> arrayDatum) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayDatum = arrayDatum;
        this.mContext = context;
        _appPrefs = new AppPreferences(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_my_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ListData datum = arrayDatum.get(position);

        holder.tvServiceId.setText(datum.getServiceID());

        String mAdhocid="";
        try{
            if(datum.getServiceID().contains("ADH")) {
                mAdhocid=  MasterDbLists.getAdhocBasedonServiceID(datum.getServiceID());
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        try {
            if (datum.getAdhocType().length() > 0) {
                holder.tvCustomer.setText(datum.getAdhocdata().get(0).getCompanyName());
            } else {
                holder.tvCustomer.setText(datum.getCustomerdetails().get(0).getCompanyName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //holder.tvCustomer.setText(datum.getContactPerson());

        try {
            if (datum.getAdhocType().length() > 0) {
                holder.tvLocation.setText(datum.getAdhocdata().get(0).getLocation());
            } else {
                holder.tvLocation.setText(datum.getLocation());
            }
            holder.tvTeam.setText(datum.getPestType());
            holder.tvDateTime.setText(Utils.getRequiredDateTime(datum.getStartsat()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (datum.getStatus().toString().trim().equalsIgnoreCase("In-Progress")) {
            holder.tvPreview.setText("CONTINUE");
        } else if (datum.getStatus().toString().trim().equalsIgnoreCase("Completed")) {

            holder.tvPreview.setText("VIEW");

            try{

                if (!TextUtils.isEmpty(datum.getUploadStatus().toString()) && datum.getUploadStatus().toString().contains("Upload")) {

                    holder.tv_Resubmit.setVisibility(View.VISIBLE);

                    if(datum.getUploadStatus().toString().equalsIgnoreCase("Upload InProgress") || datum.getUploadStatus().toString().equalsIgnoreCase("Upload Failed") ){
                        holder.tvComments.setVisibility(View.GONE);
                        holder.tvPreview.setText("Uploading..");
                        holder.tvPreview.setTextColor(Color.parseColor("#ff0000"));
                        holder.tvUploadStatus.setVisibility(View.GONE);

                        if(datum.getUploadStatus().toString().equalsIgnoreCase("Upload Failed")){
                            holder.tvPreview.setText("UPLOAD FAILED");
                        }
                    }else{
                        holder.tvUploadStatus.setVisibility(View.VISIBLE);
                    }

                }

                if(mAdhocid.length()>0){

                    holder.tv_Resubmit.setVisibility(View.VISIBLE);
                    holder.tvUploadStatus.setVisibility(View.VISIBLE);

                }
            }catch (Exception e){
                e.printStackTrace();
            }


        } else {
            holder.tvPreview.setText("PREVIEW");
        }

        try {
            holder.tvComments.setText("Comments From Tech/Office (" + datum.getCommentsdetails().size() + ")");
        } catch (Exception e) {
            holder.tvComments.setText("Comments From Tech/Office (" + datum.getCommentCount() + ")");
        }
        holder.tvComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_location = new Intent(v.getContext(), CommentsActivity.class);
                intent_location.putExtra("RequestID", datum.get_id());
                intent_location.putExtra("ServiceID", datum.getServiceID());
                (v.getContext()).startActivity(intent_location);
                //((Activity) mContext).finish();
            }
        });

        final String finalMAdhocid = mAdhocid;
        holder.tv_Resubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalMAdhocid.length()>0){
                    _appPrefs.saveSERVICEID(finalMAdhocid);
                }else{
                    _appPrefs.saveSERVICEID(datum.getServiceID().toString());
                }

                Intent previewIntent = new Intent(mContext, PreviewActivity.class);
                //previewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                (v.getContext()).startActivity(previewIntent);
                ((Activity) mContext).finish();
                //((Activity) mContext).finish();
            }
        });

        holder.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_location = new Intent(v.getContext(), MapsActivity.class);

                if (datum.getAdhocType().length() > 0) {
                    intent_location.putExtra("LatLong", datum.getAdhocdata().get(0).getLatLong());
                    intent_location.putExtra("Location", datum.getAdhocdata().get(0).getLocation());
                } else {
                    intent_location.putExtra("LatLong", datum.getLatLong());
                    intent_location.putExtra("Location", datum.getLocation());
                }

                (v.getContext()).startActivity(intent_location);

            }
        });


        holder.tvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), CustomerListActivity.class);
                try {
                    i.putExtra("CustomerId", datum.getCustomerdetails().get(0).get_id());
                } catch (Exception e) {
                    e.printStackTrace();
                    i.putExtra("CustomerId", "");
                }

                (v.getContext()).startActivity(i);
            }
        });

        holder.tvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (datum.getStatus().toString().trim().equalsIgnoreCase("In-Progress")) {
                    Intent i = new Intent(v.getContext(), MyJobActivity.class);
                    i.putExtra("ServiceId", datum.getServiceID());
                    i.putExtra("PestType", datum.getPestType());
                    i.putExtra("TeamName", datum.getTeamdetails().get(0).getTeamName());
                    i.putExtra("TeamLead", datum.getTeamdetails().get(0).getTeamLead());
                    i.putExtra("Customer", datum.getContactPerson());
                    if (datum.getCustomerdetails().size() > 0) {
                        i.putExtra("CustomerEmail", datum.getCustomerServicedetails().get(0).getsEmail());
                    } else {
                        i.putExtra("CustomerEmail", "");
                    }

                    (v.getContext()).startActivity(i);
                    //((Activity) mContext).finish();

                } else if (datum.getStatus().toString().trim().equalsIgnoreCase("Completed")) {
                    //String url = "http://www.amanaasia.net:8080/CLEANSolutions.Reports/Reports/Service.aspx?ID=" + datum.get_id();
                    String url = ApiInterface.REPORT_URL + datum.get_id() + "&Type=Download";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    (v.getContext()).startActivity(i);
                    // ((Activity) mContext).finish();

                } else if (holder.tvPreview.getText().toString().trim().equalsIgnoreCase("Uploading..")) {

                  /*  _appPrefs.saveSERVICEID(datum.getServiceID().toString());
                    Intent previewIntent = new Intent(mContext, PreviewActivity.class);
                    //previewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    (v.getContext()).startActivity(previewIntent);
                    ((Activity) mContext).finish();
                    */

                } else {

                    Intent i = new Intent(v.getContext(), ServiceDetailsActivity.class);
                    i.putExtra("ServiceId", datum.getServiceID());
                    i.putExtra("Status", datum.getStatus());
                    i.putExtra("TeamName", datum.getTeamdetails().get(0).getTeamName());
                    (v.getContext()).startActivity(i);

                   /* if(MasterDbLists.getMasterCount() >0) {

                        // ((Activity) mContext).finish();
                    }else{
                        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(mContext)
                                .setAlertFont("fonts/Avenir-Medium.otf")
                                .setSubTitleFont("fonts/Avenir-Heavy.otf")
                                .setTextSubTitle("Please clickon master sync from dashboard..")
                                .setPositiveButtonText("OK")
                                .setPositiveColor(R.color.colorPositive)
                                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                                    @Override
                                    public void OnClick(View view, Dialog dialog) {
                                        dialog.dismiss();
                                        Intent intDash = new Intent(v.getContext(), DashboardActivity.class);
                                        (v.getContext()).startActivity(intDash);
                                        ((Activity) mContext).finish();

                                    }
                                })
                                *//* .setAutoHide(true)*//*
                                .build();
                        alert.show();
                    }*/

                }

            }
        });

        holder.tvServiceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ServiceDetailsActivity.class);
                i.putExtra("ServiceId", datum.getServiceID());
                i.putExtra("Status", datum.getStatus());
                (v.getContext()).startActivity(i);
                // ((Activity) mContext).finish();
            }
        });

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayDatum.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvServiceId, tvDateTime, tvCustomer, tvTeam, tvComments, tvPreview, tvLocation, tvUploadStatus,tvUnderLine, tv_Resubmit;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServiceId = (TextView) itemView.findViewById(R.id.service_txt);
            tvDateTime = (TextView) itemView.findViewById(R.id.datetime_txt);
            tvCustomer = (TextView) itemView.findViewById(R.id.customer_txt);
            tvTeam = (TextView) itemView.findViewById(R.id.team_txt);
            tvComments = (TextView) itemView.findViewById(R.id.comments_txt);
            tvPreview = (TextView) itemView.findViewById(R.id.preview_txt);
            tvLocation = (TextView) itemView.findViewById(R.id.location_txt);
            tvUnderLine = (TextView) itemView.findViewById(R.id.Line_txt);
            tv_Resubmit = (TextView) itemView.findViewById(R.id.ReSubmit_txt);
            tvUploadStatus = (TextView) itemView.findViewById(R.id.UploadStatus_txt);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return arrayDatum.get(id).get_id();
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


