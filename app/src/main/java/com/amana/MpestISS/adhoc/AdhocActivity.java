package com.amana.MpestISS.adhoc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.amana.MpestISS.BuildConfig;
import com.amana.MpestISS.R;
import com.amana.MpestISS.adapter.GetAddressIntentService;
import com.amana.MpestISS.model.AdhocModel;
import com.amana.MpestISS.model.AdhocRequest;
import com.amana.MpestISS.model.checkinout.CheckingRequest;
import com.amana.MpestISS.model.checkinout.CheckingResponse;
import com.amana.MpestISS.model.masterdetails.MaterialData;
import com.amana.MpestISS.model.pesttype.Datum;
import com.amana.MpestISS.model.pesttype.PesttypeResponse;
import com.amana.MpestISS.model.realm.AdhocRm.AdhocRequestRm;
import com.amana.MpestISS.model.realm.Materials;
import com.amana.MpestISS.model.realm.taskdetail.MyTaskRealm;
import com.amana.MpestISS.myjob.MyJobActivity;
import com.amana.MpestISS.myjob.PreviewActivity;
import com.amana.MpestISS.restApi.ApiClient;
import com.amana.MpestISS.restApi.ApiInterface;
import com.amana.MpestISS.utils.AppLogger;
import com.amana.MpestISS.utils.AppPreferences;
import com.amana.MpestISS.utils.MasterDbLists;
import com.amana.MpestISS.utils.Utils;
import com.geniusforapp.fancydialog.FancyAlertDialog;
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
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class AdhocActivity extends AppCompatActivity implements OnTimeSetListener {

    public AlertDialog.Builder utilsAlertDialogBuilder;
    public AlertDialog utilsAlertDialog;

    ArrayList<Datum> arr_pestTypeList = new ArrayList<>();

    ArrayList<MultiSelectModel> arr_PestType = new ArrayList<>();
    ArrayList<String> arr_teams = new ArrayList<>();

    private final String TAG = this.getClass().getSimpleName();

    private AppPreferences _appPrefs;
    Context mContext;

    boolean AddressFlag = true;

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

    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    public LocationAddressResultReceiver addressResultReceiver;

    private Location currentLocation;

    private LocationCallback locationCallback;


    @BindView(R.id.lat_txt)
    TextView txt_latitube;
    @BindView(R.id.long_txt)
    TextView txt_longitube;

    @BindView(R.id.startTime_input)
    EditText edt_startTime;
    @BindView(R.id.endTime_input)
    EditText edt_endTime;
    @BindView(R.id.pestType_input)
    EditText edt_pestType;
    @BindView(R.id.email_input)
    EditText edt_email;
    @BindView(R.id.Team_input)
    EditText edt_team;
    @BindView(R.id.address_input)
    EditText edt_address;
    @BindView(R.id.postalCode_input)
    EditText edt_postalCode;
    @BindView(R.id.customer_input)
    EditText edt_customerName;
    @BindView(R.id.err_customerName)
    TextInputLayout err_customername;
    @BindView(R.id.err_email)
    TextInputLayout err_email;
    @BindView(R.id.err_pestType)
    TextInputLayout err_pestType;
    @BindView(R.id.err_team)
    TextInputLayout err_team;
    @BindView(R.id.err_startTime)
    TextInputLayout err_startTime;
    @BindView(R.id.err_endTime)
    TextInputLayout err_endTime;
    @BindView(R.id.spnr_team)
    MaterialBetterSpinner spnr_team;
    @BindView(R.id.proceed_btn)
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhoc);
        _appPrefs = new AppPreferences(getApplicationContext());
        mContext = this;
        ButterKnife.bind(this);
        //Custom Title
        Utils.CustomTitle(mContext, "Create Adhoc Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        restoreValuesFromBundle(savedInstanceState);

        checkLocationPermission();

    }

    private void init() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        arr_teams = MasterDbLists.GetTeamNameList(); // Gets all Teams List
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_dropdown_item_1line, arr_teams);
        spnr_team.setAdapter(arrayAdapter1);

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


        addressResultReceiver = new LocationAddressResultReceiver(new Handler());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }

        };

        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        if (tpd != null) tpd.setOnTimeSetListener(this);

        edt_startTime.setText("" + Utils.CurrentTime());

        edt_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setPickerTime("StartTime");

            }
        });

        edt_endTime.setText("" + Utils.CurrentTimePlusOneHR());

        edt_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickerTime("EndTime");
            }
        });

        edt_pestType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelectPestTypePopup();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubmitValidation();
            }
        });

       /* arr_PestType.add(new MultiSelectModel(0,"Bed Bugs"));
        arr_PestType.add(new MultiSelectModel(1,"Birds"));
        arr_PestType.add(new MultiSelectModel(2,"Bees"));
        arr_PestType.add(new MultiSelectModel(3,"Common Ants"));
        arr_PestType.add(new MultiSelectModel(4,"Mosquitoes"));
        arr_PestType.add(new MultiSelectModel(5,"Rodents"));
        arr_PestType.add(new MultiSelectModel(6,"Cockroaches"));
        arr_PestType.add(new MultiSelectModel(7,"Subterranean Termites"));
        arr_PestType.add(new MultiSelectModel(8,"Wasps"));
        arr_PestType.add(new MultiSelectModel(9,"Wood Worms"));
        arr_PestType.add(new MultiSelectModel(10,"Bin Chutes"));
        arr_PestType.add(new MultiSelectModel(11,"Beetles"));
        arr_PestType.add(new MultiSelectModel(12,"Bocklices"));
        arr_PestType.add(new MultiSelectModel(13,"Baiting Stations"));
        arr_PestType.add(new MultiSelectModel(14,"Bats"));
        arr_PestType.add(new MultiSelectModel(15,"Bugs"));
        arr_PestType.add(new MultiSelectModel(16,"Crawling Insects"));
        arr_PestType.add(new MultiSelectModel(17,"Centipedes"));
        arr_PestType.add(new MultiSelectModel(18,"Cats"));
        arr_PestType.add(new MultiSelectModel(19,"Disinfect and Deodorize"));
        arr_PestType.add(new MultiSelectModel(20,"Dogs"));
        arr_PestType.add(new MultiSelectModel(21,"Fruitflies"));
        arr_PestType.add(new MultiSelectModel(22,"Flying Insects"));
        arr_PestType.add(new MultiSelectModel(23,"Fleas"));
        arr_PestType.add(new MultiSelectModel(24,"Flushout"));
        arr_PestType.add(new MultiSelectModel(25,"Foaming"));
        arr_PestType.add(new MultiSelectModel(26,"Fumigation"));
        arr_PestType.add(new MultiSelectModel(27,"Flies"));
        arr_PestType.add(new MultiSelectModel(28,"Grease Interceptors"));
        arr_PestType.add(new MultiSelectModel(29,"General Pest"));
        arr_PestType.add(new MultiSelectModel(30,"Hornets"));
        arr_PestType.add(new MultiSelectModel(31,"Lizards"));
        arr_PestType.add(new MultiSelectModel(32,"Midges"));
        arr_PestType.add(new MultiSelectModel(33,"Mites"));
        arr_PestType.add(new MultiSelectModel(34,"Moth"));
        arr_PestType.add(new MultiSelectModel(35,"Milipedes"));
        arr_PestType.add(new MultiSelectModel(36,"Powderpost Beetle"));
        arr_PestType.add(new MultiSelectModel(37,"Pestigas"));
        arr_PestType.add(new MultiSelectModel(38,"Scorpions"));
        arr_PestType.add(new MultiSelectModel(39,"Snakes"));
        arr_PestType.add(new MultiSelectModel(40,"Spiders"));
        arr_PestType.add(new MultiSelectModel(41,"Woodborers"));
        arr_PestType.add(new MultiSelectModel(42,"Weevils"));
        arr_PestType.add(new MultiSelectModel(43,"Weavers"));
        arr_PestType.add(new MultiSelectModel(44,"Monitor Lizard"));
        arr_PestType.add(new MultiSelectModel(45,"Termites"));
        arr_PestType.add(new MultiSelectModel(46,"Rats"));
        arr_PestType.add(new MultiSelectModel(47,"Pigeons"));
        arr_PestType.add(new MultiSelectModel(48,"Wood Pest"));
        arr_PestType.add(new MultiSelectModel(49,"Fogging"));

*/
       getPestTypeList();
    }

    public void SubmitValidation() {
        Utils.hideKeyBoard(mContext, edt_postalCode);
        if (!validate()) {
            return;
        } else {

            if(Utils.isNetConnected(mContext)) {
                SubmitADHOC();
            } else {
                Utils.noNetPopup(mContext);
            }

        }
    }

    public boolean validate() {
        boolean valid = true;

        String customerName = edt_customerName.getText().toString();
        String email = edt_email.getText().toString();
        String PestType = edt_pestType.getText().toString();
        String teams = spnr_team.getText().toString();
        String startTime = edt_startTime.getText().toString();
        String endTiem = edt_endTime.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            err_email.setError("Please enter valid EmailId");
            valid = false;
        } else {
            err_email.setError(null);
        }


        if (customerName.isEmpty()) {
            err_customername.setError("Please enter customer name");

            valid = false;
        } else {
            err_customername.setError(null);

        }


        if (PestType.isEmpty()) {
            err_pestType.setError("Please select pest type");
            valid = false;
        } else {
            err_pestType.setError(null);

        }


        if (teams.isEmpty()) {
            spnr_team.setError("Please select team");

            valid = false;
        } else {
            spnr_team.setError(null);

        }


        if (startTime.isEmpty()) {
            err_startTime.setError("Please select start time");

            valid = false;
        } else {
            err_startTime.setError(null);

        }


        if (endTiem.isEmpty()) {
            err_endTime.setError("Please select end time");

            valid = false;
        } else {
            err_endTime.setError(null);

        }

        return valid;
    }

    /**
     * Set time 12 hr format
     *
     * @param mType
     */
    void setPickerTime(final String mType) {
        Calendar now = Calendar.getInstance();
        new android.app.TimePickerDialog(
                mContext,
                new android.app.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        Log.d("Original", "Got clicked");


                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12) {
                            timeSet = "PM";
                        } else {
                            timeSet = "AM";
                        }

                        String min = "";
                        if (minute < 10)
                            min = "0" + minute;
                        else
                            min = String.valueOf(minute);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hour).append(':')
                                .append(min).append(" ").append(timeSet).toString();
                        String time = "" + hour + "h" + minute + "m";
                        // Utils.showToast(mContext,aTime);
                        if (mType.equalsIgnoreCase("StartTime")) {
                            edt_startTime.setText(aTime);
                        } else {
                            edt_endTime.setText(aTime);
                        }

                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        ).show();
    }


    /**
     * Multiselect Team Member list
     */
    private void multiSelectPestTypePopup() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Pest Types") //setting title for dialog
                .titleSize(18)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(0)

                .multiSelectList(arr_PestType) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        // dataString ="";
                        String mSelected = "";

                        for (int i = 0; i < selectedIds.size(); i++) {
                            mSelected = mSelected + selectedNames.get(i) + ",";
                        }


                        if (mSelected.length() > 0) {
                            mSelected = mSelected.substring(0, mSelected.length() - 1);
                            edt_pestType.setText(mSelected);
                        } else {
                            edt_pestType.setText("");
                        }
                    }

                    @Override
                    public void onCancel() {
                        // Utils.showToast(mContext, "Dialog cancelled");

                    }
                });

        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    /**
     * gets Pest Type
     */
    public void getPestTypeList() {

        Utils.showCustomDialog(mContext);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PesttypeResponse> call = apiService.getPestType(_appPrefs.getCLIENTID());
        call.enqueue(new Callback<PesttypeResponse>() {
            @Override
            public void onResponse(Call<PesttypeResponse> call, Response<PesttypeResponse> response) {

                try {
                    if (response.body().getStatusCode() == 200) {

                        arr_pestTypeList = response.body().getData();

                        if (arr_pestTypeList.size() > 0) {

                            for (int i = 0; i < arr_pestTypeList.size(); i++) {

                                arr_PestType.add(new MultiSelectModel(i, arr_pestTypeList.get(i).getPestName()));
                            }
                        }
                        Utils.dismissDialog();

                    }


                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();
                    AppLogger.verbose("Materials", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<PesttypeResponse> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("Materials", "unable to get details");

            }

        });
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


            txt_latitube.setText("" + dLatitube);
            txt_longitube.setText("" + dLongitube);


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
                                    rae.startResolutionForResult(AdhocActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(AdhocActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    public void checkLocationPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                        startAddressUpdates();
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

    @SuppressWarnings("MissingPermission")
    private void startAddressUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startAddressUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
            startAddressUpdates();
        }

        updateLocationUI();

        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        if (tpd != null) tpd.setOnTimeSetListener(this);
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

        fusedLocationClient.removeLocationUpdates(locationCallback);
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
     * Check in and Out Service
     */

    public void SubmitADHOC() {

        Utils.showCustomDialog(mContext);

        String AdhocServiceId="ADHOC_"+(int )(Math.random() * 5000 + 1);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());


        SimpleDateFormat df = new SimpleDateFormat("E MMM dd yyyy");
        String currentDate = df.format(c.getTime());

        SimpleDateFormat df2 = new SimpleDateFormat("EEEE");
        String currentDay = df2.format(c.getTime());

        ArrayList<AdhocModel> adhocModels = new ArrayList<>();

        AdhocModel adhocModel = new AdhocModel();
        adhocModel.setCompanyName(edt_customerName.getText().toString());
        adhocModel.setAddress(edt_address.getText().toString());
        adhocModel.setPostal(edt_postalCode.getText().toString());
        adhocModel.setEmail(edt_email.getText().toString());
        adhocModel.setLatLong("" + dLatitube + "," + dLongitube);
        adhocModel.setLocation(edt_address.getText().toString());
        adhocModel.setPestType(edt_pestType.getText().toString());

        adhocModels.add(adhocModel);

        String StartTime = edt_startTime.getText().toString().toString().trim();
        StartTime = Utils.Convert12hrTo24(StartTime);

        String endTime = edt_endTime.getText().toString().toString().trim();
        endTime = Utils.Convert12hrTo24(endTime);

        AdhocRequest adhocRequest = new AdhocRequest();
        adhocRequest.setADHOCServiceId(AdhocServiceId);
        adhocRequest.setCompanyName(edt_customerName.getText().toString());
        adhocRequest.setContactPerson(edt_customerName.getText().toString());
        adhocRequest.setLatLong("" + dLatitube + "," + dLongitube);
        adhocRequest.setStartsat(currentDate + " " + StartTime + ":00");
        adhocRequest.setEndsat(currentDate + " " + endTime + ":00");
        adhocRequest.setServiceOn(currentDay);
        adhocRequest.setTeam(spnr_team.getText().toString());
        adhocRequest.setTeamLead(MasterDbLists.getTeamLeadName(spnr_team.getText().toString()));
        adhocRequest.setAssignedTo(MasterDbLists.getTeamID(spnr_team.getText().toString()));
        adhocRequest.setSalesPersonId(_appPrefs.getEMPLOYEEID());
        adhocRequest.setPestType(edt_pestType.getText().toString());
        adhocRequest.setAdhocdata(adhocModels);
        adhocRequest.setCreatedBy(_appPrefs.getUserID());
        adhocRequest.setClientId(_appPrefs.getCLIENTID());


        Gson gson = new Gson();
        String AdhocJson = gson.toJson(adhocRequest);

        _appPrefs.saveAdhocJson(AdhocJson);
        _appPrefs.saveSERVICEID(AdhocServiceId);

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();

        realm.createObjectFromJson(AdhocRequestRm.class, AdhocJson);// Insert from a string
        realm.commitTransaction();

        Intent startIntent = new Intent(mContext, MyJobActivity.class);
        startIntent.putExtra("ServiceId", "" +AdhocServiceId);
        startIntent.putExtra("PestType", "" + edt_pestType.getText().toString());
        startActivity(startIntent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

 /*       final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Object> call = apiService.SaveAdhoc(adhocRequest);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                try {
                    final JSONObject object = new JSONObject(new com.google.gson.Gson().toJson(response.body()));

                    int mStatusCode = object.getInt("statusCode");
                    String message = object.optString("message");
                    if (mStatusCode == 200) {

                        CustomePopuptitleMSG(mContext, "Alert", message);

                    } else {
                        CustomePopuptitleMSG(mContext, "Alert", message);
                    }
                    Utils.dismissDialog();
                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Utils.dismissDialog();
            }

        });*/

    }

    /**
     * Master Sync
     *
     * @param context
     */
    public void CustomePopuptitleMSG(final Context context, String title, String msg) {

        // get prompts.xml view
        final LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.dialog_mastersync, null);
        utilsAlertDialogBuilder = new AlertDialog.Builder(context);
        utilsAlertDialogBuilder.setCancelable(true);
        utilsAlertDialogBuilder.setView(promptsView);
        // create alert dialog
        utilsAlertDialog = utilsAlertDialogBuilder.create();
        utilsAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CircleImageView img_close = promptsView
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
                finish();

            }
        });
        utilsAlertDialog.show();
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(AdhocActivity.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);
    }

    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(AdhocActivity.this,
                        "Address not found, ",
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");
            String currentPostalCode = resultData.getString("address_postalcode");

            showResults(currentAdd,currentPostalCode);
        }
    }

    private void showResults(String currentAdd,String currentPostalCode) {
        if(AddressFlag == true && currentAdd.length() >0){
            edt_address.setText(currentAdd);
            edt_postalCode.setText(currentPostalCode);
            AddressFlag = false;
        }


    }

}



