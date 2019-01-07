package com.amana.MPESTPestoff.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.adapter.DecimalDigitsInputFilter;
import com.amana.MPESTPestoff.adapter.FragmentLifecycle;
import com.amana.MPESTPestoff.adapter.MaterialQuantityAdapter;
import com.amana.MPESTPestoff.adapter.PestAdapter;
import com.amana.MPESTPestoff.model.MaterialsCapturesModel;
import com.amana.MPESTPestoff.model.PestModel;
import com.amana.MPESTPestoff.model.realm.jobdetails.MaterialsCapturesRmModel;
import com.amana.MPESTPestoff.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.MPESTPestoff.myjob.MyJobActivity;
import com.amana.MPESTPestoff.utils.AppConstants;
import com.amana.MPESTPestoff.utils.AppLogger;
import com.amana.MPESTPestoff.utils.AppPreferences;
import com.amana.MPESTPestoff.utils.MasterDbLists;
import com.amana.MPESTPestoff.utils.Utils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MaterialFragment extends Fragment implements PestAdapter.ItemClickListener, MaterialQuantityAdapter.ItemClickListener,FragmentLifecycle {

    AppPreferences _appPrefs;
    View myFragmentView;
    PestAdapter mPestAdapter;
    MaterialQuantityAdapter materialsListAdapter;

    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder alertDialogBuilder;

    String str_material = "", str_pestType = "";

    ArrayList<PestModel> mPestList = new ArrayList<>();
    ServiceMaterialRMModel arr_ServiceSelectedList = new ServiceMaterialRMModel();

    ArrayList<MultiSelectModel> arr_materials = new ArrayList<>();
    ArrayList<Integer> arr_SelectedList = new ArrayList<>();
    ArrayList<Integer> arr_getselected = new ArrayList<>();
    ArrayList<MaterialsCapturesRmModel> arr_materialsCapturesRmModels = new ArrayList<>();

    String TAG = this.getClass().getSimpleName();
    String mServiceID="";
    boolean isVisible=false;
    String[] arr_units = {"kg","gm","pcs","litres","oz","ml"};

    @BindView(R.id.input_others)
    EditText edt_others;
    @BindView(R.id.materials_input)
    EditText edt_materials;
    @BindView(R.id.pests_rv)
    RecyclerView rv_pestTypes;
    @BindView(R.id.Materiallist_rv)
    RecyclerView rv_materiallist;

    @BindView(R.id.lnr_materiallist)
    LinearLayout lnr_materiallist;
    @BindView(R.id.proceed_btn)
    Button bn_Proceed;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.fragment_materials, container, false);
        ButterKnife.bind(this, myFragmentView);
        _appPrefs = new AppPreferences(getContext());
        init();
        return myFragmentView;
    }

    private void init() {

        updatePestType();
        GetupdateMaterialList(str_pestType);
        GetupdateSelectMaterialsList("MATERIALS", str_pestType);

        mServiceID = _appPrefs.getSERVICEID();
        edt_materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MasterDbLists.UpdateServiceMaterialRemarks(_appPrefs.getSERVICEID(), "MATERIALS", str_pestType, edt_others.getText().toString()); // insert/update data in Realm
                multiSelectServicePopup(arr_getselected);
            }
        });

        bn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Before Pest Type Change add others and extra fields in db
                MasterDbLists.UpdateServiceMaterialRemarks(_appPrefs.getSERVICEID(), "MATERIALS", str_pestType, edt_others.getText().toString());

                ((MyJobActivity) getActivity()).switchFragment(3);

            }
        });


    }


    private void updatePestType() {

        String[] arrayString = (_appPrefs.getPESTS().toString()).split(",");
        str_pestType = arrayString[0];
        mPestList.clear();
        for (int i = 0; arrayString.length > i; i++) {
            mPestList.add(new PestModel(0, arrayString[i]));
            MasterDbLists.saveServiceDetailsInDb(getActivity(), arrayString[i], "MATERIALS", _appPrefs.getSERVICEID().toString());
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
    void GetupdateMaterialList(String mPestType) {
        arr_materials = MasterDbLists.getAllMaterialList(mPestType); // Db Call
    }

    /**
     * Fetching Selected Materials from Db
     *
     * @param mType
     * @param mPestType
     */
    private void GetupdateSelectMaterialsList(String mType, String mPestType) { // mType service or Materials

        Utils.showCustomDialog(myFragmentView.getContext());

        arr_ServiceSelectedList = MasterDbLists.getServiceMaterislDetails(mType, mPestType, _appPrefs.getSERVICEID());
        arr_getselected.clear();
        arr_materialsCapturesRmModels.clear();
        if (arr_ServiceSelectedList != null) {
            arr_materialsCapturesRmModels.addAll(arr_ServiceSelectedList.getMaterialsCaptures());
            arr_getselected.addAll(arr_ServiceSelectedList.getSelectedPositions());
            edt_materials.setText(arr_ServiceSelectedList.getSM_remarks());
            if (arr_ServiceSelectedList.getOthers().length() > 0) {
                edt_others.setText(arr_ServiceSelectedList.getOthers());
            } else {
                edt_others.setText("");
            }

            if(arr_materialsCapturesRmModels.size() > 0){
                materialsListAdapter = new MaterialQuantityAdapter(getActivity(), arr_materialsCapturesRmModels);
                rv_materiallist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                rv_materiallist.setAdapter(materialsListAdapter);
                materialsListAdapter.setClickListener(this);
                lnr_materiallist.setVisibility(View.VISIBLE);
            }else{
                lnr_materiallist.setVisibility(View.GONE);
            }



        } else {
            // no data
            edt_materials.setText("");
            edt_others.setText("");

        }
        Utils.dismissDialog();

    }

    /**
     * Materials Popup
     */
    private void multiSelectServicePopup(final ArrayList<Integer> selectedArr) {
        //MultiSelectModel

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Material Type") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
               // .setMaxSelectionLimit(arr_services.size())
                .preSelectIDsList(selectedArr)
                .multiSelectList(arr_materials) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        // dataString ="";
                        String mSelected = "";
                        arr_SelectedList.clear();
                        arr_materialsCapturesRmModels.clear();
                        for (int i = 0; i < selectedIds.size(); i++) {
                            mSelected = mSelected + selectedNames.get(i) + ",";
                            try{
                                arr_SelectedList.add(selectedIds.get(i));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            MaterialsCapturesRmModel capturesRmModel = new MaterialsCapturesRmModel();
                            capturesRmModel.setMaterialName(selectedNames.get(i));
                           arr_materialsCapturesRmModels.add(capturesRmModel);

                        }
                        // Save materials quantity list
                        savefieldDetails(str_pestType,arr_materialsCapturesRmModels);
                        GetupdateSelectMaterialsList("MATERIALS", str_pestType);

                        if (mSelected.length() > 0) {
                            mSelected = mSelected.substring(0, mSelected.length() - 1);
                            edt_materials.setText(mSelected);
                            MasterDbLists.UpdateServiceMaterialRemarks(_appPrefs.getSERVICEID(),"MATERIALS", str_pestType, mSelected, arr_SelectedList, edt_others.getText().toString());

                        } else {
                            edt_materials.setText("");
                        }
                    }

                    @Override
                    public void onCancel() {
                     //   Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getActivity().getSupportFragmentManager(), "multiSelectDialog");
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


    public void savefieldDetails(String str_pestType, ArrayList<MaterialsCapturesRmModel> materialsCapturesRmModels) {

        MasterDbLists.UpdateMaterialListRemarks(_appPrefs.getSERVICEID(), "MATERIALS", str_pestType, materialsCapturesRmModels);
    }

    @Override
    public void onPestItemClick(View view, int position) {
        // Before Pest Type Change add others and extra fields in db
        MasterDbLists.UpdateServiceMaterialRemarks(_appPrefs.getSERVICEID(), "MATERIALS", str_pestType, edt_others.getText().toString());

        AppLogger.info(TAG, "PestType " + mPestAdapter.getItem(position).getTitle() + ", position " + position);

        str_pestType = mPestAdapter.getItem(position).getTitle().toString().trim();

        GetupdateSelectMaterialsList("MATERIALS", str_pestType);

        arr_materials = MasterDbLists.getAllMaterialList(str_pestType); // Db Call

        clearOldSelection();
        mPestList.get(position).setSelectedPosition(position);
        mPestAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, int position, int ClickType) {

        switch (ClickType) {
            case AppConstants.TYPE_CLCIK_REMARKS:
                AppLogger.info(TAG, "Materials " + materialsListAdapter.getItem(position).getMaterialName() + ", position " + position);

                popup_QuantityUnit(position);

                break;
            case AppConstants.TYPE_CLCIK_DELETE:
                AppLogger.info(TAG, "Materials " + materialsListAdapter.getItem(position).getMaterialName() + ", position " + position);
                popup_QuantityUnit(position);
                break;
        }

    }

    private void popup_QuantityUnit(final int position) {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_material_qty, null);
        alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CircleImageView img_close= (CircleImageView) promptsView
                .findViewById(R.id.close_img);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        final TextView txt_title = (TextView) promptsView.findViewById(R.id.txt_material);
        final EditText edt_qty = (EditText) promptsView.findViewById(R.id.input_remarks);
        edt_qty.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
        final MaterialBetterSpinner spnr_unit = (MaterialBetterSpinner) promptsView.findViewById(R.id.spnr_unit);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arr_units);
        spnr_unit.setAdapter(arrayAdapter1);

        edt_qty.setText(arr_materialsCapturesRmModels.get(position).getQuantity());
        spnr_unit.setText(arr_materialsCapturesRmModels.get(position).getUnit());
        txt_title.setText(arr_materialsCapturesRmModels.get(position).getMaterialName());

        Button btn_ok = (Button) promptsView.findViewById(R.id.ok_btn);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                editedMaterialFieldSave(position, edt_qty.getText().toString(), spnr_unit.getText().toString());
            }
        });
        alertDialog.show();

    }

    // Save Materials Quantity and units on edit
    private void editedMaterialFieldSave(int position, String mQty, String mUnit) {
        final ArrayList<MaterialsCapturesModel> materialsCapturesRmModels = new ArrayList<>();

        for(MaterialsCapturesRmModel capturesRmModel: arr_materialsCapturesRmModels){
            MaterialsCapturesModel materialsCapturesModel = new MaterialsCapturesModel();
            materialsCapturesModel.setMaterialName(capturesRmModel.getMaterialName());
            materialsCapturesModel.setQuantity(capturesRmModel.getQuantity());
            materialsCapturesModel.setUnit(capturesRmModel.getUnit());

            materialsCapturesRmModels.add(materialsCapturesModel);
        }

        materialsCapturesRmModels.get(position).setQuantity(mQty);
        materialsCapturesRmModels.get(position).setUnit(mUnit);

        arr_materialsCapturesRmModels.clear();

        for(MaterialsCapturesModel capturesRmModel: materialsCapturesRmModels){
            MaterialsCapturesRmModel materialsCapturesModel = new MaterialsCapturesRmModel();
            materialsCapturesModel.setMaterialName(capturesRmModel.getMaterialName());
            materialsCapturesModel.setQuantity(capturesRmModel.getQuantity());
            materialsCapturesModel.setUnit(capturesRmModel.getUnit());

            arr_materialsCapturesRmModels.add(materialsCapturesModel);
        }

        // Save materials quantity list
        savefieldDetails(str_pestType,arr_materialsCapturesRmModels);
        GetupdateSelectMaterialsList("MATERIALS", str_pestType);

    }


    @Override
    public void onPauseFragment() {
        AppLogger.verbose(TAG,"onPauseFragment");
        // Before Pest Type Change add others and extra fields in db
        try{
            MasterDbLists.UpdateServiceMaterialRemarks(_appPrefs.getSERVICEID(), "MATERIALS", str_pestType, edt_others.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onResumeFragment() {

    }



}
