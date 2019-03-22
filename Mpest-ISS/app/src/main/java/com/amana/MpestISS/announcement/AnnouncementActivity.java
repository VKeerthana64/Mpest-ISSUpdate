package com.amana.MpestISS.announcement;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amana.MpestISS.R;
import com.amana.MpestISS.announcement.model.AnnouncementResponse;
import com.amana.MpestISS.announcement.model.Datum;
import com.amana.MpestISS.joblist.MyTaskAdapter;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnnouncementActivity extends AppCompatActivity {

    AppPreferences _appPrefs;
    Context mContext;
    AnnouncementAdapter mListAdapter;
    ArrayList<Datum> mList = new ArrayList<>();

    @BindView(R.id.noData_txt)
    TextView tv_nodata;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab_clear)
    FloatingActionButton fab_sync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "Announcement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
        getAnnouncementList(mContext,_appPrefs.getCLIENTID().toString(),_appPrefs.getUserID().toString());

    }

    public void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });



    }

    /**
     * Pull n push Recycler view Reloader
     */
    void refreshItems() {
        // Load items
        //gets data from local db
        getAnnouncementList(mContext,_appPrefs.getCLIENTID().toString(),_appPrefs.getUserID().toString());
        // Load complete
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Fetching task list from Save Db and Populates to recyclerView
     */
    public void getAnnouncementList(final Context context, String Client_id, String userId) {

        Utils.showCustomDialog(context);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<AnnouncementResponse> call = apiService.getAnnouncement(Client_id,userId);
        call.enqueue(new Callback<AnnouncementResponse>() {
            @Override
            public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {

                try {
                    if (response.body().getStatusCode() == 200) {
                       handleListResponse(response.body().getData());
                    }else{
                        Toast.makeText(mContext,"Something went wrong. Please try again later",Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext,"Something went wrong. Please try again later",Toast.LENGTH_LONG).show();
                }finally {
                    Utils.dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
                Utils.dismissDialog();
                Toast.makeText(mContext,"Unable to get data from server. Please try again later",Toast.LENGTH_LONG).show();

            }

        });
    }

    void handleListResponse(ArrayList<Datum> mList){
        if (mList.size() == 0) { // if no data no data text is set
            mSwipeRefreshLayout.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        } else { // if data available
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
            mListAdapter = new AnnouncementAdapter(mContext, mList);
            mRecyclerView.setAdapter(mListAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
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
        // ((MainActivity) a).performQuoteListClick();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    // Service Calls



}
