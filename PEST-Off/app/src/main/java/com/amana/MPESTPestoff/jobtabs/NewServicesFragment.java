package com.amana.MPESTPestoff.jobtabs;

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
import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.adapter.FragmentLifecycle;
import com.amana.MPESTPestoff.adapter.PestAdapter;
import com.amana.MPESTPestoff.model.PestModel;
import com.amana.MPESTPestoff.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.MPESTPestoff.model.realm.jobdetails.ServicesCapturesRmModel;
import com.amana.MPESTPestoff.myjob.MyJobActivity;
import com.amana.MPESTPestoff.utils.AppLogger;
import com.amana.MPESTPestoff.utils.AppPreferences;
import com.amana.MPESTPestoff.utils.MasterDbLists;
import com.amana.MPESTPestoff.utils.Utils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class NewServicesFragment extends Fragment implements FragmentLifecycle {

    AppPreferences _appPrefs;
    View myFragmentView;

    String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView = inflater.inflate(R.layout.new_fragment_service, container, false);
        ButterKnife.bind(this, myFragmentView);
        _appPrefs = new AppPreferences(getContext());

        return myFragmentView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPauseFragment() {
        Log.i(TAG, "onPauseFragment()");
    }

    @Override
    public void onResumeFragment() {
        Log.i(TAG, "onResumeFragment()");
        //Toast.makeText(getActivity(), "onResumeFragment():" + TAG, Toast.LENGTH_SHORT).show();

    }


}
