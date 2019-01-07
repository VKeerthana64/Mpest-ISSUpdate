package com.amana.MPESTRidpest.myjob;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.amana.MPESTRidpest.adapter.PreviewMaterialsAdapter;
import com.amana.MPESTRidpest.adapter.PreviewServicesAdapter;
import com.amana.MPESTRidpest.model.ADHOCUploadRequest;
import com.amana.MPESTRidpest.model.ADHOCUploadRequestWithID;
import com.amana.MPESTRidpest.model.AdhocModel;
import com.amana.MPESTRidpest.model.AdhocRequest;
import com.amana.MPESTRidpest.model.MaterialPreviewModel;
import com.amana.MPESTRidpest.model.UploadRequest;
import com.amana.MPESTRidpest.model.finalUpload.MaterialsRequest;
import com.amana.MPESTRidpest.model.finalUpload.PhotoAfterRequest;
import com.amana.MPESTRidpest.model.finalUpload.PhotoBeforeRequest;
import com.amana.MPESTRidpest.model.finalUpload.ServicesRequest;
import com.amana.MPESTRidpest.model.realm.AdhocRm.AdhocModelRm;
import com.amana.MPESTRidpest.model.realm.AdhocRm.AdhocRequestRm;
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
import com.amana.MPESTRidpest.utils.AppLogger;
import com.amana.MPESTRidpest.utils.AppPreferences;
import com.amana.MPESTRidpest.utils.MasterDbLists;
import com.amana.MPESTRidpest.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadBgService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    String mServiceId = "", mStatus = "", SelectedServices = "", SelectedMaterial = "";
    String Breeding = "", NoOfCulls = "", Species = "", MistingCariedOutNo = "", MistingCariedOut = "", Density = "",
            Instar = "", NoOfBurrows = "", NoOfDead = "", Habitat = "", Action = "",
            Recommendation = "", Others = "", Reason = "", mWorkOrderNo = "", mContractNo = "", mLatitude = "", mLongitube = "";
    String StartTime = "", endTime = "", mJobId = "", msetCustomerName = "",
            mScheduledDate = "", mActualStartTime = "", mActualEndTime = "",
            mTeam = "", mTeamMember = "", mPestType = "",
            mJobPerformedBy = "", mLocation = "",
            mLatLong = "", msetTypes = "";

    String TeamMember = "", TeamRemarks = "", TeamSign = "";

    String Mode = "", TotalPay = "", note = "", AreaPlanned = "", AreaCompleted = "", paymentRemark = "", sor = "";
    ArrayList<PhotoBeforeRequest> arr_photoBeforeRequest = new ArrayList<>();
    ArrayList<PhotoAfterRequest> arr_photoAfterRequest = new ArrayList<>();
    ArrayList<ServicesRequest> arr_serviceRequest = new ArrayList<>();
    ArrayList<MaterialsRequest> arr_materialsRequest = new ArrayList<>();

    ArrayList<String> arr_FilePaths = new ArrayList<>();
    UploadRequest uploadRequest;


    String Reting = "", EmailId = "", CustomerRemarks = "", CustomerSignature = "";

    AdhocRequestRm adhocRequest = new AdhocRequestRm();

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

    public UploadBgService() {
        super("Upload_To_Serve_Service");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "Upload started.", Toast.LENGTH_LONG).show();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        //Toast.makeText(this, "Upload completed.", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        synchronized (this) {

            /*String Data = intent.getStringExtra("MyData");*/
            mServiceId = intent.getStringExtra("ServiceId");
            mJobId = intent.getStringExtra("JobId");
            mWorkOrderNo = intent.getStringExtra("WorkOrderNo");
            mContractNo = intent.getStringExtra("ContractNo");
            msetCustomerName = intent.getStringExtra("setCustomerName");
            mScheduledDate = intent.getStringExtra("ScheduledDate");
            mActualStartTime = intent.getStringExtra("ActualStartTime");
            mActualEndTime = intent.getStringExtra("ActualEndTime");
            mTeam = intent.getStringExtra("Team");
            mTeamMember = intent.getStringExtra("TeamMember");
            mPestType = intent.getStringExtra("PestType");
            mJobPerformedBy = intent.getStringExtra("JobPerformedBy");
            mLocation = intent.getStringExtra("Location");
            mLatLong = intent.getStringExtra("LatLong");
            msetTypes = intent.getStringExtra("setTypes");
            mLatitude = intent.getStringExtra("Latitude");
            mLongitube = intent.getStringExtra("Longitube");

            new getInitialise().execute();

        }


    }

    /**
     * Gets request body.
     *
     * @return the request body
     */
    public static RequestBody getRequestBody(JSONObject objt) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), objt.toString());
    }


    //Initialise details n get data
    class getInitialise extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {

        }

        protected String doInBackground(Object... parametros) {

            try {
                // set  job details
                AppPreferences _appPrefs = new AppPreferences(getApplicationContext());

                if (_appPrefs.getSERVICEID().contains("ADHOC_")) {
                    String response = _appPrefs.getAdhocJson();
                    Gson gson = new Gson();
                    //adhocRequest = gson.fromJson(response, AdhocRequest.class);

                    adhocRequest = MasterDbLists.GetAhocData(_appPrefs.getSERVICEID());


                } else {
                    // set  job details
                    mList = MasterDbLists.getServiceDetailsfromDb(mServiceId.toString());
                    AppLogger.verbose("mList ", "" + mList.size());

                }

                arr_photoAFTER = MasterDbLists.getPhotoRemarksDetails("AFTER", mServiceId);
                arr_photoBEFORE = MasterDbLists.getPhotoRemarksDetails("BEFORE", mServiceId);

                arr_materialedetail = MasterDbLists.getServiceMaterialFromDB(mServiceId, "MATERIALS");

                //Service Details from db
                arr_servicedetail = MasterDbLists.getServiceMaterialFromDB(mServiceId, "SERVICE");

                try {
                    //Payment Details
                    paymentCaptureRmModel = MasterDbLists.getPaymentSavedDetails(mServiceId.toString());

                    // Get Team Details
                    teamCaptureRmModel = MasterDbLists.getTeamSavedDetails(mServiceId);

                    // GEt Feedback Details
                    feedbackCaptureRmModel = MasterDbLists.getFeedbackSavedDetails(mServiceId);

                    //init();
                    fetchAllDetails();

                    /// ADHOC
                    if (_appPrefs.getSERVICEID().contains("ADHOC_")) {

                        AdhocModel adhocModelRm = new AdhocModel();
                        ArrayList<AdhocModel> adhocModel = new ArrayList<>();
                        adhocModelRm.setCompanyName(adhocRequest.getAdhocdata().get(0).getCompanyName());
                        adhocModelRm.setAddress(adhocRequest.getAdhocdata().get(0).getAddress());
                        adhocModelRm.setPestType(adhocRequest.getAdhocdata().get(0).getPestType());
                        adhocModelRm.setPostal(adhocRequest.getAdhocdata().get(0).getPostal());
                        adhocModelRm.setEmail(adhocRequest.getAdhocdata().get(0).getEmail());
                        adhocModelRm.setLatLong(adhocRequest.getAdhocdata().get(0).getLatLong());
                        adhocModelRm.setLocation(adhocRequest.getAdhocdata().get(0).getLocation());
                        adhocModelRm.setSalesPersonId(adhocRequest.getAdhocdata().get(0).getSalesPerson_id());
                        adhocModel.add(adhocModelRm);


                        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                        Date d = new Date();
                        String dayOfTheWeek = sdf.format(d);


                        final JSONObject object;
                        try{
                            if(adhocRequest.get_id().length() >0){ // Passing _id Primary key used select model class

                                ADHOCUploadRequestWithID adhocUploadRequest = new ADHOCUploadRequestWithID();
                                adhocUploadRequest.set_id(adhocRequest.get_id());
                                adhocUploadRequest.setServiceOn(dayOfTheWeek);
                                adhocUploadRequest.setAssignedTo(adhocRequest.getAssignedTo());
                                adhocUploadRequest.setTeam(adhocRequest.getTeam());
                                adhocUploadRequest.setWorkorderNo(mWorkOrderNo);
                                adhocUploadRequest.setContractOrderNo(mContractNo);
                                adhocUploadRequest.setCustomerName(adhocRequest.getContactPerson());
                                adhocUploadRequest.setLocation(adhocRequest.getAdhocdata().get(0).getLocation());
                                adhocUploadRequest.setStartsat(_appPrefs.getJobStartedTime().toString());
                                adhocUploadRequest.setEndsat(Utils.getCurrentDateTime());
                                adhocUploadRequest.setPesttype(adhocRequest.getPestType());
                                adhocUploadRequest.setServices(SelectedServices);
                                adhocUploadRequest.setTeamLead(adhocRequest.getTeamLead());
                                adhocUploadRequest.setTeamMember(TeamMember);
                                adhocUploadRequest.setTeamRemarks(TeamRemarks);
                                adhocUploadRequest.setTeamSignature(TeamSign);
                                adhocUploadRequest.setStartLocation(_appPrefs.getLOCATION());
                                adhocUploadRequest.setEndLocation("" + mLatitude + "," + mLongitube);
                                adhocUploadRequest.setBreedingFound(Breeding);
                                adhocUploadRequest.setSpecies(Species);
                                adhocUploadRequest.setMistingCariedOutNo(MistingCariedOutNo);
                                adhocUploadRequest.setMistingCariedOut(MistingCariedOut);
                                adhocUploadRequest.setDensity(Density);
                                adhocUploadRequest.setInstar(Instar);
                                adhocUploadRequest.setNoOfCulls(NoOfCulls);
                                adhocUploadRequest.setNoOfBurrows(NoOfBurrows);
                                adhocUploadRequest.setNoOfDead(NoOfDead);
                                adhocUploadRequest.setHabitat(Habitat);
                                adhocUploadRequest.setAction(Action);
                                adhocUploadRequest.setRecommendation(Recommendation);
                                adhocUploadRequest.setSor(sor);
                                adhocUploadRequest.setOthers(Others);
                                adhocUploadRequest.setReason(Reason);
                                adhocUploadRequest.setRating(Reting);
                                adhocUploadRequest.setMaterials(SelectedMaterial);
                                adhocUploadRequest.setCustomerRemarks(CustomerRemarks);
                                adhocUploadRequest.setCustomerSignature(CustomerSignature);
                                adhocUploadRequest.setPaymentmethod(Mode);
                                adhocUploadRequest.setTotalpayment(TotalPay);
                                adhocUploadRequest.setPaymentNotes(note);
                                adhocUploadRequest.setTotalarea(AreaPlanned);
                                adhocUploadRequest.setEmailId(EmailId);
                                adhocUploadRequest.setPaymentRemarks(paymentRemark);
                                adhocUploadRequest.setCustomerCurrentDate("" + Utils.getCurrentTime());
                                adhocUploadRequest.setTotalcompleted(AreaCompleted);
                                adhocUploadRequest.setCreatedBy(_appPrefs.getUserID());
                                adhocUploadRequest.setClientId(""+_appPrefs.getCLIENTID());
                                adhocUploadRequest.setPhotosBeforeArray(arr_photoBeforeRequest);
                                adhocUploadRequest.setPhotosAfterArray(arr_photoAfterRequest);
                                adhocUploadRequest.setServicesArray(arr_serviceRequest);
                                adhocUploadRequest.setMaterialsArray(arr_materialsRequest);

                                adhocUploadRequest.setAdhocdata(adhocModel);
                                final ApiInterface apiService =
                                        ApiClient.getClient().create(ApiInterface.class);
                                Call<Object> call = apiService.ADHOCUploadToServerWithPrimaryKey(adhocUploadRequest);

                                object = new JSONObject(new com.google.gson.Gson().toJson(call.execute().body()));

                                _appPrefs.saveJobStartedTime("" + Utils.getCurrentDateTime());

                                AdhocUploadResultParse(object);

                            }else{

                                final ADHOCUploadRequest adhocUploadRequest = new ADHOCUploadRequest();
                                adhocUploadRequest.setServiceOn(dayOfTheWeek);
                                adhocUploadRequest.setAssignedTo(adhocRequest.getAssignedTo());
                                adhocUploadRequest.setTeam(adhocRequest.getTeam());
                                adhocUploadRequest.setWorkorderNo(mWorkOrderNo);
                                adhocUploadRequest.setContractOrderNo(mContractNo);
                                adhocUploadRequest.setCustomerName(adhocRequest.getContactPerson());
                                adhocUploadRequest.setLocation(adhocRequest.getAdhocdata().get(0).getLocation());
                                adhocUploadRequest.setStartsat(_appPrefs.getJobStartedTime().toString());
                                adhocUploadRequest.setEndsat(Utils.getCurrentDateTime());
                                adhocUploadRequest.setPesttype(adhocRequest.getPestType());
                                adhocUploadRequest.setServices(SelectedServices);
                                adhocUploadRequest.setTeamLead(adhocRequest.getTeamLead());
                                adhocUploadRequest.setTeamMember(TeamMember);
                                adhocUploadRequest.setTeamRemarks(TeamRemarks);
                                adhocUploadRequest.setTeamSignature(TeamSign);
                                adhocUploadRequest.setStartLocation(_appPrefs.getLOCATION());
                                adhocUploadRequest.setEndLocation("" + mLatitude + "," + mLongitube);
                                adhocUploadRequest.setBreedingFound(Breeding);
                                adhocUploadRequest.setSpecies(Species);
                                adhocUploadRequest.setMistingCariedOutNo(MistingCariedOutNo);
                                adhocUploadRequest.setMistingCariedOut(MistingCariedOut);
                                adhocUploadRequest.setDensity(Density);
                                adhocUploadRequest.setInstar(Instar);
                                adhocUploadRequest.setNoOfCulls(NoOfCulls);
                                adhocUploadRequest.setNoOfBurrows(NoOfBurrows);
                                adhocUploadRequest.setNoOfDead(NoOfDead);
                                adhocUploadRequest.setHabitat(Habitat);
                                adhocUploadRequest.setAction(Action);
                                adhocUploadRequest.setRecommendation(Recommendation);
                                adhocUploadRequest.setSor(sor);
                                adhocUploadRequest.setOthers(Others);
                                adhocUploadRequest.setReason(Reason);
                                adhocUploadRequest.setRating(Reting);
                                adhocUploadRequest.setMaterials(SelectedMaterial);
                                adhocUploadRequest.setCustomerRemarks(CustomerRemarks);
                                adhocUploadRequest.setCustomerSignature(CustomerSignature);
                                adhocUploadRequest.setPaymentmethod(Mode);
                                adhocUploadRequest.setTotalpayment(TotalPay);
                                adhocUploadRequest.setPaymentNotes(note);
                                adhocUploadRequest.setTotalarea(AreaPlanned);
                                adhocUploadRequest.setEmailId(EmailId);
                                adhocUploadRequest.setPaymentRemarks(paymentRemark);
                                adhocUploadRequest.setCustomerCurrentDate("" + Utils.getCurrentTime());
                                adhocUploadRequest.setTotalcompleted(AreaCompleted);
                                adhocUploadRequest.setCreatedBy(_appPrefs.getUserID());
                                adhocUploadRequest.setClientId(""+_appPrefs.getCLIENTID());
                                adhocUploadRequest.setPhotosBeforeArray(arr_photoBeforeRequest);
                                adhocUploadRequest.setPhotosAfterArray(arr_photoAfterRequest);
                                adhocUploadRequest.setServicesArray(arr_serviceRequest);
                                adhocUploadRequest.setMaterialsArray(arr_materialsRequest);

                                adhocUploadRequest.setAdhocdata(adhocModel);

                                final ApiInterface apiService =
                                        ApiClient.getClient().create(ApiInterface.class);
                                Call<Object> call = apiService.ADHOCUploadToServerInBackGround(adhocUploadRequest);

                                object = new JSONObject(new com.google.gson.Gson().toJson(call.execute().body()));

                                _appPrefs.saveJobStartedTime("" + Utils.getCurrentDateTime());

                                AdhocUploadResultParse(object);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    // After Noraml Jobs
                    } else {
                        uploadRequest = new UploadRequest();
                        uploadRequest.setServiceId(mList.get(0).get_id());
                        uploadRequest.setTeam(mList.get(0).getTeamdetails().get(0).getTeamName());
                        uploadRequest.setScheduledate(Utils.getRequiredDate(mList.get(0).getCreateDate()));
                        uploadRequest.setWorkorderNo(mWorkOrderNo);
                        uploadRequest.setContractOrderNo(mContractNo);
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
                        uploadRequest.setEndLocation("" + mLatitude + "," + mLongitube);
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
                        uploadRequest.setCustomerCurrentDate("" + Utils.getCurrentTime());
                        uploadRequest.setTotalcompleted(AreaCompleted);

                        uploadRequest.setCreatedBy(_appPrefs.getUserID());
                        uploadRequest.setClientId(mList.get(0).getClient_Id());
                        uploadRequest.setPhotosBeforeArray(arr_photoBeforeRequest);
                        uploadRequest.setPhotosAfterArray(arr_photoAfterRequest);
                        uploadRequest.setServicesArray(arr_serviceRequest);
                        uploadRequest.setMaterialsArray(arr_materialsRequest);

                        try {

                            final ApiInterface apiService =
                                    ApiClient.getClient().create(ApiInterface.class);
                            Call<Object> call = apiService.UploadToServerInBackGround(uploadRequest);

                            final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(call.execute().body()));

                            int mStatusCode = object.getInt("statusCode");

                            if (mStatusCode == 200) {

                                final String message = object.optString("message");

                                _appPrefs.saveJobStartedTime("" + Utils.getCurrentDateTime());
                                if (message.equalsIgnoreCase("Job Already Completed")) {

                                    MasterDbLists.UploadJobStatus(mServiceId, "Completed", "Upload Success");
                                    // MasterDbLists.ClearDbAfterUpload();

                                } else {

                                    if (MasterDbLists.GetLogDuplicateCount(mServiceId) == 0) {

                                        MasterDbLists.AddLog(mJobId, mServiceId, "Completed", Utils.CurrentDate(), Utils.CurrentTime());

                                        // Saves Log details
                                        final Realm realm = Realm.getDefaultInstance(); // opens db
                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                //Create a user if there isn't one
                                                final LogsServiceDetails serviceDetails = realm.createObject(LogsServiceDetails.class); // Create a new String
                                                serviceDetails.setServiceId(mServiceId);
                                                serviceDetails.setWorkOrderNo(mWorkOrderNo);
                                                serviceDetails.setContractNo(mContractNo);
                                                serviceDetails.setCustomerName(msetCustomerName);
                                                serviceDetails.setScheduledDate(mScheduledDate);
                                                serviceDetails.setActualStartTime(mActualStartTime);
                                                serviceDetails.setActualEndTime(mActualEndTime);
                                                serviceDetails.setTeam(mTeam);
                                                serviceDetails.setTeamMember(mTeamMember);
                                                serviceDetails.setPestType(mPestType);
                                                serviceDetails.setJobPerformedBy(mJobPerformedBy);
                                                serviceDetails.setLocation(mLocation);
                                                serviceDetails.setLatLong(mLatLong);
                                                serviceDetails.setTypes(msetTypes);
                                            }
                                        });

                                        MasterDbLists.CopyTotalDetailToLog(mServiceId.toString());
                                    }

                                    // Change Job Status in Api
                                    Call<Object> call2 = apiService.JobStatusUpdate(mJobId, "Completed", _appPrefs.getUserID());
                                    call2.enqueue(new Callback<Object>() {
                                        @Override
                                        public void onResponse(Call<Object> call2, Response<Object> response) {

                                            try {
                                                final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(response.body()));

                                                int mStatusCode = object.getInt("statusCode");

                                                if (mStatusCode == 200) {
                                                    MasterDbLists.UploadJobStatus(mServiceId, "Completed", "Upload Success");
                                                    //  MasterDbLists.ClearDbAfterUpload();

                                                } else {

                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<Object> call2, Throwable t) {

                                        }

                                    });

                                }

                            }


                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }


            } catch (Exception e) {
                Utils.dismissDialog();
                e.printStackTrace();

            }

            return "true";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {


            }
            super.onPostExecute(result);
        }

    }


    public void AdhocUploadResultParse(JSONObject object){

        try {

            int mStatusCode = object.getInt("statusCode");

            if (mStatusCode == 200) {

                final String message = object.optString("message");


                if (message.equalsIgnoreCase("Job Already Completed")) {

                    // MasterDbLists.UploadJobStatus(mServiceId, "Completed", "Upload Success");

                } else {

                    JSONArray data_Objt = object.optJSONArray("data");

                    final JSONObject jsonObject = data_Objt.optJSONObject(0).getJSONObject("data");


                    if (MasterDbLists.GetLogDuplicateCount(jsonObject.optString("ServiceID")) == 0) {

                        MasterDbLists.AddLog(""+jsonObject.optString("id"), jsonObject.optString("ServiceID")+"@@"+mServiceId, "Completed", Utils.CurrentDate(), Utils.CurrentTime());

                        MasterDbLists.UploadAdhocJob(mServiceId,jsonObject.optString("ServiceID"));

                        MasterDbLists.UploadAdhocIdPrimaryKey(mServiceId,jsonObject.optString("id"));

                        // Saves Log details
                        final Realm realm = Realm.getDefaultInstance(); // opens db
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                //Create a user if there isn't one
                                final LogsServiceDetails serviceDetails = realm.createObject(LogsServiceDetails.class); // Create a new String
                                serviceDetails.setServiceId(""+jsonObject.optString("ServiceID"));
                                serviceDetails.setWorkOrderNo(mWorkOrderNo);
                                serviceDetails.setContractNo(mContractNo);
                                serviceDetails.setCustomerName(msetCustomerName);
                                serviceDetails.setScheduledDate(mScheduledDate);
                                serviceDetails.setActualStartTime(mActualStartTime);
                                serviceDetails.setActualEndTime(mActualEndTime);
                                serviceDetails.setTeam(mTeam);
                                serviceDetails.setTeamMember(mTeamMember);
                                serviceDetails.setPestType(mPestType);
                                serviceDetails.setJobPerformedBy(mJobPerformedBy);
                                serviceDetails.setLocation(mLocation);
                                serviceDetails.setLatLong(mLatLong);
                                serviceDetails.setTypes(msetTypes);
                            }
                        });

                        MasterDbLists.CopyTotalDetailToLog(mServiceId.toString());

                    }

                }

            }else{
                Toast.makeText(UploadBgService.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {

            e.printStackTrace();

        }


    }


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
            SelectedMaterial = SelectedMaterial.substring(0, SelectedMaterial.length() - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SelectedServices = SelectedServices.substring(0, SelectedServices.length() - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get Mosquito Details
        ServicesCapturesRmModel MosquitoDetails = new ServicesCapturesRmModel();
        ServicesCapturesRmModel BirDsDetails = new ServicesCapturesRmModel();
        ServicesCapturesRmModel RodentDetails = new ServicesCapturesRmModel();

        for (int i = 0; i < arr_servicedetail.size(); i++) {

            try {
                if (arr_servicedetail.get(i).getPestType().equalsIgnoreCase("BIRDS")) {

                    BirDsDetails = arr_servicedetail.get(i).getServcieCaptures().get(0);
                } else if (arr_servicedetail.get(i).getPestType().equalsIgnoreCase("RODENTS")) {
                    RodentDetails = arr_servicedetail.get(i).getServcieCaptures().get(0);
                } else if (arr_servicedetail.get(i).getPestType().equalsIgnoreCase("MOSQUITOES")) {
                    MosquitoDetails = arr_servicedetail.get(i).getServcieCaptures().get(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (MosquitoDetails != null) {
            Species = MosquitoDetails.getSpeices() == null ? "" : MosquitoDetails.getSpeices();
            MistingCariedOutNo = MosquitoDetails.getMistingCarriedIFNo() == null ? "" : MosquitoDetails.getMistingCarriedIFNo();
            MistingCariedOut = MosquitoDetails.getMistingCarriedOut() == null ? "" : MosquitoDetails.getMistingCarriedOut();
            Density = MosquitoDetails.getDensity() == null ? "" : MosquitoDetails.getDensity();
            Instar = MosquitoDetails.getInstar() == null ? "" : MosquitoDetails.getInstar();
            NoOfBurrows = MosquitoDetails.getNoBorrows() == null ? "" : MosquitoDetails.getNoBorrows();
            NoOfDead = MosquitoDetails.getNoDead() == null ? "" : MosquitoDetails.getNoDead();
            Habitat = MosquitoDetails.getHabitat() == null ? "" : MosquitoDetails.getHabitat();
            Action = MosquitoDetails.getAction() == null ? "" : MosquitoDetails.getAction();
            Recommendation = MosquitoDetails.getRecommendation() == null ? "" : MosquitoDetails.getRecommendation();
            Others = MosquitoDetails.getMistingCarriedOthers() == null ? "" : MosquitoDetails.getMistingCarriedOthers();
            Reason = MosquitoDetails.getReason() == null ? "" : MosquitoDetails.getReason();
            Breeding = MosquitoDetails.getBreeding() == null ? "" : MosquitoDetails.getBreeding();
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


        if (feedbackCaptureRmModel != null) {
            Reting = feedbackCaptureRmModel.getRating();
            EmailId = feedbackCaptureRmModel.getEmailID();
            CustomerRemarks = feedbackCaptureRmModel.getRemarks();
            CustomerSignature = feedbackCaptureRmModel.getCustomerSign();
        }

        if(!mServiceId.contains("ADHOC_")){
            if (mList.get(0).getJobOrdersdetails().size() > 0) {
                StartTime = mList.get(0).getJobOrdersdetails().get(0).getServiceStartDate();
                endTime = mList.get(0).getJobOrdersdetails().get(0).getServiceEndDate();
            }

        }


        if (teamCaptureRmModel != null) {

            TeamMember = teamCaptureRmModel.getTeamMembers();
            //tv_team_members.setText(TeamMember);
            TeamRemarks = teamCaptureRmModel.getRemarks();
            TeamSign = teamCaptureRmModel.getTechSign();
        }


        if (paymentCaptureRmModel != null) {

            Mode = paymentCaptureRmModel.getPaymentMode();
            TotalPay = paymentCaptureRmModel.getTotalPayment();
            note = paymentCaptureRmModel.getPaymentNote_chequeNo();
            AreaPlanned = paymentCaptureRmModel.getTotalAreaPlanned();
            AreaCompleted = paymentCaptureRmModel.getTotalAreaCompleted();
            paymentRemark = paymentCaptureRmModel.getPaymentRemarks();
            sor = paymentCaptureRmModel.getSOR();

        }

        arr_FilePaths.clear();
        if (arr_photoBEFORE.size() > 0) {

            for (int i = 0; i < arr_photoBEFORE.size(); i++) {
                PhotoBeforeRequest photoBeforeRequest = new PhotoBeforeRequest();
                photoBeforeRequest.setPestType(arr_photoBEFORE.get(i).getPestType());
                // photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getRemarks());

                if (arr_photoBEFORE.get(i).getOthers().length() > 0 && arr_photoBEFORE.get(i).getRemarks().length() > 0) {
                    photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getRemarks().toString() + "," + arr_photoBEFORE.get(i).getOthers().toString());


                } else if (arr_photoBEFORE.get(i).getOthers().length() > 0 && arr_photoBEFORE.get(i).getRemarks().length() == 0) {
                    photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getOthers().toString());
                } else if (arr_photoBEFORE.get(i).getOthers().length() == 0 && arr_photoBEFORE.get(i).getRemarks().length() > 0) {
                    photoBeforeRequest.setRemarks(arr_photoBEFORE.get(i).getRemarks().toString());
                } else {
                    photoBeforeRequest.setRemarks("");
                }

                File imgFile = new File(arr_photoBEFORE.get(i).getImgBase64().toString());
                if (imgFile.exists()) {

                    try {
                        Bitmap imgBitmap = Utils.compressImage(arr_photoBEFORE.get(i).getImgBase64().toString());

                        photoBeforeRequest.setSchimgbefore(Utils.convertBitmapToBase64(imgBitmap));

                        arr_photoBeforeRequest.add(photoBeforeRequest);
                        arr_FilePaths.add(arr_photoBEFORE.get(i).getImgBase64().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        }

        if (arr_photoAFTER.size() > 0) {

            for (int i = 0; i < arr_photoAFTER.size(); i++) {

                PhotoAfterRequest photoAfterRequest = new PhotoAfterRequest();
                photoAfterRequest.setPestType(arr_photoAFTER.get(i).getPestType());

                if (arr_photoAFTER.get(i).getOthers().length() > 0 && arr_photoAFTER.get(i).getRemarks().length() > 0) {
                    photoAfterRequest.setRemarks(arr_photoAFTER.get(i).getRemarks().toString() + "," + arr_photoAFTER.get(i).getOthers().toString());

                } else if (arr_photoAFTER.get(i).getOthers().length() > 0 && arr_photoAFTER.get(i).getRemarks().length() == 0) {
                    photoAfterRequest.setRemarks(arr_photoAFTER.get(i).getOthers().toString());

                } else if (arr_photoAFTER.get(i).getOthers().length() == 0 && arr_photoAFTER.get(i).getRemarks().length() > 0) {
                    photoAfterRequest.setRemarks(arr_photoAFTER.get(i).getRemarks().toString());
                } else {
                    photoAfterRequest.setRemarks("");
                }


                File imgFile = new File(arr_photoAFTER.get(i).getImgBase64().toString());
                if (imgFile.exists()) {

                    try {
                        Bitmap imgBitmap = Utils.compressImage(arr_photoAFTER.get(i).getImgBase64().toString());

                        photoAfterRequest.setSchimgafter(Utils.convertBitmapToBase64(imgBitmap));

                        arr_photoAfterRequest.add(photoAfterRequest);

                        arr_FilePaths.add(arr_photoAFTER.get(i).getImgBase64().toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        }


        if (arr_servicedetail.size() > 0) {

            for (int i = 0; i < arr_servicedetail.size(); i++) {


                if (arr_servicedetail.get(i).getSM_remarks().length() != 0 || arr_servicedetail.get(i).getOthers().length() != 0) {

                    ServicesRequest servicesRequest = new ServicesRequest();
                    servicesRequest.setPestType(arr_servicedetail.get(i).getPestType());

                    if (arr_servicedetail.get(i).getOthers().length() > 0 && arr_servicedetail.get(i).getSM_remarks().length() > 0) {
                        servicesRequest.setServices(arr_servicedetail.get(i).getSM_remarks() + "," + arr_servicedetail.get(i).getOthers());
                    } else if (arr_servicedetail.get(i).getOthers().length() == 0 && arr_servicedetail.get(i).getSM_remarks().length() > 0) {
                        servicesRequest.setServices(arr_servicedetail.get(i).getSM_remarks());
                    } else if (arr_servicedetail.get(i).getOthers().length() > 0 && arr_servicedetail.get(i).getSM_remarks().length() == 0) {
                        servicesRequest.setServices(arr_servicedetail.get(i).getOthers());
                    } else {
                        servicesRequest.setServices("");
                    }

                    arr_serviceRequest.add(servicesRequest);
                }

            }
        }

        arr_previewMaterial.clear();

        for (int j = 0; j < arr_materialedetail.size(); j++) {
            String materialOthers = "", materials = "";

            materialOthers = arr_materialedetail.get(j).getOthers();
            if (arr_materialedetail.get(j).getOthers().length() != 0 || arr_materialedetail.get(j).getSM_remarks().length() != 0) {

                for (int i = 0; i < arr_materialedetail.get(j).getMaterialsCaptures().size(); i++) {

                    materials = arr_materialedetail.get(j).getPestType();
                    MaterialPreviewModel materialPreviewModel = new MaterialPreviewModel();
                    materialPreviewModel.setPestType(arr_materialedetail.get(j).getPestType());
                    materialPreviewModel.setMaterials(arr_materialedetail.get(j).getMaterialsCaptures().get(i).getMaterialName());
                    materialPreviewModel.setQuantity(arr_materialedetail.get(j).getMaterialsCaptures().get(i).getQuantity());
                    materialPreviewModel.setUnit(arr_materialedetail.get(j).getMaterialsCaptures().get(i).getUnit());
                    arr_previewMaterial.add(materialPreviewModel);
                }

                if (materialOthers.length() > 0) {
                    MaterialPreviewModel materialPreviewModel = new MaterialPreviewModel();

                    if (materials.length() > 0) {
                        materialPreviewModel.setPestType(materials);
                    } else {
                        materialPreviewModel.setPestType(arr_materialedetail.get(j).getPestType());
                    }

                    materialPreviewModel.setMaterials(materialOthers);
                    materialPreviewModel.setQuantity("");
                    materialPreviewModel.setUnit("");
                    arr_previewMaterial.add(materialPreviewModel);

                }

            }

        }


        if (arr_previewMaterial.size() > 0) {

            for (int i = 0; i < arr_previewMaterial.size(); i++) {
                MaterialsRequest materialsRequest = new MaterialsRequest();
                // if(arr_previewMaterial.get(i).ge)
                materialsRequest.setMaterialName(arr_previewMaterial.get(i).getMaterials());
                materialsRequest.setPestType(arr_previewMaterial.get(i).getPestType());
                materialsRequest.setQuantity(arr_previewMaterial.get(i).getQuantity());
                materialsRequest.setUnit(arr_previewMaterial.get(i).getUnit());
                arr_materialsRequest.add(materialsRequest);
            }

        }


    }


}
