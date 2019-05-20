package com.amana.MpestISS.joblist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amana.MpestISS.R;
import com.amana.MpestISS.MapsActivity;
import com.amana.MpestISS.comments.CommentsActivity;
import com.amana.MpestISS.model.realm.taskdetail.ListData1;
import com.amana.MpestISS.myjob.MyJobActivity;
import com.amana.MpestISS.myjob.PreviewActivity;
import com.amana.MpestISS.myjob.ServiceDetailsActivity;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;

/**
 * Created by Naga babu on 28/05/18.
 */
public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {
    AppPreferences _appPrefs;
    private ArrayList<ListData1> arrayDatum;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    Context mContext;

    MyTaskAdapter(Context context, ArrayList<ListData1> arrayDatum) {
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
        final ListData1 datum = arrayDatum.get(position);

        holder.tvServiceId.setText(datum.getServiceID());

        String mAdhocid="";

        try{
            if(datum.getServiceID().contains("ADH")) {
                mAdhocid=  MasterDbLists.getAdhocBasedonServiceID(datum.getServiceID());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try
        {
            if (datum.getSchedulerTrackingdetails().size() > 0)
            {
                if (datum.getSchedulerTrackingdetails().get(0).getStartTime().equalsIgnoreCase("") &&  datum.getSchedulerTrackingdetails().get(0).getEndTime().equalsIgnoreCase("")) {
                    holder.stop_txt.setVisibility(View.VISIBLE);
                    holder.stop_txt.setEnabled(false);
                    holder.start_txt.setVisibility(View.VISIBLE);
                    holder.start_txtview.setVisibility(View.GONE);
                    holder.stop_txtview.setVisibility(View.GONE);
                }
                else if(datum.getSchedulerTrackingdetails().get(0).getStartTime()== null && datum.getSchedulerTrackingdetails().get(0).getEndTime() != "")
                {
                    holder.stop_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getEndTime()));
                    holder.stop_txtview.setVisibility(View.VISIBLE);
                    holder.start_txtview.setVisibility(View.GONE);
                    holder.stop_txt.setVisibility(View.GONE);
                    holder.start_txt.setVisibility(View.VISIBLE);
                }
                else if(datum.getSchedulerTrackingdetails().get(0).getStartTime() != "" && datum.getSchedulerTrackingdetails().get(0).getEndTime() == null)
                {
                        holder.start_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getStartTime()));
                        holder.start_txtview.setVisibility(View.VISIBLE);
                        holder.stop_txtview.setVisibility(View.GONE);
                        holder.start_txt.setVisibility(View.GONE);
                        holder.stop_txt.setVisibility(View.VISIBLE);
                        holder.stop_txt.setBackgroundResource(R.drawable.button_border);
                        holder.stop_txt.setEnabled(true);
                }
                else
                {
                    if (datum.getStatus().toString().trim().equalsIgnoreCase("In-Progress")) {
                        holder.start_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getStartTime()));
                        if(datum.getSchedulerTrackingdetails().get(0).getEndTime().trim().length() > 8)
                        {
                            holder.stop_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getEndTime()));
                        }
                        else
                        {
                            holder.stop_txtview.setText(datum.getSchedulerTrackingdetails().get(0).getEndTime());
                        }
                    }
                    else if (datum.getStatus().toString().trim().equalsIgnoreCase("Completed")) {
                        holder.start_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getStartTime()));
                        if(datum.getSchedulerTrackingdetails().get(0).getEndTime().trim().length() > 8)
                        {
                            holder.stop_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getEndTime()));
                        }
                        else
                        {
                            holder.stop_txtview.setText(datum.getSchedulerTrackingdetails().get(0).getEndTime());
                        }
                    }
                    else
                    {
                        holder.start_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getStartTime()));
                        if(datum.getSchedulerTrackingdetails().get(0).getEndTime().trim().length() > 8)
                        {
                            holder.stop_txtview.setText(Utils.GetTime(datum.getSchedulerTrackingdetails().get(0).getEndTime()));
                        }
                        else
                        {
                            holder.stop_txtview.setText(datum.getSchedulerTrackingdetails().get(0).getEndTime());
                        }
                    }

                    holder.start_txtview.setVisibility(View.VISIBLE);
                    holder.stop_txtview.setVisibility(View.VISIBLE);
                    holder.stop_txt.setVisibility(View.GONE);
                    holder.start_txt.setVisibility(View.GONE);
                }
            }
            else
            {
                if (datum.getStatus().toString().trim().equalsIgnoreCase("In-Progress") || datum.getStatus().toString().trim().equalsIgnoreCase("Completed")) {
                    holder.stop_txt.setVisibility(View.GONE);
                    holder.start_txt.setVisibility(View.GONE);
                    holder.start_txtview.setVisibility(View.VISIBLE);
                    holder.stop_txtview.setVisibility(View.VISIBLE);

                    holder.start_txtview.setText("");
                    holder.stop_txtview.setText("");
                }
                else
                {
                    holder.stop_txt.setVisibility(View.VISIBLE);
                    holder.start_txt.setVisibility(View.VISIBLE);
                    holder.stop_txt.setEnabled(false);
                    holder.start_txtview.setVisibility(View.GONE);
                    holder.stop_txtview.setVisibility(View.GONE);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try {

            if (datum.getStatus().toString().trim().equalsIgnoreCase("Completed")) {
                holder.lin_lay.setVisibility(View.VISIBLE);
                if (datum.getEndsat().trim().length() > 8)
                {
                    // todo getedDate or getRequiredDate OR getSingRequiredDate

                    if(ApiInterface.BASE_URL.trim().equals("https://mpest.isssg.biz:96/") || ApiInterface.BASE_URL.trim().equals("https://www.amanaasia.net:86/") ) {

                        holder.startjob_txtview.setText(Utils.getSingRequiredDate(datum.getStartsat()));
                        holder.stopjob_txtview.setText(Utils.getSingRequiredDate(datum.getEndsat()));
                    }
                    else
                    {

                        holder.startjob_txtview.setText(Utils.getSingRequiredDate(datum.getStartsat()));
                        holder.stopjob_txtview.setText(Utils.getSingRequiredDate(datum.getEndsat()));
//                        holder.startjob_txtview.setText(Utils.getedDate(datum.getStartsat()));
//                        holder.stopjob_txtview.setText(Utils.getedDate(datum.getEndsat()));
                    }
                }
                else
                {
                    holder.startjob_txtview.setText(datum.getStartsat());
                    holder.stopjob_txtview.setText(datum.getEndsat());
                }
            } else {
                holder.lin_lay.setVisibility(View.GONE);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try {
            if (datum.getAdhocType().length() > 0) {
                holder.tvCustomer.setText(datum.getAdhocdata().get(0).getCompanyName());
            } else {
                holder.tvCustomer.setText(datum.getJobOrdersdetails().get(0).getSiteName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d("xcxv","cvbcv"+ datum.getJobOrdersdetails().get(0).getJobDetails());
            Log.d("xcxv","cvbcv"+ datum.getJobOrdersdetails().get(0).getJobNotes());

            if (datum.getCustomerdetails().size() > 0) {
                holder.servicearea_txt.setText(datum.getCustomerServicedetails().get(0).getsServiceArea());
            }

            if (datum.getJobOrdersdetails().size() > 0) {
                holder.jobdetails_txt.setText(datum.getJobOrdersdetails().get(0).getJobDetails());
                holder.jobnotes_txt.setText(datum.getJobOrdersdetails().get(0).getJobNotes());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        //holder.tvCustomer.setText(datum.getContactPerson());

        try {
            if (datum.getAdhocType().length() > 0) {
                holder.tvLocation.setText(datum.getAdhocdata().get(0).getLocation());
            } else {
                holder.tvLocation.setText(datum.getLocation());
            }

            holder.tvTeam.setText(datum.getPestType());
            holder.teamlabel_txt.setText(datum.getTeamdetails().get(0).getTeamName());
            holder.contactPerson_txt.setText(datum.getJobOrdersdetails().get(0).getContactPerson());

            //todo

            if(ApiInterface.BASE_URL.trim().equals("https://mpest.isssg.biz:96/") || ApiInterface.BASE_URL.trim().equals("https://www.amanaasia.net:86/") ) {

                holder.tvDateTime.setText(Utils.getSingRequiredDate(datum.getStartsat()));
            }
            else
            {
                holder.tvDateTime.setText(Utils.getSingRequiredDate(datum.getStartsat()));
//                holder.tvDateTime.setText(Utils.getedDate(datum.getStartsat()));
            }

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

        try
        {
            holder.start_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.start_txt.setVisibility(View.GONE);
                    holder.start_txtview.setVisibility(View.VISIBLE);
                    holder.start_txtview.setText(Utils.CurrentTime());
                    holder.stop_txt.setVisibility(View.VISIBLE);
                    holder.stop_txt.setBackgroundResource(R.drawable.button_border);
                    holder.stop_txtview.setVisibility(View.GONE);
                    holder.stop_txt.setEnabled(true);

                    new startTime(datum.getStatus(),datum.get_id(),datum.getLatLong(),_appPrefs.getUserID().toString()).execute();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            holder.stop_txt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    holder.start_txt.setVisibility(View.GONE);
                    holder.start_txtview.setVisibility(View.VISIBLE);
                    holder.stop_txtview.setVisibility(View.VISIBLE);
                    holder.stop_txt.setVisibility(View.GONE);
                    holder.stop_txtview.setText(Utils.CurrentTime());

                    new stopTime(datum.getStatus(),datum.get_id(),datum.getLatLong(),_appPrefs.getUserID().toString()).execute();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        final String finalMAdhocid = mAdhocid;
        holder.tv_Resubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(finalMAdhocid.length()>0)
                {
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
                }
                else {
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
                try
                {
                    Log.d("hhsasf","sdf" + datum.getStatus().toString().trim());
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

                    }
                    else if (holder.tvPreview.getText().toString().trim().equalsIgnoreCase("Uploading..")) {

                        Log.d("jhasdhs3","bnv");
                    }
                    else
                    {
                        try
                        {
                            Intent i = new Intent(mContext, ServiceDetailsActivity.class);
                            i.putExtra("ServiceId", datum.getServiceID());
                            i.putExtra("Status", datum.getStatus());
                            i.putExtra("_ID", datum.get_id());
                            i.putExtra("LatLong", datum.getLatLong());
                            i.putExtra("TrackingId", datum.getSchedulerTrackingdetails().get(0).get_id());
                            (mContext).startActivity(i);
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            Intent i = new Intent(mContext, ServiceDetailsActivity.class);
                            i.putExtra("ServiceId", datum.getServiceID());
                            i.putExtra("Status", datum.getStatus());
                            i.putExtra("_ID", datum.get_id());
                            i.putExtra("LatLong", datum.getLatLong());
                            i.putExtra("TrackingId", "");
                            (mContext).startActivity(i);
                        }
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Log.d("asxcvxcv","sdf" + ex);
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

    public class startTime extends AsyncTask<Object, Void, String> {

        String id,LatLong, UserId,status;

        public startTime(String status, String id, String latlon, String userid) {
            this.id = id;
            this.LatLong = latlon;
            this.UserId = userid;
            this.status = status;
        }

        protected void onPreExecute() {
            Utils.showCustomDialog(mContext);
        }

        protected String doInBackground(Object... parametros) {
            // Do some validation here

            final ApiInterface apiService = ApiClient.getClientForList().create(ApiInterface.class);

            Call<Object> call = apiService.ApiStartTime(id,LatLong,UserId);

            try {
                final JSONArray object = new JSONArray(new com.google.gson.Gson().toJson(call.clone().execute().body()));

             //   Toast.makeText(mContext,"Started",Toast.LENGTH_LONG).show();
            } catch (Exception e) {

                Utils.dismissDialog();
              //  Toast.makeText(mContext,"Unable to get data from server. Please try again later",Toast.LENGTH_LONG).show();

                e.printStackTrace();

            }finally {
                Utils.dismissDialog();
            }

            return "true";
        }


        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {
                Utils.dismissDialog();
            }
            super.onPostExecute(result);

            ((JobDetailsActivity)mContext).refreshItems();
        }

    }

    public class stopTime extends AsyncTask<Object, Void, String> {

        String id,LatLong, UserId,status;

        public stopTime(String status, String id, String latlon, String userid) {
            this.id = id;
            this.LatLong = latlon;
            this.UserId = userid;
            this.status = status;
        }

        protected void onPreExecute() {
            Utils.showCustomDialog(mContext);
        }

        protected String doInBackground(Object... parametros) {
            // Do some validation here
            final ApiInterface apiService = ApiClient.getClientForList().create(ApiInterface.class);

            Call<Object> call = apiService.ApiEndTime(id,LatLong,UserId);
            try {

                final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(call.clone().execute().body()));

          //      Toast.makeText(mContext,"Stopped",Toast.LENGTH_LONG).show();


            } catch (Exception e) {

                Utils.dismissDialog();

        //        Toast.makeText(mContext,"Unable to get data from server. Please try again later",Toast.LENGTH_LONG).show();

                e.printStackTrace();

            }finally {
                Utils.dismissDialog();
            }

            return "true";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {
                Utils.dismissDialog();
            }
            super.onPostExecute(result);

            ((JobDetailsActivity)mContext).refreshItems();
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayDatum.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView tvServiceId, tvDateTime, tvCustomer,contactPerson_txt, tvTeam,teamlabel_txt,start_txt, stop_txt,
                stop_txtview,start_txtview,tvComments, tvPreview, tvLocation, tvUploadStatus,tvUnderLine, tv_Resubmit,
                startjob_txtview,stopjob_txtview,servicearea_txt,jobdetails_txt,jobnotes_txt;
        public LinearLayout lin_lay;

        public ViewHolder(View itemView) {
            super(itemView);
            lin_lay = (LinearLayout) itemView.findViewById(R.id.lin_lay);
            tvServiceId = (TextView) itemView.findViewById(R.id.service_txt);
            tvDateTime = (TextView) itemView.findViewById(R.id.datetime_txt);
            tvCustomer = (TextView) itemView.findViewById(R.id.customer_txt);
            start_txtview = (TextView) itemView.findViewById(R.id.start_txtview);
            stop_txtview = (TextView) itemView.findViewById(R.id.stop_txtview);
            tvTeam = (TextView) itemView.findViewById(R.id.team_txt);
            contactPerson_txt = (TextView) itemView.findViewById(R.id.contactPerson_txt);
            teamlabel_txt = (TextView) itemView.findViewById(R.id.teamlabel_txt);
            start_txt = (TextView) itemView.findViewById(R.id.start_txt);
            stop_txt = (TextView) itemView.findViewById(R.id.stop_txt);
            tvComments = (TextView) itemView.findViewById(R.id.comments_txt);
            tvPreview = (TextView) itemView.findViewById(R.id.preview_txt);
            tvLocation = (TextView) itemView.findViewById(R.id.location_txt);
            tvUnderLine = (TextView) itemView.findViewById(R.id.Line_txt);
            tv_Resubmit = (TextView) itemView.findViewById(R.id.ReSubmit_txt);
            tvUploadStatus = (TextView) itemView.findViewById(R.id.UploadStatus_txt);
            startjob_txtview = (TextView) itemView.findViewById(R.id.startjob_txtview);
            stopjob_txtview = (TextView) itemView.findViewById(R.id.stopjob_txtview);
            servicearea_txt = (TextView) itemView.findViewById(R.id.servicearea_txt);
            jobdetails_txt = (TextView) itemView.findViewById(R.id.jobdetails_txt);
            jobnotes_txt = (TextView) itemView.findViewById(R.id.jobnotes_txt);

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