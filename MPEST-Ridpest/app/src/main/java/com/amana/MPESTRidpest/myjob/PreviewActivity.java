package com.amana.MPESTRidpest.myjob;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amana.MPESTRidpest.BuildConfig;
import com.amana.MPESTRidpest.MapsActivity;
import com.amana.MPESTRidpest.R;
import com.amana.MPESTRidpest.adapter.PreviewMaterialsAdapter;
import com.amana.MPESTRidpest.adapter.PreviewServicesAdapter;
import com.amana.MPESTRidpest.model.AdhocRequest;
import com.amana.MPESTRidpest.model.MaterialPreviewModel;
import com.amana.MPESTRidpest.model.UploadRequest;
import com.amana.MPESTRidpest.model.finalUpload.MaterialsRequest;
import com.amana.MPESTRidpest.model.finalUpload.PhotoAfterRequest;
import com.amana.MPESTRidpest.model.finalUpload.PhotoBeforeRequest;
import com.amana.MPESTRidpest.model.finalUpload.ServicesRequest;
import com.amana.MPESTRidpest.model.realm.Materials;
import com.amana.MPESTRidpest.model.realm.jobdetails.FeedbackCaptureRmModel;
import com.amana.MPESTRidpest.model.realm.jobdetails.PaymentCaptureRmModel;
import com.amana.MPESTRidpest.model.realm.jobdetails.PhotoRemarkRMModel;
import com.amana.MPESTRidpest.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.MPESTRidpest.model.realm.jobdetails.ServicesCapturesRmModel;
import com.amana.MPESTRidpest.model.realm.jobdetails.TeamCaptureRmModel;
import com.amana.MPESTRidpest.model.realm.logdetails.LogsServiceDetails;
import com.amana.MPESTRidpest.model.realm.taskdetail.Datum;
import com.amana.MPESTRidpest.restApi.ApiClient;
import com.amana.MPESTRidpest.restApi.ApiInterface;
import com.amana.MPESTRidpest.joblist.JobDetailsActivity;
import com.amana.MPESTRidpest.utils.AppLogger;
import com.amana.MPESTRidpest.utils.AppPreferences;
import com.amana.MPESTRidpest.utils.MasterDbLists;
import com.amana.MPESTRidpest.utils.Utils;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PreviewActivity extends AppCompatActivity {

    AppPreferences _appPrefs;
    Context mContext;
    String mServiceId = "", mStatus = "",SelectedServices = "",SelectedMaterial="";
    String Breeding="",NoOfCulls = "", Species = "", MistingCariedOutNo = "", MistingCariedOut = "", Density = "",
            Instar = "", NoOfBurrows = "", NoOfDead = "", Habitat = "", Action = "",
            Recommendation = "", Others = "", Reason = "";
    String StartTime="", endTime="";

    String TeamMember = "",TeamRemarks ="", TeamSign = "";

    String Mode="",TotalPay = "",note="",AreaPlanned ="", AreaCompleted="", paymentRemark="", sor="";
    ArrayList<PhotoBeforeRequest> arr_photoBeforeRequest = new ArrayList<>();
    ArrayList<PhotoAfterRequest> arr_photoAfterRequest = new ArrayList<>();
    ArrayList<ServicesRequest> arr_serviceRequest = new ArrayList<>();
    ArrayList<MaterialsRequest> arr_materialsRequest = new ArrayList<>();

    ArrayList<String> arr_FilePaths = new ArrayList<>();
    UploadRequest uploadRequest;

    String Reting="", EmailId="",CustomerRemarks="",CustomerSignature="";

    PreviewServicesAdapter previewServicesAdapter;
    PreviewMaterialsAdapter previewMaterialsAdapter;

    PaymentCaptureRmModel paymentCaptureRmModel = new PaymentCaptureRmModel();
    TeamCaptureRmModel teamCaptureRmModel = new TeamCaptureRmModel();
    FeedbackCaptureRmModel feedbackCaptureRmModel = new FeedbackCaptureRmModel();
    ArrayList<MaterialPreviewModel> arr_previewMaterial = new ArrayList<>();

    ArrayList<Datum> mList;
    ArrayList<ServiceMaterialRMModel> arr_servicedetail = new ArrayList<>();
    ArrayList<ServiceMaterialRMModel> arr_materialedetail = new ArrayList<>();

    ArrayList<PhotoRemarkRMModel> arr_photoAFTER = new ArrayList<>();
    ArrayList<PhotoRemarkRMModel> arr_photoBEFORE = new ArrayList<>();

    private String TAG = this.getClass().getSimpleName();

    private File compressedImage;

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
    @BindView(R.id.performedBy_txt)
    TextView tv_performedBy;
    @BindView(R.id.location_txt)
    TextView tv_location;
    @BindView(R.id.cancel_btn)
    Button btn_cancel;

    @BindView(R.id.rv_services)
    RecyclerView rv_services;
    @BindView(R.id.rv_materials)
    RecyclerView rv_materials;

    @BindView(R.id.payment_txt)
    TextView payment_txt;
    @BindView(R.id.totalPayment_txt)
    TextView totalPayment_txt;
    @BindView(R.id.notes_txt)
    TextView notes_txt;
    @BindView(R.id.AreaPlanned_txt)
    TextView AreaPlanned_txt;
    @BindView(R.id.AreaComp_txt)
    TextView AreaComp_txt;
    @BindView(R.id.Types_txt)
    TextView Types_txt;

    @BindView(R.id.upload_btn)
    Button upload_btn;

    double dLatitube, dLongitube;
    // location last updated time
    private String mLastUpdateTime;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    AdhocRequest adhocRequest = new AdhocRequest();

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    ProgressDialog dialog;
    String mADOCServiceID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "My Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        locationSetup();

        mADOCServiceID = _appPrefs.getSERVICEID().toString();
        if (mADOCServiceID.contains("ADHOC_")) {

            String response = _appPrefs.getAdhocJson();
            Gson gson = new Gson();

            adhocRequest = gson.fromJson(response, AdhocRequest.class);
        }

            restoreValuesFromBundle(savedInstanceState);

        //updateLocationUI();

        checkLocationPermission();

        new getInitialise().execute();
    }


    public void locationSetup(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };



        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();


    }


    public void init() {

       // Utils.showCustomDialog(mContext);

        if(_appPrefs.getSERVICEID().contains("ADHOC_")){
            tv_serviceId.setText(_appPrefs.getSERVICEID());
            tv_customer_name.setText(adhocRequest.getContactPerson());
            tv_location.setText(adhocRequest.getAdhocdata().get(0).getLocation());
           // tv_scheduled_date.setText(Utils.getRequiredDate(adhocRequest.getStartsat()));
            tv_pest_type.setText(adhocRequest.getPestType());
            _appPrefs.saveTEAMLEAD(adhocRequest.getTeam()); // set's Team Lead to preferrence
            tv_team.setText(adhocRequest.getTeam());
            _appPrefs.saveTEAMNAME(adhocRequest.getTeam());

            tv_work_orderno.setText("");

            tv_contract_no.setText("");
            Types_txt.setText("");

            _appPrefs.saveCUSTOMEREMAIL(adhocRequest.getAdhocdata().get(0).getEmail());

            _appPrefs.savePESTS(adhocRequest.getPestType()); // set's Pest Type to preferrence
            _appPrefs.saveCUSTOMER(adhocRequest.getContactPerson()); // set's Customer Name to preferrence



        }else{
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

            }catch (Exception e){
                e.printStackTrace();
            }

            tv_scheduled_date.setText(Utils.getRequiredDate(mList.get(0).getStartsat()));
            tv_pest_type.setText(mList.get(0).getPestType());


            if(mList.get(0).getTeamdetails().size() > 0){

                _appPrefs.saveTEAMLEAD(mList.get(0).getTeamdetails().get(0).getTeamLead()); // set's Team Lead to preferrence
                tv_team.setText(mList.get(0).getTeamdetails().get(0).getTeamName());
                _appPrefs.saveTEAMNAME(mList.get(0).getTeamdetails().get(0).getTeamName());
            }

            if(mList.get(0).getContracterdetails().size() >0){
                tv_contract_no.setText(mList.get(0).getContracterdetails().get(0).getContractReferenceNo());
            }


            if(mList.get(0).getJobOrdersdetails().size()>0){
                Types_txt.setText(mList.get(0).getJobOrdersdetails().get(0).getTypes());
            }

            if(mList.get(0).getJobOrdersdetails().size() > 0){
                tv_work_orderno.setText(mList.get(0).getJobOrdersdetails().get(0).getSalesOrderNo());

            }

            if(mList.get(0).getCustomerdetails().size() > 0){
                _appPrefs.saveCUSTOMEREMAIL(mList.get(0).getCustomerServicedetails().getsEmail());
            }


            try{
                _appPrefs.savePESTS(mList.get(0).getPestType()); // set's Pest Type to preferrence
                _appPrefs.saveCUSTOMER(mList.get(0).getContactPerson()); // set's Customer Name to preferrence

            }catch (Exception e){e.printStackTrace();}


        }



        tv_performedBy.setText(_appPrefs.getUserID());


        tv_scheduled_start_time.setText(Utils.CurrentTime(_appPrefs.getJobStartedTime().toString()));
        tv_scheduled_end_time.setText(Utils.CurrentTime());



        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_location = new Intent(v.getContext(), MapsActivity.class);

                if(_appPrefs.getSERVICEID().toString().contains("ADHOC_")){
                    intent_location.putExtra("LatLong", adhocRequest.getAdhocdata().get(0).getLatLong());
                    intent_location.putExtra("Location", adhocRequest.getAdhocdata().get(0).getLocation());
                }else{
                    if(mList.get(0).getAdhocType().length() > 0){

                        intent_location.putExtra("LatLong", mList.get(0).getAdhocdata().get(0).getLatLong());
                        intent_location.putExtra("Location", mList.get(0).getAdhocdata().get(0).getLocation());

                    }else{
                        intent_location.putExtra("LatLong", mList.get(0).getLatLong());
                        intent_location.putExtra("Location", mList.get(0).getLocation());
                    }
                }




                startActivity(intent_location);
            }
        });


        previewServicesAdapter = new PreviewServicesAdapter(mContext, arr_servicedetail);
        rv_services.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_services.setAdapter(previewServicesAdapter);
        rv_services.setItemAnimator(new DefaultItemAnimator());
        // ------ //


         arr_previewMaterial.clear();

        for (int j = 0; j < arr_materialedetail.size(); j++) {
            String materialOthers = "", materials="";

            materialOthers = arr_materialedetail.get(j).getOthers();
            if(arr_materialedetail.get(j).getOthers().length() !=0 || arr_materialedetail.get(j).getSM_remarks().length() !=0){

                for (int i = 0; i < arr_materialedetail.get(j).getMaterialsCaptures().size(); i++) {

                    materials = arr_materialedetail.get(j).getPestType();
                    MaterialPreviewModel materialPreviewModel = new MaterialPreviewModel();
                    materialPreviewModel.setPestType(arr_materialedetail.get(j).getPestType());
                    materialPreviewModel.setMaterials(arr_materialedetail.get(j).getMaterialsCaptures().get(i).getMaterialName());
                    materialPreviewModel.setQuantity(arr_materialedetail.get(j).getMaterialsCaptures().get(i).getQuantity());
                    materialPreviewModel.setUnit(arr_materialedetail.get(j).getMaterialsCaptures().get(i).getUnit());
                    arr_previewMaterial.add(materialPreviewModel);
                }

                if(materialOthers.length() > 0){
                    MaterialPreviewModel materialPreviewModel = new MaterialPreviewModel();

                    if(materials.length() >0){
                        materialPreviewModel.setPestType(materials);
                    }else{
                        materialPreviewModel.setPestType(arr_materialedetail.get(j).getPestType());
                    }

                    materialPreviewModel.setMaterials(materialOthers);
                    materialPreviewModel.setQuantity("");
                    materialPreviewModel.setUnit("");
                    arr_previewMaterial.add(materialPreviewModel);

                }

            }

        }

        previewMaterialsAdapter = new PreviewMaterialsAdapter(mContext, arr_previewMaterial);
        rv_materials.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_materials.setAdapter(previewMaterialsAdapter);
        rv_materials.setItemAnimator(new DefaultItemAnimator());
        // ----- //


        if (paymentCaptureRmModel != null) {
            payment_txt.setText(paymentCaptureRmModel.getPaymentMode().toString());
            totalPayment_txt.setText(paymentCaptureRmModel.getTotalPayment());
            notes_txt.setText(paymentCaptureRmModel.getPaymentNote_chequeNo());
            AreaPlanned_txt.setText(paymentCaptureRmModel.getTotalAreaPlanned());
            AreaComp_txt.setText(paymentCaptureRmModel.getTotalAreaCompleted());
        }

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.showCustomDialog(mContext);

              /*  uploadRequest = new UploadRequest();
                uploadRequest.setServiceId(mList.get(0).get_id());
                uploadRequest.setTeam(mList.get(0).getTeamdetails().get(0).getTeamName());
                uploadRequest.setScheduledate(Utils.getRequiredDate(mList.get(0).getCreateDate()));
                uploadRequest.setWorkorderNo(tv_work_orderno.getText().toString());
                uploadRequest.setContractOrderNo(tv_contract_no.getText().toString());
                uploadRequest.setCustomerName(mList.get(0).getContactPerson());
                uploadRequest.setLocation(mList.get(0).getLocation());
                uploadRequest.setStartsat(_appPrefs.getJobStartedTime().toString());
                uploadRequest.setEndsat(Utils.getCurrentDateTime());
                uploadRequest.setPesttype(mList.get(0).getPestType());
                uploadRequest.setServices(SelectedServices);
                uploadRequest.setTeamLead(mList.get(0).getTeamdetails().get(0).getTeamLead());
                uploadRequest.setTeamMember(TeamMember);
                uploadRequest.setTeamRemarks(TeamRemarks);
                uploadRequest.setTeamSignature(TeamSign);
                uploadRequest.setStartLocation(_appPrefs.getLOCATION());
                uploadRequest.setEndLocation(""+dLatitube+","+dLongitube);
                uploadRequest.setBreedingFound(Breeding);
                uploadRequest.setSpecies(Species);
                uploadRequest.setMistingCariedOutNo(MistingCariedOutNo);
                uploadRequest.setMistingCariedOut(MistingCariedOut);
                uploadRequest.setDensity(Density);
                uploadRequest.setInstar(Instar);
                uploadRequest.setNoOfCulls(NoOfCulls);
                uploadRequest.setNoOfBurrows(NoOfBurrows);
                uploadRequest.setNoOfDead(NoOfDead);
                uploadRequest.setHabitat(Habitat);
                uploadRequest.setAction(Action);
                uploadRequest.setRecommendation(Recommendation);
                uploadRequest.setSor(sor);
                uploadRequest.setOthers(Others);
                uploadRequest.setReason(Reason);
                uploadRequest.setRating(Reting);
                uploadRequest.setMaterials(SelectedMaterial);
                uploadRequest.setCustomerRemarks(CustomerRemarks);
                uploadRequest.setCustomerSignature(CustomerSignature);
                uploadRequest.setPaymentmethod(Mode);
                uploadRequest.setTotalpayment(TotalPay);
                uploadRequest.setPaymentNotes(note);
                uploadRequest.setTotalarea(AreaPlanned);
                uploadRequest.setEmailId(EmailId);
                uploadRequest.setPaymentRemarks(paymentRemark);
                uploadRequest.setCustomerCurrentDate(""+Utils.getCurrentTime());
                uploadRequest.setTotalcompleted(AreaCompleted);
                uploadRequest.setCreatedBy(_appPrefs.getUserID());
                uploadRequest.setCreatedBy(_appPrefs.getUserID());
                uploadRequest.setClientId(mList.get(0).getClient_Id());
                uploadRequest.setPhotosBeforeArray(arr_photoBeforeRequest);
                uploadRequest.setPhotosAfterArray(arr_photoAfterRequest);
                uploadRequest.setServicesArray(arr_serviceRequest);
                uploadRequest.setMaterialsArray(arr_materialsRequest);


                Gson gson = new Gson();
                String json = gson.toJson(uploadRequest);*/


                Intent uploadService = new Intent(mContext,UploadBgService.class);

                if(_appPrefs.getSERVICEID().toString().contains("ADHOC_")){
                    uploadService.putExtra("JobId","");
                    uploadService.putExtra("LatLong",adhocRequest.getAdhocdata().get(0).getLatLong());
                }else{
                    uploadService.putExtra("JobId",mList.get(0).get_id());
                    if(mList.get(0).getAdhocType().length() > 0){
                        uploadService.putExtra("LatLong",mList.get(0).getAdhocdata().get(0).getLatLong());
                    }else{
                        uploadService.putExtra("LatLong",mList.get(0).getLatLong());
                    }
                }
               // uploadService.putExtra("MyData",json);

                uploadService.putExtra("ServiceId",_appPrefs.getSERVICEID());
                uploadService.putExtra("WorkOrderNo",tv_work_orderno.getText().toString());
                uploadService.putExtra("ContractNo",tv_contract_no.getText().toString());
                uploadService.putExtra("setCustomerName",tv_customer_name.getText().toString());
                uploadService.putExtra("ScheduledDate",tv_scheduled_date.getText().toString());
                uploadService.putExtra("ActualStartTime",tv_scheduled_start_time.getText().toString());
                uploadService.putExtra("ActualEndTime",tv_scheduled_end_time.getText().toString());
                uploadService.putExtra("Team",tv_team.getText().toString());
                uploadService.putExtra("TeamMember",tv_team_members.getText().toString());
                uploadService.putExtra("PestType",tv_pest_type.getText().toString());
                uploadService.putExtra("JobPerformedBy",tv_performedBy.getText().toString());
                uploadService.putExtra("Location",tv_location.getText().toString());
                uploadService.putExtra("Latitude",String.valueOf(dLatitube));
                uploadService.putExtra("Longitube",String.valueOf(dLongitube));
                 uploadService.putExtra("setTypes",Types_txt.getText().toString());
                startService(uploadService);


                Utils.dismissDialog();

                // Will change the status in local Db
                if(!(_appPrefs.getSERVICEID().toString().contains("ADHOC_"))) {
                    MasterDbLists.UploadJobStatus(_appPrefs.getSERVICEID(), "Completed", "Upload InProgress");
                }
                Toast.makeText(mContext,"Uploading in background",Toast.LENGTH_LONG).show();

                finish();

                //new FinalEndService().execute();


            }
        });



         //fetchAllDetails();

       // dialog.dismiss();
       // Utils.dismissDialog();
    }



    //Initialise details n get data
    class getInitialise extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {
            Utils.showCustomDialogwithBG(mContext);
        }

        protected String doInBackground(Object... parametros) {

            try {

                if(_appPrefs.getSERVICEID().contains("ADHOC_")){

                }else{
                    // set  job details
                    mList = MasterDbLists.getServiceDetailsfromDb(_appPrefs.getSERVICEID().toString());
                    AppLogger.verbose("mList ",""+mList.size());

                }


                arr_photoAFTER = MasterDbLists.getPhotoRemarksDetails("AFTER",_appPrefs.getSERVICEID());
                arr_photoBEFORE = MasterDbLists.getPhotoRemarksDetails("BEFORE",_appPrefs.getSERVICEID());

                AppLogger.verbose("arr_photoAFTER ",""+arr_photoAFTER.size());

                AppLogger.verbose("arr_photoBEFORE ",""+arr_photoBEFORE.size());
                //Material Details from db
                arr_materialedetail = MasterDbLists.getServiceMaterialFromDB(_appPrefs.getSERVICEID(), "MATERIALS");

                //Service Details from db
                arr_servicedetail = MasterDbLists.getServiceMaterialFromDB(_appPrefs.getSERVICEID(), "SERVICE");


                AppLogger.verbose("arr_materialedetail ",""+arr_materialedetail.size());

                AppLogger.verbose("arr_servicedetail ",""+arr_servicedetail.size());



            } catch (Exception e) {
                Utils.dismissDialog();
                e.printStackTrace();

            }

            return "true";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {
                try{

                    //Payment Details
                    paymentCaptureRmModel = MasterDbLists.getPaymentSavedDetails(_appPrefs.getSERVICEID().toString());

                    // Get Team Details
                    teamCaptureRmModel = MasterDbLists.getTeamSavedDetails(_appPrefs.getSERVICEID());

                    // GEt Feedback Details
                    feedbackCaptureRmModel = MasterDbLists.getFeedbackSavedDetails(_appPrefs.getSERVICEID());

                    init();
                    fetchAllDetails();

                    Utils.dismissDialog();
                }catch (Exception e){
                    Utils.dismissDialog();
                    e.printStackTrace();
                }

            }
            super.onPostExecute(result);
        }

    }

    class FinalEndService extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {
            Utils.showCustomDialog(mContext);

            uploadRequest = new UploadRequest();
            uploadRequest.setServiceId(mList.get(0).get_id());
            uploadRequest.setTeam(mList.get(0).getTeamdetails().get(0).getTeamName());
            uploadRequest.setScheduledate(Utils.getRequiredDate(mList.get(0).getCreateDate()));
            uploadRequest.setWorkorderNo(tv_work_orderno.getText().toString());
            uploadRequest.setContractOrderNo(tv_contract_no.getText().toString());
            uploadRequest.setCustomerName(mList.get(0).getContactPerson());
            uploadRequest.setLocation(mList.get(0).getLocation());
            uploadRequest.setStartsat(_appPrefs.getJobStartedTime().toString());
            uploadRequest.setEndsat(Utils.getCurrentDateTime());
            uploadRequest.setPesttype(mList.get(0).getPestType());
            uploadRequest.setServices(SelectedServices);
            uploadRequest.setTeamLead(mList.get(0).getTeamdetails().get(0).getTeamLead());
            uploadRequest.setTeamMember(TeamMember);
            uploadRequest.setTeamRemarks(TeamRemarks);
            uploadRequest.setTeamSignature(TeamSign);
            uploadRequest.setStartLocation(_appPrefs.getLOCATION());
            uploadRequest.setEndLocation(""+dLatitube+","+dLongitube);
            uploadRequest.setBreedingFound(Breeding);
            uploadRequest.setSpecies(Species);
            uploadRequest.setMistingCariedOutNo(MistingCariedOutNo);
            uploadRequest.setMistingCariedOut(MistingCariedOut);
            uploadRequest.setDensity(Density);
            uploadRequest.setInstar(Instar);
            uploadRequest.setNoOfCulls(NoOfCulls);
            uploadRequest.setNoOfBurrows(NoOfBurrows);
            uploadRequest.setNoOfDead(NoOfDead);
            uploadRequest.setHabitat(Habitat);
            uploadRequest.setAction(Action);
            uploadRequest.setRecommendation(Recommendation);
            uploadRequest.setSor(sor);
            uploadRequest.setOthers(Others);
            uploadRequest.setReason(Reason);
            uploadRequest.setRating(Reting);
            uploadRequest.setMaterials(SelectedMaterial);
            uploadRequest.setCustomerRemarks(CustomerRemarks);
            uploadRequest.setCustomerSignature(CustomerSignature);
            uploadRequest.setPaymentmethod(Mode);
            uploadRequest.setTotalpayment(TotalPay);
            uploadRequest.setPaymentNotes(note);
            uploadRequest.setTotalarea(AreaPlanned);
            uploadRequest.setEmailId(EmailId);
            uploadRequest.setPaymentRemarks(paymentRemark);
            uploadRequest.setCustomerCurrentDate(""+Utils.getCurrentTime());
            uploadRequest.setTotalcompleted(AreaCompleted);
            uploadRequest.setCreatedBy(_appPrefs.getUserID());
            uploadRequest.setCreatedBy(_appPrefs.getUserID());
            uploadRequest.setClientId(mList.get(0).getClient_Id());
            uploadRequest.setPhotosBeforeArray(arr_photoBeforeRequest);
            uploadRequest.setPhotosAfterArray(arr_photoAfterRequest);
            uploadRequest.setServicesArray(arr_serviceRequest);
            uploadRequest.setMaterialsArray(arr_materialsRequest);


            Gson gson = new Gson();
            String mUploadData = gson.toJson(uploadRequest);


            Intent uploadService = new Intent(mContext,UploadBgService.class);
            uploadService.putExtra("MyData",mUploadData);
            uploadService.putExtra("JobId",mList.get(0).get_id());
            uploadService.putExtra("ServiceId",_appPrefs.getSERVICEID());
             uploadService.putExtra("WorkOrderNo",tv_work_orderno.getText().toString());
            uploadService.putExtra("ContractNo",tv_contract_no.getText().toString());
            uploadService.putExtra("setCustomerName",tv_customer_name.getText().toString());
            uploadService.putExtra("ScheduledDate",tv_scheduled_date.getText().toString());
            uploadService.putExtra("ActualStartTime",tv_scheduled_start_time.getText().toString());
            uploadService.putExtra("ActualEndTime",tv_scheduled_end_time.getText().toString());
            uploadService.putExtra("Team",tv_team.getText().toString());
            uploadService.putExtra("TeamMember",tv_team_members.getText().toString());
            uploadService.putExtra("PestType",tv_pest_type.getText().toString());
            uploadService.putExtra("JobPerformedBy",tv_performedBy.getText().toString());
            uploadService.putExtra("Location",tv_location.getText().toString());
            if(mList.get(0).getAdhocType().length() > 0){
                uploadService.putExtra("LatLong",mList.get(0).getAdhocdata().get(0).getLatLong());
            }else{
                uploadService.putExtra("LatLong",mList.get(0).getLatLong());
            }

            uploadService.putExtra("setTypes",Types_txt.getText().toString());
            startService(uploadService);


        }

        protected String doInBackground(Object... parametros) {

          /*  try {

                    final ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<Object> call = apiService.UploadToServer(uploadRequest);

                    final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(call.execute().body()));

                    int mStatusCode = object.getInt("statusCode");

                    if (mStatusCode == 200) {

                        MasterDbLists.AddLog(mList.get(0).get_id(),_appPrefs.getSERVICEID(),"Completed",Utils.CurrentDate(),Utils.CurrentTime());
                        final String  message= object.optString("message");
                        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(mContext)
                                .setAlertFont("fonts/Avenir-Medium.otf")
                                .setSubTitleFont("fonts/Avenir-Heavy.otf")
                                .setTextSubTitle(message)
                                .setPositiveButtonText("OK")
                                .setCancelable(false)
                                .setPositiveColor(R.color.colorPositive)
                                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                                    @Override
                                    public void OnClick(View view, Dialog dialog) {
                                        dialog.dismiss();
                                        _appPrefs.saveJobStartedTime(""+ Utils.getCurrentDateTime());
                                        if(message.equalsIgnoreCase("Job Already Completed")){

                                            MasterDbLists.UploadJobStatus(_appPrefs.getSERVICEID(), "Completed");
                                            MasterDbLists.ClearDbAfterUpload();
                                            Intent intent = new Intent(PreviewActivity.this, JobDetailsActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{

                                            SaveLogDetails();
                                            JobStatusUpdate(mList.get(0).get_id(),_appPrefs.getUserID());
                                        }


                                    }
                                })
                                *//* .setAutoHide(true)*//*
                                .build();
                        alert.show();


                        // new UploadRemainingDEtails().execute();
                        // UploadONSuccess();
                    }


                Utils.dismissDialog();

                } catch (Exception e) {
                Utils.dismissDialog();
                e.printStackTrace();

            }*/

            return "true";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {
                try{

                    Utils.dismissDialog();
                }catch (Exception e){
                    Utils.dismissDialog();
                    e.printStackTrace();
                }

            }
            super.onPostExecute(result);
        }

    }


    @SuppressLint("CheckResult")
    private void fetchAllDetails() {

        //Get selected Services
        SelectedServices = "";
        for (int i = 0; i < arr_servicedetail.size(); i++) {
            SelectedServices = SelectedServices + arr_servicedetail.get(i).getSM_remarks() + ",";
        }

        //get Selected Materials
        SelectedMaterial = "";
        for (int i = 0; i < arr_materialedetail.size(); i++) {
            SelectedMaterial = SelectedMaterial + arr_materialedetail.get(i).getSM_remarks() + ",";

        }
        try {
            SelectedMaterial = SelectedMaterial.substring(0, SelectedMaterial.length() -1);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            SelectedServices = SelectedServices.substring(0, SelectedServices.length() -1);

        }catch (Exception e){
            e.printStackTrace();
        }

        // Get Mosquito Details
        ServicesCapturesRmModel MosquitoDetails = new ServicesCapturesRmModel();
        ServicesCapturesRmModel BirDsDetails = new ServicesCapturesRmModel();
        ServicesCapturesRmModel RodentDetails = new ServicesCapturesRmModel();

        for (int i = 0; i < arr_servicedetail.size(); i++) {

            try{
                if (arr_servicedetail.get(i).getPestType().equalsIgnoreCase("BIRDS")) {

                    BirDsDetails = arr_servicedetail.get(i).getServcieCaptures().get(0);
                } else if (arr_servicedetail.get(i).getPestType().equalsIgnoreCase("RODENTS")) {
                    RodentDetails = arr_servicedetail.get(i).getServcieCaptures().get(0);
                } else if (arr_servicedetail.get(i).getPestType().equalsIgnoreCase("MOSQUITOES")) {
                    MosquitoDetails = arr_servicedetail.get(i).getServcieCaptures().get(0);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (MosquitoDetails != null) {
            Species = MosquitoDetails.getSpeices()== null? "": MosquitoDetails.getSpeices();
            MistingCariedOutNo = MosquitoDetails.getMistingCarriedIFNo()== null? "": MosquitoDetails.getMistingCarriedIFNo();
            MistingCariedOut = MosquitoDetails.getMistingCarriedOut()== null? "": MosquitoDetails.getMistingCarriedOut();
            Density = MosquitoDetails.getDensity()== null? "": MosquitoDetails.getDensity();
            Instar = MosquitoDetails.getInstar()== null? "": MosquitoDetails.getInstar();
            NoOfBurrows = MosquitoDetails.getNoBorrows()== null? "": MosquitoDetails.getNoBorrows();
            NoOfDead = MosquitoDetails.getNoDead()== null? "": MosquitoDetails.getNoDead();
            Habitat = MosquitoDetails.getHabitat()== null? "": MosquitoDetails.getHabitat();
            Action = MosquitoDetails.getAction()== null? "": MosquitoDetails.getAction();
            Recommendation = MosquitoDetails.getRecommendation()== null? "": MosquitoDetails.getRecommendation();
            Others = MosquitoDetails.getMistingCarriedOthers()== null? "": MosquitoDetails.getMistingCarriedOthers();
            Reason = MosquitoDetails.getReason()== null? "": MosquitoDetails.getReason();
            Breeding = MosquitoDetails.getBreeding()== null? "": MosquitoDetails.getBreeding();
        }

        if (BirDsDetails != null) {
            NoOfCulls = BirDsDetails.getNoCulls();
        } else {
            NoOfCulls = "";
        }

        if (RodentDetails != null) {
            NoOfBurrows = RodentDetails.getNoBorrows();
            NoOfDead = RodentDetails.getNoDead();
        }
        // ------------------- //


        if(feedbackCaptureRmModel != null){
            Reting = feedbackCaptureRmModel.getRating();
            EmailId = feedbackCaptureRmModel.getEmailID();
            CustomerRemarks = feedbackCaptureRmModel.getRemarks();
            CustomerSignature = feedbackCaptureRmModel.getCustomerSign();
        }


        if(!_appPrefs.getSERVICEID().contains("ADHOC_")){
            if(mList.get(0).getJobOrdersdetails().size() > 0) {
                StartTime= mList.get(0).getJobOrdersdetails().get(0).getServiceStartDate();
                endTime=mList.get(0).getJobOrdersdetails().get(0).getServiceEndDate();
            }
        }



        if(teamCaptureRmModel != null){

            TeamMember = teamCaptureRmModel.getTeamMembers();
            tv_team_members.setText(TeamMember);
            TeamRemarks=teamCaptureRmModel.getRemarks();
            TeamSign = teamCaptureRmModel.getTechSign();
        }


        if(paymentCaptureRmModel != null){

            Mode =  paymentCaptureRmModel.getPaymentMode();
            TotalPay = paymentCaptureRmModel.getTotalPayment();
            note = paymentCaptureRmModel.getPaymentNote_chequeNo();
            AreaPlanned = paymentCaptureRmModel.getTotalAreaPlanned();
            AreaCompleted = paymentCaptureRmModel.getTotalAreaCompleted();
            paymentRemark = paymentCaptureRmModel.getPaymentRemarks();
            sor = paymentCaptureRmModel.getSOR();

        }

        arr_FilePaths.clear();
        if(arr_photoBEFORE.size() > 0){

            for(int i= 0;i<arr_photoBEFORE.size();i++){
                PhotoBeforeRequest photoBeforeRequest = new PhotoBeforeRequest();
                photoBeforeRequest.setPestType(arr_photoBEFORE.get(i).getPestType());
               // photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getRemarks());

                if(arr_photoBEFORE.get(i).getOthers().length() >0 && arr_photoBEFORE.get(i).getRemarks().length() >0){
                    photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getRemarks().toString()+","+arr_photoBEFORE.get(i).getOthers().toString());


                }else if(arr_photoBEFORE.get(i).getOthers().length() >0 && arr_photoBEFORE.get(i).getRemarks().length() ==0){
                    photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getOthers().toString());
                }else if(arr_photoBEFORE.get(i).getOthers().length() ==0 && arr_photoBEFORE.get(i).getRemarks().length() > 0){
                    photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getRemarks().toString());
                }else{
                    photoBeforeRequest.setRemarks("");
                }

                File imgFile = new File(arr_photoBEFORE.get(i).getImgBase64().toString());
                if(imgFile.exists()) {

                    try {
                        Bitmap imgBitmap = Utils.compressImage(arr_photoBEFORE.get(i).getImgBase64().toString());

                        photoBeforeRequest.setSchimgbefore(Utils.convertBitmapToBase64(imgBitmap));

                        arr_photoBeforeRequest.add(photoBeforeRequest);
                        arr_FilePaths.add(arr_photoBEFORE.get(i).getImgBase64().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }
        }

        if(arr_photoAFTER.size() > 0){

            for(int i= 0;i<arr_photoAFTER.size();i++){

                PhotoAfterRequest photoAfterRequest = new PhotoAfterRequest();
                photoAfterRequest.setPestType(arr_photoAFTER.get(i).getPestType());

                if(arr_photoAFTER.get(i).getOthers().length() >0 && arr_photoAFTER.get(i).getRemarks().length() >0){
                    photoAfterRequest.setRemarks(arr_photoAFTER.get(i).getRemarks().toString()+","+arr_photoAFTER.get(i).getOthers().toString());

                }else if(arr_photoAFTER.get(i).getOthers().length() >0 && arr_photoAFTER.get(i).getRemarks().length() ==0){
                    photoAfterRequest.setRemarks(arr_photoAFTER.get(i).getOthers().toString());

                }else if(arr_photoAFTER.get(i).getOthers().length() ==0 && arr_photoAFTER.get(i).getRemarks().length() > 0){
                    photoAfterRequest.setRemarks(arr_photoAFTER.get(i).getRemarks().toString());
                }else{
                    photoAfterRequest.setRemarks("");
                }


                File imgFile = new File(arr_photoAFTER.get(i).getImgBase64().toString());
                if(imgFile.exists()) {

                    try {
                        Bitmap imgBitmap = Utils.compressImage(arr_photoAFTER.get(i).getImgBase64().toString());

                        photoAfterRequest.setSchimgafter(Utils.convertBitmapToBase64(imgBitmap));

                        arr_photoAfterRequest.add(photoAfterRequest);

                        arr_FilePaths.add(arr_photoAFTER.get(i).getImgBase64().toString());

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }
        }


        if(arr_servicedetail.size()>0){

            for(int i= 0; i<arr_servicedetail.size(); i++){


                if(arr_servicedetail.get(i).getSM_remarks().length() != 0 || arr_servicedetail.get(i).getOthers().length() != 0){

                    ServicesRequest servicesRequest = new ServicesRequest();
                    servicesRequest.setPestType(arr_servicedetail.get(i).getPestType());

                    if(arr_servicedetail.get(i).getOthers().length() >0 && arr_servicedetail.get(i).getSM_remarks().length() > 0){
                        servicesRequest.setServices(arr_servicedetail.get(i).getSM_remarks()+","+arr_servicedetail.get(i).getOthers());
                    }else if(arr_servicedetail.get(i).getOthers().length() ==0 && arr_servicedetail.get(i).getSM_remarks().length() > 0) {
                        servicesRequest.setServices(arr_servicedetail.get(i).getSM_remarks());
                    }else if(arr_servicedetail.get(i).getOthers().length() >0 && arr_servicedetail.get(i).getSM_remarks().length() == 0) {
                        servicesRequest.setServices(arr_servicedetail.get(i).getOthers());
                    }else{
                        servicesRequest.setServices("");
                    }

                    arr_serviceRequest.add(servicesRequest);
                }

            }
        }


        if(arr_previewMaterial.size() >0){

            for(int i= 0; i<arr_previewMaterial.size(); i++){
                MaterialsRequest materialsRequest = new MaterialsRequest();
               // if(arr_previewMaterial.get(i).ge)
                materialsRequest.setMaterialName(arr_previewMaterial.get(i).getMaterials());
                materialsRequest.setPestType(arr_previewMaterial.get(i).getPestType());
                materialsRequest.setQuantity(arr_previewMaterial.get(i).getQuantity());
                materialsRequest.setUnit(arr_previewMaterial.get(i).getUnit());
                arr_materialsRequest.add(materialsRequest);
            }

        }

         Utils.dismissDialog();
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }



    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            dLatitube = mCurrentLocation.getLatitude();
            dLongitube = mCurrentLocation.getLongitude();

            AppLogger.verbose("Location Details --",""+dLatitube+","+dLongitube);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //  Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(PreviewActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(PreviewActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }



    public void checkLocationPermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        showSettingsAlert();
                       // openSettings();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Locations. Please enable.");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openSettings();

                    }
                });
        alertDialog.show();
    }


    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResume() {
        super.onResume();

        try{
            if (mRequestingLocationUpdates && checkPermissions()) {
                startLocationUpdates();
            }

            updateLocationUI();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
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

        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * Finally Update Status
     */
    public void JobStatusUpdate(String _id, String UserID) {

        Utils.dismissDialog();
        Utils.showCustomDialog(mContext);

     /*   try{
            for(int i=0; i< arr_FilePaths.size(); i++){

                Utils.DeleteFile(arr_FilePaths.get(i));
                Utils.DeleteAndScanFile(mContext,arr_FilePaths.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Object> call = apiService.JobStatusUpdate(_id, "Completed", UserID);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(response.body()));

                    int mStatusCode = object.getInt("statusCode");

                    if (mStatusCode == 200) {
                        MasterDbLists.UploadJobStatus(_appPrefs.getSERVICEID(), "Completed");
                       // MasterDbLists.ClearDbAfterUpload();
                        Intent intent = new Intent(PreviewActivity.this, JobDetailsActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Utils.CommanPopup(mContext,"StatusCode --"+mStatusCode);
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
