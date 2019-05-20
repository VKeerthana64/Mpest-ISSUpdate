package com.amana.MpestISS.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.TimeZone;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amana.MpestISS.R;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by naga on 30/05/2017.
 */
public class Utils {

    public static AlertDialog.Builder utilsAlertDialogBuilder;
    public static AlertDialog utilsAlertDialog;
    public static AppPreferences _appPrefs;
    public static String dateKey = "F";;

    private static File compressedImage2;

    public static void CustomTitle(Context context, String Title) {
        TextView tv = new TextView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(Title);
        int dp = (int) (context.getResources().getDimension(R.dimen.title_size) / context.getResources().getDisplayMetrics().density);
        tv.setTextSize(dp);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Heavy.otf");
        tv.setTypeface(tf);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) context).getSupportActionBar().setCustomView(tv);
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    protected static ProgressDialog progressDialog = null;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static final String PREFS_NAME = "TAKEOFFANDROID";

    public static final String KEY_COUNTRIES = "Countries";

    public static final int REQUEST_CODE = 1234;

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String formatPrice(double amnt, String mCurrency) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(mCurrency.trim());
        formatter.setCurrency(currency);

        /*Hide Currency symbol*/
        DecimalFormatSymbols decimalFormatSymbols = formatter.getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        formatter.setDecimalFormatSymbols(decimalFormatSymbols);

        formatter.setMinimumFractionDigits(currency.getDefaultFractionDigits());
        formatter.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        return formatter.format(amnt);
    }


    public static boolean isNetConnected(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) (context).getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    public static void noNetPopup(Context context) {

        try {
            FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                    .setAlertFont("fonts/Avenir-Medium.otf")
                    .setSubTitleFont("fonts/Avenir-Heavy.otf")
                    .setTextSubTitle("The Internet connection appears to be offline.")
                    .setPositiveButtonText("OK")
                    .setPositiveColor(R.color.colorPositive)
                    .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                        @Override
                        public void OnClick(View view, Dialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    /* .setAutoHide(true)*/
                    .build();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
         /*Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No Internet connection found. Check your connection and try again.", Snackbar.LENGTH_LONG).show();*/
    }


    public static void CommanPopup(Context context, String msg) {

        try {
            FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                    .setAlertFont("fonts/Avenir-Medium.otf")
                    .setSubTitleFont("fonts/Avenir-Heavy.otf")
                    .setTextSubTitle("" + msg)
                    .setPositiveButtonText("OK")
                    .setPositiveColor(R.color.colorPositive)
                    .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                        @Override
                        public void OnClick(View view, Dialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    /* .setAutoHide(true)*/
                    .build();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
         /*Snackbar.make(getActivity().findViewById(android.R.id.content),
                    "No Internet connection found. Check your connection and try again.", Snackbar.LENGTH_LONG).show();*/
    }

    public static void LocationPermissionPopup(final Context context, String msg) {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                /* .setimageResource(R.drawable.ic_error_black_24dp)*/
                .setAlertFont("fonts/Avenir-Medium.otf")
                .setSubTitleFont("fonts/Avenir-Heavy.otf")
                .setTextSubTitle("" + msg)
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        isPermissionGranted(context);
                        dialog.dismiss();

                    }
                })
                .build();
        alert.show();
    }


    public static void popup_message(final Context context, String msg, String title) {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context)
                .setAlertFont("fonts/Avenir-Medium.otf")
                .setSubTitleFont("fonts/Avenir-Heavy.otf")
                .setBodyFont("fonts/Avenir-Heavy.otf")
                .setTextSubTitle("" + title)
                .setBody("" + msg)
                .setPositiveButtonText("OK")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build();
        alert.show();
    }


    public static boolean isPermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission((Activity) context,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                Log.v("TAG", "Permission is granted");
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return true;
            } else {
                LocationPermissionPopup(context, "Location details are required.");
                Log.v("TAG", "Permission is revoked");

                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    /**
     * close keyboard
     *
     * @param myEditText
     */
    public static void hideKeyBoard(Context ctx, EditText myEditText) {
        InputMethodManager imm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
            imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        }

    }

    /**
     * show keyborad
     *
     * @param myEditText
     */
    public static void showKeyBoard(Context ctx, EditText myEditText) {

        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(myEditText.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);

    }


    /**
     * show TOAST
     *
     * @param msg
     */
    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * show Custom Dialog
     *
     * @param context
     */
    public static void showCustomDialog(Context context) {
        if (context != null) {
            try {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog_progress, null);
                utilsAlertDialogBuilder = new AlertDialog.Builder(context);
                utilsAlertDialogBuilder.setCancelable(false);
                utilsAlertDialogBuilder.setView(promptsView);
                // create alert dialog
                utilsAlertDialog = utilsAlertDialogBuilder.create();
                utilsAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                utilsAlertDialog.show();
            } catch (IllegalArgumentException e) {
                AppLogger.logStackTrace(e);
            } catch (Exception e) {
                AppLogger.logStackTrace(e);
            }
        }
    }

    /**
     * show Custom Dialog
     *
     * @param context
     */
    public static void showCustomDialogwithBG(Context context) {
        if (context != null) {
            try {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog_progress_text_white, null);
                utilsAlertDialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                utilsAlertDialogBuilder.setCancelable(false);
                utilsAlertDialogBuilder.setView(promptsView);
                utilsAlertDialog = utilsAlertDialogBuilder.create();
                // utilsAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                utilsAlertDialog.show();
            } catch (IllegalArgumentException e) {
                AppLogger.logStackTrace(e);
            } catch (Exception e) {
                AppLogger.logStackTrace(e);
            }
        }
    }

    /**
     * show Custom Dialog
     *
     * @param context
     */
    public static void showCustomDialog(Context context, String message) {
        if (context != null) {
            try {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog_progress_withtext, null);
                utilsAlertDialogBuilder = new AlertDialog.Builder(context);
                utilsAlertDialogBuilder.setCancelable(false);
                TextView txt_msg = (TextView) promptsView.findViewById(R.id.msg_txt);
                txt_msg.setText("" + message); // Message set
                utilsAlertDialogBuilder.setView(promptsView);
                // create alert dialog
                utilsAlertDialog = utilsAlertDialogBuilder.create();
                utilsAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                utilsAlertDialog.show();
            } catch (IllegalArgumentException e) {
                AppLogger.logStackTrace(e);
            } catch (Exception e) {
                AppLogger.logStackTrace(e);
            }
        }
    }


    /**
     * Dismiss DAILOG
     */
    public static void dismissDialog() {
        try {
            if (utilsAlertDialog != null)
                utilsAlertDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String FirstLetterCapital(String value) {
        String name = value;
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        return name;
    }


    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    public static Bitmap convertBase64toBitmap(String mBase64) {
        byte[] decodedString = Base64.decode(mBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


    // Gets Current Time
    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    // Gets Current Time
    public static String getDateFilter() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    // Gets Current Time
    public static String getDateFilter(CalendarDay day) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedDate = df.format(day);
        return formattedDate;
    }

    // Gets Current Time
    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        // System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedDate = df.format(c.getTime());
        System.out.println("Current time =&gt; " + formattedDate);
        return formattedDate;
    }

    public static String Convert12hrTo24(String time) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

        Date d = null;
        try {
            d = parseFormat.parse(time);
            System.out.println(parseFormat.format(d) + " = " + displayFormat.format(d));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = displayFormat.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;

    }


    // Gets Current Time
    public static String CurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    // Gets Current Time
    public static String CurrentTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String CurrentTime(String mDate) {
        //SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm a");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }


    public static String SingaporeCurrentTime(String mDate) {
        //SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String GetDateTime(String mDate) {
        //SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String GetTime(String mDate) {
        //SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm a");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }


    public static String CurrentTimePlusOneHR() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String currentDateandTime = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(currentDateandTime);

            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
        String test = sdf2.format(calendar.getTime());
        System.out.println("Time here " + test);

        return test;
    }


    // Gets Time in MiliSeconds
    public static long getTimeInMiliseconds(String myDate) {

        long millis = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date date = sdf.parse(myDate);
            millis = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return millis;
    }

    public static String getRequiredDateTime(String mDate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }


    public static String getRequiredDate(String mDate) {
        // SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String getRequireDate(String mDate) {
        // SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String getSingRequiredDate(String mDate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
       // SimpleDateFormat input = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String getedTime(String mDate) {
        SimpleDateFormat input = new SimpleDateFormat("M/d/yyyy, HH:mm:ss a");
        // SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("hh:mm a");

        Date d = null;
        try {
            d = input.parse(mDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String getsingTime(String mDate) {
        //SimpleDateFormat input = new SimpleDateFormat("M/d/yyyy, HH:mm:ss a");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("hh:mm a");

        Date d = null;
        try {
            d = input.parse(mDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String getedDate(String mDate) {
        SimpleDateFormat input = new SimpleDateFormat("M/d/yyyy, HH:mm:ss a");
        // SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("M/d/yyyy");

        Date d = null;
        try {
            d = input.parse(mDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }

    public static String getRequiredTime(String mDate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat output = new SimpleDateFormat("hh:mm a");

        Date d = null;
        try {
            d = input.parse(mDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        Log.i("DATE", "" + formatted);
        return formatted;
    }


    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    public static String getCompressBitmapToBase64(Context mContext, File imagefile) {

        final Bitmap.CompressFormat compressFormat;
        if (imagefile.getAbsolutePath().toString().toLowerCase().equalsIgnoreCase("jpeg") || imagefile.getAbsolutePath().toString().toLowerCase().equalsIgnoreCase("jpeg")) {
            compressFormat = Bitmap.CompressFormat.JPEG;
        } else {
            compressFormat = Bitmap.CompressFormat.WEBP;
        }

        if (imagefile.exists()) {
            try {
                File compressedImage = new Compressor(mContext)
                        .setMaxWidth(300)
                        .setMaxHeight(250)
                        .setQuality(75)
                        .setCompressFormat(compressFormat)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(imagefile);

                Bitmap myBitmap = BitmapFactory.decodeFile(compressedImage.getAbsolutePath());

                AppLogger.verbose("File Size :", String.format("Size : %s", Utils.getReadableFileSize(compressedImage.length())));

                return Utils.convertBitmapToBase64(myBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "";
        }

        return "";
    }


    public static String getNormalCompressBitmapToBase64(final Context mContext, final File imagefile) {

        if (imagefile.exists()) {
            try {

                final Bitmap.CompressFormat compressFormat;
                if (imagefile.getAbsolutePath().toString().toLowerCase().equalsIgnoreCase("jpeg") || imagefile.getAbsolutePath().toString().toLowerCase().equalsIgnoreCase("jpeg")) {
                    compressFormat = Bitmap.CompressFormat.JPEG;
                } else {
                    compressFormat = Bitmap.CompressFormat.WEBP;
                }

                if (imagefile.exists()) {
                    File compressedImage = null;
                    compressedImage = new Compressor(mContext)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(compressFormat)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imagefile);

                    Bitmap myBitmap = BitmapFactory.decodeFile(compressedImage.getAbsolutePath());

                    AppLogger.verbose("File Size :", String.format("Size : %s", Utils.getReadableFileSize(compressedImage.length())));

                    return Utils.convertBitmapToBase64(myBitmap);
                }


            } catch (Exception ex) {

                try {

                    new Compressor(mContext)
                            .compressToFileAsFlowable(imagefile)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<File>() {
                                @Override
                                public void accept(File file) {
                                    compressedImage2 = file;
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) {
                                    throwable.printStackTrace();

                                }
                            });


                    Bitmap myBitmap = BitmapFactory.decodeFile(compressedImage2.getAbsolutePath());

                    AppLogger.verbose("File Size :", String.format("Size : %s", Utils.getReadableFileSize(compressedImage2.length())));

                    return Utils.convertBitmapToBase64(myBitmap);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                ex.printStackTrace();
            }


        } else {
            return "";
        }

        return "";
    }


    // Whatsapp type Image compress
    public static Bitmap compressImage(String filePath) {

        /* String filePath = getRealPathFromURI(imageUri);*/
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612 // 400x300

        float maxHeight = 400.0f;
        float maxWidth = 300.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        AppLogger.verbose("Compress Image Size -- ", String.valueOf(calculateInSampleSize(options, actualWidth, actualHeight)));

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return scaledBitmap;

    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static void DeleteFile(String filePath){
        try {
            String root = Environment.getExternalStorageDirectory().toString();
           // File file = new File(root + "/images/media/2918");
            File fdelete = new File(root+Uri.parse(filePath).getPath());
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    System.out.println("file Deleted :" + filePath);
                } else {
                    System.out.println("file not Deleted :" + filePath);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void DeleteAndScanFile(final Context context, String path) {
        String fpath = path.substring(path.lastIndexOf("/") + 1);
        Log.i("fpath", fpath);
        final File fi = new File(Uri.parse(path).getPath());
        try {
            MediaScannerConnection.scanFile(context, new String[] { Environment
                            .getExternalStorageDirectory().toString()
                            + "/images/"
                            + fpath.toString() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            if (uri != null) {
                                context.getContentResolver().delete(uri, null,
                                        null);
                            }
                            fi.delete();
                            System.out.println("file Scan n Deleted :" + fi.getPath());
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   public static void deleteFolder(Context context){

        String path = Environment.getExternalStorageDirectory()
                + "/Android/data/" + context.getPackageName() + "/profile";

        try{

            File dir = new File(path);
            if (dir.isDirectory())
            {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(dir, children[i]).delete();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
