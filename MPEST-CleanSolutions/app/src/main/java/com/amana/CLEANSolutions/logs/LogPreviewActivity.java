package com.amana.CLEANSolutions.logs;

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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import com.amana.CLEANSolutions.BuildConfig;
import com.amana.CLEANSolutions.MapsActivity;
import com.amana.CLEANSolutions.R;
import com.amana.CLEANSolutions.adapter.PreviewMaterialsAdapter;
import com.amana.CLEANSolutions.adapter.PreviewServicesAdapter;
import com.amana.CLEANSolutions.joblist.JobDetailsActivity;
import com.amana.CLEANSolutions.model.MaterialPreviewModel;
import com.amana.CLEANSolutions.model.UploadRequest;
import com.amana.CLEANSolutions.model.finalUpload.MaterialsRequest;
import com.amana.CLEANSolutions.model.finalUpload.PhotoAfterRequest;
import com.amana.CLEANSolutions.model.finalUpload.PhotoBeforeRequest;
import com.amana.CLEANSolutions.model.finalUpload.ServicesRequest;
import com.amana.CLEANSolutions.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.CLEANSolutions.model.realm.jobdetails.ServicesCapturesRmModel;
import com.amana.CLEANSolutions.model.realm.logdetails.LogFeedbackCaptureRmModel;
import com.amana.CLEANSolutions.model.realm.logdetails.LogPaymentCaptureRmModel;
import com.amana.CLEANSolutions.model.realm.logdetails.LogPhotoRemarkRMModel;
import com.amana.CLEANSolutions.model.realm.logdetails.LogTeamCaptureRmModel;
import com.amana.CLEANSolutions.model.realm.logdetails.LogsServiceDetails;
import com.amana.CLEANSolutions.restApi.ApiClient;
import com.amana.CLEANSolutions.restApi.ApiInterface;
import com.amana.CLEANSolutions.utils.AppLogger;
import com.amana.CLEANSolutions.utils.AppPreferences;
import com.amana.CLEANSolutions.utils.MasterDbLists;
import com.amana.CLEANSolutions.utils.Utils;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogPreviewActivity extends AppCompatActivity {

    AppPreferences _appPrefs;
    Context mContext;
    String mServiceId = "", mStatus = "",SelectedServices = "",SelectedMaterial="";
    String Breeding="",NoOfCulls = "", Species = "", MistingCariedOutNo = "", MistingCariedOut = "", Density = "",
            Instar = "", NoOfBurrows = "", NoOfDead = "", Habitat = "", Action = "",
            Recommendation = "", Others = "", Reason = "";


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
    LogPhotoAdapter logPhotoAdapter;

    LogPaymentCaptureRmModel paymentCaptureRmModel = new LogPaymentCaptureRmModel();
    LogTeamCaptureRmModel teamCaptureRmModel = new LogTeamCaptureRmModel();
    LogFeedbackCaptureRmModel feedbackCaptureRmModel = new LogFeedbackCaptureRmModel();
    ArrayList<MaterialPreviewModel> arr_previewMaterial = new ArrayList<>();

    LogsServiceDetails mList;
    ArrayList<ServiceMaterialRMModel> arr_servicedetail = new ArrayList<>();
    ArrayList<ServiceMaterialRMModel> arr_materialedetail = new ArrayList<>();

    ArrayList<LogPhotoRemarkRMModel> arr_photoAFTER = new ArrayList<>();
    ArrayList<LogPhotoRemarkRMModel> arr_photoBEFORE = new ArrayList<>();

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
    @BindView(R.id.no_photobefore)
    TextView no_photobefore;
    @BindView(R.id.no_photoafter)
    TextView no_photoafter;

    @BindView(R.id.cancel_btn)
    Button btn_cancel;

    @BindView(R.id.rv_services)
    RecyclerView rv_services;
    @BindView(R.id.rv_materials)
    RecyclerView rv_materials;
    @BindView(R.id.rv_photoBefore)
    RecyclerView rv_photoBefore;
    @BindView(R.id.rv_photoAfter)
    RecyclerView rv_photoAfter;


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

    String[] arrServiceID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_preview);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);


        mServiceId = getIntent().getExtras().getString("ServiceId");

        arrServiceID =mServiceId.split("@@");
        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, arrServiceID[0]);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        upload_btn.setVisibility(View.GONE);
        try{
            if(arrServiceID[1].contains("ADHOC_")){
                mServiceId =  arrServiceID[1].toString();
            }
        }catch (Exception e){e.printStackTrace();}

        new Initialise().execute("");
    }



    //Initialise details n get data
    class Initialise extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {
            Utils.showCustomDialogwithBG(mContext);
        }

        protected String doInBackground(Object... parametros) {

            try {


                arr_photoAFTER = MasterDbLists.getLOGPhotoRemarksDetails("AFTER",mServiceId);
                arr_photoBEFORE = MasterDbLists.getLOGPhotoRemarksDetails("BEFORE",mServiceId);

                AppLogger.verbose("arr_photoAFTER ",""+arr_photoAFTER.size());

                AppLogger.verbose("arr_photoBEFORE ",""+arr_photoBEFORE.size());
                //Material Details from db
                arr_materialedetail = MasterDbLists.getServiceMaterialFromDB(mServiceId, "MATERIALS");

                //Service Details from db
                arr_servicedetail = MasterDbLists.getServiceMaterialFromDB(mServiceId, "SERVICE");

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

                    if(mServiceId.contains("ADHOC_")){
                        // set  job details
                        mList = MasterDbLists.getLOGServiceDetailsfromDb(arrServiceID[0].toString());

                    }else{
                        // set  job details
                        mList = MasterDbLists.getLOGServiceDetailsfromDb(mServiceId.toString());

                    }

                    //Payment Details
                    paymentCaptureRmModel = MasterDbLists.getLOGPaymentSavedDetails(mServiceId.toString());

                    // Get Team Details
                    teamCaptureRmModel = MasterDbLists.getLOGTeamSavedDetails(mServiceId);

                    // GEt Feedback Details
                    feedbackCaptureRmModel = MasterDbLists.getLOGFeedbackSavedDetails(mServiceId);

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
                materialsRequest.setMaterialName(arr_previewMaterial.get(i).getMaterials());
                materialsRequest.setPestType(arr_previewMaterial.get(i).getPestType());
                materialsRequest.setQuantity(arr_previewMaterial.get(i).getQuantity());
                materialsRequest.setUnit(arr_previewMaterial.get(i).getUnit());
                arr_materialsRequest.add(materialsRequest);
            }

        }

         Utils.dismissDialog();
    }

    public void init() {

        // Utils.showCustomDialog(mContext);

        tv_serviceId.setText(mList.getServiceId());
        try {

            tv_customer_name.setText(mList.getCustomerName());
            tv_location.setText(mList.getLocation());

            tv_performedBy.setText(mList.getJobPerformedBy());

            tv_scheduled_date.setText(mList.getScheduledDate());
            tv_pest_type.setText(mList.getPestType());

            tv_scheduled_start_time.setText(mList.getActualStartTime());
            tv_scheduled_end_time.setText(mList.getActualEndTime());

            tv_team.setText(mList.getTeam());

            tv_contract_no.setText(mList.getContractNo());

            Types_txt.setText(mList.getTypes());

            tv_work_orderno.setText(mList.getWorkOrderNo());

            tv_team_members.setText(mList.getTeamMember());

        } catch (Exception e) {
            e.printStackTrace();
        }

        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_location = new Intent(v.getContext(), MapsActivity.class);

                if(mList.getLocation().length() > 0 && mList.getLatLong().length() >0){

                    intent_location.putExtra("LatLong", mList.getLatLong());
                    intent_location.putExtra("Location", mList.getLocation());
                    startActivity(intent_location);
                }



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

        if(arr_photoBEFORE.size() > 0){

            logPhotoAdapter = new LogPhotoAdapter(mContext, arr_photoBEFORE);
            rv_photoBefore.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            rv_photoBefore.setAdapter(logPhotoAdapter);
            rv_photoBefore.setItemAnimator(new DefaultItemAnimator());
            no_photobefore.setVisibility(View.GONE);
        }else{
            no_photobefore.setVisibility(View.VISIBLE);
        }


        if(arr_photoAFTER.size() > 0){

            logPhotoAdapter = new LogPhotoAdapter(mContext, arr_photoAFTER);
            rv_photoAfter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            rv_photoAfter.setAdapter(logPhotoAdapter);
            rv_photoAfter.setItemAnimator(new DefaultItemAnimator());
            no_photoafter.setVisibility(View.GONE);
        }else{
            no_photoafter.setVisibility(View.VISIBLE);
        }


        if (paymentCaptureRmModel != null) {
            payment_txt.setText(paymentCaptureRmModel.getPaymentMode().toString());
            totalPayment_txt.setText(paymentCaptureRmModel.getTotalPayment());
            notes_txt.setText(paymentCaptureRmModel.getPaymentNote_chequeNo());
            AreaPlanned_txt.setText(paymentCaptureRmModel.getTotalAreaPlanned());
            AreaComp_txt.setText(paymentCaptureRmModel.getTotalAreaCompleted());
        }

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
        finish();

    }



}
