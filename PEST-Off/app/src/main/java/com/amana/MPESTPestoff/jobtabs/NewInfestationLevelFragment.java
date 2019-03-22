package com.amana.MPESTPestoff.jobtabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.adapter.FragmentLifecycle;
import com.amana.MPESTPestoff.utils.AppPreferences;

import butterknife.ButterKnife;

public class NewInfestationLevelFragment extends Fragment implements FragmentLifecycle {

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
        myFragmentView = inflater.inflate(R.layout.new_fragment_infestation_level, container, false);
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
