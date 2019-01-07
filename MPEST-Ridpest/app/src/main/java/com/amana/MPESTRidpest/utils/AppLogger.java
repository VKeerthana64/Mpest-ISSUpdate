package com.amana.MPESTRidpest.utils;

import android.content.Context;
import android.util.Log;

import com.amana.MPESTRidpest.restApi.ApiInterface;

/**
 * @author naga
 */

public class AppLogger {
    private static String TAG = "APP_LOGGER";

    private static Context context = null;


    public static boolean init(Context context) {
        boolean initialized = true;

        // Operation 1: Its very important to have a valid context here
        if (context == null) {
            Log.e(TAG, "Error initializing the ShipaLogger. Supplied context is null");
            return false;
        }
        AppLogger.context = context;

        return initialized;
    }


    public static void warn(String TAG, String message) {
        Log.w(TAG, message);
    }

    public static void info(String TAG, String message) {
        Log.i(TAG, message);
    }

    public static void verbose(String TAG, String message) {
        Log.v(TAG, message);
    }

    public static void error(String TAG, String message) {
        Log.e(TAG, message);
    }


    public static void logStackTrace(Exception e) {
        String exception = "";
        exception = e.toString() + "\n";
        StackTraceElement[] temp = e.getStackTrace();
        for (int i = 0; i < temp.length; i++) {
            exception = exception + "Exception at " + temp[i].toString() + "\n";
        }

        Log.w(TAG, exception);

    }

    public static void logStackTrace(OutOfMemoryError e) {
        String exception = "";
        exception = e.toString() + "\n";
        StackTraceElement[] temp = e.getStackTrace();
        for (int i = 0; i < temp.length; i++) {
            exception = exception + "Exception at " + temp[i].toString() + "\n";
        }

        Log.w(TAG, exception);
    }

}
