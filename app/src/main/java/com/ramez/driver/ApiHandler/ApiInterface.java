package com.ramez.driver.ApiHandler;


import com.ramez.driver.Classes.CityModelResult;
import com.ramez.driver.Classes.OtpModel;
import com.ramez.driver.Classes.SettingModel;
import com.ramez.driver.Classes.orderListCall;
import com.ramez.driver.Models.AreasResultModel;
import com.ramez.driver.Models.AutoCompeteResult;
import com.ramez.driver.Models.CartProcessModel;
import com.ramez.driver.Models.CartResultModel;
import com.ramez.driver.Models.CategoryResultModel;
import com.ramez.driver.Models.CountryModelResult;
import com.ramez.driver.Models.DeliveryResultModel;
import com.ramez.driver.Models.DeliveryStatusResponse;
import com.ramez.driver.Models.FavouriteResultModel;
import com.ramez.driver.Models.FinicalModels;
import com.ramez.driver.Models.GeneralModel;
import com.ramez.driver.Models.InvoiceInfoModel;
import com.ramez.driver.Models.LoginResultModel;
import com.ramez.driver.Models.MainModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.OrderCall;
import com.ramez.driver.Models.OrderItemDetail;
import com.ramez.driver.Models.OrderNewModel;
import com.ramez.driver.Models.OrdersResultModel;
import com.ramez.driver.Models.PaymentResultModel;
import com.ramez.driver.Models.ProductDetailsModel;
import com.ramez.driver.Models.ProfileData;
import com.ramez.driver.Models.RamesOrders;
import com.ramez.driver.Models.RamezOrderCall;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.Models.ReviewModel;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface

