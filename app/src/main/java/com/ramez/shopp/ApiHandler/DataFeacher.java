package com.ramez.shopp.ApiHandler;


import android.app.Activity;

import com.ramez.shopp.Utils.SharedPManger;

import java.util.HashMap;
import java.util.Map;



public class DataFeacher {
    Activity activity;
    DataFetcherCallBack dataFetcherCallBack;
    ApiInterface apiService;

    //    Realm realm;
    SharedPManger sharedPManger;
//    int city;

    final String TAG = "Log";

    String accessToken;
    String lang;
    Map<String, Object> headerMap = new HashMap<>();


    public DataFeacher(Activity activity) {
        this.activity = activity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        this.dataFetcherCallBack = (obj, func, IsSuccess) -> {

        };


    }
}
