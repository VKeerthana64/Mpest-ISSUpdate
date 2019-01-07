package com.amana.MpestISS.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.MpestISS.R;
import com.amana.MpestISS.adapter.FragmentLifecycle;
import com.amana.MpestISS.joblist.JobDetailsActivity;
import com.amana.MpestISS.model.comments.CommetsResponse;
import com.amana.MpestISS.model.realm.jobdetails.FeedbackCaptureRmModel;
import com.amana.MpestISS.model.realm.jobdetails.PaymentCaptureRmModel;
import com.amana.MpestISS.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.MpestISS.model.realm.taskdetail.Adhocdata;
import com.amana.MpestISS.model.realm.taskdetail.Contracterdetail;
import com.amana.MpestISS.model.realm.taskdetail.Customerdetail;
import com.amana.MpestISS.model.realm.taskdetail.Datum;
import com.amana.MpestISS.model.realm.taskdetail.JobOrdersdetail;
import com.amana.MpestISS.model.realm.taskdetail.MyTaskRealm;
import com.amana.MpestISS.model.realm.taskdetail.Teamdetail;
import com.amana.MpestISS.myjob.PreviewActivity;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;

public class PaymentFragment extends Fragment implements FragmentLifecycle, DatePickerDialog.OnDateSetListener{
    AppPreferences _appPrefs;
    View myFragmentView;

    String mDateFilter ="";

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

    @BindView(R.id.follwup_chbk)
    CheckBox chbK_followup;
    @BindView(R.id.input_followupDate)
    EditText edt_followuUpDate;

    private Boolean IsFollowUp = false;
    private String FollowUpDate;

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

        //Material Details from db
        arr_materialedetail = MasterDbLists.getServiceMaterialFromDB(_appPrefs.getSERVICEID(), "MATERIALS");

        //Service Details from db
        arr_servicedetail = MasterDbLists.getServiceMaterialFromDB(_appPrefs.getSERVICEID(), "SERVICE");


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

        edt_followuUpDate.setVisibility(View.GONE);

       chbK_followup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               if(isChecked){
                   if(edt_followuUpDate.getText().toString().length() ==0) {
                       FollowUpDatePicker();
                       edt_followuUpDate.setVisibility(View.VISIBLE);
                       IsFollowUp = true;
                   }
               }else{
                   IsFollowUp = false;
                   mDateFilter="";
                   edt_followuUpDate.setText("");
                   edt_followuUpDate.setVisibility(View.GONE);
               }
           }
       });

        edt_followuUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowUpDatePicker();
            }
        });

        getPaymentMode();
        GetSavedPaymentDetails();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        mDateFilter = dayOfMonth + "-" + (++monthOfYear) + "-" + year;

        //mDateFilter = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        //mDateFilter = Utils.SingaporeCurrentTime(mDateFilter);

        edt_followuUpDate.setText(""+mDateFilter);

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
                } else {
                    Intent previewIntent = new Intent(getContext(), PreviewActivity.class);
                    //previewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(previewIntent);
                    ((Activity) getContext()).finish();

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

                edt_followuUpDate.setVisibility(View.VISIBLE);
                edt_followuUpDate.setText(""+paymentCaptureRmModel.getFollowUpDate());
                chbK_followup.setChecked(true);
            }else{
                chbK_followup.setChecked(false);
                edt_followuUpDate.setVisibility(View.GONE);
                edt_followuUpDate.setText("");
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

        MasterDbLists.saveUpdatePaymentDetailsInDb(getContext(), _appPrefs.getSERVICEID(), edt_paymentMode.getText().toString(), edt_total_payment.getText().toString(), edt_notes_chequeNo.getText().toString(), edt_remarks.getText().toString(), edt_planned_area.getText().toString(), edt_completed_area.getText().toString(), edt_SOR.getText().toString(),IsFollowUp, mDateFilter);

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
