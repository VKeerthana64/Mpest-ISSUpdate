package com.amana.MpestISS.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {

    public static final String KEY_PREFS_USER_ID = "KEY_PREFS_USER_ID";
    public static final String KEY_PREFS_USER_NAME = "KEY_PREFS_USER_NAME";
    public static final String KEY_PREFS_EMAIL = "KEY_PREFS_EMAIL";
    public static final String KEY_PREFS_EMPLOYEEID = "KEY_PREFS_EMPLOYEEID";
    public static final String KEY_PREFS_CLIENTID = "KEY_PREFS_CLIENTID";
    public static final String KEY_PEST_TYPES = "KEY_PEST_TYPES";
    public static final String KEY_SERVICE_ID = "KEY_SERVICE_ID";
    public static final String KEY_TEAM_LEAD = "KEY_TEAM_LEAD";
    public static final String KEY_TEAM_NAME = "KEY_TEAM_NAME";
    public static final String KEY_CUSTOMER_NAME = "KEY_CUSTOMER_NAME";
    public static final String KEY_CUSTOMER_EMAIL = "KEY_CUSTOMER_EMAIL";
    public static final String KEY_CHECKIN_DT = "KEY_CHECKIN_DT";
    public static final String KEY_CHECKOUT_DT = "KEY_CHECKOUT_DT";
    public static final String KEY_JOBSTARTEDTIME = "KEY_JOBSTARTEDTIME";
    public static final String KEY_LOCATION = "KEY_LOCATION";
    public static final String KEY_ADHOCJSON = "KEY_ADHOCJSON";

    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName(); //  Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public AppPreferences(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getUserID() {
        return _sharedPrefs.getString(KEY_PREFS_USER_ID, "");
    }

    public void saveUserID(String text) {
        _prefsEditor.putString(KEY_PREFS_USER_ID, text);
        _prefsEditor.commit();
    }

    public String getUserName() {
        return _sharedPrefs.getString(KEY_PREFS_USER_NAME, "");
    }

    public void saveUserName(String text) {
        _prefsEditor.putString(KEY_PREFS_USER_NAME, text);
        _prefsEditor.commit();
    }

    public String getUserEmail() {
        return _sharedPrefs.getString(KEY_PREFS_EMAIL, "");
    }

    public void saveUserEmail(String text) {
        _prefsEditor.putString(KEY_PREFS_EMAIL, text);
        _prefsEditor.commit();
    }

    public String getCLIENTID() {
        return _sharedPrefs.getString(KEY_PREFS_CLIENTID, "");
    }

    public void saveCLIENTID(String text) {
        _prefsEditor.putString(KEY_PREFS_CLIENTID, text);
        _prefsEditor.commit();
    }

    public String getEMPLOYEEID() {
        return _sharedPrefs.getString(KEY_PREFS_EMPLOYEEID, "");
    }

    public void saveEMPLOYEEID(String text) {
        _prefsEditor.putString(KEY_PREFS_EMPLOYEEID, text);
        _prefsEditor.commit();
    }

    public String getPESTS() {
        return _sharedPrefs.getString(KEY_PEST_TYPES, "");
    }

    public void savePESTS(String text) {
        _prefsEditor.putString(KEY_PEST_TYPES, text);
        _prefsEditor.commit();
    }

    public String getSERVICEID() {
        return _sharedPrefs.getString(KEY_SERVICE_ID, "");
    }

    public void saveSERVICEID(String text) {
        _prefsEditor.putString(KEY_SERVICE_ID, text);
        _prefsEditor.commit();
    }

    public String getTEAMLEAD() {
        return _sharedPrefs.getString(KEY_TEAM_LEAD, "");
    }

    public void saveTEAMLEAD(String text) {
        _prefsEditor.putString(KEY_TEAM_LEAD, text);
        _prefsEditor.commit();
    }

    public String getCUSTOMER() {
        return _sharedPrefs.getString(KEY_CUSTOMER_NAME, "");
    }

    public void saveCUSTOMER(String text) {
        _prefsEditor.putString(KEY_CUSTOMER_NAME, text);
        _prefsEditor.commit();
    }

    public String getCUSTOMEREMAIL() {
        return _sharedPrefs.getString(KEY_CUSTOMER_EMAIL, "");
    }

    public void saveCUSTOMEREMAIL(String text) {
        _prefsEditor.putString(KEY_CUSTOMER_EMAIL, text);
        _prefsEditor.commit();
    }

    public String getTEAMNAME() {
        return _sharedPrefs.getString(KEY_TEAM_NAME, "");
    }

    public void saveTEAMNAME(String text) {
        _prefsEditor.putString(KEY_TEAM_NAME, text);
        _prefsEditor.commit();
    }

    public String getCHECKIN_DT() {
        return _sharedPrefs.getString(KEY_CHECKIN_DT, "0");
    }

    public void saveCHECKIN_DT(String text) {
        _prefsEditor.putString(KEY_CHECKIN_DT, text);
        _prefsEditor.commit();
    }

    public String getCHECKOUT_DT() {
        return _sharedPrefs.getString(KEY_CHECKOUT_DT, "0");
    }

    public void saveCHECKOUT_DT(String text) {
        _prefsEditor.putString(KEY_CHECKOUT_DT, text);
        _prefsEditor.commit();
    }

    public String getJobStartedTime() {
        return _sharedPrefs.getString(KEY_JOBSTARTEDTIME, "");
    }

    public void saveJobStartedTime(String text) {
        _prefsEditor.putString(KEY_JOBSTARTEDTIME, text);
        _prefsEditor.commit();
    }

    public String getLOCATION() {
        return _sharedPrefs.getString(KEY_LOCATION, "");
    }

    public void saveLOCATION(String text) {
        _prefsEditor.putString(KEY_LOCATION, text);
        _prefsEditor.commit();
    }

    public String getAdhocJson() {
        return _sharedPrefs.getString(KEY_ADHOCJSON, "");
    }

    public void saveAdhocJson(String text) {
        _prefsEditor.putString(KEY_ADHOCJSON, text);
        _prefsEditor.commit();
    }
}