ApiInterface {


    @POST("v3/Account/userRegister")
    Call<LoginResultModel> registerUserHandle(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/driverRegister")
    Call<LoginResultModel> registerDriverHandle(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/login")
    Call<LoginResultModel> loginUserHandle(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST
    Call<LoginResultModel> loginDriverHandle(@Url String url, @HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST
    Call<ResultAPIModel<ArrayList<FinicalModels>>> GetDeliveryShops(@Url String url, @HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST
    Call<ResultAPIModel<DeliveryStatusResponse>> DeliveryInvoiceUpdate(@Url String url, @HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);




    @POST
    Call<ResultAPIModel<String>> GetInvoiceProductPickStatus(@Url String url, @HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);





    @POST("v3/Account/getotp")
    Call<OtpModel> GetOptHandle(@HeaderMap() Map<String, Object> headerParams, @Query("mobile_number") String mobile_number);

    @POST
    Call<ResultAPIModel<ProfileData>> getUserDetail(@Url String url, @HeaderMap() Map<String, Object> headerParams, @Query("customer_id") int customer_id);


    @POST("v3/Account/forgotPassword")
    Call<OtpModel> ForgetPasswordHandle(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/changePassword")
    Call<OtpModel> changePasswordHandle(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @POST("v3/Account/updatePassword")
    Call<GeneralModel> updatePasswordHandle(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @POST("v3/Account/updateDeviceToken")
    Call<ResultAPIModel<String>> UpdateTokenHandle(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @POST("v3/Account/otpVerify")
    Call<GeneralModel> otpVerifyUserHandle(@HeaderMap() Map<String, Object> headerParams, @Body OtpModel param);

    /* ------------------------- Address Handle ------------------------- */
    @POST("v3/Locations/countryList")
    Call<CountryModelResult> GetCountry(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @GET("v3/Locations/getValidate")
    Call<GeneralModel> getValidate(@HeaderMap() Map<String, Object> headerParams, @Query("device_type") String device_type, @Query("app_version") String app_version, @Query("app_build") int app_build);

    @POST
    Call<CityModelResult> GetCity(@Url String url, @HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @GET("v3/Locations/getAreas")
    Call<AreasResultModel> GetAreas(@HeaderMap() Map<String, Object> headerParams, @Query("country_id") int country_id);


    @GET("v3/Address/setDefaultAddress")
    Call<GeneralModel> setDefaultAddress(@HeaderMap() Map<String, Object> headerParams, @Query("user_id") int user_id, @Query("address_id") int address_id);


    @GET("v3/Account/logout")
    Call<ResultAPIModel<MemberModel>> logout(@HeaderMap() Map<String, Object> headerParams, @Query("user_id") int user_id);


    @GET("v3/Products/singleproductList")
    Call<ProductDetailsModel> GetSignalProducts(@HeaderMap() Map<String, Object> headerParams, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("product_id") int product_id, @Query("user_id") String user_id);

    @GET("v3/Products/categoryList")
    Call<MainModel> GetMainPage(@HeaderMap() Map<String, Object> headerParams, @Query("category_id") int category_id, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("user_id") String user_id);

    @GET("v3/Products/AllCategories")
    Call<CategoryResultModel> GetAllCategories(@HeaderMap() Map<String, Object> headerParams, @Query("sotre_id") int sotre_id);

    @POST("v3/Products/setrate")
    Call<ResultAPIModel<ReviewModel>> setRate(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Products/GetRates")
    Call<ResultAPIModel<ArrayList<ReviewModel>>> GetRates(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @POST("v3/Company/setrate")
    Call<ResultAPIModel<ReviewModel>> setAppRate(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @POST("v3/Company/GetRates")
    Call<ResultAPIModel<ArrayList<ReviewModel>>> getAppRate(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @GET("v3/Company/AboutAs")
    Call<ResultAPIModel<SettingModel>> getSetting(@HeaderMap() Map<String, Object> headerParams, @Query("lng") String lng);

    @POST("v3/Favourite/addFavouriteProduct")
    Call<GeneralModel> addFavouriteProduct(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Favourite/deleteFavouriteProduct")
    Call<GeneralModel> deleteFavouriteProduct(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Carts/addToCart")
    Call<CartProcessModel> addToCart(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Carts/deleteCartItems")
    Call<CartProcessModel> deleteCartItems(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Carts/updateRemark")
    Call<CartProcessModel> updateRemark(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);


    @POST("v3/Carts/updateCart")
    Call<CartProcessModel> updateCart(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @GET("v3/Carts/checkOut")
    Call<CartResultModel> GetACarts(@HeaderMap() Map<String, Object> headerParams, @Query("user_id") int user_id, @Query("store_ID") int sotre_id);

    @GET("v3/Products/productList")
    Call<FavouriteResultModel> GetFavoriteProducts(@HeaderMap() Map<String, Object> headerParams, @Query("category_id") int category_id, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("user_id") String user_id, @Query("filter") String filter, @Query("page_number") int page_number, @Query("page_size") int page_size);

    @POST("v3/Account/updateProfile")
    Call<LoginResultModel> updateProfile(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/UploadPhoto")
    Call<ResultAPIModel<GeneralModel>> uploadPhoto(@HeaderMap() Map<String, Object> headerParams, @Body RequestBody params, @Query("user_id") int user_id);

    @GET("v3/Products/search")
    Call<FavouriteResultModel> searchProduct(@HeaderMap() Map<String, Object> headerParams, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("user_id") String user_id, @Query("text") String text, @Query("page_number") int page_number, @Query("page_size") int page_size);

    @GET("v3/Products/barcodeSearch")
    Call<FavouriteResultModel> barcodeSearch(@HeaderMap() Map<String, Object> headerParams, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("user_id") String user_id, @Query("barcode") String barcode, @Query("page_number") int page_number, @Query("page_size") int page_size);

    @GET("v3/Products/autocomplete")
    Call<AutoCompeteResult> autocomplete(@HeaderMap() Map<String, Object> headerParams, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("user_id") String user_id, @Query("text") String text);

    @GET("v3/Products/productList")
    Call<FavouriteResultModel> getCatProductList(@HeaderMap() Map<String, Object> headerParams, @Query("category_id") int category_id, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("user_id") String user_id, @Query("filter") String filter, @Query("page_number") int page_number, @Query("page_size") int page_size);


    @GET("v3/Orders/getPastOrders")
    Call<OrdersResultModel> getPastOrders(@HeaderMap() Map<String, Object> headerParams, @Query("user_id") int user_id);

    @GET("v3/Orders/getUpcomingOrders")
    Call<OrdersResultModel> getUpcomingOrders(@HeaderMap() Map<String, Object> headerParams, @Query("user_id") int user_id);

    @GET("v3/Orders/GetOrdersList")
    Call<ResultAPIModel<ArrayList<OrderNewModel>>> GetOrdersList(@HeaderMap() Map<String, Object> headerParams, @Body orderListCall param);


    @POST("v3/Orders/GetOrderDetails")
    Call<ResultAPIModel<OrderItemDetail>> GetOrderDetails(@HeaderMap() Map<String, Object> headerParams, @Body orderListCall param);


    @GET("v3/Orders/GetOrderDelivery")
    Call<OrdersResultModel> getOrderDelivery(@HeaderMap() Map<String, Object> headerParams, @Query("user_id") int user_id);

    @GET("v3/Orders/getPaymentMethod")
    Call<PaymentResultModel> getPaymentMethod(@HeaderMap() Map<String, Object> headerParams, @Query("sotre_id") int sotre_id);


    @GET("v3/Orders/deliveryTimeList")
    Call<DeliveryResultModel> getDeliveryTimeList(@HeaderMap() Map<String, Object> headerParams, @Query("sotre_id") int sotre_id);

    @POST("v3/Orders/CreateOrder")
    Call<OrdersResultModel> makeOrder(@HeaderMap() Map<String, Object> headerParams, @Body OrderCall param);

    @GET("v3/Products/productList")
    Call<FavouriteResultModel> getProductList(@HeaderMap() Map<String, Object> headerParams, @Query("category_id") int category_id, @Query("country_id") int country_id, @Query("city_id") int city_id, @Query("user_id") String user_id, @Query("filter") String filter, @Query("page_number") int page_number, @Query("page_size") int page_size);


    @POST("P2/Orders/GetOrdersList")
    Call<ResultAPIModel<ArrayList<RamesOrders>>> GetOrdersList(@HeaderMap() Map<String, Object> headerParams, @Body RamezOrderCall param);


    @POST("P2/Orders/ChangeOrderStatus")
    Call<ResultAPIModel<String>> changeOrderStatus(@HeaderMap() Map<String, Object> headerParams, @Body RamezOrderCall param);


    @POST("P2/Orders/GetOrderDetails")
    Call<ResultAPIModel<InvoiceInfoModel>> GetOrderDetails(@HeaderMap() Map<String, Object> headerParams, @Body RamezOrderCall param);


    Call<ResultAPIModel<MemberModel>> updateProfilePost(@HeaderMap() Map<String, Object> headerParams, @Body RequestBody params);

    @POST("v3/Account/uploadFile")
    Call<ResultAPIModel<GeneralModel>> uploadPhoto(@HeaderMap() Map<String, Object> headerParams, @Body RequestBody params);


    @POST("v3/me/update")
    Call<ResultAPIModel<MemberModel>> changePassword(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/addRemove/{id}/FromFavorite")
    Call<ResultAPIModel> addToFavorite(@HeaderMap() Map<String, Object> headerParams, @Path("id") int id);

    @POST("v3/delete/realEstate")
    Call<ResultAPIModel> deleteAdv(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/sendMsg")
    Call<ResultAPIModel> sendMessage(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/mobile/TestSmsSend")
    Call<ResultAPIModel<MemberModel>> sendConfirmCode(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/confirm")
    Call<ResultAPIModel<MemberModel>> confirmRegister(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/confirm")
    Call<ResultAPIModel<MemberModel>> confirmRegisterFirebase(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/otpVerify")
    Call<ResultAPIModel> sendResetPassword(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/forgotPassword")
    Call<ResultAPIModel> confirmResetPassword(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/createPassword")
    Call<ResultAPIModel> ResetPassword(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/changePassword")
    Call<ResultAPIModel> ChangePassword(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    @POST("v3/Account/updatePassword")
    Call<ResultAPIModel> UpdatePassword(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

//    @POST("v3/RealEstate/{id}/show")
//    Call<ResultAPIModel<PlaceModel>> getRealEstateDetails(@HeaderMap() Map<String, Object> headerParams, @Path("id") int id);

    @POST("v3/createRealEstate")
    Call<ResultAPIModel<Integer>> createRealEstate(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

//    @POST("v3/update/{id}/RealEstate")
//    Call<ResultAPIModel> updateRealEstate(@HeaderMap() Map<String, Object> headerParams, @Path("id") int id, @Body Map<String, Object> params);
//
//    @POST("v3/deleteImg")
//    Call<ResultAPIModel> deletePhoto(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);
//
//    @POST("v3/addImg")
//    Call<ResultAPIModel<PhotoModel>> uploadPhoto(@HeaderMap() Map<String, Object> headerParams, @Body RequestBody params);
//
//    @POST("v3/contact/us")
//    Call<ResultAPIModel> sendContactUs(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);
//
//    @POST("v3/sendReport")
//    Call<ResultAPIModel> reportAdv(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);
//
//    /* ------------------------- GET Handle ------------------------- */
//
//    @GET("v3/getCities")
//    Call<ResultAPIModel<List<CityModel>>> getCities(@HeaderMap() Map<String, Object> headerParams);
//
//    @GET("v3/getRealEstateType")
//    Call<ResultAPIModel<List<PlaceTypeModel>>> getRealEstateType(@HeaderMap() Map<String, Object> headerParams);
//
//    @GET("v3/getRealEstateOperation")
//    Call<ResultAPIModel<List<PlaceOperationModel>>> getRealEstateOperation(@HeaderMap() Map<String, Object> headerParams);
//
//    @GET("getMainSection")
//    Call<ResultAPIModel<AllPlacesModel>> getMainSection(@HeaderMap() Map<String, Object> headerParams);
//
//    @GET("v3/me/favourites")
//    Call<ResultAPIModel<AllPlacesModel>> getFavorite(@HeaderMap() Map<String, Object> headerParams, @Query("page") int page);
//
//    @GET("v3/getRealEstates")
//    Call<ResultAPIModel<AllPlacesModel>> getRealEstates(@HeaderMap() Map<String, Object> headerParams, @QueryMap Map<String, Object> queryParam);
//
//    @GET("v3/myRealEstate")
//    Call<ResultAPIModel<AllPlacesModel>> getMyRealEstates(@HeaderMap() Map<String, Object> headerParams, @Query("page") int page);
//
//    @GET("v3/myMsg")
//    Call<ResultAPIModel<AllChatsModel>> getChats(@HeaderMap() Map<String, Object> headerParams, @Query("page") int page);
//
//    @GET("v3/MsgDetails")
//    Call<ResultAPIModel<AllMessagesModel>> getChatMessages(@HeaderMap() Map<String, Object> headerParams, @Query("user_id") int userId, @Query("page") int page);
//
//    @GET("v3/searchRealEstates")
//    Call<ResultAPIModel<AllPlacesModel>> searchPlaces(@HeaderMap() Map<String, Object> headerParams, @QueryMap Map<String, Object> queryParam);
//
//    @GET("v3/me")
//    Call<ResultAPIModel<MemberModel>> getMyProfile(@HeaderMap() Map<String, Object> headerParams);
//
//    @GET("v1/pages")
//    Call<ResultAPIModel<List<PageModel>>> getPages(@HeaderMap() Map<String, Object> headerParams);
//
//    @GET("v3/terms_and_conditions")
//    Call<ResultAPIModel<PageModel>> getTerms(@HeaderMap() Map<String, Object> headerParams);
//
//    @GET("v1/pages/{slug}")
//    Call<ResultAPIModel<PageModel>> getPageBySlug(@HeaderMap() Map<String, Object> headerParams, @Path("slug") String slug);

    /* ------------------------- PUT Handle ------------------------- */

//    @PUT("v1/me/notifications/{notification_id}/mark-as-read/")
//    Call<ResultAPIModel> readNotification(@HeaderMap() Map<String, Object> headerParams, @Path("notification_id") Object id);

    /* ------------------------- PATCH Handle ------------------------- */

//    @PATCH("v1/me")
//    Call<ResultAPIModel<MemberModel>> updateProfilePatch(@HeaderMap() Map<String, Object> headerParams, @Body Map<String, Object> params);

    /* ------------------------- DELETE Handle ------------------------- */

//    @DELETE("v1/bookings/{id}/cancel")
//    Call<ResultAPIModel> cancelReservation(@HeaderMap() Map<String, Object> headerParams, @Path("id") int id);

//    @DELETE("v1/me/favorites/{id}")
//    Call<ResultAPIModel> removeUserFromWishlist(@HeaderMap() Map<String, Object> headerParams, @Path("id") int id);
//
//    @DELETE("v1/me/ignored-list/{id}")
//    Call<ResultAPIModel> removeUserFromIgnore(@HeaderMap() Map<String, Object> headerParams, @Path("id") int id);

//    @DELETE("v1/chats/{id}")
//    Call<ResultAPIModel> deleteChat(@HeaderMap() Map<String, Object> headerParams, @Path("id") int id);

}

