package com.amana.CLEANSolutions.fragment;

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
import android.widget.EditText;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.CLEANSolutions.R;
import com.amana.CLEANSolutions.adapter.FragmentLifecycle;
import com.amana.CLEANSolutions.model.comments.CommetsResponse;
import com.amana.CLEANSolutions.model.realm.jobdetails.FeedbackCaptureRmModel;
import com.amana.CLEANSolutions.model.realm.jobdetails.PaymentCaptureRmModel;
import com.amana.CLEANSolutions.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.CLEANSolutions.model.realm.taskdetail.Adhocdata;
import com.amana.CLEANSolutions.model.realm.taskdetail.Contracterdetail;
import com.amana.CLEANSolutions.model.realm.taskdetail.Customerdetail;
import com.amana.CLEANSolutions.model.realm.taskdetail.Datum;
import com.amana.CLEANSolutions.model.realm.taskdetail.JobOrdersdetail;
import com.amana.CLEANSolutions.model.realm.taskdetail.MyTaskRealm;
import com.amana.CLEANSolutions.model.realm.taskdetail.Teamdetail;
import com.amana.CLEANSolutions.myjob.PreviewActivity;
import com.amana.CLEANSolutions.restApi.ApiClient;
import com.amana.CLEANSolutions.restApi.ApiInterface;
import com.amana.CLEANSolutions.utils.AppPreferences;
import com.amana.CLEANSolutions.utils.MasterDbLists;
import com.amana.CLEANSolutions.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;

public class PaymentFragment extends Fragment implements FragmentLifecycle {
    AppPreferences _appPrefs;
    View myFragmentView;

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

        getPaymentMode();
        GetSavedPaymentDetails();
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

        MasterDbLists.saveUpdatePaymentDetailsInDb(getContext(), _appPrefs.getSERVICEID(), edt_paymentMode.getText().toString(), edt_total_payment.getText().toString(), edt_notes_chequeNo.getText().toString(), edt_remarks.getText().toString(), edt_planned_area.getText().toString(), edt_completed_area.getText().toString(), edt_SOR.getText().toString());

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
