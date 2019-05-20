package com.amana.MpestISS;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.amana.MpestISS.dashboard.DashboardActivity;
import com.amana.MpestISS.utils.AppPreferences;

public class SplashActivity extends AppCompatActivity {
    //Duration of wait
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private AppPreferences _appPrefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        _appPrefs = new AppPreferences(getApplicationContext());
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(_appPrefs.getUserID().isEmpty()){
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }else {
                    intent = new Intent(SplashActivity.this, DashboardActivity.class);
                }
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}