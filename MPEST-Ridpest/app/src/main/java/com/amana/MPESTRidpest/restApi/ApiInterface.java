package com.amana.MPESTRidpest.restApi;

import com.amana.MPESTRidpest.model.ADHOCUploadRequest;
import com.amana.MPESTRidpest.model.ADHOCUploadRequestWithID;
import com.amana.MPESTRidpest.model.AdhocRequest;
import com.amana.MPESTRidpest.model.UploadRequest;
import com.amana.MPESTRidpest.model.checkinout.CheckingRequest;
import com.amana.MPESTRidpest.model.checkinout.CheckingResponse;
import com.amana.MPESTRidpest.model.comments.CommetsResponse;
import com.amana.MPESTRidpest.model.customerlist.CustomersListResponse;
import com.amana.MPESTRidpest.model.login.LoginApiRequest;
import com.amana.MPESTRidpest.model.login.LoginApiResponse;
import com.amana.MPESTRidpest.model.masterdetails.MaterialData;
import com.amana.MPESTRidpest.model.masterdetails.PaymentModeData;
import com.amana.MPESTRidpest.model.masterdetails.ServiceTypeData;
import com.amana.MPESTRidpest.model.masterdetails.SettingRemarksData;
import com.amana.MPESTRidpest.model.masterdetails.TeamData;
import com.amana.MPESTRidpest.model.pesttype.PesttypeResponse;
import com.amana.MPESTRidpest.model.schedule.ScheduleResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    //Global URl
    //UAT
    String BASE_URL = "http://www.amanaasia.net:84/";// UAT
    String REPORT_URL = "http://www.amanaasia.net:8080/Ridpest.Reports/Reports/CompletedEmailServiceReport.aspx?ID="; //' + SERVICE_ID + '&Type=Download';

    // String REPORT_URL = "http://www.amanaasia.net:8080/MPESTRidpest.Reports/Reports/";

    //PRoducion
    //String BASE_URL = "http://104.215.149.183:84/";// Production
    // String REPORT_URL = "http://104.215.149.183:8080/MPEST.Reports/Reports/CompletedEmailServiceReport.aspx?ID=";//' + SERVICE_ID + '&Type=Download';

    //String REPORT_URL = "http://104.215.149.183:8080/MPEST.Reports/Reports/";

    String POST_SIGNIN = "login";
    String POST_CHECKING = "attendance/updatepunch";
    String POST_MATERIALS = "materials";
    String POST_SERVICETYPELIST = "servicetypelist";
    String POST_SETTINGS_REMARKS = "settings/Remarks";
    String POST_SETTINGS_PAYMENTMODE = "settings/PaymentMode";
    String POST_TEAM = "team";
    String POST_SCHEDULER = "scheduler/getforMobile";  //  getScheduledJobs
    String POST_SCHEDULERFILTER = "scheduler/getOutstandingJobs";

    String POST_GETCALENDERLIST = "scheduler/getJobsForCalendarView";
    //String POST_UPLOAD_SERVER = "tabletschedulerrecord/add";
    String POST_UPLOAD_SERVER = "tabletschedulerrecord/uploadAll";
    String POST_ADHOC_UPLOAD_SERVER = "tabletschedulerrecord/Insertadhocjob";

    String POST_STATUSUPDATE = "scheduler/Statusupdate";
    String POST_PHOTOAFTER = "schedulerpicafter/add";
    String POST_SERVICES = "tabletlighttracklist/add";
    String POST_MATERIALUPLOAD = "tabletMaterials/add";
    String POST_CUSTOMERLIST = "scheduler/getLast10";
    String POST_ADHOC = "customer/addfrommobile";
    String POST_GETALLCOMMENTS = "comment/getByRequestID";
    String POST_ADDCOMMENT = "comment/add";
    String POST_PESTTYPE = "pesttype";

    @POST(BASE_URL + POST_SIGNIN)
    Call<LoginApiResponse> signIN(@Body LoginApiRequest loginApiRequest);


    @POST(BASE_URL + POST_CHECKING)
    Call<CheckingResponse> checkingIn_out(@Body CheckingRequest checkingRequest);

    @FormUrlEncoded
    @POST(BASE_URL + POST_MATERIALS)
    Call<MaterialData> getMaterials(@Field("Client_Id") String Client_Id);

    @FormUrlEncoded
    @POST(BASE_URL + POST_PESTTYPE)
    Call<PesttypeResponse> getPestType(@Field("Client_Id") String Client_Id);

    @FormUrlEncoded
    @POST(BASE_URL + POST_SERVICETYPELIST)
    Call<ServiceTypeData> getServiceTypes(@Field("Client_Id") String Client_Id);

    @FormUrlEncoded
    @POST(BASE_URL + POST_SETTINGS_REMARKS)
    Call<SettingRemarksData> getSettingRemarks(@Field("Client_Id") String Client_Id);

    @FormUrlEncoded
    @POST(BASE_URL + POST_SETTINGS_PAYMENTMODE)
    Call<PaymentModeData> getPaymentModes(@Field("Client_Id") String Client_Id);

    @FormUrlEncoded
    @POST(BASE_URL + POST_TEAM)
    Call<TeamData> getTeamList(@Field("Client_Id") String Client_Id);

    //My Task Api
    @FormUrlEncoded
    @POST(BASE_URL + POST_SCHEDULER)
    Call<Object> getMyTaskDetails(@Field("Client_Id") String Client_Id, @Field("EmployeeID") String EmployeeID, @Field("UserID") String UserID);

    @FormUrlEncoded
    @POST(BASE_URL + POST_SCHEDULERFILTER)
    Call<Object> getMyTaskDetailsByFilter(@Field("Client_Id") String Client_Id, @Field("EmployeeID") String EmployeeID, @Field("UserID") String UserID, @Field("FilterDate") String FilterDate);


    @FormUrlEncoded
    @POST(BASE_URL + POST_GETCALENDERLIST)
    Call<ScheduleResponse> getScheduleList(@Field("Client_Id") String Client_Id, @Field("EmployeeID") String EmployeeID, @Field("UserID") String UserID, @Field("FilterDate") String FilterDate);


    @POST(BASE_URL + POST_UPLOAD_SERVER)
    Call<Object> UploadToServer(@Body RequestBody uploadRequest);

    @POST(BASE_URL + POST_UPLOAD_SERVER)
    Call<Object> UploadToServerInBackGround(@Body UploadRequest uploadRequest);

    @POST(BASE_URL + POST_ADHOC_UPLOAD_SERVER)
    Call<Object> ADHOCUploadToServerInBackGround(@Body ADHOCUploadRequest adhocUploadRequest);


    @POST(BASE_URL + POST_ADHOC_UPLOAD_SERVER)
    Call<Object> ADHOCUploadToServerWithPrimaryKey(@Body ADHOCUploadRequestWithID adhocUploadRequest);


    //My Task Api
    @FormUrlEncoded
    @PUT(BASE_URL + POST_STATUSUPDATE)
    Call<Object> JobStatusUpdate(@Field("_id") String _Id, @Field("Status") String mStatus, @Field("UpdatedBy") String mUpdatedBy);

    @FormUrlEncoded
    @POST(BASE_URL + POST_PHOTOAFTER)
    Call<Object> PICAFTER(@Field("ServiceId") String _Id, @Field("schimgafter") String mschimgafter, @Field("PestType") String mUpdatedBy, @Field("Remarks") String Remarks, @Field("schimgbefore") String schimgbefore);


    @FormUrlEncoded
    @POST(BASE_URL + POST_PHOTOAFTER)
    Call<Object> PICBEFORE(@Field("ServiceId") String _Id, @Field("schimgbefore") String mschimgbefore, @Field("PestType") String mUpdatedBy, @Field("Remarks") String Remarks, @Field("schimgafter") String schimgbefore);


    @FormUrlEncoded
    @POST(BASE_URL + POST_SERVICES)
    Call<Object> UploadSERVICES(@Field("ServiceId") String _Id, @Field("PestType") String PestType, @Field("Services") String Services, @Field("Status") String Status, @Field("CreatedBy") String CreatedBy);

    @FormUrlEncoded
    @POST(BASE_URL + POST_MATERIALUPLOAD)
    Call<Object> UploadMATERIALS(@Field("ServiceId") String _Id, @Field("PestType") String PestType, @Field("MaterialName") String MaterialName, @Field("Quantity") String Quantity, @Field("Unit") String Unit, @Field("CreatedBy") String CreatedBy);


    @FormUrlEncoded
    @POST(BASE_URL + POST_CUSTOMERLIST)
    Call<CustomersListResponse> CustomerList(@Field("Customer_Id") String CustomerId);


    @POST(BASE_URL + POST_ADHOC)
    Call<Object> SaveAdhoc(@Body AdhocRequest adhocRequest);

    @FormUrlEncoded
    @POST(BASE_URL + POST_GETALLCOMMENTS)
    Call<CommetsResponse> GetallComments(@Field("RequestID") String RequestID);


    @FormUrlEncoded
    @POST(BASE_URL + POST_ADDCOMMENT)
    Call<Object> AddComments(@Field("TableRowID") String RequestID, @Field("CommentedBy") String CommentedBy, @Field("cmt") String cmt, @Field("CreatedBy") String CreatedBy, @Field("IsActive") String IsActive);

}
