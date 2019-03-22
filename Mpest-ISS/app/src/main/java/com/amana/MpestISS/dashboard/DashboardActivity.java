package com.amana.MpestISS.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amana.MpestISS.LoginActivity;
import com.amana.MpestISS.R;
import com.amana.MpestISS.adhoc.AdhocActivity;
import com.amana.MpestISS.announcement.AnnouncementActivity;
import com.amana.MpestISS.attendance.CheckInAndOutActivity;
import com.amana.MpestISS.logs.LogsActivity;
import com.amana.MpestISS.joblist.JobDetailsActivity;
import com.amana.MpestISS.myjob.PreviewActivity;
import com.amana.MpestISS.myschedule.MyScheduleActivity;
import com.amana.MpestISS.quiz.QuizActivity;
import com.amana.MpestISS.utils.AppConstants;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.geniusforapp.fancydialog.FancyAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardActivity extends AppCompatActivity implements DashboardAdapter.ItemClickListener {
    @BindView(R.id.rv_dashboard)
    RecyclerView recyclerView;
    @BindView(R.id.copyRight_txt)
    TextView txt_copyRight;
    @BindView(R.id.checkStatus_txt)
    TextView txt_Checkstatus;
    @BindView(R.id.username_txt)
    TextView txt_username;

    DashboardAdapter adapter;
    Context mcontext;
    private AppPreferences _appPrefs;
    private String TAG = this.getClass().getSimpleName();
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBuilder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        _appPrefs = new AppPreferences(getApplicationContext());
        mcontext = this;


        handleHeader();

        FooterTextWithUserName();

        updateGridAdapter();
    }

    private void handleHeader() {

        txt_username.setText("Hi, " + _appPrefs.getUserID());
        txt_Checkstatus.setText("You are: " + _appPrefs.getCheckStatus());

    }

    @Override
    protected void onRestart() {
        handleHeader();
        super.onRestart();
    }

    public void FooterTextWithUserName() {
        if (!_appPrefs.getUserID().isEmpty()) {
           txt_copyRight.setText("" + getString(R.string.powered_by));
          // txt_copyRight.setText("" + getString(R.string.powered_by) + "  [" + _appPrefs.getUserID() + "]");
        }
    }

    private void updateGridAdapter() {

        try {

            String[] data = {"CHECK IN/OUT","ANNOUNCEMENT", "JOB DETAILS","LOGS",
                    "MY SCHEDULE", "MASTER SYNC","ADHOC", "LOGOUT"};

            int[] icons = {R.drawable.db_checkin, R.drawable.db_quiz, R.drawable.db_mytask, R.drawable.db_logs,
                    R.drawable.db_schedule, R.drawable.db_sync,
                    R.drawable.db_adhoc, R.drawable.db_logout};
            // set up the RecyclerView
            int numberOfColumns = 2;
            recyclerView.setLayoutManager(new GridLayoutManager(DashboardActivity.this, numberOfColumns));
            adapter = new DashboardAdapter(DashboardActivity.this, data, icons, 1, false);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    @Override
    public void onItemClick(View view, int position) {
        AppLogger.info(TAG, "Tab " + adapter.getItem(position) + ", position " + position);

        switch (position) {

            case 0: // home
               // AppConstants.EXTRA_MENU = "HOME";
                Intent shedule = new Intent(mcontext, CheckInAndOutActivity.class);
                startActivity(shedule);


                break;
            case 1: // logOut
                Intent intent_quiz = new Intent(mcontext, AnnouncementActivity.class);
                startActivity(intent_quiz);

                break;
            case 2: // Check in
                Intent intent_myTask = new Intent(mcontext, JobDetailsActivity.class);
                startActivity(intent_myTask);

                break;
            case 3: // My Task
                Intent intent_logs = new Intent(mcontext, LogsActivity.class);
                startActivity(intent_logs);
                break;

            case 4: // My Schedule
                Intent intent_sche = new Intent(mcontext, MyScheduleActivity.class);
                startActivity(intent_sche);
                break;
            case 5: // Master Sync
                masterSync(mcontext);
                break;
            case 6: //adhoc
                Intent intent_ad = new Intent(mcontext, AdhocActivity.class);
                //Intent intent_ad = new Intent(mcontext, PreviewActivity.class);
                startActivity(intent_ad);
                break;
            case 7: // logs

                logout(mcontext);
                break;


        }


    }


    /**
     * LogOut Methods
     *
     * @param context
     */
    public void logout(final Context context) {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                .setAlertFont("fonts/Avenir-Medium.otf")
                .setSubTitleFont("fonts/Avenir-Heavy.otf")
                .setTextSubTitle("Do you really want to logout?")
                .setNegativeColor(R.color.colorPositive)
                .setNegativeButtonText("CANCEL")
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.colorNegative)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {

                        _appPrefs.saveUserID("");
                        _appPrefs.saveCheckStatus("Checked OUT");
                        Intent loginIntent = new Intent(context, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                        dialog.dismiss();
                    }
                })
                .build();
        alert.show();
    }

    /**
     *  Master Sync
     * @param context
     */
    public void masterSync(Context context){

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_mastersync, null);
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CircleImageView img_close= promptsView
                .findViewById(R.id.close_img);

       Button btn_ok = promptsView.findViewById(R.id.ok_btn);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                MasterDbLists.getMaterialList(mcontext,_appPrefs.getCLIENTID().toString());

            }
        });
        alertDialog.show();
    }

}

