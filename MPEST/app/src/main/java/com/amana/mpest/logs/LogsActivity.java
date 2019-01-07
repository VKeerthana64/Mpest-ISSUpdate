package com.amana.mpest.logs;

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

import com.amana.mpest.R;
import com.amana.mpest.model.realm.LogsTable;
import com.amana.mpest.utils.AppPreferences;
import com.amana.mpest.utils.MasterDbLists;
import com.amana.mpest.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LogsActivity extends AppCompatActivity {

    AppPreferences _appPrefs;
    Context mContext;
    LogsAdapter mListAdapter;
    ArrayList<LogsTable> mList;

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
        setContentView(R.layout.activity_logs);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "Mobile Log");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
        getTaskFromDbSetList();

    }

    public void init() {

        fab_sync.setVisibility(View.VISIBLE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });


        fab_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MasterDbLists.DeleteLogDetails();
                getTaskFromDbSetList();
            }
        });
    }

    /**
     * Pull n push Recycler view Reloader
     */
    void refreshItems() {
        // Load items
        //gets data from local db
        getTaskFromDbSetList();
        // Load complete
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Fetching task list from Save Db and Populates to recyclerView
     */
    void getTaskFromDbSetList() {
        mList = MasterDbLists.getAllLogDetails();

        if (mList.size() == 0) { // if no data no data text is set
            mSwipeRefreshLayout.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        } else { // if data available
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
            mListAdapter = new LogsAdapter(mContext, mList);
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
