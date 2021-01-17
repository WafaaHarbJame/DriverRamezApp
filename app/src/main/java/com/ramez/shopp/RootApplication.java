package com.ramez.shopp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;


import com.androidnetworking.AndroidNetworking;
import com.onesignal.OneSignal;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Utils.LocaleUtils;
import com.ramez.shopp.Utils.SharedPManger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class RootApplication extends Application {

    private static RootApplication rootApplication;
    private static final String ONESIGNAL_APP_ID = "06c038db-2891-4e93-b03f-ac3a308efc8e";

    SharedPManger sharedPManger;


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized RootApplication getInstance() {
        return rootApplication;
    }

    public  SharedPManger getSharedPManger() {
        return sharedPManger;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        rootApplication = this;
        sharedPManger = new SharedPManger(this);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.init(this,"",ONESIGNAL_APP_ID);

        String appLanguage = UtilityApp.getLanguage();
        if (appLanguage == null) {
            appLanguage = Constants.English;
            UtilityApp.setLanguage(appLanguage);

            LocaleUtils.setLocale(new Locale(appLanguage));
            LocaleUtils.updateConfig(rootApplication, rootApplication.getResources().getConfiguration());

        } else {
            UtilityApp.setLanguage(appLanguage);
            LocaleUtils.setLocale(new Locale(appLanguage));
            LocaleUtils.updateConfig(rootApplication, rootApplication.getResources().getConfiguration());

        }

        AndroidNetworking.initialize(getApplicationContext());



    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(rootApplication, newConfig);
    }



}

