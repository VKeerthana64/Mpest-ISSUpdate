package com.amana.MpestISS.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.MpestISS.R;
import com.amana.MpestISS.adapter.FragmentLifecycle;
import com.amana.MpestISS.model.PestModel;
import com.amana.MpestISS.model.pesttype.Datum;
import com.amana.MpestISS.model.pesttype.PesttypeResponse;
import com.amana.MpestISS.model.realm.jobdetails.FeedbackCaptureRmModel;
import com.amana.MpestISS.model.realm.jobdetails.PaymentCaptureRmModel;
import com.amana.MpestISS.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.MpestISS.myjob.PreviewActivity;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment implements FragmentLifecycle, DatePickerDialog.OnDateSetListener
{
    AppPreferences _appPrefs;
    View myFragmentView;

    String mDateFilter ="",mServiceDateFilter = "", mCntDateFilter = "", mAdhocDateFilter = "";

    Context mContext;
    ArrayList<MultiSelectModel> arr_payment_mode = new ArrayList<>();
    ArrayList<Integer> arr_SelectedList = new ArrayList<>();
    ArrayList<Integer> arr_getselected = new ArrayList<>();

    PaymentCaptureRmModel paymentCaptureRmModel = new PaymentCaptureRmModel();
    ArrayList<ServiceMaterialRMModel> arr_servicedetail = new ArrayList<>();
    ArrayList<ServiceMaterialRMModel> arr_materialedetail = new ArrayList<>();
    FeedbackCaptureRmModel feedbackCaptureRmModel = new FeedbackCaptureRmModel();

    @BindView(R.id.proceed_btn)
    Button bn_Proceed;
    @BindView(R.id.input_paymentMode)
    EditText edt_paymentMode;
    @BindView(R.id.input_total_payment)
    EditText edt_total_payment;
    @BindView(R.id.input_note_chqno)
    EditText edt_notes_chequeNo;
    @BindView(R.id.input_remarks)
    EditText edt_remarks;
    @BindView(R.id.input_planned_area)
    EditText edt_planned_area;
    @BindView(R.id.input_completed_area)
    EditText edt_completed_area;
    @BindView(R.id.input_sor)
    EditText edt_SOR;
    @BindView(R.id.textinputjob)
    TextInputLayout textinputjob;
    @BindView(R.id.err_pestType)
    TextInputLayout err_pestType;
    @BindView(R.id.pestType_input)
    EditText edt_pestType;

    @BindView(R.id.input_ServicefollowupDate)
    EditText input_ServicefollowupDate;
    @BindView(R.id.ServicepestType_input)
    EditText ServicepestType_input;
    @BindView(R.id.Serviceinput_notes)
    EditText Serviceinput_notes;
    @BindView(R.id.Serviceinput_servicearea)
    EditText Serviceinput_servicearea;
    @BindView(R.id.input_ctnefollowupDate)
    EditText input_ctnefollowupDate;
    @BindView(R.id.ctnepestType_input)
    EditText ctnepestType_input;
    @BindView(R.id.ctneinput_ctnearea)
    EditText ctneinput_ctnearea;
    @BindView(R.id.ctneinput_notes)
    EditText ctneinput_notes;

    @BindView(R.id.input_AdhocfollowupDate)
    EditText input_AdhocfollowupDate;
    @BindView(R.id.AdhocpestType_input)
    EditText AdhocpestType_input;
    @BindView(R.id.Adhocinput_Adhocarea)
    EditText Adhocinput_Adhocarea;
    @BindView(R.id.Adhocinput_notes)
    EditText Adhocinput_notes;

    @BindView(R.id.follwup_chbk)
    CheckBox chbK_followup;
    @BindView(R.id.service_chbk)
    CheckBox service_chbk;
    @BindView(R.id.ctne_chbk)
    CheckBox ctne_chbk;
    @BindView(R.id.adhoc_chbk)
    CheckBox adhoc_chbk;

    @BindView(R.id.lin_laydate)
    LinearLayout lin_laydate;
    @BindView(R.id.lin_Service)
    LinearLayout lin_Service;
    @BindView(R.id.lin_ctne)
    LinearLayout lin_ctne;
    @BindView(R.id.lin_Adhoc)
    LinearLayout lin_Adhoc;

    @BindView(R.id.input_amount)
    EditText input_amount;

    @BindView(R.id.input_followupDate)
    EditText edt_followuUpDate;
    @BindView(R.id.input_servicearea)
    EditText input_servicearea;
    @BindView(R.id.input_notes)
    EditText input_notes;

    ArrayList<MultiSelectModel> arr_PestType = new ArrayList<>();
    ArrayList<PestModel> arr_pest_list = new ArrayList<>();
    private Boolean IsFollowUp = false;
    private Boolean IsServiceCall = false;
    private Boolean IsContinueJob = false;
    private Boolean IsAdhoc = false;
    private String FollowUpDate;
    private ArrayList<Datum> arr_pestTypeList;
    String pestKey = "F";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, myFragmentView);
        _appPrefs = new AppPreferences(getContext());
        mContext = getContext();

        init();

        return myFragmentView;
    }

    private void init() {

        if(_appPrefs.getSERVICEID().contains("ADHOC_"))
        {
            chbK_followup.setVisibility(View.GONE);
        }else{
            chbK_followup.setVisibility(View.VISIBLE);
        }

        //Material Details from db
        arr_materialedetail = MasterDbLists.getServiceMaterialFromDB(_appPrefs.getSERVICEID(), "MATERIALS");

        //Service Details from db
        arr_servicedetail = MasterDbLists.getServiceFromDB(_appPrefs.getSERVICEID(), "SERVICE");


        edt_paymentMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectPaymentModePopup();
            }
        });

        bn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveUpdatePaymentDetail();
                GetSavedPaymentDetails();

                new GetPreview().execute();

            }
        });

        lin_laydate.setVisibility(View.GONE);
        lin_Service.setVisibility(View.GONE);
        lin_ctne.setVisibility(View.GONE);
        lin_Adhoc.setVisibility(View.GONE);
        input_amount.setVisibility(View.GONE);

        edt_pestType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pestKey = "F";
                getPestTypeList();

            }
        });

        ServicepestType_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pestKey = "S";
                getPestTypeList();

            }
        });
        ctnepestType_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pestKey = "C";
                getPestTypeList();

            }
        });
        AdhocpestType_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pestKey = "A";
                getPestTypeList();

            }
        });


        edt_followuUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dateKey = "F";
                FollowUpDatePicker();
            }
        });

        input_ServicefollowupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dateKey = "S";
                FollowUpDatePicker();

            }
        });

        input_ctnefollowupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dateKey = "C";
                FollowUpDatePicker();
            }
        });

        input_AdhocfollowupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dateKey = "A";
                FollowUpDatePicker();
            }
        });

        getPaymentMode();
        GetSavedPaymentDetails();

        chbK_followup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("asssssss","xz" + edt_followuUpDate.getText().toString());
                    Log.d("asssssss","xz" + mDateFilter);
                    if(edt_followuUpDate.getText().toString().length() ==0) {
                        Utils.dateKey = "F";
                        FollowUpDatePicker();

                        lin_laydate.setVisibility(View.VISIBLE);
                        IsFollowUp = true;
                    }
                }else{
                    IsFollowUp = false;
                    mDateFilter="";
                    edt_followuUpDate.setText("");
                    edt_pestType.setText("");
                    input_servicearea.setText("Follow up service at");
                    input_notes.setText("");
                    lin_laydate.setVisibility(View.GONE);
                }
            }
        });

        adhoc_chbk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Log.d("asssssss","xz" + input_AdhocfollowupDate.getText().toString());
                    Log.d("asssssss","xz" + mAdhocDateFilter);
                    if(input_AdhocfollowupDate.getText().toString().length() ==0) {
                        IsAdhoc = true;
                        Utils.dateKey = "A";
                        FollowUpDatePicker();
                        lin_Adhoc.setVisibility(View.VISIBLE);
                        input_amount.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    IsAdhoc = false;
                    mAdhocDateFilter="";
                    input_AdhocfollowupDate.setText("");
                    AdhocpestType_input.setText("");
                    Adhocinput_Adhocarea.setText("");
                    Adhocinput_notes.setText("");

                    input_amount.setVisibility(View.GONE);
                    lin_Adhoc.setVisibility(View.GONE);

                }
            }
        });

        ctne_chbk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Log.d("asssssss","xz" + input_ctnefollowupDate.getText().toString());

                    if(input_ctnefollowupDate.getText().toString().length() ==0) {
                        IsContinueJob = true;
                        Utils.dateKey = "C";
                        FollowUpDatePicker();
                        lin_ctne.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    IsContinueJob = false;
                    mCntDateFilter="";
                    input_ctnefollowupDate.setText("");
                    ctnepestType_input.setText("");
                    ctneinput_ctnearea.setText("");
                    ctneinput_notes.setText("");
                    lin_ctne.setVisibility(View.GONE);
                }
            }
        });

        service_chbk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Log.d("asssssss","xz" + input_ServicefollowupDate.getText().toString());

                    if(input_ServicefollowupDate.getText().toString().length() ==0) {
                        IsServiceCall = true;
                        Utils.dateKey = "S";
                        FollowUpDatePicker();
                        lin_Service.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    IsServiceCall = false;
                    mServiceDateFilter="";
                    input_ServicefollowupDate.setText("");
                    ServicepestType_input.setText("");
                    Serviceinput_servicearea.setText("");
                    Serviceinput_notes.setText("");
                    lin_Service.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Log.d("cbdcb","vbn" + Utils.dateKey);
        if(Utils.dateKey.trim().equals("F")) {
            mDateFilter = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
            edt_followuUpDate.setText("" + mDateFilter);
        }
        else if(Utils.dateKey.trim().equals("S"))
        {
            mServiceDateFilter = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
            input_ServicefollowupDate.setText("" + mServiceDateFilter);
        }
        else if(Utils.dateKey.trim().equals("C"))
        {
            mCntDateFilter = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
            input_ctnefollowupDate.setText("" + mCntDateFilter);
        }
        else
        {
            mAdhocDateFilter = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
            input_AdhocfollowupDate.setText("" + mAdhocDateFilter);
        }

    }

    public void getPestTypeList() {

        Utils.showCustomDialog(mContext);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PesttypeResponse> call = apiService.getPestType(_appPrefs.getCLIENTID());
        call.enqueue(new Callback<PesttypeResponse>() {
            @Override
            public void onResponse(Call<PesttypeResponse> call, Response<PesttypeResponse> response) {

                try {
                    if (response.body().getStatusCode() == 200) {

                        arr_pestTypeList = response.body().getData();

                        if (arr_pestTypeList.size() > 0) {

                            for (int i = 0; i < arr_pestTypeList.size(); i++) {

                                arr_PestType.add(new MultiSelectModel(i, arr_pestTypeList.get(i).getPestName()));
                            }
                        }
                        Utils.dismissDialog();


                        multiSelectPestTypePopup();
                    }


                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();
                    AppLogger.verbose("Materials", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<PesttypeResponse> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("Materials", "unable to get details");

            }

        });
    }

    private void multiSelectPestTypePopup() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Pest Types") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)

                .multiSelectList(arr_PestType) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        // dataString ="";
                        String mSelected = "";

                        for (int i = 0; i < selectedIds.size(); i++) {
                            mSelected = mSelected + selectedNames.get(i) + ",";
                        }


                        if (mSelected.length() > 0) {
                            mSelected = mSelected.substring(0, mSelected.length() - 1);

                            if(pestKey.trim().equals("F")) {
                                edt_pestType.setText(mSelected);
                            }
                            else  if(pestKey.trim().equals("S"))
                            {
                                ServicepestType_input.setText(mSelected);
                            }
                            else  if(pestKey.trim().equals("C"))
                            {
                                ctnepestType_input.setText(mSelected);
                            }
                            else
                            {
                                AdhocpestType_input.setText(mSelected);
                            }
                        } else {
                            if(pestKey.trim().equals("F")) {
                                edt_pestType.setText("");
                            }
                            else  if(pestKey.trim().equals("S"))
                            {
                                ServicepestType_input.setText("");
                            }
                            else  if(pestKey.trim().equals("C"))
                            {
                                ctnepestType_input.setText("");
                            }
                            else
                            {
                                AdhocpestType_input.setText("");
                            }
                        }
                    }

                    @Override
                    public void onCancel() {
                        // Utils.showToast(mContext, "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getFragmentManager(), "multiSelectDialog");
    }


    public void FollowUpDatePicker() {
        try {

            Calendar now = Calendar.getInstance();

            String dateInString = Utils.getCurrentTime(); // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(dateInString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            now.setTime(c.getTime());

            com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                    PaymentFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)

            );
            dpd.setTitle("Follow up date");
            //dpd.setMaxDate(now);

            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -3);
            String PreDate = format.format(cal.getTime());

            Calendar endDate = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
            endDate.setTime(sdf2.parse(PreDate));

            dpd.setMinDate(now);

            dpd.setThemeDark(false);
            dpd.vibrate(false);
            dpd.dismissOnPause(false);
            dpd.showYearPickerFirst(false);
            dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2);
            /*  dpd.setAccentColor(Color.parseColor("#F08200"));
             */
            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Get QuoteDetails when Quotation Number is passed
    class GetPreview extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {
            Utils.showCustomDialog(mContext);

        }

        protected String doInBackground(Object... parametros) {

            try {
                //Material Details from db
                arr_materialedetail = MasterDbLists.getServiceMaterialFromDB(_appPrefs.getSERVICEID(), "MATERIALS");

               /* //Service Details from db
                arr_servicedetail = MasterDbLists.getServiceMaterialFromDB(_appPrefs.getSERVICEID(), "SERVICE");
                */

                // GEt Feedback Details
                feedbackCaptureRmModel = MasterDbLists.getFeedbackSavedDetails(_appPrefs.getSERVICEID());
                String Reting = "", EmailId = "", CustomerRemarks = "", CustomerSignature = "";
                if (feedbackCaptureRmModel != null) {

                    CustomerSignature = feedbackCaptureRmModel.getCustomerSign();
                } else {
                    CustomerSignature = "";
                }
                Utils.dismissDialog();
                if (arr_materialedetail.size() == 0) {

                    Utils.CommanPopup(getActivity(), "Please select any one material");
                } else if (CustomerSignature.length() == 0) {
                    Utils.CommanPopup(getActivity(), "Customer signature is required!");
                }
                else
                {
                    Log.d("zxcxzvzxc","zxc" + edt_pestType.getText().toString());
                    Log.d("zxcxzvzxc","zxc" + ServicepestType_input.getText().toString());
                    Log.d("zxcxzvzxc","zxc" + ctnepestType_input.getText().toString());
                    Log.d("zxcxzvzxc","zxc" + AdhocpestType_input.getText().toString());
                    Log.d("zxcxzvzxc1","zxc" + input_servicearea.getText().toString());
                    Log.d("zxcxzvzxc1","zxc" + Serviceinput_servicearea.getText().toString());
                    Log.d("zxcxzvzxc1","zxc" + ctneinput_ctnearea.getText().toString());
                    Log.d("zxcxzvzxc1","zxc" + Adhocinput_Adhocarea.getText().toString());
                    Log.d("zxcxzvzxc","zxc" + chbK_followup.isChecked());
                    Log.d("zxcxzvzxc","zxc" + service_chbk.isChecked());
                    Log.d("zxcxzvzxc","zxc" + ctne_chbk.isChecked());
                    Log.d("zxcxzvzxc","zxc" + adhoc_chbk.isChecked());
                    if((edt_pestType.getText().toString().trim().equals("") || input_servicearea.getText().toString().trim().equals("")) && chbK_followup.isChecked())
                    {
                        Utils.CommanPopup(getActivity(), "Required PestType or ServiceArea cannot be Empty");

                    }
                    else if((ServicepestType_input.getText().toString().trim().equals("") || Serviceinput_servicearea.getText().toString().trim().equals("")) && service_chbk.isChecked())
                    {
                        Utils.CommanPopup(getActivity(), "Required PestType or ServiceArea cannot be Empty");
                    }
                    else if((ctnepestType_input.getText().toString().trim().equals("") || ctneinput_ctnearea.getText().toString().trim().equals("")) && ctne_chbk.isChecked())
                    {
                        Utils.CommanPopup(getActivity(), "Required PestType or ServiceArea cannot be Empty");
                    }
                    else if((AdhocpestType_input.getText().toString().trim().equals("") || Adhocinput_Adhocarea.getText().toString().trim().equals("")) && adhoc_chbk.isChecked())
                    {
                        Utils.CommanPopup(getActivity(), "Required PestType or ServiceArea cannot be Empty");
                    }
                    else
                    {
                       callIntent();
                    }
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


                Utils.dismissDialog();
            }
            super.onPostExecute(result);
        }

    }

    private void callIntent() {
        Intent previewIntent = new Intent(getContext(), PreviewActivity.class);
        //previewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(previewIntent);
        ((Activity) getContext()).finish();
    }


    private void getPaymentMode() {

        arr_payment_mode = MasterDbLists.PaymentModeList();
    }

    /**
     * Multiselect Payment Mode list
     */
    private void multiSelectPaymentModePopup() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Payment Mode") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMaxSelectionLimit(1)
                .preSelectIDsList(arr_SelectedList)
                .multiSelectList(arr_payment_mode) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {

                        String mSelected = "";
                        for (int i = 0; i < selectedIds.size(); i++) {
                            mSelected = mSelected + selectedNames.get(i) + ",";

                        }

                        if (mSelected.length() > 0) {
                            edt_paymentMode.setText(mSelected.substring(0, mSelected.length() - 1));
                        }
                    }

                    @Override
                    public void onCancel() {
                        // Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getActivity().getSupportFragmentManager(), "multiSelectDialog");
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void GetSavedPaymentDetails() { // mType service or Materials

        paymentCaptureRmModel = MasterDbLists.getPaymentSavedDetails(_appPrefs.getSERVICEID());

        if (paymentCaptureRmModel != null) {

            edt_paymentMode.setText(paymentCaptureRmModel.getPaymentMode());
            edt_total_payment.setText(paymentCaptureRmModel.getTotalPayment());
            edt_notes_chequeNo.setText(paymentCaptureRmModel.getPaymentNote_chequeNo());
            edt_remarks.setText(paymentCaptureRmModel.getPaymentRemarks());

            edt_completed_area.setText(paymentCaptureRmModel.getTotalAreaCompleted());
            edt_SOR.setText(paymentCaptureRmModel.getSOR());

            if(paymentCaptureRmModel.getFollowUp()){
                chbK_followup.setChecked(true);
                lin_laydate.setVisibility(View.VISIBLE);

                edt_followuUpDate.setText(""+paymentCaptureRmModel.getFollowUpDate());
                edt_pestType.setText(""+paymentCaptureRmModel.getFollowUpPestType());
                input_servicearea.setText(""+paymentCaptureRmModel.getFollowUpServiceArea());
                input_notes.setText(""+paymentCaptureRmModel.getFollowUpJobNotes());

            }else{
                chbK_followup.setChecked(false);
                lin_laydate.setVisibility(View.GONE);

                edt_followuUpDate.setText("");
                edt_pestType.setText("");
                input_servicearea.setText("Follow up service at");
                input_notes.setText("");
            }

            if(paymentCaptureRmModel.getServiceCall())
            {
                service_chbk.setChecked(true);
                lin_Service.setVisibility(View.VISIBLE);

                input_ServicefollowupDate.setText(""+paymentCaptureRmModel.getServiceFollowUpDate());
                ServicepestType_input.setText(""+paymentCaptureRmModel.getServiceFollowUpPestType());
                Serviceinput_servicearea.setText(""+paymentCaptureRmModel.getServiceFollowUpServiceArea());
                Serviceinput_notes.setText(""+paymentCaptureRmModel.getServiceFollowUpJobNotes());

            }else{
                service_chbk.setChecked(false);
                lin_Service.setVisibility(View.GONE);

                input_ServicefollowupDate.setText("");
                ServicepestType_input.setText("");
                Serviceinput_servicearea.setText("");
                Serviceinput_notes.setText("");
            }

            if(paymentCaptureRmModel.getContinueJob())
            {
                ctne_chbk.setChecked(true);
                lin_ctne.setVisibility(View.VISIBLE);

                input_ctnefollowupDate.setText(""+paymentCaptureRmModel.getCtneFollowUpDate());
                ctnepestType_input.setText(""+paymentCaptureRmModel.getCtneFollowUpPestType());
                ctneinput_ctnearea.setText(""+paymentCaptureRmModel.getCtneFollowUpServiceArea());
                ctneinput_notes.setText(""+paymentCaptureRmModel.getCtneFollowUpJobNotes());
            }
            else
            {
                ctne_chbk.setChecked(false);
                lin_ctne.setVisibility(View.GONE);

                input_ctnefollowupDate.setText("");
                ctnepestType_input.setText("");
                ctneinput_ctnearea.setText("");
                ctneinput_notes.setText("");
            }

            if(paymentCaptureRmModel.getAdhoc())
            {
                adhoc_chbk.setChecked(true);
                input_amount.setText(paymentCaptureRmModel.getAmount());
                input_amount.setVisibility(View.VISIBLE);

                lin_Adhoc.setVisibility(View.VISIBLE);

                input_AdhocfollowupDate.setText(""+paymentCaptureRmModel.getAdhocFollowUpDate());
                AdhocpestType_input.setText(""+paymentCaptureRmModel.getAdhocFollowUpPestType());
                Adhocinput_Adhocarea.setText(""+paymentCaptureRmModel.getAdhocFollowUpServiceArea());
                Adhocinput_notes.setText(""+paymentCaptureRmModel.getAdhocFollowUpJobNotes());
            }
            else
            {
                adhoc_chbk.setChecked(false);
                input_amount.setText("");
                input_amount.setVisibility(View.GONE);

                lin_Adhoc.setVisibility(View.GONE);

                input_AdhocfollowupDate.setText("");
                AdhocpestType_input.setText("");
                Adhocinput_Adhocarea.setText("");
                Adhocinput_notes.setText("");
            }
        } else {
            // no data
            edt_paymentMode.setText("");
            edt_total_payment.setText("");
            edt_notes_chequeNo.setText("");
            edt_remarks.setText("");
            edt_planned_area.setText("0");
            edt_completed_area.setText("");
            edt_SOR.setText("");

        }

    }

    // Save/update Team Details
    public void SaveUpdatePaymentDetail() {

        Log.d("sdfvcvcv","vbn" + chbK_followup.isChecked());
        Log.d("sdfvcvcv","vbn" + service_chbk.isChecked());
        Log.d("sdfvcvcv","vbn" + ctne_chbk.isChecked());
        Log.d("sdfvcvcv","vbn" + adhoc_chbk.isChecked());

        MasterDbLists.saveUpdatePaymentDetailsInDb(getContext(), _appPrefs.getSERVICEID(), edt_paymentMode.getText().toString(), edt_total_payment.getText().toString(), edt_notes_chequeNo.getText().toString(), edt_remarks.getText().toString(), edt_planned_area.getText().toString(), edt_completed_area.getText().toString(), edt_SOR.getText().toString(),chbK_followup.isChecked(), edt_followuUpDate.getText().toString().trim(), edt_pestType.getText().toString(),
                input_servicearea.getText().toString(), input_notes.getText().toString(),service_chbk.isChecked(),ctne_chbk.isChecked(),adhoc_chbk.isChecked(),
                input_amount.getText().toString(), input_ServicefollowupDate.getText().toString().trim(), ServicepestType_input.getText().toString(),
                Serviceinput_servicearea.getText().toString(), Serviceinput_notes.getText().toString(),input_ctnefollowupDate.getText().toString().trim(), ctnepestType_input.getText().toString(),
                ctneinput_ctnearea.getText().toString(), ctneinput_notes.getText().toString(),input_AdhocfollowupDate.getText().toString().trim(), AdhocpestType_input.getText().toString(),
                Adhocinput_Adhocarea.getText().toString(), Adhocinput_notes.getText().toString());

    }


    @Override
    public void onPauseFragment() {
        try {
            SaveUpdatePaymentDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResumeFragment() {

    }

}
