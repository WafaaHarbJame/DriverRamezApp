package com.ramez.shopp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;


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

    public static Map<String, Object> changesMap = new HashMap<>();

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

    public synchronized SharedPManger getSharedPManger() {
        return sharedPManger;
    }

//    @GlideModule
//    public final class MyAppGlideModule extends AppGlideModule {}

    @Override
    public void onCreate() {
        super.onCreate();

        rootApplication = this;
        sharedPManger = new SharedPManger(this);

//        FirebaseMessaging.getInstance().subscribeToTopic("promotional");

//        Crashlytics crashlyticsKit = new Crashlytics.Builder()
//                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
//                .build();

//        Fabric.with(this, crashlyticsKit);

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().name("warda.realm")
//                .schemaVersion(1)
//                .deleteRealmIfMigrationNeeded()
//                .migration(new RootMigration())
//                .build();
//        Realm.setDefaultConfiguration(config);


        String appLanguage = UtilityApp.getLanguage();
        if (appLanguage == null) {
//            appLanguage = Locale.getDefault().getLanguage();
            appLanguage = Constants.Arabic;
            UtilityApp.setLanguage(appLanguage);

            LocaleUtils.setLocale(new Locale(appLanguage));
            LocaleUtils.updateConfig(rootApplication, rootApplication.getResources().getConfiguration());

        } else {
            UtilityApp.setLanguage(appLanguage);
            LocaleUtils.setLocale(new Locale(appLanguage));
            LocaleUtils.updateConfig(rootApplication, rootApplication.getResources().getConfiguration());

        }

        rootApplication = this;

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(rootApplication, newConfig);
    }


}

