package com.amana.MPESTPestoff.myjob;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amana.MPESTPestoff.MapsActivity;
import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.dashboard.DashboardActivity;
import com.amana.MPESTPestoff.model.realm.taskdetail.Datum;
import com.amana.MPESTPestoff.myjob.MyJobActivity;
import com.amana.MPESTPestoff.restApi.ApiClient;
import com.amana.MPESTPestoff.restApi.ApiInterface;
import com.amana.MPESTPestoff.utils.AppPreferences;
import com.amana.MPESTPestoff.utils.MasterDbLists;
import com.amana.MPESTPestoff.utils.Utils;
import com.geniusforapp.fancydialog.FancyAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceDetailsActivity extends AppCompatActivity {

    AppPreferences _appPrefs;
    Context mContext;
    String mServiceId = "", mStatus = "";

    ArrayList<Datum> mList;
    @BindView(R.id.serviceId_txt)
    TextView tv_serviceId;
    @BindView(R.id.work_orderno_txt)
    TextView tv_work_orderno;
    @BindView(R.id.contract_no_txt)
    TextView tv_contract_no;
    @BindView(R.id.team_txt)
    TextView tv_team;
    @BindView(R.id.customer_name_txt)
    TextView tv_customer_name;
    @BindView(R.id.scheduled_date_txt)
    TextView tv_scheduled_date;
    @BindView(R.id.scheduled_start_time_txt)
    TextView tv_scheduled_start_time;
    @BindView(R.id.scheduled_end_time_txt)
    TextView tv_scheduled_end_time;
    @BindView(R.id.team_members_txt)
    TextView tv_team_members;
    @BindView(R.id.pest_type_txt)
    TextView tv_pest_type;
    @BindView(R.id.location_txt)
    TextView tv_location;
    @BindView(R.id.Types_txt)
    TextView Types_txt;

    @BindView(R.id.cancel_btn)
    Button btn_cancel;
    @BindView(R.id.start_btn)
    Button btn_start;
    int mMasterCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "Service Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();


    }

    public void init() {

        mMasterCount = MasterDbLists.getMasterCount();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mServiceId = extras.getString("ServiceId");
            mStatus = extras.getString("Status");

            if (mStatus.equalsIgnoreCase("Yet to Start")) {
                btn_start.setText("START");
                btn_start.setVisibility(View.VISIBLE);
            }else if (mStatus.equalsIgnoreCase("In-Progress") ) {
                btn_start.setText("CONTINUE");
                btn_start.setVisibility(View.VISIBLE);
            }else {
                btn_start.setVisibility(View.GONE);
            }

            mList = MasterDbLists.getServiceDetailsfromDb(mServiceId);

            tv_serviceId.setText(mList.get(0).getServiceID());

            try {
                if(mList.get(0).getAdhocType().length() > 0){
                    tv_customer_name.setText(mList.get(0).getAdhocdata().get(0).getCompanyName());
                }else{
                    tv_customer_name.setText(mList.get(0).getCustomerdetails().get(0).getCompanyName());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try{

                if(mList.get(0).getAdhocType().length() > 0){
                    tv_location.setText(mList.get(0).getAdhocdata().get(0).getLocation());

                }else{
                    tv_location.setText(mList.get(0).getLocation());
                }

                if(mList.get(0).getJobOrdersdetails().size()>0){
                    Types_txt.setText(mList.get(0).getJobOrdersdetails().get(0).getTypes());
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            tv_scheduled_date.setText(Utils.getRequiredDate(mList.get(0).getStartsat()));
            tv_pest_type.setText(mList.get(0).getPestType());
            tv_scheduled_start_time.setText(Utils.getRequiredTime(mList.get(0).getStartsat()));
            tv_scheduled_end_time.setText(Utils.getRequiredTime(mList.get(0).getEndsat()));


            if (mList.get(0).getTeamdetails().size() > 0) {
                tv_team_members.setText(mList.get(0).getTeamdetails().get(0).getTeamMembers());
                _appPrefs.saveTEAMLEAD(mList.get(0).getTeamdetails().get(0).getTeamLead()); // set's Team Lead to preferrence
                tv_team.setText(mList.get(0).getTeamdetails().get(0).getTeamName());
                _appPrefs.saveTEAMNAME(mList.get(0).getTeamdetails().get(0).getTeamName());
            }

            if (mList.get(0).getContracterdetails().size() > 0) {
                tv_contract_no.setText(mList.get(0).getContracterdetails().get(0).getContractReferenceNo());
            }

            if (mList.get(0).getJobOrdersdetails().size() > 0) {
                tv_work_orderno.setText(mList.get(0).getJobOrdersdetails().get(0).getSalesOrderNo());

            }

            if (mList.get(0).getCustomerdetails().size() > 0) {
                _appPrefs.saveCUSTOMEREMAIL(mList.get(0).getCustomerServicedetails().getsEmail());
            }


            try {
                _appPrefs.savePESTS(mList.get(0).getPestType()); // set's Pest Type to preferrence
                _appPrefs.saveCUSTOMER(mList.get(0).getContactPerson()); // set's Customer Name to preferrence

            } catch (Exception e) {
                e.printStackTrace();
            }


            tv_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_location = new Intent(v.getContext(), MapsActivity.class);

                    if(mList.get(0).getAdhocType().length() > 0){

                        intent_location.putExtra("LatLong", mList.get(0).getAdhocdata().get(0).getLatLong());
                        intent_location.putExtra("Location", mList.get(0).getAdhocdata().get(0).getLocation());

                    }else{
                        intent_location.putExtra("LatLong", mList.get(0).getLatLong());
                        intent_location.putExtra("Location", mList.get(0).getLocation());
                    }

                    intent_location.putExtra("LatLong", mList.get(0).getLatLong());
                    intent_location.putExtra("Location", mList.get(0).getLocation());
                    startActivity(intent_location);
                }
            });
        }

    }

    @OnClick(R.id.cancel_btn)
    public void cancelButtonClick() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.start_btn)
    public void startButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library

            if(btn_start.getText().toString().equalsIgnoreCase("Continue")){
                Intent startIntent = new Intent(mContext, MyJobActivity.class);
                startIntent.putExtra("ServiceId", "" + tv_serviceId.getText().toString());
                startIntent.putExtra("PestType", "" + tv_pest_type.getText().toString());
                startActivity(startIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }else{
                if(mList.get(0).getCustomerdetails().get(0).getMultipleJobs()){

                    if(MasterDbLists.checkInProgressExist() == 1){
                        if(tv_customer_name.getText().toString().equalsIgnoreCase(""+ MasterDbLists.getCustomerName_InProgressExist())){
                            popup_AllowToStartService();
                        }else{
                            popup_JobExist();
                        }
                    }else{
                        popup_AllowToStartService();
                    }

                }
                else if (MasterDbLists.checkInProgressExist() == 0 ) {
                    popup_AllowToStartService();

                } else {
                    popup_JobExist();
                }
            }

    }

    public void popup_AllowToStartService(){

        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(mContext)
                .setAlertFont("fonts/Avenir-Medium.otf")
                .setSubTitleFont("fonts/Avenir-Heavy.otf")
                .setTextSubTitle("Are you sure you want to start this job? you will not be to revert.")
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.colorPositive)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                        if(Utils.isNetConnected(mContext)) {
                            JobStatusUpdate(mList.get(0).get_id(), _appPrefs.getUserID());
                        } else {
                            Utils.noNetPopup(mContext);
                        }



                    }
                })
                /* .setAutoHide(true)*/
                .build();
        alert.show();
    }


    public void popup_JobExist(){
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(mContext)
                .setAlertFont("fonts/Avenir-Medium.otf")
                .setSubTitleFont("fonts/Avenir-Heavy.otf")
                .setTextSubTitle("Another job is in progress...")
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.colorPositive)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        finish();

                    }
                })
                /* .setAutoHide(true)*/
                .build();
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // ((MainActivity) a).performQuoteListClick();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * GET Job Update
     */
    public void JobStatusUpdate(String _id, String UserID) {

        Utils.showCustomDialog(mContext);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Object> call = apiService.JobStatusUpdate(_id, "In-Progress", UserID);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(response.body()));

                    int mStatusCode = object.getInt("statusCode");

                    if (mStatusCode == 200) {

                        _appPrefs.saveJobStartedTime(""+Utils.getCurrentDateTime());
                       // MasterDbLists.AddLog(_appPrefs.getSERVICEID().toString(), "In-Progress", Utils.CurrentDate(), Utils.CurrentTime());
                        MasterDbLists.UploadJobStatus(tv_serviceId.getText().toString(), "In-Progress"); //In-Progress
                        //MasterDbLists.ClearDbAfterUpload();
                        Intent startIntent = new Intent(mContext, MyJobActivity.class);
                        startIntent.putExtra("ServiceId", "" + tv_serviceId.getText().toString());
                        startIntent.putExtra("PestType", "" + tv_pest_type.getText().toString());
                        startActivity(startIntent);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        Utils.CommanPopup(mContext, "StatusCode --" + mStatusCode);
                    }

                    Utils.dismissDialog();
                } catch (Exception e) {

                    e.printStackTrace();
                    Utils.dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Utils.dismissDialog();
            }

        });
    }


}
