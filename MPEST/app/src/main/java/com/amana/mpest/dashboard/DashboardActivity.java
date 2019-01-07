package com.amana.mpest.dashboard;

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

import com.amana.mpest.LoginActivity;
import com.amana.mpest.R;
import com.amana.mpest.adhoc.AdhocActivity;
import com.amana.mpest.attendance.CheckInAndOutActivity;
import com.amana.mpest.joblist.JobDetailsActivity;
import com.amana.mpest.logs.LogsActivity;
import com.amana.mpest.myschedule.MyScheduleActivity;
import com.amana.mpest.utils.AppConstants;
import com.amana.mpest.utils.AppLogger;
import com.amana.mpest.utils.AppPreferences;
import com.amana.mpest.utils.MasterDbLists;
import com.geniusforapp.fancydialog.FancyAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardActivity extends AppCompatActivity implements DashboardAdapter.ItemClickListener {
    @BindView(R.id.rv_dashboard)
    RecyclerView recyclerView;
    @BindView(R.id.copyRight_txt)
    TextView txt_copyRight;

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

        FooterTextWithUserName();

        updateGridAdapter();
    }

    public void FooterTextWithUserName() {
        if (!_appPrefs.getUserID().isEmpty()) {
            txt_copyRight.setText("" + getString(R.string.powered_by) + "  [" + _appPrefs.getUserID() + "]");
        }
    }

    private void updateGridAdapter() {

        try {

            String[] data = {"HOME", "LOGOUT", "CHECK IN/OUT", "MY TASKS",
                    "MY SCHEDULE", "MASTER SYNC","ADHOC", "LOGS"};

            int[] icons = {R.drawable.db_home, R.drawable.db_logout, R.drawable.db_checkin, R.drawable.db_mytask,
                    R.drawable.db_schedule, R.drawable.db_sync,
                    R.drawable.db_adhoc, R.drawable.db_logs};
            // set up the RecyclerView
            int numberOfColumns = 2;
            recyclerView.setLayoutManager(new GridLayoutManager(DashboardActivity.this, numberOfColumns));
            adapter = new DashboardAdapter(DashboardActivity.this, data, icons, 0, false);
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
                AppConstants.EXTRA_MENU = "HOME";
                break;
            case 1: // logOut
                logout(mcontext);
                break;
            case 2: // Check in
                Intent shedule = new Intent(mcontext, CheckInAndOutActivity.class);
                startActivity(shedule);
                break;
            case 3: // My Task
                Intent intent_myTask = new Intent(mcontext, JobDetailsActivity.class);
                startActivity(intent_myTask);
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
                Intent intent_logs = new Intent(mcontext, LogsActivity.class);
                startActivity(intent_logs);
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
        CircleImageView img_close= (CircleImageView) promptsView
                .findViewById(R.id.close_img);

       Button btn_ok = (Button) promptsView.findViewById(R.id.ok_btn);
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

