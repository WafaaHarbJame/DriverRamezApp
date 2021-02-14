package com.ramez.driver.ApiHandler;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.GlobalData;
import com.ramez.driver.Classes.OtpModel;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Classes.orderListCall;
import com.ramez.driver.Models.LoginResultModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.OrderCall;
import com.ramez.driver.Models.RamezOrderCall;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.Models.ReviewModel;

import java.io.File;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataFeacher {
    final String TAG = "Log";
    final String LOGIN_URL = "/"+ GlobalData.COUNTRY+ "/GroceryStoreApi/api/v3/Account/login";
    DataFetcherCallBack dataFetcherCallBack;
    ApiInterface apiService;
    //    int city;
    String accessToken;
    String lang;
    Map<String, Object> headerMap = new HashMap<>();
    private Callback callbackApi;

    public DataFeacher() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        this.dataFetcherCallBack = (obj, func, IsSuccess) -> {

        };

        headerMap.put("ApiKey", Constants.api_key);
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");

        callbackApi = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {


                if (response.isSuccessful()) {

//                    ResultAPIModel result = (ResultAPIModel) response.body();

                    if (dataFetcherCallBack != null)
                        dataFetcherCallBack.Result(response.body(), Constants.success, true);
//                    if (dataFetcherCallBack != null) {
//
//                        if (result != null && result.status == 0) {
//                            dataFetcherCallBack.Result(response.body(), Constants.ERROR, false);
//
//                        } else {
//                            dataFetcherCallBack.Result(response.body(), Constants.success, true);
//
//                        }
//                    }

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


    public DataFeacher(boolean isLong, DataFetcherCallBack dataFetcherCallBack) {
        this.dataFetcherCallBack = dataFetcherCallBack;
        apiService = isLong ? ApiClient.getLongClient().create(ApiInterface.class) : ApiClient.getClient().create(ApiInterface.class);

        headerMap.put("ApiKey", Constants.api_key);
        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");

        callbackApi = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {
                    System.out.println("Log url " + call.request().url().url().getPath());

                    String url = call.request().url().url().getPath();
                    if (url.equals(LOGIN_URL)) {
                        LoginResultModel result = (LoginResultModel) response.body();

                        if (dataFetcherCallBack != null) {

                            if (result != null && result.getStatus() == 0) {
                                dataFetcherCallBack.Result(response.body(), Constants.ERROR, false);

                            } else {
                                dataFetcherCallBack.Result(response.body(), Constants.success, true);
                            }
                        }

                    } else {
                        if (dataFetcherCallBack != null)
                            dataFetcherCallBack.Result(response.body(), Constants.success, true);
                    }

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

    public DataFeacher(boolean isLong, DataFetcherCallBack dataFetcherCallBack, String s) {
        this.dataFetcherCallBack = dataFetcherCallBack;
        apiService = isLong ? ApiClient.getRestClient().create(ApiInterface.class) : ApiClient.getRestClient().create(ApiInterface.class);

        lang = UtilityApp.getLanguage();
        if (lang == null) {
            lang=Locale.getDefault().getLanguage();
        }

        headerMap.put("lang", lang);

        headerMap.put("Accept", "application/json");
        headerMap.put("Content-Type", "application/json");

        callbackApi = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {
                    System.out.println("Log url " + call.request().url().url().getPath());

                    String url = call.request().url().url().getPath();
                    if (url.equals(LOGIN_URL)) {
                        LoginResultModel result = (LoginResultModel) response.body();

                        if (dataFetcherCallBack != null) {

                            if (result != null && result.getStatus() == 0) {
                                dataFetcherCallBack.Result(response.body(), Constants.ERROR, false);

                            } else {
                                dataFetcherCallBack.Result(response.body(), Constants.success, true);
                            }
                        }

                    } else {
                        if (dataFetcherCallBack != null)
                            dataFetcherCallBack.Result(response.body(), Constants.success, true);
                    }

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
        params.put("device_type", Constants.deviceType);
        params.put("device_token", memberModel.getDeviceToken());
        params.put("device_id", memberModel.getDeviceToken());

        Log.i(TAG, "Log loginHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log getMobileNumber " + memberModel.getMobileNumber());
        Log.i(TAG, "Log password " + memberModel.getPassword());
        Log.i(TAG, "Log device_type " + "android");
        Log.i(TAG, "Log device_token " + memberModel.getDeviceToken());
        Log.i(TAG, "Log getDeviceId " + memberModel.getDeviceId());
        Log.i(TAG, "Log user_type " + memberModel.getUserType());

        Call call = apiService.loginUserHandle(headerMap, params);
        call.enqueue(callbackApi);

    }


    public void loginDriverHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("mobile", memberModel.getMobileNumber());
        params.put("password", memberModel.getPassword());

        Log.i(TAG, "Log loginHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log getUserMobile " + memberModel.getMobileNumber());
        Log.i(TAG, "Log password " + memberModel.getPassword());

        String url = "https://ristsys.store/api/LoginDelivery";

        Call call = apiService.loginDriverHandle(url,headerMap, params);

        call.enqueue(callbackApi);

    }




    public void DeliveryInvoiceUpdate(String product_id) {

        Map<String, Object> params = new HashMap<>();
        params.put("product_id", product_id);


        Log.i(TAG, "Log DeliveryInvoiceUpdate");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log product_id " + product_id);

        String url = "https://ristsys.store/api/DeliveryInvoiceUpdate";

        Call call = apiService.DeliveryInvoiceUpdate(url,headerMap, params);

        call.enqueue(callbackApi);

    }


    public void GetInvoiceProductPickStatus(String product_id,int pick_status) {

        Map<String, Object> params = new HashMap<>();
        params.put("ip_id", product_id);
        params.put("pick_status", pick_status);

        Log.i(TAG, "Log GetInvoiceProductPickStatus");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log product_id " + product_id);

        String url = "https://ristsys.store/api/GetInvoiceProductPickStatus";

        Call call = apiService.GetInvoiceProductPickStatus(url,headerMap, params);

        call.enqueue(callbackApi);

    }




    public void GetDeliveryShops(int  delivery_id) {

        Map<String, Object> params = new HashMap<>();
        params.put("delivery_id", delivery_id);


        Log.i(TAG, "Log GetDeliveryShops");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log delivery_id " + delivery_id);

        String url = "https://ristsys.store/api/GetDeliveryShops";

        Call call = apiService.GetDeliveryShops(url,headerMap, params);

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
        params.put("name", memberModel.getName());
//        params.put("country", "BH");
        params.put("city", memberModel.getCity());
        params.put("email", memberModel.getEmail());

        Log.i(TAG, "Log RegisterHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log username " + memberModel.getName());
        Log.i(TAG, "Log password " + memberModel.getPassword());
        Log.i(TAG, "Log device_type " + "android");
        Log.i(TAG, "Log city ID  " + memberModel.getCity());
        Log.i(TAG, "Log mobile_number " + memberModel.getMobileNumber());
        Log.i(TAG, "Log EMAIL " + memberModel.getEmail());

        Call call = apiService.registerUserHandle(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void ForgetPasswordHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("mobile_number", memberModel.getMobileNumber());

        Log.i(TAG, "Log ForgetPasswordHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " + memberModel.getMobileNumber());

        Call call = apiService.ForgetPasswordHandle(headerMap, params);
        call.enqueue(callbackApi);
    }


    public void ChangePasswordHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", memberModel.getId());
        params.put("password", memberModel.getPassword());
        params.put("new_password", memberModel.getNew_password());

        Log.i(TAG, "Log ChangePasswordHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + memberModel.getId());
        Log.i(TAG, "Log password " + memberModel.getPassword());
        Log.i(TAG, "Log new_password " + memberModel.getNew_password());


        Call call = apiService.changePasswordHandle(headerMap, params);
        call.enqueue(callbackApi);

    }


    public void UpdatePasswordHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("mobile_number", memberModel.getMobileNumber());
        params.put("password", memberModel.getPassword());
        params.put("re_password", memberModel.getNew_password());

        Log.i(TAG, "Log UpdatePasswordHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " +memberModel.getMobileNumber());
        Log.i(TAG, "Log password " + memberModel.getPassword());
        Log.i(TAG, "Log re_password " + memberModel.getNew_password());


        Call call = apiService.updatePasswordHandle(headerMap, params);
        call.enqueue(callbackApi);

    }



    public void UpdateTokenHandle(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", memberModel.getId());
        params.put("device_token", memberModel.getDeviceToken());

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

        String countryCode = "";
        if (UtilityApp.getLocalData().getShortname() != null)
            countryCode = UtilityApp.getLocalData().getShortname();
        else countryCode =GlobalData.COUNTRY;

        String url = " https://risteh.com/" + countryCode + "/GroceryStoreApi/api/v3/Locations/citiesByCountry";

        if (UtilityApp.getLanguage() != null) {
            lang = UtilityApp.getLanguage();
        } else {
            lang = Locale.getDefault().getLanguage();
        }

        Log.i(TAG, "Log lang " + lang);

        Map<String, Object> params = new HashMap<>();
        params.put("country_id", country_id);


        Call call = apiService.GetCity(url, headerMap, params);
        call.enqueue(callbackApi);
    }

    public void sendOpt(String mobile_number) {

        Log.i(TAG, "Log sendOpt");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " + mobile_number);

        Call call = apiService.GetOptHandle(headerMap, mobile_number);
        call.enqueue(callbackApi);

    }

    public void VerifyOtpHandle(String mobile_number, String otp) {

        Log.i(TAG, "Log VerifyOtpHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log mobile_number " + mobile_number);
        Log.i(TAG, "Log otp " + otp);

        OtpModel otpModel=new OtpModel();
        otpModel.setMobileNumber(mobile_number);
        otpModel.setOtp(otp);

        Call call = apiService.otpVerifyUserHandle(headerMap, otpModel);
        call.enqueue(callbackApi);
    }



    public void setDefaultAddress(int user_id,int address_id) {

        Log.i(TAG, "Log setDefaultAddress");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log addressId " + address_id);


        Call call = apiService.setDefaultAddress(headerMap, user_id,address_id);
        call.enqueue(callbackApi);
    }





    public void GetAreasHandle(int country_id) {

        Log.i(TAG, "Log GetAreasHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);

        Call call = apiService.GetAreas(headerMap, country_id);
        call.enqueue(callbackApi);
    }




    public void GetMainPage(int category_id, int country_id, int city_id, String user_id) {

        Log.i(TAG, "Log GetMainPage");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log city_id " + city_id);

        Call call = apiService.GetMainPage(headerMap, category_id, country_id, city_id, user_id);
        call.enqueue( callbackApi);
    }

    public void GetSingleProduct(int country_id, int city_id, int product_id, String user_id) {

        Log.i(TAG, "Log GetSingleProduct");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log product_id " + product_id);
        Log.i(TAG, "Log user_id " + user_id);

        Call call = apiService.GetSignalProducts(headerMap, country_id, city_id, product_id, user_id);
        call.enqueue(callbackApi);
    }


    public void GetAllCategories(int sotre_id) {

        Log.i(TAG, "Log GetSingleProduct");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + sotre_id);

        Call call = apiService.GetAllCategories(headerMap, sotre_id);
        call.enqueue(callbackApi);
    }

    public void addToFavoriteHandle(int user_id, int store_ID, int product_id) {

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

    public void deleteFromFavoriteHandle(int user_id, int store_ID, int product_id) {

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

    public void addCartHandle(int productId, int product_barcode_id, int quantity, int userId, int storeId) {

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

    public void updateCartHandle(int productId, int product_barcode_id, int quantity, int userId, int storeId, int cart_id, String update_quantity) {

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

    public void deleteCartHandle(int productId, int product_barcode_id, int cart_id, int userId, int storeId) {

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


    public void updateRemarkCartHandle(int cart_id, String remark) {

        Map<String, Object> params = new HashMap<>();
        params.put("cart_id", cart_id);
        params.put("remark", remark);



        Log.i(TAG, "Log updateRemarkCartHandle");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log remark " + remark);
        Log.i(TAG, "Log cart_id " + cart_id);


        Call call = apiService.updateRemark(headerMap, params);
        call.enqueue(callbackApi);
    }


    public void GetCarts(int sotre_id, int user_id) {

        Log.i(TAG, "Log GetCarts");
        Log.i(TAG, "Log headerMap " + headerMap);

        Log.i(TAG, "Log sotre_id " + sotre_id);
        Log.i(TAG, "Log user_id " + user_id);

        Call call = apiService.GetACarts(headerMap, user_id, sotre_id);
        call.enqueue(callbackApi);


    }


    public void getFavorite(int category_id, int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {

        Log.i(TAG, "Log getFavorite");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log category_id " + category_id);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log city_id " + city_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log filter " + filter);
        Log.i(TAG, "Log page_number " + page_number);
        Log.i(TAG, "Log page_size " + page_size);

        Call call = apiService.GetFavoriteProducts(headerMap, category_id, country_id, city_id, user_id, filter, page_number, page_size);
        call.enqueue(callbackApi);
    }


    public void updateProfile(MemberModel memberModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", memberModel.getId());
        params.put("name", memberModel.getName());
        params.put("email", memberModel.getEmail());
        params.put("state", "1");
//        params.put("city", Integer.parseInt(String.valueOf(memberModel.getCity())));

        Log.i(TAG, "Log updateProfile");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log name " + memberModel.getName());
        Log.i(TAG, "Log email " + memberModel.getEmail());
        Log.i(TAG, "Log city " + memberModel.getCity());


        Call call = apiService.updateProfile(headerMap, params);
        call.enqueue(callbackApi);

    }


    public void uploadPhoto(int userId, File photo) {

        Log.i(TAG, "Log uploadPhoto");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log userId " + userId);
        Log.i(TAG, "Log photo Name  " + photo.getName());

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (photo != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
            builder.addFormDataPart("file", photo.lastModified() + ".png", requestBody);
        }

        MultipartBody requestBody = builder.build();

        Call call = apiService.uploadPhoto(headerMap, requestBody, userId);
        call.enqueue(callbackApi);

    }


    public void getUserDetails(int user_id) {

        Log.i(TAG, "Log getUserDetails");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + user_id);
        String url = "https://ristsys.store/api/CustomerInfo";

        Call call = apiService.getUserDetail(url,headerMap, user_id);
        call.enqueue(callbackApi);
    }




    public void barcodeSearch(int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {

        Log.i(TAG, "Log barcodeSearch");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log city_id " + city_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log filter " + filter);
        Log.i(TAG, "Log page_number " + page_number);
        Log.i(TAG, "Log page_size " + page_size);

        Call call = apiService.barcodeSearch(headerMap, country_id, city_id, user_id, filter, page_number, page_size);
        call.enqueue(callbackApi);
    }

    public void searchTxt(int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {

        Log.i(TAG, "Log searchTxt");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log city_id " + city_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log filter " + filter);
        Log.i(TAG, "Log page_number " + page_number);
        Log.i(TAG, "Log page_size " + page_size);

        Call call = apiService.searchProduct(headerMap, country_id, city_id, user_id, filter, page_number, page_size);
        call.enqueue(callbackApi);
    }

    public Call autocomplete(int country_id, int city_id, String user_id, String text, int page_number, int page_size) {

        Log.i(TAG, "Log autocomplete");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log city_id " + city_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log text " + text);
        Log.i(TAG, "Log page_number " + page_number);
        Log.i(TAG, "Log page_size " + page_size);

        Call call = apiService.autocomplete(headerMap, country_id, city_id, user_id, text);
        call.enqueue(callbackApi);

        return call;
    }

    public void getCatProductList(int category_id, int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {

        Log.i(TAG, "Log getCatProductList");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log category_id " + category_id);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log city_id " + city_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log filter " + filter);
        Log.i(TAG, "Log page_number " + page_number);
        Log.i(TAG, "Log page_size " + page_size);

        Call call = apiService.getCatProductList(headerMap, category_id, country_id, city_id, user_id, filter, page_number, page_size);
        call.enqueue(callbackApi);
    }


    public void getPastOrders(int user_id) {

        Log.i(TAG, "Log getPastOrders");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + user_id);

        Call call = apiService.getPastOrders(headerMap, user_id);
        call.enqueue(callbackApi);
    }

    public void getUpcomingOrders(int user_id) {

        Log.i(TAG, "Log getUpcomingOrders");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + user_id);

        Call call = apiService.getUpcomingOrders(headerMap, user_id);
        call.enqueue(callbackApi);
    }

    public void getPaymentMethod(int sotre_id) {

        Log.i(TAG, "Log getPaymentMethod");

        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log sotre_id " + sotre_id);

        Call call = apiService.getPaymentMethod(headerMap, sotre_id);
        call.enqueue(callbackApi);
    }


    public void getDeliveryTimeList(int sotre_id) {

        Log.i(TAG, "Log getDeliveryTimeList");

        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log sotre_id " + sotre_id);

        Call call = apiService.getDeliveryTimeList(headerMap, sotre_id);
        call.enqueue(callbackApi);
    }

    public void makeOrder(OrderCall orderCalls) {
        OrderCall orderCall = new OrderCall();
        orderCall.user_id = orderCalls.user_id;
        orderCall.store_ID = orderCalls.store_ID;
        orderCall.address_id = orderCalls.address_id;
        orderCall.payment_method = orderCalls.payment_method;
        orderCall.coupon_code_id = orderCalls.coupon_code_id;
        orderCall.delivery_date_id = orderCalls.delivery_date_id;
        orderCall.expressDelivery = orderCalls.expressDelivery;

        Log.i(TAG, "Log makeOrder");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + orderCalls.user_id);
        Log.i(TAG, "Log store_ID " + orderCalls.store_ID);
        Log.i(TAG, "Log addressId " + orderCalls.address_id);
        Log.i(TAG, "Log payment_method " + orderCalls.payment_method);
        Log.i(TAG, "Log coupon_code_id " + orderCalls.coupon_code_id);
        Log.i(TAG, "Log delivery_date_id " + orderCalls.delivery_date_id);
        Log.i(TAG, "Log expressDelivery " + orderCalls.expressDelivery);

        Call call = apiService.makeOrder(headerMap, orderCall);
        call.enqueue(callbackApi);
    }


    public void getRamezOrder(RamezOrderCall orderCalls) {
        RamezOrderCall orderCall = new RamezOrderCall();
        orderCall.user_id = orderCalls.user_id;
        orderCall.store_id = orderCalls.store_id;
        orderCall.type = orderCalls.type;
        orderCall.mode = orderCalls.mode;

        Log.i(TAG, "Log getRamezOrder");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + orderCalls.user_id);
        Log.i(TAG, "Log store_ID " + orderCalls.store_id);
        Log.i(TAG, "Log mode " + orderCalls.mode);
        Log.i(TAG, "Log type " + orderCalls.type);
        Call call = apiService.GetOrdersList(headerMap, orderCall);
        call.enqueue(callbackApi);
    }


    public void ChangeOrderStatusFromRamez(RamezOrderCall orderCalls) {
        RamezOrderCall orderCall = new RamezOrderCall();
        orderCall.user_id = orderCalls.user_id;
        orderCall.store_id = orderCalls.store_id;
        orderCall.type = orderCalls.type;
        orderCall.mode = orderCalls.mode;
        orderCall.order_id = orderCalls.order_id;

        Log.i(TAG, "Log ChangeOrderStatusFromRamez");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + orderCalls.user_id);
        Log.i(TAG, "Log store_ID " + orderCalls.store_id);
        Log.i(TAG, "Log mode " + orderCalls.mode);
        Log.i(TAG, "Log type " + orderCalls.type);
        Log.i(TAG, "Log order_id " + orderCalls.order_id);
        Call call = apiService.changeOrderStatus(headerMap, orderCall);
        call.enqueue(callbackApi);
    }



    public void getInvoiceInfo(RamezOrderCall orderCalls) {
        RamezOrderCall orderCall = new RamezOrderCall();
        orderCall.user_id = orderCalls.user_id;
        orderCall.store_id = orderCalls.store_id;
        orderCall.type = orderCalls.type;
        orderCall.mode = orderCalls.mode;
        orderCall.order_id = orderCalls.order_id;

        Log.i(TAG, "Log getInvoiceInfo");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log user_id " + orderCalls.user_id);
        Log.i(TAG, "Log store_ID " + orderCalls.store_id);
        Log.i(TAG, "Log mode " + orderCalls.mode);
        Log.i(TAG, "Log type " + orderCalls.type);
        Log.i(TAG, "Log order_id " + orderCalls.order_id);
        Call call = apiService.GetOrderDetails(headerMap, orderCall);
        call.enqueue(callbackApi);
    }


    public void getProductList(int category_id, int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {

        Log.i(TAG, "Log getProductList");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log category_id " + category_id);
        Log.i(TAG, "Log country_id " + country_id);
        Log.i(TAG, "Log city_id " + city_id);
        Log.i(TAG, "Log user_id " + user_id);
        Log.i(TAG, "Log filter " + filter);
        Log.i(TAG, "Log page_number " + page_number);
        Log.i(TAG, "Log page_size " + page_size);

        Call call = apiService.getProductList(headerMap, category_id, country_id, city_id, user_id, filter, page_number, page_size);
        call.enqueue(callbackApi);
    }



    public void getRate(int productId, int store_id) {

        Map<String, Object> params = new HashMap<>();
        params.put("store_id", store_id);
        params.put("product_id", productId);

        Log.i(TAG, "Log getRate");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log store_ID " + store_id);
        Log.i(TAG, "Log product_id " + productId);


        Call call = apiService.GetRates(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void getAppRate() {

        Map<String, Object> params = new HashMap<>();

        Log.i(TAG, "Log getAppRate");
        Log.i(TAG, "Log headerMap " + headerMap);


        Call call = apiService.getAppRate(headerMap, params);
        call.enqueue(callbackApi);
    }



    public void setRate(ReviewModel reviewModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("store_id", reviewModel.getStoreId());
        params.put("user_id", reviewModel.getUser_id());
        params.put("product_id", reviewModel.getProductId());
        params.put("rate", reviewModel.getRate());
        params.put("comment", reviewModel.getComment());


        Log.i(TAG, "Log setRate");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log store_ID " + reviewModel.getStoreId());
        Log.i(TAG, "Log product_id " + reviewModel.getProductId());
        Log.i(TAG, "Log rate " + reviewModel.getRate());
        Log.i(TAG, "Log comment " + reviewModel.getComment());


        Call call = apiService.setRate(headerMap, params);
        call.enqueue(callbackApi);
    }

    public void setAppRate(ReviewModel reviewModel) {

        Map<String, Object> params = new HashMap<>();
        params.put("user_id", reviewModel.getUser_id());
        params.put("rate", reviewModel.getRate());
        params.put("comment", reviewModel.getComment());

        Log.i(TAG, "Log setAppRate");
        Log.i(TAG, "Log headerMap " + headerMap);

        Log.i(TAG, "Log user_id " + reviewModel.getUser_id());
        Log.i(TAG, "Log rate " + reviewModel.getRate());
        Log.i(TAG, "Log comment " + reviewModel.getComment());


        Call call = apiService.setAppRate(headerMap, params);
        call.enqueue(callbackApi);
    }


    public void getSetting() {

        Log.i(TAG, "Log getSetting");

        lang = UtilityApp.getLanguage();
        if (lang != null) {
           lang=UtilityApp.getLanguage();
        } else {
            lang=Locale.getDefault().getLanguage();

        }

        Log.i(TAG, "Log lang "+lang);


        Call call = apiService.getSetting(headerMap,lang);
        call.enqueue(callbackApi);
    }



    public void getValidate(String device_type, String  app_version, int app_build) {

        Log.i(TAG, "Log getValidate");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log app_version " + app_version);
        Log.i(TAG, "Log app_build " + app_build);

        Call call = apiService.getValidate(headerMap, device_type,app_version,app_build);
        call.enqueue(callbackApi);
    }


    public void getOrders(int user_id,String type,String filter) {

        Log.i(TAG, "Log getOrders");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log type " + type);
        Log.i(TAG, "Log filter " + filter);
        orderListCall orderListCall=new orderListCall();
        orderListCall.setUserId(user_id);
        orderListCall.setType(type);
        orderListCall.setFilter(filter);

        Call call = apiService.GetOrdersList(headerMap, orderListCall);
        call.enqueue(callbackApi);
    }

    public void getOrderDetails(int order_id,int user_id,int store_id,String type) {

        Log.i(TAG, "Log getOrderDetails");
        Log.i(TAG, "Log headerMap " + headerMap);
        Log.i(TAG, "Log type " + type);
        Log.i(TAG, "Log store_id " + store_id);
        Log.i(TAG, "Log store_id " + order_id);
        Log.i(TAG, "Log user_id " + user_id);

        orderListCall orderListCall=new orderListCall();
        orderListCall.setOrderId(order_id);
        orderListCall.setUserId(user_id);
        orderListCall.setStoreId(store_id);
        orderListCall.setType(type);

        Call call = apiService.GetOrderDetails(headerMap, orderListCall);
        call.enqueue(callbackApi);
    }

}