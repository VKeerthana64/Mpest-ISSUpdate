package com.amana.MPESTPestoff.myschedule;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amana.MPESTPestoff.R;
import com.amana.MPESTPestoff.adapter.EventDecorator;
import com.amana.MPESTPestoff.adapter.OneDayDecorator;
import com.amana.MPESTPestoff.model.schedule.Datum;
import com.amana.MPESTPestoff.model.schedule.ScheduleResponse;
import com.amana.MPESTPestoff.restApi.ApiClient;
import com.amana.MPESTPestoff.restApi.ApiInterface;
import com.amana.MPESTPestoff.utils.AppLogger;
import com.amana.MPESTPestoff.utils.AppPreferences;
import com.amana.MPESTPestoff.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyScheduleActivity extends AppCompatActivity implements ScheduleAdapter.ItemClickListener{

    AppPreferences _appPrefs;
    Context mContext;

    ScheduleAdapter mListAdapter;

    String mDateFilter="", mServiceId="", mWorkOrder="", mContractNo="", mTeam="", mScheduleFrom="", mscheduleTo="";

    ArrayList<Datum> arr_datum = new ArrayList<>();
    ArrayList<CalendarDay> arr_dates = new ArrayList<>();
    android.support.v7.app.AlertDialog alertDialog;
    android.support.v7.app.AlertDialog.Builder alertDialogBuilder;

    @BindView(R.id.cv_setDate)
    MaterialCalendarView CV_date;
    @BindView(R.id.noData_txt)
    TextView tv_nodata;
    @BindView(R.id.rv_schedule)
    RecyclerView Rv_schedule;

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);

        //Custom Title
        getSupportActionBar().setElevation(0);
        Utils.CustomTitle(mContext, "My Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);

        CalendarDay day = CalendarDay.from(calendar);
        arr_dates.add(day);
        CV_date.addDecorator(new EventDecorator(Color.RED, arr_dates));
       // new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }


    public void init(){


        mDateFilter = Utils.getDateFilter();

        CV_date.getSelectedDate();
        Calendar calendar = Calendar.getInstance();
        CV_date.setDateSelected(calendar.getTime(),true);
        CV_date.setTileHeight(50);

        CV_date.addDecorators(
                /*new MySelectorDecorator(this),*/
                /*new HighlightWeekendsDecorator(),*/
                oneDayDecorator
        );

        CV_date.state().edit()
                .setMinimumDate(CalendarDay.from(2016, 4, 3))
                .setMaximumDate(CalendarDay.from(2020, 5, 12))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        CV_date.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                oneDayDecorator.setDate(date.getDate());
                widget.invalidateDecorators();

                mDateFilter = FORMATTER.format(date.getDate());
                //Toast.makeText(mContext,mDateFilter,Toast.LENGTH_LONG).show();
                 //new GetTaskListByDateFilter().execute();
                getScheduleList();
            }
        });

       // new GetTaskListByDateFilter().execute();
        getScheduleList();

    }

    @Override
    public void onItemClick(View view, int position) {

        AppLogger.info("Schedule", "Schedule " + mListAdapter.getItem(position).getId() + ", position " + position);

        mServiceId = ""+mListAdapter.getItem(position).getServiceID();

        try{
            if (mListAdapter.getItem(position).getJobOrdersdetails().size() > 0) {
                mWorkOrder= (mListAdapter.getItem(position).getJobOrdersdetails().get(0).getSalesOrderNo());
            }else{
                mWorkOrder="";
            }

        }catch (Exception e){
            mWorkOrder = "";
            e.printStackTrace();
        }

        try{
            if (mListAdapter.getItem(position).getTeamdetails().size() > 0) {

               mTeam= (mListAdapter.getItem(position).getTeamdetails().get(0).getTeamName());

            }else{
                mTeam="";
            }

            if (mListAdapter.getItem(position).getContracterdetails().size() > 0) {
                mContractNo=(mListAdapter.getItem(position).getContracterdetails().get(0).getContractReferenceNo());
            }else{
                mContractNo="";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{

            mScheduleFrom = (Utils.getRequiredDateTime(mListAdapter.getItem(position).getStartsat()));

            mscheduleTo=(Utils.getRequiredTime(mListAdapter.getItem(position).getEndsat()));

        }catch (Exception e){
            mScheduleFrom = "";
            mscheduleTo = "";
            e.printStackTrace();
        }

       // Toast.makeText(mContext,""+mListAdapter.getItem(position).getId(),Toast.LENGTH_SHORT).show();
        popup_Schedule();
    }

    private void popup_Schedule() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.dialog_schedule, null);
        alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CircleImageView img_close= (CircleImageView) promptsView
                .findViewById(R.id.close_img);

        ((TextView) promptsView.findViewById(R.id.serviceId_txt)).setText(""+mServiceId);
        ((TextView) promptsView.findViewById(R.id.work_orderno_txt)).setText(""+mWorkOrder);
        ((TextView) promptsView.findViewById(R.id.contract_no_txt)).setText(""+mContractNo);
        ((TextView) promptsView.findViewById(R.id.team_txt)).setText(""+mTeam);
        ((TextView) promptsView.findViewById(R.id.scheduled_start_time_txt)).setText(""+mScheduleFrom);
        ((TextView) promptsView.findViewById(R.id.scheduled_end_time_txt)).setText(""+mscheduleTo);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        Button btn_ok = (Button) promptsView.findViewById(R.id.ok_btn);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();

    }



    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            CV_date.addDecorator(new EventDecorator(Color.RED, calendarDays));
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
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ScheduleResponse> call = apiService.getScheduleList(_appPrefs.getCLIENTID().toString(), _appPrefs.getEMPLOYEEID().toString(), _appPrefs.getUserID(),""+mDateFilter);

            try {

                arr_datum.clear();
                arr_datum = call.execute().body().getData();
                getTaskFromDbSetList();

                Utils.dismissDialog();
            } catch (Exception e) {
                Utils.dismissDialog();
                e.printStackTrace();

            }

            return "true";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("true")) {


                Utils.dismissDialog();
            }
            super.onPostExecute(result);
        }

    }

    /**
     * Fetching task list from Save Db and Populates to recyclerView
     */
    void getTaskFromDbSetList() {

        if (arr_datum.size() == 0) { // if no data no data text is set

            tv_nodata.setVisibility(View.VISIBLE);
            Rv_schedule.setVisibility(View.GONE);
        } else { // if data available

            tv_nodata.setVisibility(View.GONE);
            Rv_schedule.setVisibility(View.VISIBLE);
            Rv_schedule.setLayoutManager(new LinearLayoutManager(mContext));
            mListAdapter = new ScheduleAdapter(mContext, arr_datum);
            Rv_schedule.setAdapter(mListAdapter);
            mListAdapter.setClickListener(this);

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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public void getScheduleList() {

        Utils.showCustomDialog(mContext);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ScheduleResponse> call = apiService.getScheduleList(_appPrefs.getCLIENTID().toString(), _appPrefs.getEMPLOYEEID().toString(), _appPrefs.getUserID(),""+mDateFilter);

        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {

                try {

                        arr_datum = response.body().getData();

                        getTaskFromDbSetList();

                        Utils.dismissDialog();


                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();
                    AppLogger.verbose("Materials", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("Materials", "unable to get details");

            }

        });
    }


}
