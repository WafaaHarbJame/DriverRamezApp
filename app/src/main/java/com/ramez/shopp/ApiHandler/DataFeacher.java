package com.ramez.shopp.ApiHandler;


import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.AddressModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ResultAPIModel;

import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataFeacher {
    final String TAG = "Log";
    Activity activity;
    DataFetcherCallBack dataFetcherCallBack;
    ApiInterface apiService;
    //    int city;
    String accessToken;
    String lang;
    Map<String, Object> headerMap = new HashMap<>();
    private Callback callbackApi;


    public DataFeacher(Activity activity) {
        this.activity = activity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        this.dataFetcherCallBack = (obj, func, IsSuccess) -> {

        };

        callbackApi = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

//                T result = response.body();
                if (response.isSuccessful()) {

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack.Result(response.body(), Constants.success, true);

                } else {
                    ResultAPIModel errorModel = null;
                    try {
                        String error = response.errorBody().string();
                        Log.e("Log", "Log error " + error);
                        errorModel = new Gson().fromJson(error, new TypeToken<ResultAPIModel>() {
                        }.getType());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dataFetcherCallBack.Result(errorModel, Constants.ERROR, false);

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                if ((t instanceof UnknownHostException || t instanceof NoRouteToHostException) && dataFetcherCallBack != null) {
                    dataFetcherCallBack.Result(null, Constants.NO_CONNECTION, false);
                } else {
                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack.Result(null, Constants.FAIL, false);
                }
            }
        };

    }

    public DataFeacher(Activity activity, DataFetcherCallBack dataFetcherCallBack) {
        this.activity = activity;
        this.dataFetcherCallBack = dataFetcherCallBack;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        headerMap.put("ApiKey", Constants.api_key);
        headerMap.put("Content-Type", "application/json");

        callbackApi = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {
                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack.Result(response.body(), Constants.success, true);

                } else {
                    ResultAPIModel errorModel = null;
                    try {
                        String error = response.errorBody().string();
                        Log.e("Log", "Log error " + error);
                        errorModel = new Gson().fromJson(error, new TypeToken<ResultAPIModel>() {
                        }.getType());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dataFetcherCallBack.Result(errorModel, Constants.ERROR, false);

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                if ((t instanceof UnknownHostException || t instanceof NoRouteToHostException) && dataFetcherCallBack != null) {
                    dataFetcherCallBack.Result(null, Constants.NO_CONNECTION, false);
                } else {
                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack.Result(null, Constants.FAIL, false);
                }
            }
        };


    }

    public void loginHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("mobile_number", memberModel.getMobileNumber());
        params.put("password", memberModel.getPassword());
        params.put("user_type", memberModel.getUserType());
        params.put("device_type", "android");
        params.put("device_token", memberModel.getDeviceToken());
        params.put("device_id", memberModel.getDeviceToken());

        Log.i(TAG, "Log loginHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log username " + memberModel.getMobileNumber());
        Log.i(TAG, "Log password " + memberModel.getPassword());
        Log.i(TAG, "Log device_type " + "android");
        Log.i(TAG, "Log device_token " + memberModel.getDeviceToken());
        Log.i(TAG, "Log device_type " + memberModel.getDeviceId());

        Call call = apiService.loginUserHandle(headerMap, params);
        call.enqueue(callbackApi);

    }

    public void logOut(MemberModel memberModel) {

        Log.i(TAG, "Log logOut");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + memberModel.getId());

        Call call = apiService.logout(headerMap, memberModel.getId());
        call.enqueue(callbackApi);

    }

    public void RegisterHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("mobile_number", memberModel.getMobileNumber());
        params.put("password", memberModel.getPassword());
        params.put("user_type", Constants.user_type);
        params.put("device_type", memberModel.getDeviceType());
        params.put("device_token", memberModel.getDeviceToken());
        params.put("name", memberModel.getName());
        params.put("country", memberModel.getCountry());
        params.put("city", memberModel.getCity());
        params.put("email", memberModel.getEmail());
        params.put("device_id", memberModel.getDeviceId());

        Log.i(TAG, "Log RegisterHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log username " + memberModel.getName());
        Log.i(TAG, "Log password " + memberModel.getPassword());
        Log.i(TAG, "Log device_type " + "android");
        Log.i(TAG, "Log device_token " + memberModel.getDeviceToken());
        Log.i(TAG, "Log city ID  " + memberModel.getCity());
        Log.i(TAG, "Log country Name " + memberModel.getCountry());

        Call call = apiService.registerUserHandle(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void ForgetPasswordHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("mobile_number", memberModel.getMobileNumber());
        params.put("user_type",memberModel.getUserType());

        Log.i(TAG, "Log ForgetPasswordHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " + memberModel.getMobileNumber());
        Log.i(TAG, "Log user_type " + memberModel.getUserType());

        Call call = apiService.ForgetPasswordHandle(headerMap, params);
        call.enqueue(callbackApi);
    }


    public void ChangePasswordHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", memberModel.getId());
        params.put("password",memberModel.getPassword());
        params.put("new_password",memberModel.getNew_password());

        Log.i(TAG, "Log ChangePasswordHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + memberModel.getId());
        Log.i(TAG, "Log password " + memberModel.getPassword());
        Log.i(TAG, "Log new_password " + memberModel.getNew_password());


        Call call = apiService.changePasswordHandle(headerMap, params);
        call.enqueue(callbackApi);

    }

    public void UpdateTokenHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", memberModel.getId());
        params.put("device_token",memberModel.getDeviceToken());

        Log.i(TAG, "Log UpdateTokenHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + memberModel.getId());
        Log.i(TAG, "Log device_token " + memberModel.getDeviceToken());

        Call call = apiService.UpdateTokenHandle(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void CountryHandle() {

        Map<String, Object> params = new HashMap<>();
        lang = UtilityApp.getLanguage();
        if (lang != null) {
            params.put("lan", lang);
        } else {
            params.put("lan", Locale.getDefault().getLanguage());

        }

        Log.i(TAG, "Log GetCountryHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log lan " + lang);

        Call call = apiService.GetCountry(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void CityHandle(int country_id) {

        Log.i(TAG, "Log CityHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);

        if(UtilityApp.getLanguage()!=null){
            lang = UtilityApp.getLanguage();
        }
        else {
            lang= Locale.getDefault().getLanguage();
        }


        Log.i(TAG, "Log lang " + lang);

        Map<String, Object> params = new HashMap<>();
        params.put("country_id", country_id);


        Call call = apiService.GetCity(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void sendOpt(String mobile_number) {

        Log.i(TAG, "Log sendOpt");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " +mobile_number);

        Call call = apiService.GetOptHandle(headerMap, mobile_number);
        call.enqueue(callbackApi);

    }

    public void VerifyOtpHandle(String mobile_number,String otp) {

        Log.i(TAG, "Log VerifyOtpHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " + mobile_number);
        Log.i(TAG, "Log otp " + otp);
        Map<String, Object> params = new HashMap<>();
        params.put("mobile_number", mobile_number);
        params.put("otp", otp);

        Call call = apiService.otpVerifyUserHandle(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void GetAddressHandle(int user_id) {

        Log.i(TAG, "Log GetAddressHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + user_id);

        Call call = apiService.GetUserAddress(headerMap, user_id);
        call.enqueue(callbackApi);
    }

    public void GetAddressByIdHandle(int address_id) {

        Log.i(TAG, "Log GetAddressByIdHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log address_id " + address_id);

        Call call = apiService.GetAddressById(headerMap, address_id);
        call.enqueue(callbackApi);

    }

    public void CreateAddressHandle(AddressModel addressModel) {


        Log.i(TAG, "Log CreateAddressHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " + addressModel.getMobileNumber());
        Log.i(TAG, "Log user id  " + addressModel.getUserId());
        Log.i(TAG, "Log name " + addressModel.getName());
        Log.i(TAG, "Log area id  " + addressModel.getAreaId());
        Log.i(TAG, "Log state id  " + addressModel.getState());
        Log.i(TAG, "Log getBlock   " + addressModel.getBlock());
        Log.i(TAG, "Log getStreetDetails  " + addressModel.getStreetDetails());
        Log.i(TAG, "Log getHouseNo  " + addressModel.getHouseNo());
        Log.i(TAG, "Log apartment_no  " + addressModel.getApartmentNo());
        Log.i(TAG, "Log phone_prefix  " + addressModel.getPhonePrefix());
        Log.i(TAG, "Log mobile_number  " + addressModel.getMobileNumber());
        Log.i(TAG, "Log longitude  " + addressModel.getLongitude());
        Log.i(TAG, "Log getLatitude  " + addressModel.getLatitude());
        Log.i(TAG, "Log google_address  " + addressModel.getGoogleAddress());

        Map<String, Object> params = new HashMap<>();
        params.put("user_id",addressModel.getUserId());
        params.put("name",addressModel.getName());
        params.put("area_id",addressModel.getAreaId());
        params.put("state_id",addressModel.getState());
        params.put("block",addressModel.getBlock());
        params.put("street_details",addressModel.getStreetDetails());
        params.put("house_no",addressModel.getHouseNo());
        params.put("apartment_no",addressModel.getApartmentNo());
        params.put("phone_prefix",addressModel.getPhonePrefix());
        params.put("mobile_number",addressModel.getMobileNumber());
        params.put("longitude",addressModel.getLongitude());
        params.put("latitude",addressModel.getLatitude());
        params.put("google_address",addressModel.getGoogleAddress());


        Call call = apiService.CreateNewAddress(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void GetAreasHandle(int country_id) {

        Log.i(TAG, "Log GetAreasHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);

        Call call = apiService.GetAreas(headerMap, country_id);
        call.enqueue(callbackApi);
    }

    public void deleteAddressHandle(int address_id) {

        Log.i(TAG, "Log deleteAddressHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log address_id " + address_id);

        Call call = apiService.deleteAddress(headerMap, address_id);
        call.enqueue(callbackApi);

    }


    public void GetMainPage(int category_id,int country_id,int city_id,String user_id) {

        Log.i(TAG, "Log GetAreasHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);

        Call call = apiService.GetMainPage(headerMap, category_id,country_id,city_id,user_id);
        call.enqueue(callbackApi);
    }

    public void GetSingleProduct(int country_id,int city_id,int product_id,String user_id) {

        Log.i(TAG, "Log GetSingleProduct");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);

        Call call = apiService.GetSignalProducts(headerMap,country_id,city_id,product_id,user_id);
        call.enqueue(callbackApi);
    }


    public void GetAllCategories(int sotre_id) {

        Log.i(TAG, "Log GetSingleProduct");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + sotre_id);

        Call call = apiService.GetAllCategories(headerMap,sotre_id);
        call.enqueue(callbackApi);
    }

    public void addToFavoriteHandle(int user_id,int store_ID,int product_id ) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("store_ID", store_ID);
        params.put("product_id", product_id);


        Log.i(TAG, "Log addToFavoriteHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log store_ID " + store_ID);
        Log.i(TAG, "Log product_id " + product_id);

        Call call = apiService.addFavouriteProduct(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void deleteFromFavoriteHandle(int user_id,int store_ID,int product_id ) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("store_ID", store_ID);
        params.put("product_id", product_id);

        Log.i(TAG, "Log deleteFromFavoriteHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log store_ID " + store_ID);
        Log.i(TAG, "Log product_id " + product_id);

        Call call = apiService.deleteFavouriteProduct(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void addCartHandle(int productId,int product_barcode_id,int quantity, int userId,int storeId ) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("store_ID", storeId);
        params.put("product_id", productId);
        params.put("quantity", quantity);
        params.put("product_barcode_id", product_barcode_id);


        Log.i(TAG, "Log addCartHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + userId);
        Log.i(TAG, "Log store_ID " + storeId);
        Log.i(TAG, "Log product_id " + productId);
        Log.i(TAG, "Log product_barcode_id " + product_barcode_id);
        Log.i(TAG, "Log quantity " + quantity);


        Call call = apiService.addToCart(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void updateCartHandle(int productId,int product_barcode_id,int quantity, int userId,int storeId,int cart_id,String update_quantity ) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("store_ID", storeId);
        params.put("product_id", productId);
        params.put("quantity", quantity);
        params.put("cart_id", cart_id);
        params.put("update_type", update_quantity);
        params.put("product_barcode_id", product_barcode_id);



        Log.i(TAG, "Log updateCartHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + userId);
        Log.i(TAG, "Log store_ID " + storeId);
        Log.i(TAG, "Log product_id " + productId);
        Log.i(TAG, "Log product_barcode_id " + product_barcode_id);
        Log.i(TAG, "Log quantity " + quantity);


        Call call = apiService.updateCart(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void deleteCartHandle(int productId,int product_barcode_id,int cart_id, int userId,int storeId ) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("store_ID", storeId);
        params.put("product_id", productId);
        params.put("cart_id", cart_id);
        params.put("product_barcode_id", product_barcode_id);


        Log.i(TAG, "Log deleteCartHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + userId);
        Log.i(TAG, "Log store_ID " + storeId);
        Log.i(TAG, "Log product_id " + productId);
        Log.i(TAG, "Log product_barcode_id " + product_barcode_id);
        Log.i(TAG, "Log cart_id " + cart_id);


        Call call = apiService.deleteCartItems(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void GetCarts(int sotre_id,int user_id) {

        Log.i(TAG, "Log GetCarts");
        Log.i(TAG, "Log headerMap " + headerMap);

        Log.i(TAG, "Log sotre_id " + sotre_id);
        Log.i(TAG, "Log user_id " + user_id);

        Call call = apiService.GetACarts(headerMap,user_id,sotre_id);
        call.enqueue(callbackApi);


    }


    public void getFavorite(int category_id,int country_id,int city_id,String user_id,String filter,int page_number,int page_size ) {

        Log.i(TAG, "Log getFavorite");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log category_id " + category_id);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log city_id " + city_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log filter " + filter);
        Log.i(TAG, "Log page_number " + page_number);
        Log.i(TAG, "Log page_size " + page_size);

        Call call = apiService.GetFavoriteProducts(headerMap, category_id,country_id,city_id,user_id,filter,page_number,page_size);
        call.enqueue(callbackApi);
    }


}
