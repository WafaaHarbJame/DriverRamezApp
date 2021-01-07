package com.ramez.shopp.Classes;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ramez.shopp.BuildConfig;
import com.ramez.shopp.Models.CountryModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.RootApplication;
import com.ramez.shopp.Utils.LocaleUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UtilityApp {

    public static String getUnique() {

        String android_id = Settings.Secure.getString(RootApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);

        return android_id;
    }

    public static int getAppVersion() {

        PackageInfo pinfo = null;
        try {
            pinfo = RootApplication.getInstance().getPackageManager().getPackageInfo(RootApplication.getInstance().getPackageName(), 0);

            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;

            Log.i("Utility", "Log versionNumber " + versionNumber);
            Log.i("Utility", "Log versionName " + versionName);
            Log.i("Utility", "Log VERSION_CODE " + BuildConfig.VERSION_CODE);

            return versionNumber;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


//        int versionCode = BuildConfig.VERSION_CODE;


        return 0;
    }

    public static String getAppVersionStr() {

        PackageInfo pinfo = null;
        try {
            pinfo = RootApplication.getInstance().getPackageManager().getPackageInfo(RootApplication.getInstance().getPackageName(), 0);

            String versionName = pinfo.versionName;

            Log.i("Utility", "Log versionName " + versionName);
            Log.i("Utility", "Log VERSION_CODE " + BuildConfig.VERSION_CODE);


//            Log.i("Utility", "Log versionNumber " + versionNumber);

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


//        int versionCode = BuildConfig.VERSION_CODE;


        return "";
    }

    public static boolean isFirstRun() {

        boolean isFirstRun = RootApplication.getInstance().getSharedPManger().getDataBool(Constants.KEY_FIRST_RUN, true);
        return isFirstRun;
    }

    public static void setIsFirstRun(boolean isFirstRun) {

        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_FIRST_RUN, isFirstRun);
    }


    public static boolean isLogin() {
        String userToken = RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_MEMBER);
        return userToken != null;
    }

//    public static boolean isLogin() {
//        boolean isLogin = RootApplication.getInstance().getSharedPManger().getDataBool(Constants.KEY_LOGIN_PREFERANCE, false);
//        return isLogin;
//    }


    public static void logOut() {
        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_MEMBER, null);
        RootApplication.getInstance().getSharedPManger().RemoveData(Constants.KEY_LOGIN_PREFERANCE);

    }

    public static void setToShPref(String key, String data) {
        RootApplication.getInstance().getSharedPManger().SetData(key, data);
    }


    public static void setToShPref(String key, boolean data) {
        RootApplication.getInstance().getSharedPManger().SetData(key, data);
    }
    public static String getFromShPref(String key) {
        return RootApplication.getInstance().getSharedPManger().getDataString(key);
    }

    public static void setFCMToken(String fcmToken) {
        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_FIREBASE_TOKEN, fcmToken);
    }

    public static String getFCMToken() {
        return RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_FIREBASE_TOKEN);
    }

    public static void setLanguage(String language) {
        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_MEMBER_LANGUAGE, language);
    }


    public static String getLanguage() {
        return RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_MEMBER_LANGUAGE);
    }


    public static void setAppLanguage() {
        String lang = getLanguage();
        if (lang == null) lang = Constants.English;
        LocaleUtils.setLocale(new Locale(lang));
        LocaleUtils.updateConfig(RootApplication.getInstance(), RootApplication.getInstance().getResources().getConfiguration());

    }

    public static void setUserData(MemberModel user) {
        String userData = new Gson().toJson(user);
        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_MEMBER, userData);
    }

    public static MemberModel getUserData() {
        String userJsonData = RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_MEMBER);
        MemberModel user = new Gson().fromJson(userJsonData, MemberModel.class);
        return user;
    }


    public static void setSetting(SettingModel user) {
        String settingData = new Gson().toJson(user);
        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_SETTING, settingData);
    }


    public static SettingModel getSettingData() {
        String settingJsonData = RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_SETTING);
        SettingModel settingModel = new Gson().fromJson(settingJsonData, SettingModel.class);
        return settingModel;
    }




    public static void setCountriesData(ArrayList<CountryModel> countryModelList) {
        String userData = new Gson().toJson(countryModelList);
        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_COUNTRIES, userData);
    }

    public static ArrayList<CountryModel> getCountriesData() {
        String dataString = RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_COUNTRIES);
        return new Gson().fromJson(dataString, new TypeToken<List<CountryModel>>() {
        }.getType());

    }

    public static void setLocalData(LocalModel model) {
        String localData = new Gson().toJson(model);
        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_Local, localData);
    }

    public static LocalModel getLocalData() {
        String localJsonData = RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_Local);
        LocalModel model = new Gson().fromJson(localJsonData, LocalModel.class);
        return model;
    }



    public static Boolean isEnglish() {
        Boolean isEnglish = false;
        if (UtilityApp.getLanguage().equals(Constants.English))  isEnglish = true;
        return isEnglish;

    }






}
