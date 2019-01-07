package com.amana.MpestISS.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.MpestISS.R;
import com.amana.MpestISS.adapter.FragmentLifecycle;
import com.amana.MpestISS.adapter.MaterialQuantityAdapter;
import com.amana.MpestISS.model.AdhocModel;
import com.amana.MpestISS.model.AdhocRequest;
import com.amana.MpestISS.model.realm.jobdetails.MaterialsCapturesRmModel;
import com.amana.MpestISS.model.realm.jobdetails.TeamCaptureRmModel;
import com.amana.MpestISS.model.realm.taskdetail.Datum;
import com.amana.MpestISS.myjob.MyJobActivity;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamsFragment extends Fragment implements FragmentLifecycle {
    AppPreferences _appPrefs;
    View myFragmentView;

    ArrayList<MultiSelectModel> arr_Teams = new ArrayList<>();
    ArrayList<Integer> arr_SelectedList = new ArrayList<>();
    ArrayList<Integer> arr_getselected = new ArrayList<>();
    Datum datum = new Datum();

    TeamCaptureRmModel teamCaptureRmModel = new TeamCaptureRmModel();
    private String TAG = this.getClass().getSimpleName();
    String str_TechSignBase64 = "";
    @BindView(R.id.proceed_btn)
    Button bn_Proceed;
    @BindView(R.id.clear_btn)
    Button bn_clear;
    @BindView(R.id.input_name)
    EditText edt_name;
    @BindView(R.id.input_members)
    EditText edt_teamMembers;
    @BindView(R.id.input_remarks)
    EditText edt_remarks;
    @BindView(R.id.signature_pad)
    SignaturePad mSignaturePad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_teams, container, false);
        ButterKnife.bind(this, myFragmentView);
        _appPrefs = new AppPreferences(getContext());

        init();

        return myFragmentView;
    }


    private void init() {

        bn_clear.setEnabled(false);


        edt_teamMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectTeamsPopup(arr_getselected);
            }
        });


        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                // Toast.makeText(getContext(), "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                bn_clear.setEnabled(true);

            }

            @Override
            public void onClear() {
                bn_clear.setEnabled(false);
            }
        });

        bn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        bn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveUpdateTEAMDetail();// details will be saved n moved to next screen
                GetSavedTeamDetails();
                ((MyJobActivity) getActivity()).switchFragment(5);

            }
        });

        // this method will get the TeamMembers Based on TeamName
        getTeamMemberList(_appPrefs.getSERVICEID().toString());

        GetSavedTeamDetails();

        try {
            edt_name.setText(datum.getTeamdetails().get(0).getTeamLead());
        } catch (Exception e) {
            e.printStackTrace();
        }


        String mADOCServiceID = _appPrefs.getSERVICEID().toString();

        if (mADOCServiceID.contains("ADHOC_")) {

            String response = _appPrefs.getAdhocJson();
            Gson gson = new Gson();
            AdhocRequest adhocRequest = new AdhocRequest();
            adhocRequest = gson.fromJson(response, AdhocRequest.class);

            String mTeamId = adhocRequest.getAssignedTo();

            getAdhocTeamMemberList(mTeamId);

            try {
                edt_name.setText(adhocRequest.getTeam());
                edt_teamMembers.setText(MasterDbLists.getTeamMemberForAdhoc(mTeamId));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        //   edt_teamMembers.setText(datum.getTeamdetails().get(0).getTeamMembers());

    }

    private void getTeamMemberList(String mServiceID) {

        // list
        arr_Teams = MasterDbLists.TeamMemberList(mServiceID);
        arr_getselected.clear();
        for (int i = 0; i < arr_Teams.size(); i++) {
            arr_getselected.add(i);
        }
        datum = MasterDbLists.GetTeamDetails(mServiceID);


    }

    private void getAdhocTeamMemberList(String mTeamID) {

        // list
        arr_Teams = MasterDbLists.TeamAdhocMemberList(mTeamID);
        arr_getselected.clear();
        for (int i = 0; i < arr_Teams.size(); i++) {
            arr_getselected.add(i);
        }

    }


    /**
     * Multiselect Team Member list
     */
    private void multiSelectTeamsPopup(final ArrayList<Integer> selectedArr) {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Teams Members") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                // .setMaxSelectionLimit(arr_services.size())
                .preSelectIDsList(selectedArr)
                .multiSelectList(arr_Teams) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        // dataString ="";
                        String mSelected = "";
                        arr_SelectedList.clear();

                        for (int i = 0; i < selectedIds.size(); i++) {

                            try {
                                mSelected = mSelected + selectedNames.get(i) + ",";
                                arr_SelectedList.add(selectedIds.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                        if (mSelected.length() > 0) {
                            mSelected = mSelected.substring(0, mSelected.length() - 1);
                            edt_teamMembers.setText(mSelected);

                        } else {
                            // edt_teamMembers.setText("");
                        }

                        //Save Team Details
                        SaveUpdateTEAMDetail();
                        GetSavedTeamDetails();

                    }

                    @Override
                    public void onCancel() {
                        // Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getActivity().getSupportFragmentManager(), "multiSelectDialog");
    }

    private void GetSavedTeamDetails() { // mType service or Materials

        teamCaptureRmModel = MasterDbLists.getTeamSavedDetails(_appPrefs.getSERVICEID());

        try {
            if (teamCaptureRmModel.getSelectedPositions().size() > 0) {
                arr_getselected.clear();
                arr_getselected.addAll(teamCaptureRmModel.getSelectedPositions());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (teamCaptureRmModel != null) {

                // arr_getselected.addAll(teamCaptureRmModel.getSelectedPositions());
                edt_remarks.setText(teamCaptureRmModel.getRemarks());

                try {
                    if (teamCaptureRmModel.getTeamMembers().length() > 0) {
                        edt_teamMembers.setText(teamCaptureRmModel.getTeamMembers());
                    } else {
                        edt_teamMembers.setText(datum.getTeamdetails().get(0).getTeamMembers());

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (teamCaptureRmModel.getTechSign().length() > 0) {
                    mSignaturePad.setSignatureBitmap(Utils.convertBase64toBitmap(teamCaptureRmModel.getTechSign()));
                    bn_clear.setEnabled(true);
                } else {
                    mSignaturePad.clear();
                    bn_clear.setEnabled(false);
                }

            } else {
                // no data
                try {
                    edt_remarks.setText("");
                    mSignaturePad.clear();
                    bn_clear.setEnabled(false);
                    try {
                        edt_teamMembers.setText(datum.getTeamdetails().get(0).getTeamMembers());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    edt_remarks.setText("");
                    mSignaturePad.clear();
                    bn_clear.setEnabled(false);



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Save/update Team Details
    public void SaveUpdateTEAMDetail() {

        try {
            if (bn_clear.isEnabled()) {
                str_TechSignBase64 = Utils.convertBitmapToBase64(mSignaturePad.getSignatureBitmap());
            } else {
                str_TechSignBase64 = "";
            }
            MasterDbLists.saveUpdateTeamDetailsInDb(getContext(), _appPrefs.getSERVICEID(), _appPrefs.getTEAMLEAD().toString(), edt_teamMembers.getText().toString(), edt_remarks.getText().toString(), str_TechSignBase64, arr_SelectedList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPauseFragment() {
        AppLogger.verbose(TAG, "onPauseFragment");
        try {
            SaveUpdateTEAMDetail();// details will be saved n moved to next screen
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResumeFragment() {

    }
}
