package com.amana.MPESTPestoff.chemicals;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.adapter.DecimalDigitsInputFilter;
import com.amana.MPESTPestoff.model.MaterialsCapturesModel;
import com.amana.MPESTPestoff.model.realm.chemicalInventory.ChemicalCaptureRmModel;
import com.amana.MPESTPestoff.model.realm.jobdetails.MaterialsCapturesRmModel;
import com.amana.MPESTPestoff.utils.AppConstants;
import com.amana.MPESTPestoff.utils.AppPreferences;
import com.amana.MPESTPestoff.utils.MasterDbLists;
import com.amana.MPESTPestoff.utils.Utils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class ChemicalOrderActivity extends AppCompatActivity implements ChemicalOrderAdapter.ItemClickListener{

    AppPreferences _appPrefs;
    Context mContext;
    @BindView(R.id.input_chemical)
    EditText edt_chemicals;
    @BindView(R.id.input_baits)
    EditText edt_baits;

    @BindView(R.id.proceed_btn)
    Button btn_submit;

    @BindView(R.id.chemicallist_rv)
    RecyclerView rv_chemicalList;
    @BindView(R.id.lnr_chemicallist)
    LinearLayout lnr_chemicalList;

    @BindView(R.id.Baitslist_rv)
    RecyclerView rv_baitsList;
    @BindView(R.id.lnr_Baitsllist)
    LinearLayout lnr_baitsList;

    String[] arr_units = {"kg","gm","pcs","litres","oz","ml"};

    String mType="";

    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder alertDialogBuilder;

    ChemicalOrderAdapter chemicalOrderAdapter, baitsOrderAdapter;

    ArrayList<ChemicalCaptureRmModel> arrChemicalCaptureRmModels = new ArrayList<>();
    ArrayList<MultiSelectModel> arr_chemicals = new ArrayList<>();
    ArrayList<Integer> arr_SelectedList = new ArrayList<>();

    ArrayList<ChemicalCaptureRmModel> arrBaitsCaptureRmModels = new ArrayList<>();
    ArrayList<MultiSelectModel> arr_baits = new ArrayList<>();
    ArrayList<Integer> arr_SelectedBaitsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_order);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);
        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "Chemical Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

    }

    private void init() {

        Realm.init(this);

        arr_chemicals.clear();
        arr_chemicals.add(new MultiSelectModel(0, "Kill Master (GP)"));
        arr_chemicals.add(new MultiSelectModel(1, "New Hunt (Bees)"));
        arr_chemicals.add(new MultiSelectModel(2, "Able Max (AM MB)"));

        arr_baits.clear();
        arr_baits.add(new MultiSelectModel(0, "Kill Master (GP)"));
        arr_baits.add(new MultiSelectModel(1, "New Hunt (Bees)"));
        arr_baits.add(new MultiSelectModel(2, "Able Max (AM MB)"));

        edt_chemicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mType = "Chemicals";
                multiSelectChemicalPopup(arr_SelectedList);

            }
        });

        edt_baits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mType = "Baits";
                multiSelectBaitsPopup(arr_SelectedBaitsList);
            }
        });


       // loadChemicalOrderDetails("Chemicals");
       // loadChemicalOrderDetails("Baits");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"service need to integrate",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChemicalOrderDetails(String mType) { // mType service or Materials

        Utils.showCustomDialog(mContext);

        ArrayList<ChemicalCaptureRmModel> arrDbChecmicalList = MasterDbLists.getSavedChemicalList(""+mType);


        if (mType.equalsIgnoreCase("Chemicals")){

            if(arrDbChecmicalList.size() > 0) {
                chemicalOrderAdapter = new ChemicalOrderAdapter(mContext, arrDbChecmicalList);
                rv_chemicalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                rv_chemicalList.setAdapter(chemicalOrderAdapter);
                chemicalOrderAdapter.setClickListener(this);
                lnr_chemicalList.setVisibility(View.VISIBLE);
            }else{
                lnr_chemicalList.setVisibility(View.GONE);
            }


        }else {
            if(arrDbChecmicalList.size() > 0) {
                baitsOrderAdapter = new ChemicalOrderAdapter(mContext, arrDbChecmicalList);
                rv_baitsList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                rv_baitsList.setAdapter(baitsOrderAdapter);
                baitsOrderAdapter.setClickListener(this);
                lnr_baitsList.setVisibility(View.VISIBLE);
            }else{
                lnr_baitsList.setVisibility(View.GONE);
            }
        }



        Utils.dismissDialog();

    }

    /**
     * Materials Popup
     */
    private void multiSelectChemicalPopup(final ArrayList<Integer> selectedArr) {
        //MultiSelectModel

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Chemicals") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                // .setMaxSelectionLimit(arr_services.size())
                .preSelectIDsList(selectedArr)
                .multiSelectList(arr_chemicals) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {

                        for (int i = 0; i < selectedIds.size(); i++) {
                            Toast.makeText(ChemicalOrderActivity.this, "Selected Ids : " + selectedIds.get(i) + "\n" +
                                    "Selected Names : " + selectedNames.get(i) + "\n" +
                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();

                            edt_chemicals.setText(dataString);

                        }

                        String[] strChemicals = dataString.toString().split(",");
                        arrChemicalCaptureRmModels.clear();

                        for(int i=0; i<strChemicals.length; i++){

                            Log.v("Testing -- : ",strChemicals[i].trim());

                            ChemicalCaptureRmModel chemicalCaptureRmModel = new ChemicalCaptureRmModel();
                            chemicalCaptureRmModel.setName(strChemicals[i].trim());
                            chemicalCaptureRmModel.setType("Chemicals");
                            arrChemicalCaptureRmModels.add(chemicalCaptureRmModel);

                        }

                        MasterDbLists.SaveChemicalList("Chemicals",arrChemicalCaptureRmModels);

                        loadChemicalOrderDetails("Chemicals");

                    }

                    @Override
                    public void onCancel() {
                        //   Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    /**
     * Materials Popup
     */
    private void multiSelectBaitsPopup(final ArrayList<Integer> selectedArr) {
        //MultiSelectModel

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Baits") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)
                // .setMaxSelectionLimit(arr_services.size())
                .preSelectIDsList(selectedArr)
                .multiSelectList(arr_baits) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {

                        for (int i = 0; i < selectedIds.size(); i++) {
                            Toast.makeText(ChemicalOrderActivity.this, "Selected Ids : " + selectedIds.get(i) + "\n" +
                                    "Selected Names : " + selectedNames.get(i) + "\n" +
                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();

                            edt_baits.setText(dataString);

                        }

                        String[] strChemicals = dataString.toString().split(",");
                        arrBaitsCaptureRmModels.clear();

                        for(int i=0; i<strChemicals.length; i++){

                            Log.v("Testing -- : ",strChemicals[i].trim());

                            ChemicalCaptureRmModel chemicalCaptureRmModel = new ChemicalCaptureRmModel();
                            chemicalCaptureRmModel.setName(strChemicals[i].trim());
                            chemicalCaptureRmModel.setType("Baits");
                            arrBaitsCaptureRmModels.add(chemicalCaptureRmModel);

                        }

                        MasterDbLists.SaveChemicalList("Baits",arrBaitsCaptureRmModels);

                        loadChemicalOrderDetails("Baits");

                    }

                    @Override
                    public void onCancel() {
                        //   Utils.showToast(getActivity(), "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }


    private void popup_QuantityUnit(final int position, final String mType) {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_material_qty, null);
        alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
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

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, arr_units);
        spnr_unit.setAdapter(arrayAdapter1);

        if(mType.equalsIgnoreCase("Chemicals")){
            edt_qty.setText(arrChemicalCaptureRmModels.get(position).getQuantity());
            spnr_unit.setText(arrChemicalCaptureRmModels.get(position).getUnit());
            txt_title.setText(arrChemicalCaptureRmModels.get(position).getName());

        }else{
            edt_qty.setText(arrBaitsCaptureRmModels.get(position).getQuantity());
            spnr_unit.setText(arrBaitsCaptureRmModels.get(position).getUnit());
            txt_title.setText(arrBaitsCaptureRmModels.get(position).getName());

        }

        Button btn_ok = (Button) promptsView.findViewById(R.id.ok_btn);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                editedChemicalFieldSave(position, edt_qty.getText().toString(), spnr_unit.getText().toString(), mType);
            }
        });
        alertDialog.show();

    }

    // Save Materials Quantity and units on edit
    private void editedChemicalFieldSave(int position, String mQty, String mUnit, String mType) {

        if(mType.equalsIgnoreCase("Chemicals")){

            final ArrayList<ChemicalCaptureRmModel> chemicalCaptureRmModels = new ArrayList<>();
            for(ChemicalCaptureRmModel capturesRmModel: arrChemicalCaptureRmModels){
                ChemicalCaptureRmModel chemicalCaptureRmModel = new ChemicalCaptureRmModel();
                chemicalCaptureRmModel.setName(capturesRmModel.getName());
                chemicalCaptureRmModel.setQuantity(capturesRmModel.getQuantity());
                chemicalCaptureRmModel.setUnit(capturesRmModel.getUnit());

                chemicalCaptureRmModels.add(chemicalCaptureRmModel);
            }

            chemicalCaptureRmModels.get(position).setQuantity(mQty);
            chemicalCaptureRmModels.get(position).setUnit(mUnit);

            arrChemicalCaptureRmModels.clear();

            for(ChemicalCaptureRmModel capturesRmModel: chemicalCaptureRmModels){
                ChemicalCaptureRmModel materialsCapturesModel = new ChemicalCaptureRmModel();
                materialsCapturesModel.setName(capturesRmModel.getName());
                materialsCapturesModel.setQuantity(capturesRmModel.getQuantity());
                materialsCapturesModel.setUnit(capturesRmModel.getUnit());

                arrChemicalCaptureRmModels.add(materialsCapturesModel);
            }

            MasterDbLists.SaveChemicalList("Chemicals",arrChemicalCaptureRmModels);
            loadChemicalOrderDetails("Chemicals");

        }else {

            final ArrayList<ChemicalCaptureRmModel> baitsCaptureRmModels = new ArrayList<>();
            for(ChemicalCaptureRmModel capturesRmModel: arrBaitsCaptureRmModels){
                ChemicalCaptureRmModel chemicalCaptureRmModel = new ChemicalCaptureRmModel();
                chemicalCaptureRmModel.setName(capturesRmModel.getName());
                chemicalCaptureRmModel.setQuantity(capturesRmModel.getQuantity());
                chemicalCaptureRmModel.setUnit(capturesRmModel.getUnit());

                baitsCaptureRmModels.add(chemicalCaptureRmModel);
            }

            baitsCaptureRmModels.get(position).setQuantity(mQty);
            baitsCaptureRmModels.get(position).setUnit(mUnit);

            arrBaitsCaptureRmModels.clear();

            for(ChemicalCaptureRmModel capturesRmModel: baitsCaptureRmModels){
                ChemicalCaptureRmModel materialsCapturesModel = new ChemicalCaptureRmModel();
                materialsCapturesModel.setName(capturesRmModel.getName());
                materialsCapturesModel.setQuantity(capturesRmModel.getQuantity());
                materialsCapturesModel.setUnit(capturesRmModel.getUnit());

                arrBaitsCaptureRmModels.add(materialsCapturesModel);
            }

            MasterDbLists.SaveChemicalList("Baits",arrBaitsCaptureRmModels);
            loadChemicalOrderDetails("Baits");
        }

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

    @Override
    public void onItemClick(View view, int position, int ClickType, String mType) {

        popup_QuantityUnit(position,mType);
    }


}

