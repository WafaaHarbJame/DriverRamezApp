package com.ramez.shopp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;


import com.androidnetworking.AndroidNetworking;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Utils.LocaleUtils;
import com.ramez.shopp.Utils.SharedPManger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class RootApplication extends Application {

    private static RootApplication rootApplication;

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


        String appLanguage = UtilityApp.getLanguage();
        if (appLanguage == null) {
            appLanguage = Constants.Arabic;
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

