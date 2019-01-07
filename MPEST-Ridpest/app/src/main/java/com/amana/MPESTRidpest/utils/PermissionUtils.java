package com.amana.MPESTRidpest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Siddammag on 24-02-2017.
 */
public class PermissionUtils {

    public static final int PERMISSION_REQUEST_CODE_FILE = 1001;
    public static final int PERMISSION_REQUEST_CODE_CAMERA = 1002;
    public static final int PERMISSION_REQUEST_CODE_GALLERY = 1003;

    public static boolean isPermissionGranted(Context context, String permission){
        int result = ContextCompat.checkSelfPermission(context, permission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermission(Context context, Activity activity, String permission, int permissionRequestCode){
        ActivityCompat.requestPermissions(activity,new String[]{permission},permissionRequestCode);

      /*  if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            Toast.makeText(context,"Please allow permission in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{permission},permissionRequestCode);
        }*/
    }
}
