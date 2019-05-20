package com.amana.MpestISS.attendance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amana.MpestISS.BuildConfig;
import com.amana.MpestISS.R;
import com.amana.MpestISS.model.checkinout.CheckingRequest;
import com.amana.MpestISS.model.checkinout.CheckingResponse;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.Utils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class CheckInAndOutActivity extends AppCompatActivity {

    public AlertDialog.Builder utilsAlertDialogBuilder;
    public AlertDialog utilsAlertDialog;

    String mType="",mTitle="";
    private final String TAG = this.getClass().getSimpleName();
    private AppPreferences _appPrefs;
    Context mContext;
    double dLatitube, dLongitube;
    // location last updated time
    private String mLastUpdateTime;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    long MAX_DURATION = MILLISECONDS.convert(5, MINUTES);

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    @BindView(R.id.checkIn_img)
    CircleImageView img_checkIn;

    @BindView(R.id.checkOut_img)
    CircleImageView img_checkOut;

    @BindView(R.id.username_txt)
    TextView txt_username;

    @BindView(R.id.datetime_txt)
    TextView txt_dateTime;

    @BindView(R.id.lat_txt)
    TextView txt_latitube;

    @BindView(R.id.long_txt)
    TextView txt_longitube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_out);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext =this;
        ButterKnife.bind(this);
        //Custom Title
        Utils.CustomTitle(mContext, "Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        restoreValuesFromBundle(savedInstanceState);

        checkLocationPermission();

        txt_username.setText(""+_appPrefs.getUserName().toString());
        txt_dateTime.setText(""+Utils.getCurrentTime());
    }

    @OnClick(R.id.checkIn_img)
    public void checkINButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        mType="IN";
        mTitle="Check In";
        // checkLocationPermission();

        String PreviousDT = _appPrefs.getCHECKIN_DT();
        AppLogger.verbose("PREVIOUS DT",PreviousDT);

        long CurrentDtmiliseconds = Calendar.getInstance().getTimeInMillis();

        long duration = CurrentDtmiliseconds - Utils.getTimeInMiliseconds(PreviousDT);

        AppLogger.verbose("PREVIOUS DT",""+duration);


        if (duration >= MAX_DURATION) {
            AppLogger.verbose("PREVIOUS DT","Greater than 5 min");

            if(Utils.isNetConnected(mContext)) {
                PostCheckinandOut(mType,mTitle);
            } else {
                Utils.noNetPopup(mContext);
            }
        }else{
            AppLogger.verbose("PREVIOUS DT","Less than 5 min");
            CustomePopuptitleMSG(mContext,"ALERT","System allow to Check In/Check Out only after 5 minutes from previous Check In/Check Out");
        }


      //  PostCheckinandOut(mType,mTitle);

    }

    @OnClick(R.id.checkOut_img)
    public void checkOUTButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        mType="OUT";
        mTitle="Check Out";
        // checkLocationPermission();

        String PreviousDT = _appPrefs.getCHECKIN_DT();
        AppLogger.verbose("PREVIOUS DT",PreviousDT);

        long CurrentDtmiliseconds = Calendar.getInstance().getTimeInMillis();

        long duration = CurrentDtmiliseconds - Utils.getTimeInMiliseconds(PreviousDT);

        AppLogger.verbose("PREVIOUS DT",""+duration);


        if (duration >= MAX_DURATION) {
            AppLogger.verbose("PREVIOUS DT","Greater than 5 min");

            if(Utils.isNetConnected(mContext)) {
                PostCheckinandOut(mType,mTitle);
            } else {
                Utils.noNetPopup(mContext);
            }

        }else{
            AppLogger.verbose("PREVIOUS DT","Less than 5 min");
            CustomePopuptitleMSG(mContext,"ALERT","System allow to Check In/Check Out only after 5 minutes from previous Check In/Check Out");
        }

    }

    @SuppressLint("RestrictedApi")
    private void init() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }


    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            dLatitube = mCurrentLocation.getLatitude();
            dLongitube = mCurrentLocation.getLongitude();

            txt_latitube.setText(""+dLatitube);
            txt_longitube.setText(""+dLongitube);


           // Toast.makeText(this,"Lat: " + dLatitube + ", " +"Lng: " + dLongitube,Toast.LENGTH_SHORT).show();

          }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                      //  Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(CheckInAndOutActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(CheckInAndOutActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }



    public void checkLocationPermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        openSettings();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
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
        // ((MainActivity) a).performQuoteListClick();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    /**
     *  Check in and Out Service
     */

    public void PostCheckinandOut(final String type, final String title) {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currentDate = df.format(c.getTime());

        CheckingRequest checkingRequest = new CheckingRequest();

        checkingRequest.setEmployeeID(_appPrefs.getEMPLOYEEID().toString());
        checkingRequest.setClientId(_appPrefs.getCLIENTID().toString());
        checkingRequest.setCreatedBy(_appPrefs.getUserID().toString());
        checkingRequest.setType(type);
        checkingRequest.setLatitude(dLatitube);
        checkingRequest.setLongitude(dLongitube);
        checkingRequest.setDateTime(currentDate);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CheckingResponse> call = apiService.checkingIn_out(checkingRequest);
        call.enqueue(new Callback<CheckingResponse>()
        {
            @Override
            public void onResponse(Call<CheckingResponse> call, Response<CheckingResponse> response) {

                try {
                    String mMsg = response.body().getMessage();

                    if (response.body().getStatusCode() == 200) {
                        if(type.equalsIgnoreCase("IN")){
                            _appPrefs.saveCheckStatus("Checked IN");
                            _appPrefs.saveCHECKIN_DT(Utils.getCurrentTime());
                        }else{
                            _appPrefs.saveCheckStatus("Checked OUT");
                            _appPrefs.saveCHECKOUT_DT(Utils.getCurrentTime());
                        }
                        CustomePopuptitleMSG(mContext,title,mMsg);
                      //Utils.popup_message(mContext,mMsg,title);


                    } else {
                        CustomePopuptitleMSG(mContext,title,mMsg);
                      //Utils.popup_message(mContext,mMsg,title);


                    }
                } catch (Exception e) {

                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(Call<CheckingResponse> call, Throwable t) {

            }

        });
    }

    /**
     *  Master Sync
     * @param context
     */
    public void CustomePopuptitleMSG(final Context context, String title, String msg){

        // get prompts.xml view
        final LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.dialog_mastersync, null);
        utilsAlertDialogBuilder = new AlertDialog.Builder(context);
        utilsAlertDialogBuilder.setCancelable(true);
        utilsAlertDialogBuilder.setView(promptsView);
        // create alert dialog
        utilsAlertDialog = utilsAlertDialogBuilder.create();
        utilsAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CircleImageView img_close= promptsView
                .findViewById(R.id.close_img);
        img_close.setVisibility(View.GONE);
        TextView tvTitle = promptsView
                .findViewById(R.id.title_tv);
        TextView tvMSG = promptsView
                .findViewById(R.id.msg_tv);

        tvTitle.setText(title);
        tvMSG.setText(msg);

        Button btn_ok = promptsView.findViewById(R.id.ok_btn);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsAlertDialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilsAlertDialog.dismiss();

            }
        });
        utilsAlertDialog.show();
    }
}
