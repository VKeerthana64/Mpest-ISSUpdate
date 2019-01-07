package com.amana.mpest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.amana.mpest.R;
import com.amana.mpest.adapter.FragmentLifecycle;
import com.amana.mpest.components.tokenAutoComplete.ContactsCompletionView;
import com.amana.mpest.components.tokenAutoComplete.Person;
import com.amana.mpest.model.AdhocRequest;
import com.amana.mpest.model.realm.jobdetails.FeedbackCaptureRmModel;
import com.amana.mpest.model.realm.taskdetail.Datum;
import com.amana.mpest.myjob.MyJobActivity;
import com.amana.mpest.utils.AppLogger;
import com.amana.mpest.utils.AppPreferences;
import com.amana.mpest.utils.MasterDbLists;
import com.amana.mpest.utils.Utils;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackFragment extends Fragment implements FragmentLifecycle {
    AppPreferences _appPrefs;
    View myFragmentView;
    Context mcontext;
    String Str_CompleteViewtxt = "", str_shareEmail = "", str_CustomerSignBase64 = "";
    private String TAG = this.getClass().getSimpleName();

    FeedbackCaptureRmModel feedbackCaptureRmModel = new FeedbackCaptureRmModel();
    Datum datum = new Datum();

    @BindView(R.id.proceed_btn)
    Button bn_Proceed;
    @BindView(R.id.clear_btn)
    Button bn_clear;
    @BindView(R.id.input_name)
    EditText edt_name;
    @BindView(R.id.input_date)
    EditText edt_date;
    @BindView(R.id.input_email)
    EditText edt_email;
    @BindView(R.id.ratingBar)
    RatingBar rb_rating;
    @BindView(R.id.input_remarks)
    EditText edt_remarks;
    @BindView(R.id.signature_pad)
    SignaturePad mSignaturePad;

    @BindView(R.id.err_remarks)
    TextInputLayout err_remarks;
    @BindView(R.id.clear_img)
    ImageView img_clear;
    @BindView(R.id.searchView)
    ContactsCompletionView completionView;

    private boolean isSignatured = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, myFragmentView);
        _appPrefs = new AppPreferences(getContext());

        init();

        return myFragmentView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void init() {

        datum = MasterDbLists.GetTeamDetails(_appPrefs.getSERVICEID());
        bn_clear.setEnabled(false); // onstart on app disable clear button
        try {
            if (datum.getAdhocType().length() > 0) {
                edt_name.setText(datum.getAdhocdata().get(0).getCompanyName());
            } else {
                edt_name.setText(datum.getCustomerdetails().get(0).getCompanyName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // mSignaturePad.setEnabled(false);
        try {

            if (datum.getAdhocType().length() > 0) {
                if (datum.getAdhocdata().get(0).getEmail().length() > 0) {
                    completionView.addObject(new Person("", datum.getAdhocdata().get(0).getEmail()), "");
                } else {
                    completionView.clear();
                }

            } else {
                if (datum.getCustomerServicedetails().getsEmail().length() > 0) {
                    completionView.addObject(new Person("", datum.getCustomerServicedetails().getsEmail()), "");
                } else {
                    completionView.clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        completionView.allowDuplicates(false);
        completionView.allowCollapse(false);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {


                err_remarks.setError(null);
                edt_remarks.setEnabled(false);
                rb_rating.setEnabled(false);
                // Toast.makeText(getContext(), "OnStartSigning", Toast.LENGTH_SHORT).show();

               /* if(edt_remarks.getText().toString().length()  == 0){

                    Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "Please enter remarks", Snackbar.LENGTH_LONG).show();

                    mSignaturePad.clear();

                    //Toast.makeText(getContext(),"Please enter remarks",Toast.LENGTH_SHORT).show();
                    err_remarks.setError("Please enter remarks");

                }else{
                    err_remarks.setError(null);

                }*/

                SaveUpdateFeedBackDetail();
                //fetchs feedback details
                //  GetSavedFeedbackDetails();


            }

            @Override
            public void onSigned() {
                isSignatured = true;
                bn_clear.setEnabled(true);

                /*if(edt_remarks.getText().toString().length()  == 0){

                }*/

            }

            @Override
            public void onClear() {

                bn_clear.setEnabled(false);
            }
        });

        bn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isSignatured = false;
                img_clear.setVisibility(View.VISIBLE);
                mSignaturePad.clear();
                mSignaturePad.setEnabled(true);
                edt_remarks.setEnabled(true);
                rb_rating.setEnabled(true);


            }
        });

        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_rating.setRating(0F);

            }
        });


        rb_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // Do what you want
                if (rating == 2.0 || rating == 1.0) {
                    if (edt_remarks.getText().toString().length() == 0) {
                        err_remarks.setError("Please enter remarks");
                        edt_remarks.setEnabled(true);
                    } else {

                        err_remarks.setError(null);
                    }

                } else {

                    err_remarks.setError(null);
                }
                SaveUpdateFeedBackDetail();
            }
        });


        bn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppLogger.verbose(TAG, updateTokenConfirmation());
                AppLogger.verbose(TAG, "" + rb_rating.getRating());
                // ((MyJobActivity) getActivity()).switchFragment(6);


                if (!validate()) {
                    return;
                } else {
                    if(isSignatured == false){
                        Utils.CommanPopup(getActivity(), "Customer signature is required");

                    }else{
                        SaveUpdateFeedBackDetail();
                        //fetchs feedback details
                        GetSavedFeedbackDetails();
                        ((MyJobActivity) getActivity()).switchFragment(6);
                    }

                }

            /*    if (rb_rating.getRating() <= 2.0 && rb_rating.getRating() > 0) {

                    if (edt_remarks.getText().toString().length() == 0) {
                        Toast.makeText(getContext(), "Please enter remarks", Toast.LENGTH_SHORT).show();
                        err_remarks.setError("Please enter remarks");
                    } else {
                        err_remarks.setError(null);
                        SaveUpdateFeedBackDetail();
                        //fetchs feedback details
                        GetSavedFeedbackDetails();
                        ((MyJobActivity) getActivity()).switchFragment(6);

                    }


                } else {
                    err_remarks.setError(null);
                    SaveUpdateFeedBackDetail();
                    //fetchs feedback details
                    GetSavedFeedbackDetails();
                    ((MyJobActivity) getActivity()).switchFragment(6);
                }*/
            }

        });

        //fetchs feedback details
        GetSavedFeedbackDetails();

        String mADOCServiceID = _appPrefs.getSERVICEID().toString();
        if (mADOCServiceID.contains("ADHOC_")) {

            String response = _appPrefs.getAdhocJson();
            Gson gson = new Gson();
            AdhocRequest adhocRequest = new AdhocRequest();
            adhocRequest = gson.fromJson(response, AdhocRequest.class);

            try {

                    edt_name.setText(adhocRequest.getContactPerson());

            }catch (Exception e){
                e.printStackTrace();
            }

            // mSignaturePad.setEnabled(false);
            try {

                  if (adhocRequest.getAdhocdata().get(0).getEmail().length() > 0) {
                        completionView.addObject(new Person("", adhocRequest.getAdhocdata().get(0).getEmail()), "");
                  } else {
                        completionView.clear();
                  }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public boolean validate() {
        boolean valid = true;

        if (str_shareEmail.length() == 0) {
            valid = true;
        }else{

            System.out.println("Email id's   ----: "+str_shareEmail);

            String[] parts = str_shareEmail.split(",");
            boolean mIsNotValidEmail = false;
            for(int i=0;i<parts.length;i++){
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(parts[i]).matches())
                    mIsNotValidEmail = true;
            }

            if (str_shareEmail.isEmpty() || str_shareEmail.equalsIgnoreCase("Email id")){
                // edt_shareEmail.setError("Email is required.");
                completionView.setError("Email is required.");;
                valid = false;
            } else if(mIsNotValidEmail) {
                // edt_shareEmail.setError("Enter valid Email Id.");
                completionView.setError("Enter valid Email Id.");
                valid = false;
            }

        }

        if (rb_rating.getRating() <= 2.0 && rb_rating.getRating() > 0) {

            if (edt_remarks.getText().toString().length() == 0) {
                Toast.makeText(getContext(), "Please enter remarks", Toast.LENGTH_SHORT).show();
                err_remarks.setError("Please enter remarks");
                edt_remarks.setEnabled(true);
                valid = false;
            } else {
                err_remarks.setError(null);
            }

        }

        return valid;
    }

    //Use to get Email id
    private String updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder();
        for (Object token : completionView.getObjects()) {
            if (token.toString().contains(";")) {
                String[] separated = token.toString().split(";");
                for (int k = 0; k < separated.length; k++) {
                    sb.append(separated[k]);
                    sb.append(",");
                }

            } else {
                if (sb.toString().toUpperCase().contains(token.toString().toUpperCase())) {

                    Toast.makeText(getContext(), token.toString() + " already selected.", Toast.LENGTH_LONG).show();

                } else {
                    sb.append(token.toString());
                    sb.append(",");
                }

            }
        }

        Str_CompleteViewtxt = completionView.getText().toString().replace("*Email To: ", "");


        Str_CompleteViewtxt = Str_CompleteViewtxt.replace(",", "").trim();

        if (Str_CompleteViewtxt.length() != 0) {
            sb.append(Str_CompleteViewtxt);
            sb.append(",");
        }

        str_shareEmail = sb.toString();
        if (str_shareEmail.endsWith(",")) {
            str_shareEmail = str_shareEmail.substring(0, str_shareEmail.length() - 1);
        }
        //((TextView)findViewById(R.id.input_email)).setText(sb);
        return str_shareEmail;
    }

    // Save/update Team Details
    public void SaveUpdateFeedBackDetail() {

        try {
            if (bn_clear.isEnabled()) {
                str_CustomerSignBase64 = Utils.convertBitmapToBase64(mSignaturePad.getSignatureBitmap());
            } else {
                str_CustomerSignBase64 = "";
            }


            MasterDbLists.saveUpdateFeedbackDetailsInDb(getContext(), _appPrefs.getSERVICEID(), edt_name.getText().toString(), updateTokenConfirmation(), String.valueOf(rb_rating.getRating()), edt_remarks.getText().toString().trim(), Utils.convertBitmapToBase64(mSignaturePad.getSignatureBitmap()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Gets Feedback Details and Set in fields and validation
    private void GetSavedFeedbackDetails() {

        feedbackCaptureRmModel = MasterDbLists.getFeedbackSavedDetails(_appPrefs.getSERVICEID());

        if (feedbackCaptureRmModel != null) {


            if (feedbackCaptureRmModel.getRemarks().length() > 0) {
                edt_remarks.setText(feedbackCaptureRmModel.getRemarks());
                edt_remarks.setEnabled(false);
                err_remarks.setError(null);
            }

            edt_remarks.setEnabled(false);

            if (feedbackCaptureRmModel.getEmailID().length() > 0) {
                completionView.clear();
                completionView.addObject(new Person("", feedbackCaptureRmModel.getEmailID()), "");

            }

            rb_rating.setEnabled(false);
            img_clear.setVisibility(View.GONE);

            if (Double.parseDouble(feedbackCaptureRmModel.getRating()) > 0) {
                rb_rating.setRating(Float.parseFloat(feedbackCaptureRmModel.getRating()));
                img_clear.setVisibility(View.GONE);
                rb_rating.setEnabled(false);

            }

            if (feedbackCaptureRmModel.getCustomerSign().length() > 0) {
                mSignaturePad.setSignatureBitmap(Utils.convertBase64toBitmap(feedbackCaptureRmModel.getCustomerSign()));
                bn_clear.setEnabled(true);
            }

            mSignaturePad.setEnabled(false);
            bn_clear.setEnabled(true);

        } else {
            // no data

            edt_remarks.setText("");
            edt_remarks.setEnabled(true);
            rb_rating.setEnabled(true);
            rb_rating.setRating(0F);
            img_clear.setVisibility(View.VISIBLE);
            edt_remarks.setText("");
            mSignaturePad.clear();
            bn_clear.setEnabled(false);

        }

    }

    @Override
    public void onPauseFragment() {
        AppLogger.verbose(TAG, "onPauseFragment");
        try {
            SaveUpdateFeedBackDetail();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onResumeFragment() {

    }
}
