package com.amana.MPESTRidpest.joblist;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amana.MPESTRidpest.R;
import com.amana.MPESTRidpest.model.AdhocRequest;
import com.amana.MPESTRidpest.model.comments.CommetsResponse;
import com.amana.MPESTRidpest.model.realm.AdhocRm.AdhocRequestRm;
import com.amana.MPESTRidpest.model.realm.taskdetail.Adhocdata;
import com.amana.MPESTRidpest.model.realm.taskdetail.Contracterdetail;
import com.amana.MPESTRidpest.model.realm.taskdetail.Customerdetail;
import com.amana.MPESTRidpest.model.realm.taskdetail.Datum;
import com.amana.MPESTRidpest.model.realm.taskdetail.JobOrdersdetail;
import com.amana.MPESTRidpest.model.realm.taskdetail.MyTaskRealm;
import com.amana.MPESTRidpest.model.realm.taskdetail.Teamdetail;
import com.amana.MPESTRidpest.restApi.ApiClient;
import com.amana.MPESTRidpest.restApi.ApiInterface;
import com.amana.MPESTRidpest.utils.AppLogger;
import com.amana.MPESTRidpest.utils.AppPreferences;
import com.amana.MPESTRidpest.utils.MasterDbLists;
import com.amana.MPESTRidpest.utils.Utils;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;


public class JobDetailsActivity extends AppCompatActivity implements MyTaskAdapter.ItemClickListener, DatePickerDialog.OnDateSetListener {

    AppPreferences _appPrefs;
    Context mContext;
    MyTaskAdapter mListAdapter;
    ArrayList<Datum> mList;

    private Menu menu;
    private SearchView search;
    private String mSearchString = "";
    String mDateFilter ="";
    String mStatus = "Yet to Start";

