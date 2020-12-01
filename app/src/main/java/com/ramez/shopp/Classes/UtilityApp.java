package com.ramez.shopp.Classes;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import com.google.gson.Gson;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.RootApplication;
import com.ramez.shopp.Utils.LocaleUtils;


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

//            Log.i("Utility", "Log versionNumber " + versionNumber);

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
        String userToken = RootApplication.getInstance().getSharedPManger().getDataString(Constants.KEY_MEMBER, null);
        return userToken != null;
    }


    public static void logOut() {

        if (isLogin()) {

//            MemberModel user = getUserData();

//            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.NOTIFICATION_TOPIC + user.getId());
//            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.NOTIFICATION_USER_TOPIC + user.getId());
//            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.CHAT_TOPIC + user.getId());

        }

        RootApplication.getInstance().getSharedPManger().SetData(Constants.KEY_MEMBER, null);
    }

    public static void setToShPref(String key, String data) {
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



    public static Boolean isEnglish() {
        Boolean isEnglish = false;
        if (UtilityApp.getLanguage().equals(Constants.English))  isEnglish = true;
        return isEnglish;

    }






}