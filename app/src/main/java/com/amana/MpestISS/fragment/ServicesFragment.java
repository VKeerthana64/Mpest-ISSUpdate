package com.amana.MpestISS.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.MpestISS.R;
import com.amana.MpestISS.adapter.FragmentLifecycle;
import com.amana.MpestISS.adapter.PestAdapter;
import com.amana.MpestISS.model.PestModel;
import com.amana.MpestISS.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.MpestISS.model.realm.jobdetails.ServicesCapturesRmModel;
import com.amana.MpestISS.myjob.MyJobActivity;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class ServicesFragment extends Fragment implements FragmentLifecycle,PestAdapter.ItemClickListener {

    AppPreferences _appPrefs;
    View myFragmentView;
    PestAdapter mPestAdapter;

    String str_pestType = "";

    ArrayList<PestModel> mPestList = new ArrayList<>();
    ServiceMaterialRMModel arr_ServiceSelectedList = new ServiceMaterialRMModel();
    ServiceMaterialRMModel arr_findSelectedList = new ServiceMaterialRMModel();
    ServiceMaterialRMModel arr_recomSelectedList = new ServiceMaterialRMModel();
    RealmList<ServicesCapturesRmModel> arrRm_serviceCaptured = new RealmList<>();
    ServicesCapturesRmModel arr_serviceCaptured = new ServicesCapturesRmModel();

    ArrayList<MultiSelectModel> arr_services = new ArrayList<>();
    ArrayList<MultiSelectModel> finding_services = new ArrayList<>();
    ArrayList<MultiSelectModel> recom_services = new ArrayList<>();
    ArrayList<Integer> arr_SelectedList = new ArrayList<>();
    ArrayList<Integer> find_SelectedList = new ArrayList<>();
    ArrayList<Integer> recom_SelectedList = new ArrayList<>();
    ArrayList<Integer> arr_getselected = new ArrayList<>();
    ArrayList<Integer> find_getselected = new ArrayList<>();
    ArrayList<Integer> recomm_getselected = new ArrayList<>();

    String TAG = this.getClass().getSimpleName();

    @BindView(R.id.input_others)
    EditText edt_others;
    @BindView(R.id.materials_input)
    EditText edt_services;
    @BindView(R.id.finding_input)
    EditText edt_finding;
    @BindView(R.id.recommendation_input)
    EditText edt_recommendation;
    @BindView(R.id.pests_rv)
    RecyclerView rv_pestTypes;

    //Birds
    @BindView(R.id.lnr_birds)
    LinearLayout lnr_birds;
    @BindView(R.id.input_culls)
    EditText edt_noculles;

    //Rodent
    @BindView(R.id.lnr_Rodent)
    LinearLayout lnr_Rodent;
    @BindView(R.id.input_borrows)
    EditText edt_noBorrows;
    @BindView(R.id.input_dead)
    EditText edt_nodead;

    // mosquito
    @BindView(R.id.lnr_mosquito)
    LinearLayout lnr_mosquito;
    @BindView(R.id.spnr_habitat)
    MaterialBetterSpinner spnr_habitat;
    @BindView(R.id.spnr_reason)
    MaterialBetterSpinner spnr_reason;
    @BindView(R.id.spnr_Action)
    MaterialBetterSpinner spnr_Action;
    @BindView(R.id.spnr_recommendation)
    MaterialBetterSpinner spnr_recommendation;
    @BindView(R.id.spnr_instar)
    MaterialBetterSpinner spnr_instar;

    @BindView(R.id.lnr_misting_other)
    LinearLayout lnr_misting_other;
    @BindView(R.id.lnr_breeding)
    LinearLayout lnr_breeding;
    @BindView(R.id.rg_breeding)
    RadioGroup radioBreeding;
    RadioButton radioBreedingBtn;
    @BindView(R.id.rg_Species)
    RadioGroup radioSpecies;
    RadioButton radioSpeciesBtn;
    @BindView(R.id.rg_misting)
    RadioGroup radioMisting;
    RadioButton radioMistingBtn;
    @BindView(R.id.rg_misting_ifNo)
    RadioGroup radiomisting_ifNo;
    RadioButton radiomisting_ifNoBtn;

    @BindView(R.id.rbn_breedingYES)
    RadioButton rbn_breedingYES;
    @BindView(R.id.rbn_breedingNO)
    RadioButton rbn_breedingNO;

    @BindView(R.id.rbn_SpecieYES)
    RadioButton rbn_SpecieYES;
    @BindView(R.id.rbn_SpecieNo)
    RadioButton rbn_SpecieNo;

    @BindView(R.id.rbn_mistingYES)
    RadioButton rbn_mistingYES;
    @BindView(R.id.rbn_mistingNo)
    RadioButton rbn_mistingNo;

    @BindView(R.id.rbn_raining)
    RadioButton rbn_raining;
    @BindView(R.id.rbn_cusReject)
    RadioButton rbn_cusReject;
    @BindView(R.id.rbn_others)
    RadioButton rbn_others;

    @BindView(R.id.lnr_misting)
    LinearLayout lnr_misting;
    @BindView(R.id.input_density)
    EditText edt_density;

    @BindView(R.id.input_misting_others)
    EditText edt_misting_others;

    @BindView(R.id.save_btn)
    Button btn_save;
    @BindView(R.id.proceed_btn)
    Button bn_Proceed;

    String[] arr_habitat = {"Discarded receptacles", "Perimeter drains", "Metal pipe", "Metal chamber", "Canvas", "Puddle grounds", "Buffier/Shrubs", "Unused tyres", "Car Parts", "Pallets"};
    String[] arr_reason = {"Water collected", "Water ponding", "May collect water",
            "Uneven gradients", "Cracks & crevices", "Choke by rubbish", "Silted with sand", "Blocked by dry leaves"};
    String[] arr_action = {"AMO applied", "Abate applied", "AMO/Abate applied"};
    String[] arr_recommendation = {"To discard water", "To secure the items", "Clear the choke", "Clear the silted", "Maintain houseKeeping", "To seal crack & crevices", "To discard items"};
    String[] arr_instar = {"1", "2", "3","4","5","Pupa"};

    String str_breeding="NO",str_speiec="",str_misting="", str_misting_ifno="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_services, container, false);
        ButterKnife.bind(this, myFragmentView);
        _appPrefs = new AppPreferences(getContext());
        init();
        return myFragmentView;
    }

    private void init() {

        updatePestType();
        updateMaterialList(str_pestType);
        getMaterialsList("SERVICE", str_pestType);

        lnr_breeding.setVisibility(View.GONE);
        lnr_misting.setVisibility(View.GONE);
        lnr_misting_other.setVisibility(View.GONE);

        edt_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MasterDbLists.UpdateServiceRemarks(_appPrefs.getSERVICEID(), "SERVICE", str_pestType, edt_others.getText().toString()); // insert/update data in Realm
                multiSelectServicePopup(arr_getselected);
            }
        });

        edt_finding.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MasterDbLists.UpdateFindMaterialRemarks(_appPrefs.getSERVICEID(), "FINDING", str_pestType, edt_others.getText().toString()); // insert/update data in Realm

                multiSelectFindingPopup(find_getselected);
                return false;
            }
        });

        edt_recommendation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MasterDbLists.UpdateRecommMaterialRemarks(_appPrefs.getSERVICEID(), "RECOMMENDATION", str_pestType, edt_others.getText().toString()); // insert/update data in Realm
                multiSelectRecomPopup(recomm_getselected);

                return false;
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savefieldDetails(str_pestType);
            }
        });

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arr_habitat);
        spnr_habitat.setAdapter(arrayAdapter1);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arr_reason);
        spnr_reason.setAdapter(arrayAdapter2);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arr_action);
        spnr_Action.setAdapter(arrayAdapter3);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arr_recommendation);
        spnr_recommendation.setAdapter(arrayAdapter4);
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arr_instar);
        spnr_instar.setAdapter(arrayAdapter5);

        radioBreeding.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                if(checkedRadioButton.getText().toString().trim().equalsIgnoreCase("YES")){
                    lnr_breeding.setVisibility(View.VISIBLE);

                }else{
                    lnr_breeding.setVisibility(View.GONE);
                }
                str_breeding = checkedRadioButton.getText().toString().trim();


            }
        });

        radioMisting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                if(checkedRadioButton.getText().toString().trim().equalsIgnoreCase("YES")){
                    lnr_misting.setVisibility(View.VISIBLE);

                }else{
                    lnr_misting.setVisibility(View.GONE);
                    lnr_misting_other.setVisibility(View.GONE);
                }
                str_misting = checkedRadioButton.getText().toString().trim();

            }
        });

        radiomisting_ifNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                if(checkedRadioButton.getText().toString().trim().equalsIgnoreCase("others")){
                    lnr_misting_other.setVisibility(View.VISIBLE);

                }else{
                    lnr_misting_other.setVisibility(View.GONE);
                }
                str_misting_ifno = checkedRadioButton.getText().toString().trim();


            }
        });

        radioSpecies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);

                    str_speiec = checkedRadioButton.getText().toString().trim();

            }
        });

        bn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savefieldDetails(str_pestType);
                ((MyJobActivity) getActivity()).switchFragment(2);

            }
        });

    }

    /**
     * Set Pest Type Buttons
     */
    private void updatePestType() {

        String[] arrayString = (_appPrefs.getPESTS().toString()).split(",");
        str_pestType = arrayString[0];
        // pest type Layouts
        showHidePestTypes(str_pestType);
        mPestList.clear();
        for (int i = 0; arrayString.length > i; i++) {
            mPestList.add(new PestModel(0, arrayString[i]));
            MasterDbLists.saveServiceDetailsInDb(getActivity(), arrayString[i], "SERVICE","FINDING","RECOMMENDATION", _appPrefs.getSERVICEID().toString());
        }

        mPestAdapter = new PestAdapter(getContext(), mPestList);
        rv_pestTypes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_pestTypes.setAdapter(mPestAdapter);
        rv_pestTypes.setItemAnimator(new DefaultItemAnimator());
        mPestAdapter.setClickListener(this);
    }

    /**
     * Fetching Material list from Save Db and Populates to recyclerView
     */
    void updateMaterialList(String mPestType) {
        arr_services = MasterDbLists.getAllServicesList(mPestType); // Db Call
        finding_services = MasterDbLists.getAllFindingList(mPestType); // Db Call
        recom_services = MasterDbLists.getAllRecommendationList(mPestType); // Db Call
    }

    /**
     * Fetching Selected Materials from Db
     *
     * @param mType
     * @param mPestType
     */
    private void getMaterialsList(String mType, String mPestType) { // mType service or Materials

        Utils.showCustomDialog(myFragmentView.getContext());

        arr_ServiceSelectedList = MasterDbLists.getServiceDetails(mType, mPestType, _appPrefs.getSERVICEID());
        arr_findSelectedList = MasterDbLists.getfmDetails("FINDING",mPestType, _appPrefs.getSERVICEID());
       arr_recomSelectedList = MasterDbLists.getRmDetails("RECOMMENDATION",mPestType, _appPrefs.getSERVICEID());

        arr_getselected.clear();
        find_getselected.clear();
        recomm_getselected.clear();

        if (arr_ServiceSelectedList != null) {
            arr_getselected.addAll(arr_ServiceSelectedList.getServiceSelectedPositions());
            find_getselected.addAll(arr_findSelectedList.getFindingPositions());
            recomm_getselected.addAll(arr_recomSelectedList.getRecomPositions());

            edt_services.setText(arr_ServiceSelectedList.getSS_remarks());
            edt_finding.setText(arr_findSelectedList.getFM_remarks());
            edt_recommendation.setText(arr_recomSelectedList.getRM_remarks());
            if (arr_ServiceSelectedList.getOthers().length() > 0) {
                edt_others.setText(arr_ServiceSelectedList.getOthers());
            } else {
                edt_others.setText("");
            }

            try{

                arrRm_serviceCaptured = arr_ServiceSelectedList.getServcieCaptures();

                arr_serviceCaptured = arrRm_serviceCaptured.first();


                if(arr_serviceCaptured != null){
                    edt_noculles.setText(arrRm_serviceCaptured.get(0).getNoCulls());

                    edt_noBorrows.setText(arrRm_serviceCaptured.get(0).getNoBorrows());
                    edt_nodead.setText(arrRm_serviceCaptured.get(0).getNoDead());
                    spnr_habitat.setText(arrRm_serviceCaptured.get(0).getHabitat());
                    spnr_reason.setText(arrRm_serviceCaptured.get(0).getReason());
                    spnr_Action.setText(arrRm_serviceCaptured.get(0).getAction());
                    spnr_recommendation.setText(arrRm_serviceCaptured.get(0).getRecommendation());
                    edt_density.setText(arrRm_serviceCaptured.get(0).getDensity());
                    spnr_instar.setText(arrRm_serviceCaptured.get(0).getInstar());


                    if(arrRm_serviceCaptured.get(0).getBreeding().length() >0){

                        if(arrRm_serviceCaptured.get(0).getBreeding().equalsIgnoreCase("YES")){
                            rbn_breedingYES.setChecked(true);
                            rbn_breedingNO.setChecked(false);
                        }else{
                            rbn_breedingNO.setChecked(true);
                            rbn_breedingYES.setChecked(false);
                        }

                    }

                    if(arrRm_serviceCaptured.get(0).getSpeices().length() >0){

                        if(arrRm_serviceCaptured.get(0).getSpeices().equalsIgnoreCase("AEDES")){
                            rbn_SpecieYES.setChecked(true);
                            rbn_SpecieNo.setChecked(false);
                        }else if(arrRm_serviceCaptured.get(0).getSpeices().equalsIgnoreCase("CULEX")){
                            rbn_SpecieNo.setChecked(true);
                            rbn_SpecieYES.setChecked(false);
                        }
                    }


                    if(arrRm_serviceCaptured.get(0).getMistingCarriedOut().length() >0){

                        if(arrRm_serviceCaptured.get(0).getMistingCarriedOut().equalsIgnoreCase("YES")){
                            rbn_mistingYES.setChecked(true);
                            rbn_mistingNo.setChecked(false);
                        }else if(arrRm_serviceCaptured.get(0).getMistingCarriedOut().equalsIgnoreCase("NO")){
                            rbn_mistingNo.setChecked(true);
                            rbn_mistingYES.setChecked(false);
                        }
                    }


                    if(arrRm_serviceCaptured.get(0).getMistingCarriedIFNo().length() >0){

                        if(arrRm_serviceCaptured.get(0).getMistingCarriedIFNo().equalsIgnoreCase("Due to raining")){

                            rbn_raining.setChecked(true);
                            rbn_cusReject.setChecked(false);
                            rbn_others.setChecked(false);

                        }else if(arrRm_serviceCaptured.get(0).getMistingCarriedIFNo().equalsIgnoreCase("Customer reject")){

                            rbn_raining.setChecked(false);
                            rbn_cusReject.setChecked(true);
                            rbn_others.setChecked(false);

                        }else if(arrRm_serviceCaptured.get(0).getMistingCarriedIFNo().equalsIgnoreCase("Others")){

                            rbn_raining.setChecked(false);
                            rbn_cusReject.setChecked(false);
                            rbn_others.setChecked(true);

                            edt_misting_others.setVisibility(View.VISIBLE);
                            edt_misting_others.setText(arrRm_serviceCaptured.get(0).getMistingCarriedOthers());
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            // no data
            edt_services.setText("");
            edt_finding.setText("");
            edt_recommendation.setText("");
            edt_others.setText("");

        }
        Utils.dismissDialog();
    }

    /**
     * Multiselect Service Popup
     */
    private void multiSelectServicePopup(final ArrayList<Integer> selectedArr) {
        //MultiSelectModel

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Service Type") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                // .setMaxSelectionLimit(arr_services.size())
                .preSelectIDsList(selectedArr)
                .multiSelectList(arr_services) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        // dataString ="";
                        String mSelected = "";
                        arr_SelectedList.clear();
                        for (int i = 0; i < selectedIds.size(); i++) {
                            mSelected = mSelected + selectedNames.get(i) + ",";
                            try {
                                arr_SelectedList.add(selectedIds.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (mSelected.length() > 0) {
                            mSelected = mSelected.substring(0, mSelected.length() - 1);
                            edt_services.setText(mSelected);
                            MasterDbLists.UpdateServiceRemarks(_appPrefs.getSERVICEID(), "SERVICE", str_pestType, mSelected, arr_SelectedList, edt_others.getText().toString());

                        } else {
                            edt_services.setText("");
                        }
                    }

                    @Override
                    public void onCancel() {
                      //  Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getActivity().getSupportFragmentManager(), "multiSelectDialog");
    }

    private void multiSelectFindingPopup(final ArrayList<Integer> selectedArr) {
        //MultiSelectModel

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Finding Type") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                // .setMaxSelectionLimit(finding_services.size())
                .preSelectIDsList(selectedArr)
                .multiSelectList(finding_services) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        // dataString ="";
                        String mSelected = "";
                        find_SelectedList.clear();
                        for (int i = 0; i < selectedIds.size(); i++) {
                            mSelected = mSelected + selectedNames.get(i) + ",";
                            try {
                                find_SelectedList.add(selectedIds.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (mSelected.length() > 0) {
                            mSelected = mSelected.substring(0, mSelected.length() - 1);
                            edt_finding.setText(mSelected);
                            MasterDbLists.UpdateFindMaterialRemarks(_appPrefs.getSERVICEID(), "FINDING", str_pestType, mSelected, find_SelectedList, edt_others.getText().toString());

                        } else {
                            edt_finding.setText("");
                        }
                    }

                    @Override
                    public void onCancel() {
                        //  Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getActivity().getSupportFragmentManager(), "multiSelectDialog");
    }


    private void multiSelectRecomPopup(final ArrayList<Integer> selectedArr) {
        //MultiSelectModel

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Service Type") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                // .setMaxSelectionLimit(arr_services.size())
                .preSelectIDsList(selectedArr)
                .multiSelectList(recom_services) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        // dataString ="";
                        String mSelected = "";
                        recom_SelectedList.clear();
                        for (int i = 0; i < selectedIds.size(); i++) {
                            mSelected = mSelected + selectedNames.get(i) + ",";
                            try {
                                recom_SelectedList.add(selectedIds.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (mSelected.length() > 0) {
                            mSelected = mSelected.substring(0, mSelected.length() - 1);
                            edt_recommendation.setText(mSelected);
                            MasterDbLists.UpdateRecommMaterialRemarks(_appPrefs.getSERVICEID(), "RECOMMENDATION", str_pestType, mSelected, recom_SelectedList,edt_others.getText().toString());

                        } else {
                            edt_recommendation.setText("");
                        }
                    }

                    @Override
                    public void onCancel() {
                        //  Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getActivity().getSupportFragmentManager(), "multiSelectDialog");
    }



    public void savefieldDetails(String str_pestType) {

        ServicesCapturesRmModel servicesCapturesRmModel = new ServicesCapturesRmModel();
        servicesCapturesRmModel.setId((int) System.currentTimeMillis());
        servicesCapturesRmModel.setNoCulls("" + edt_noculles.getText().toString());
        servicesCapturesRmModel.setNoBorrows("" + edt_noBorrows.getText());
        servicesCapturesRmModel.setNoDead("" + edt_nodead.getText());
        servicesCapturesRmModel.setHabitat("" + spnr_habitat.getText());
        servicesCapturesRmModel.setReason("" + spnr_reason.getText());
        servicesCapturesRmModel.setAction("" + spnr_Action.getText());
        servicesCapturesRmModel.setRecommendation("" + spnr_recommendation.getText());
        servicesCapturesRmModel.setBreeding("" + str_breeding);
        servicesCapturesRmModel.setSpeices("" + str_speiec);
        servicesCapturesRmModel.setDensity("" + edt_density.getText());
        servicesCapturesRmModel.setInstar("" + spnr_instar.getText());
        servicesCapturesRmModel.setMistingCarriedOut("" + str_misting);
        servicesCapturesRmModel.setMistingCarriedIFNo("" + str_misting_ifno);
        servicesCapturesRmModel.setMistingCarriedOthers("" + edt_misting_others.getText());

        ArrayList<ServicesCapturesRmModel> servicesCapturesRmModels = new ArrayList<>();
        servicesCapturesRmModels.clear();
      /*  if (str_pestType.equalsIgnoreCase("BIRDS")) {
        } else if (str_pestType.equalsIgnoreCase("RODENTS")) {
        } else if (str_pestType.equalsIgnoreCase("MOSQUITOES")) {
        }*/

        servicesCapturesRmModels.add(servicesCapturesRmModel);


        MasterDbLists.UpdateServiceRemarks(_appPrefs.getSERVICEID(), "SERVICE", str_pestType, servicesCapturesRmModels);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // this method clears the previous selection list
    private void clearOldSelection() {
        for (PestModel model : mPestList) {
            model.setSelectedPosition(-1);
        }
    }

    @Override
    public void onPestItemClick(View view, int position) {

        // Before Pest Type Change add others and extra fields in db
        MasterDbLists.UpdateServiceRemarks(_appPrefs.getSERVICEID(), "SERVICE", str_pestType, edt_others.getText().toString());
        MasterDbLists.UpdateFindMaterialRemarks(_appPrefs.getSERVICEID(), "FINDING", str_pestType, edt_others.getText().toString()); // insert/update data in Realm
        MasterDbLists.UpdateRecommMaterialRemarks(_appPrefs.getSERVICEID(), "RECOMMENDATION", str_pestType, edt_others.getText().toString()); // insert/update data in Realm

        savefieldDetails(str_pestType);

        AppLogger.info(TAG, "PestType " + mPestAdapter.getItem(position).getTitle() + ", position " + position);

        str_pestType = mPestAdapter.getItem(position).getTitle().toString().trim();

        // after pest change get Materials
        getMaterialsList("SERVICE", str_pestType);

        arr_services = MasterDbLists.getAllServicesList(str_pestType); // Db Call
        finding_services = MasterDbLists.getAllFindingList(str_pestType); // Db Call
        recom_services = MasterDbLists.getAllRecommendationList(str_pestType); // Db Call

        clearOldSelection();
        mPestList.get(position).setSelectedPosition(position);
        mPestAdapter.notifyDataSetChanged();

        // Pest Type Layouts
        showHidePestTypes(str_pestType);
    }

    /**
     * this method shows/Hide layout based on pest types
     *
     * @param mPestType
     */
    public void showHidePestTypes(String mPestType) {
        if (mPestType.equalsIgnoreCase("BIRDS")) {
            lnr_birds.setVisibility(View.VISIBLE);
            lnr_mosquito.setVisibility(View.GONE);
            lnr_Rodent.setVisibility(View.GONE);
            btn_save.setVisibility(View.GONE);
        } else if (mPestType.equalsIgnoreCase("RODENTS")) {
            lnr_mosquito.setVisibility(View.GONE);
            lnr_Rodent.setVisibility(View.VISIBLE);
            lnr_birds.setVisibility(View.GONE);
            btn_save.setVisibility(View.GONE);
        } else if (mPestType.equalsIgnoreCase("MOSQUITOES")) {
            lnr_mosquito.setVisibility(View.VISIBLE);
            lnr_Rodent.setVisibility(View.GONE);
            lnr_birds.setVisibility(View.GONE);
            btn_save.setVisibility(View.GONE);
        } else {
            lnr_mosquito.setVisibility(View.GONE);
            lnr_Rodent.setVisibility(View.GONE);
            lnr_birds.setVisibility(View.GONE);
            btn_save.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPauseFragment() {
        Log.i(TAG, "onPauseFragment()");
        try{
            MasterDbLists.UpdateServiceRemarks(_appPrefs.getSERVICEID(), "SERVICE", str_pestType, edt_others.getText().toString());
            MasterDbLists.UpdateFindMaterialRemarks(_appPrefs.getSERVICEID(), "FINDING", str_pestType, edt_others.getText().toString()); // insert/update data in Realm
            MasterDbLists.UpdateRecommMaterialRemarks(_appPrefs.getSERVICEID(), "RECOMMENDATION", str_pestType, edt_others.getText().toString()); // insert/update data in Realm

            savefieldDetails(str_pestType);
        }catch (Exception e){
            e.printStackTrace();
        }

       // Toast.makeText(getActivity(), "onPauseFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment() {
        Log.i(TAG, "onResumeFragment()");
        //Toast.makeText(getActivity(), "onResumeFragment():" + TAG, Toast.LENGTH_SHORT).show();

    }




}