    @BindView(R.id.tab_sheduled)
    TextView sheduled_tab;
    @BindView(R.id.tab_inprogress)
    TextView inProgress_tab;
    @BindView(R.id.tab_completed)
    TextView completed_tab;
    @BindView(R.id.noData_txt)
    TextView tv_nodata;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab_sync)
    FloatingActionButton fab_sync;
    ArrayList<AdhocRequestRm> adhocRequestRmsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "Job Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        if (Utils.isNetConnected(mContext)) {
            new GetAllTaskList().execute();
        } else {
            getTaskFromDbSetList(mStatus, "");
            Utils.noNetPopup(mContext);
        }


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

        sheduled_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fab_sync.setVisibility(View.VISIBLE);

                search.setIconified(true);


                inProgress_tab.setBackgroundResource(R.drawable.bottom_line_normal);
                inProgress_tab.setBackgroundColor(Color.TRANSPARENT);
                inProgress_tab.setTextColor(Color.parseColor("#f3eedf"));

                sheduled_tab.setBackgroundResource(R.drawable.bottom_line);
                sheduled_tab.setTextColor(Color.WHITE);

                completed_tab.setBackgroundResource(R.drawable.bottom_line_normal);
                completed_tab.setBackgroundColor(Color.TRANSPARENT);
                completed_tab.setTextColor(Color.parseColor("#f3eedf"));

                mStatus = "Yet to Start";

                getTaskFromDbSetList(mStatus, "");
               /* mCurrentPage = 1;
                if (isNetConnected()) {
                    GetQuoteListData();
                    //new getQuoteListAuthTask().execute();
                } else {
                    noNetPopup();
                }*/
            }
        });


        inProgress_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fab_sync.setVisibility(View.GONE);
                search.setIconified(true);
                sheduled_tab.setBackgroundResource(R.drawable.bottom_line_normal);
                sheduled_tab.setBackgroundColor(Color.TRANSPARENT);
                sheduled_tab.setTextColor(Color.parseColor("#f3eedf"));

                completed_tab.setBackgroundResource(R.drawable.bottom_line_normal);
                completed_tab.setBackgroundColor(Color.TRANSPARENT);
                completed_tab.setTextColor(Color.parseColor("#f3eedf"));

                inProgress_tab.setBackgroundResource(R.drawable.bottom_line);
                inProgress_tab.setTextColor(Color.WHITE);

                mStatus = "In-Progress";

                getTaskFromDbSetList(mStatus, "");

            }
        });


        completed_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fab_sync.setVisibility(View.VISIBLE);

                sheduled_tab.setBackgroundResource(R.drawable.bottom_line_normal);
                sheduled_tab.setBackgroundColor(Color.TRANSPARENT);
                sheduled_tab.setTextColor(Color.parseColor("#f3eedf"));

                inProgress_tab.setBackgroundResource(R.drawable.bottom_line_normal);
                inProgress_tab.setBackgroundColor(Color.TRANSPARENT);
                inProgress_tab.setTextColor(Color.parseColor("#f3eedf"));

                completed_tab.setBackgroundResource(R.drawable.bottom_line);
                completed_tab.setTextColor(Color.WHITE);

                mStatus = "Completed";

                getTaskFromDbSetList(mStatus, "");

            }
        });

        fab_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mStatus.equalsIgnoreCase("In-Progress")){
                    getTaskFromDbSetList(mStatus, "");

                }else{
                    if (Utils.isNetConnected(mContext)) {
                        new GetAllTaskList().execute();
                    } else {
                        getTaskFromDbSetList(mStatus, "");
                        Utils.noNetPopup(mContext);
                    }
                }



            }
        });
    }

    /**
     * Pull n push Recycler view Reloader
     */
    void refreshItems() {
        // Load items
        //gets data from local db
        if(mStatus.equalsIgnoreCase("In-Progress")){
            getTaskFromDbSetList(mStatus, "");

        }else{
            if (Utils.isNetConnected(mContext)) {
                new GetAllTaskList().execute();
            } else {
                getTaskFromDbSetList(mStatus, "");
                Utils.noNetPopup(mContext);
            }
        }


        // Load complete
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Fetching task list from Save Db and Populates to recyclerView
     */
    void getTaskFromDbSetList(String mStatus, String mSerach) {

        mList = MasterDbLists.getMytaskListFromdb(mStatus, mSerach);

        if(mStatus.equalsIgnoreCase("Completed")){


            //  adhocRequestRmsList = MasterDbLists.GetAdhocList();

            ArrayList<Datum> WithUploadArray = new ArrayList<Datum>();

            for(Datum event : mList){
                try {
                    if(event.getUploadStatus().contains("Upload")){
                        WithUploadArray.add(event);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(WithUploadArray.size() >0){ // If found available i will delete from table and fetch again completed data

                for(Datum event: WithUploadArray){
                    MasterDbLists.DeleteDuplicate(event.getServiceID());
                }

                mList.clear();
                mList = MasterDbLists.getMytaskListFromdb(mStatus, mSerach);



            }


            Collections.reverse(mList);

        }

        if (mList.size() == 0) { // if no data no data text is set
            mSwipeRefreshLayout.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        } else { // if data available
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
            mListAdapter = new MyTaskAdapter(mContext, mList);
            mRecyclerView.setAdapter(mListAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_job_details, menu);
        search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setIconified(false);
        search.setQueryHint("Search...");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    if (s.length() >= 2 || s.length() == 0) {
                        mSearchString = s.toString();

                        getTaskFromDbSetList(mStatus, mSearchString);

                        //mListAdapter.getFilter().filter(s);
                    }
                } catch (Exception e) {

                }
                return true;
            }
        });
        search.onActionViewCollapsed();

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onResume() {


        super.onResume();
    }

    @Override
    protected void onRestart() {

        if (Utils.isNetConnected(mContext)) {
            new GetAllTaskList().execute();
        } else {
            getTaskFromDbSetList(mStatus, "");
            Utils.noNetPopup(mContext);
        }
        super.onRestart();
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

        if (id == R.id.action_filter) {

            JobsListDatePicker();
            // new GetTaskListByDateFilter().execute();
            return true;
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

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        mDateFilter = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        mDateFilter = Utils.SingaporeCurrentTime(mDateFilter);
        new GetTaskListByDateFilter().execute();

    }

    //Get QuoteDetails when Quotation Number is passed
    class GetAllTaskList extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {
            Utils.showCustomDialog(mContext);
        }

        protected String doInBackground(Object... parametros) {
            // Do some validation here
            final ApiInterface apiService =
                    ApiClient.getClientForList().create(ApiInterface.class);

            Call<Object> call = apiService.getMyTaskDetails(_appPrefs.getCLIENTID().toString(), _appPrefs.getEMPLOYEEID().toString(), _appPrefs.getUserID());

            try {
                final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(call.execute().body()));

                final Realm realm = Realm.getDefaultInstance(); // opens db

                realm.beginTransaction();
                realm.where(MyTaskRealm.class).findAll().deleteAllFromRealm();
                //realm.rem(Datum.class).clear();
                RealmResults<Datum> users = realm.where(Datum.class)
                        .notEqualTo("Status", "In-Progress")
                        .and()
                        .notEqualTo("UploadStatus", "Upload InProgress")
                        .and()
                        .notEqualTo("UploadStatus", "Upload Success")
                        .findAll();


                for(int i=0; i<users.size(); i++){
                    if(!users.get(i).getCustomerdetails().isEmpty()){
                        users.get(i).getCustomerdetails().deleteAllFromRealm();
                    }

                    if(!users.get(i).getContracterdetails().isEmpty()){
                        users.get(i).getContracterdetails().deleteAllFromRealm();
                    }

                    if(!users.get(i).getTeamdetails().isEmpty()){
                        users.get(i).getTeamdetails().deleteAllFromRealm();
                    }

                    if(!users.get(i).getCustomerdetails().isEmpty()){
                        users.get(i).getCustomerdetails().deleteAllFromRealm();
                    }

                    if(!users.get(i).getJobOrdersdetails().isEmpty()){
                        users.get(i).getJobOrdersdetails().deleteAllFromRealm();
                    }
                    if(!users.get(i).getAdhocdata().isEmpty()){
                        users.get(i).getAdhocdata().deleteAllFromRealm();
                    }


                }
                users.deleteAllFromRealm();

                realm.createObjectFromJson(MyTaskRealm.class, object);// Insert from a string
                realm.commitTransaction();

            /*    ArrayList<Datum> arr_Datum = MasterDbLists.getMytaskListFromdb();

                if (arr_Datum.size() > 0) {

                    for (Datum datum : arr_Datum) {

                        Call<CommetsResponse> call2 = apiService.GetallComments(datum.get_id());

                        CommetsResponse commetsResponse = (CommetsResponse) call2.execute().body();

                        try {

                            int mStatusCode = commetsResponse.getStatusCode();

                            if (mStatusCode == 200) {

                                if (commetsResponse.getData().size() > 0) {
                                    MasterDbLists.UpdateMyTaskList(commetsResponse.getData().size(), datum.getServiceID());
                                }

                            }


                        } catch (Exception e) {
                            Utils.dismissDialog();
                            e.printStackTrace();

                        }

                    }
                }*/
                Utils.dismissDialog();
            } catch (Exception e) {

                Utils.dismissDialog();

                FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(mContext)
                        .setAlertFont("fonts/Avenir-Medium.otf")
                        .setSubTitleFont("fonts/Avenir-Heavy.otf")
                        .setTextSubTitle("Unable to get data from server. Please try again later..")
                        .setPositiveButtonText("OK")
                        .setPositiveColor(R.color.colorPositive)
                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                                getTaskFromDbSetList(mStatus, "");


                            }
                        })
                        /* .setAutoHide(true)*/
                        .build();
                alert.show();

                e.printStackTrace();

            }

            return "true";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {

                getTaskFromDbSetList(mStatus, "");
                Utils.dismissDialog();
            }
            super.onPostExecute(result);
        }

    }


    //Get QuoteDetails when Quotation Number is passed
    class GetTaskListByDateFilter extends AsyncTask<Object, Void, String> {

        protected void onPreExecute() {
            Utils.showCustomDialog(mContext);
        }

        protected String doInBackground(Object... parametros) {
            // Do some validation here
            final ApiInterface apiService =
                    ApiClient.getClientForList().create(ApiInterface.class);

            Call<Object> call = apiService.getMyTaskDetailsByFilter(_appPrefs.getCLIENTID().toString(), _appPrefs.getEMPLOYEEID().toString(), _appPrefs.getUserID(),""+mDateFilter);

            try {
                final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(call.execute().body()));

                final Realm realm = Realm.getDefaultInstance(); // opens db

                realm.beginTransaction();
                realm.createObjectFromJson(MyTaskRealm.class, object);// Insert from a string
                realm.commitTransaction();

               /* ArrayList<Datum> arr_Datum = MasterDbLists.getMytaskListFromdb();

                if (arr_Datum.size() > 0) {

                    for (Datum datum : arr_Datum) {

                        Call<CommetsResponse> call2 = apiService.GetallComments(datum.get_id());

                        CommetsResponse commetsResponse = (CommetsResponse) call2.execute().body();

                        try {

                            int mStatusCode = commetsResponse.getStatusCode();

                            if (mStatusCode == 200) {

                                if (commetsResponse.getData().size() > 0) {
                                    MasterDbLists.UpdateMyTaskList(commetsResponse.getData().size(), datum.getServiceID());
                                }

                            }


                        } catch (Exception e) {
                            Utils.dismissDialog();
                            e.printStackTrace();

                        }

                    }
                }*/
                Utils.dismissDialog();
            } catch (Exception e) {

                Utils.dismissDialog();

                FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(mContext)
                        .setAlertFont("fonts/Avenir-Medium.otf")
                        .setSubTitleFont("fonts/Avenir-Heavy.otf")
                        .setTextSubTitle("Unable to get data from server. Please try again later..")
                        .setPositiveButtonText("OK")
                        .setPositiveColor(R.color.colorPositive)
                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                                getTaskFromDbSetList(mStatus, "");


                            }
                        })
                        /* .setAutoHide(true)*/
                        .build();
                alert.show();
                e.printStackTrace();

            }

            return "true";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {

                getTaskFromDbSetList(mStatus, "");
                Utils.dismissDialog();
            }
            super.onPostExecute(result);
        }

    }


    public void JobsListDatePicker() {
        try {

            Calendar now = Calendar.getInstance();

            String dateInString = Utils.getCurrentTime(); // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(dateInString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            now.setTime(c.getTime());



            com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                    JobDetailsActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)

            );
            dpd.setTitle("Date Filter");
            dpd.setMaxDate(now);

            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -3);
            String PreDate = format.format(cal.getTime());

            Calendar endDate = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
            endDate.setTime(sdf2.parse(PreDate));

            dpd.setMinDate(endDate);

            dpd.setThemeDark(false);
            dpd.vibrate(false);
            dpd.dismissOnPause(false);
            dpd.showYearPickerFirst(false);
            dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2);
            /*  dpd.setAccentColor(Color.parseColor("#F08200"));
             */
            dpd.show(getFragmentManager(), "Datepickerdialog");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}