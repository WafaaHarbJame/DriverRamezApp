package com.ramez.driver.Classes;


import com.ramez.driver.HomeActivity;

/**
 * Created by wokman on 1/18/2017.
 */

public class Constants {

    public static final String LOCAL = "local";
    public static final String FACEBOOK = "facebook";
    public static final String TWITTER = "twitter";
    public static final String GOOGLE = "google";
    public static final String URL = "https://ristsys.store/";
    public static String imageURL_ramez = "https://deliveryxadok.s3.us-east-2.amazonaws.com/";

    public static final String Arabic = "ar";
    public static final String English = "en";
    public static String imageURL = URL + "public/upload/";
    public static String image_products = imageURL + "products/";

    public static String image_products_ramez = "https://s3.amazonaws.com/xadok-media/";


    public static final Class<HomeActivity> MAIN_ACTIVITY_CLASS = HomeActivity.class;
//    public static final Class<LoginRegisterActivity> INVALID_TOKEN_ACTIVITY = LoginRegisterActivity.class;

    public static final String NORMAL_FONT = "Cairo-Regular.ttf";
    public static final String BOLD_FONT = "Cairo-Bold.ttf";
    public static final String ICON_AWSM_FONT = "fa_light_300.otf";
    public static final String ICON_AWSM_BRAND_FONT = "fa-brands-400.ttf";
    public static final String ICON_OLD_AWSM_FONT = "fontawesome-webfont.ttf";
    public static final String ICON_MOON_FONT = "icomoon.ttf";

    public static final int Points_To_SAR = 100;
    public static final String TIME_ZONE = "GMT+3";
    public static final String COUNTRY_CODE = "00966";
    public static final String COUNTRY_NAME_SHORT = "COUNTRY_NAME_SHORT";
    public static final String COUNTRY_ID = "COUNTRY_ID";
    public static final String CITY_ID = "CITY_ID";
    public static final String COUNTRY_NAME = "COUNTRY_NAME";

    public static final String COUNTRY_CODE_PLUS = "+966";
    public static final String Paly_Link = "http://onelink.to/k9h4jf";

    public static final String AUTHENTICATED = "authenticated";
    public static final String UNAUTHENTICATED = "unauthenticated";

    public static final String MALE = "male";
    public static final String FEMALE = "female";

    public static final int MALE_ID = 1;
    public static final int three = 3;
    public static final int two = 2;
    public static final int country_code = 973;
    public static final int country_id = 17;

    public static final int one = 1;
    public static final int FEMALE_ID = 2;

    public final static double MIN_WEIGHT = 40, MAX_WEIGHT = 120, MIN_HEIGHT = 100, MAX_HEIGHT = 230;

    public static final String LINK_PLACE = "RealEstate";
    public static final String ACTION_REFRESH_CHAT_COUNT = "action_refresh_chat_count";

    public static final String PAGE_ABOUT = "about";
    public static final String PAGE_TERMS = "terms";

    public static final int BOOK_APPOINTMENT_ID = 1;

    public static final String REFRESH_MAIN_POSTS = "refresh_main_posts";
    public static final String REFRESH_CHAT_LIST = "refresh_cha_list";
    public static final String REFRESH_CHAT_MESSAGES = "refresh_cha_messages";

    public static final String MESSAGE_SENT = "sent";
    public static final String MESSAGE_RECEIVED = "received";

    public static final String NO_CONNECTION = "no connection";

    public static final String TOKEN_PREFIX = "token ";
    public static final String TOKEN_INVALID = "Unauthorized";
    public static final String ERROR = "error";
    public static final String NULL = "null";
    public static final String FAIL = "fail";
    public static final String UNCONFIRM = "unconfirm";
    public static final String AUTH_EXCEPTION = "Unauthorized";

    public static final String ALl_NATIONALITIES = "all";
    public static final String MY_NATIONALITIES = "strict";
    public static final String ALL_USERS_EMAIL = "all";
    public static final String WISHLIST_USERS_EMAIL = "wishlist";
    public static final String NO_USERS_EMAIL = "none";

    public static final String EXPLORE = "explore";
    public static final String IGNORE_LIST = "ignore_list";
    public static final String FAVORITE_ME_LIST = "favorite_me_list";
    public static final String SEARCH = "search";

    public static final String KEY_MEMBER = "key_member";
    public static final String KEY_SETTING = "KEY_SETTING";
    public static final String KEY_COUNTRIES= "key_countries";
    public static final String KEY_CATEGORIES= "KEY_CATEGORIES";
    public static final String KEY_Local = "KEY_Local";
    public static String KEY_FIREBASE_TOKEN = "firebase_token";
    public static String KEY_CART_SIZE = "KEY_CART_SIZE";
    public static final String KEY_MEMBER_LOCATION = "key_member_location";
    public static final String KEY_MEMBER_LANGUAGE = "key_member_language";
    public static final String KEY_FIRST_RUN = "key_first_run";
    public static final String KEY_FIRST_OPEN = "key_first_open";
    public static final String KEY_IS_NOTIFY = "key_is_notify";
    public static final String KEY_IS_CHAT_LIST = "key_is_chat_list";
    public static final String KEY_NOTIFY_TYPE = "key_notify_type";
    public static final String KEY_NOTIFICATION_TYPE = "key_notification_type";
    public static final String FROM_REGISTER = "FROM_REGISTER";
    public static final String success = "success";
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_USER_ID = "key_user_id";
    public static final String KEY_USER_NAME = "key_user_name";
    public static final String KEY_PLACE_ID = "key_place_id";
    public static final String KEY_CHAT_ID = "key_chat_id";
    public static final String KEY_AVATAR_URL = "key_avatar_url";
    public static final String KEY_AVATAR_ID = "key_avatar_id";
    public static final String KEY_IS_PROVIDER = "key_is_provider";
    public static final String KEY_REGISTER_MODEL = "key_register_model";
    public static final String KEY_NAV_LIST = "key_nac_list";
    public static final String KEY_POSITION = "key_position";
    public static final String KEY_SEARCH_STRING = "key_search_string";
    public static final String KEY_SEARCH_CITY = "key_search_city";
    public static final String KEY_SEARCH_SALARY = "key_search_salary";
    public static final String KEY_IS_BOOK_DOCTOR = "key_is_book_doctor";
    public static final String KEY_DOCTOR_MODEL = "key_doctor_model";
    public static final String KEY_DOCTOR_ID = "key_doctor_id";
    public static final String KEY_SECTION_ID = "key_section_id";
    public static final String KEY_SECTION_NAME = "key_section_name";
    public static final String KEY_SERVICE_ID = "key_service_id";
    public static final String KEY_NEWS_ID = "key_news_id";
    public static final String KEY_ALBUM_ID = "key_album_id";
    public static final String KEY_TICKET_ID = "key_ticket_id";
    public static final String KEY_TRIP_ID = "key_trip_id";
    public static final String KEY_REASON_ID = "key_reason_id";
    public static final String KEY_ALBUM_NAME = "key_album_name";
    public static final String KEY_DATE = "key_date";
    public static final String KEY_BODY = "key_body";
    public static final String KEY_IMAGE_URL = "key_image_url";
    public static final String KEY_IMAGE_DATA = "key_image_data";
    public static final String KEY_MESSAGE_ID = "key_message_id";
    public static final String KEY_COMPANY_CATEGORIES = "key_company_categories";
    public static final String KEY_CONVERSATION_ID = "key_conversation_id";
    public static final String KEY_STORY_ID = "key_story_id";
    public static final String KEY_NOTIFICATION_ID = "key_notification_id";
    public static final String KEY_MOBILE = "key_mobile";
    public static final String verify_account = "verify_account";
    public static final String reset_account = "reset_account";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_WALKTHROUGH_IMG_RES = "key_walkthrough_img_res";
    public static final String KEY_WALKTHROUGH_TITLE = "key_walkthrough_title";
    public static final String KEY_WALKTHROUGH_DESC = "key_walkthrough_desc";
    public static final String KEY_FRAGMENT_TYPE = "key_fragment_type";
    public static final String KEY_OPEN_TYPE = "key_open_type";
    public static final String KEY_LOCATION = "key_location";

    public static final String BROADCAST_REFRESH = "broadcast_refresh";

    public static final String KEY_LOGIN_PREFERANCE = "key_login_preferance";

    public static final String FRAG_TERMS = "frag_terms";
    public static final String FRAG_TICKETS = "frag_tickets";
    public static final String FRAG_POLICY = "frag_policy";
    public static final String FRAG_DELETE_ACCOUNT = "frag_delete_account";
    public static final String FRAG_CHAT_ARCHIVE = "frag_chat_archive";
    public static final String FRAG_BLOCK_ACCOUNTS = "frag_block_accounts";
    public static final String FRAG_MUTE_ACCOUNTS = "frag_mute_accounts";

    /****************************************************************/

    public static final String DB_Cities = "1";
    public static final String DB_PlaceType = "2";
    public static final String DB_PlaceOperation = "3";
    public static final String DB_Origin = "4";
    public static final String DB_BodyBuild = "5";
    public static final String DB_Worship = "6";
    public static final String DB_Prayer = "7";
    public static final String DB_Smoking = "8";
    public static final String DB_FinancialStatus = "9";
    public static final String DB_Employment = "10";
    public static final String DB_Obstruction = "11";

    public static final String DB_Avatars = "12";
    public static final String DB_FemaleSocialStatus = "13";
    public static final String DB_FemaleMarriageType = "14";
    public static final String DB_DressType = "15";
    public static final String DB_Reasons = "16";
    public static final String DB_FemaleWorship = "17";
    public static final String DB_FemalePrayer = "18";
    public static final String DB_FemaleSmoking = "19";
    public static final String DB_FemaleFinancialStatus = "20";
    public static final String DB_FemaleEmployment = "21";
    public static final String DB_FemaleObstruction = "22";

    public static final String DB_AboutModel = "24";
    public static final String LIST_MODEL = "LIST_MODEL";
    public static final String LIST_MODEL_NAME = "LIST_MODEL_NAME";
    public static final String FILTER_NAME = "FILTER_NAME";
    public static final String DB_Salary = "25";
    public static final String DB_Education = "26";
    public static final String DB_NumberOfChildren = "27";
    public static final String DB_Nationalities = "28";
    public static final String DB_TermsModel = "29";
    public static final String DB_Pages = "30";
    public static final String DB_Packages = "31";
    public static final String DB_PackageSpecs = "32";
    public static final String DB_productModel = "DB_PRODUCT_MODEL";


    public static final String CAPTURE = "capture";
    public static final String PICK = "pick";
    public static final String SAVE = "save";
    public static final String CLEAR = "clear";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String inv_id = "inv_id";
    public static final String CART = "CART";

    public static String latitude = "latitude";
    public static String longitude = "longitude";

    public  static String Sender="Sender";
    public  static String Receiver="Receiver";
    public  static String inputType_text="text";
    public  static String inputType_image="Image";
    public  static String inputType="Image";
    public  static String deviceType="android";
    public  static String user_type="User";
    public  static String picker_type="Packer";
    public  static String driver_type="Driver";
    public  static String api_key="^~>h2q=m[h=>3?bU/!M'X!m~?4GjKJP{Q@y;~fa3Vjs/M#`8FuB;x[LKwJ&>gNrxBt8!5PZ:9QLuHBUtu{TPc2s]k74]Br?PGe6+NcFUT-8";
//    public  static String api_key="1";

    public static String KEY_FILE = "KEY_FILE";
    public static String KEY_EDIT = "KEY_EDIT";
    public static String KEY_ADD_NEW = "KEY_ADD_NEW";
    public static String KEY_ADDRESS_ID = "KEY_ADDRESS_ID";
    public static String KEY_LAT = "KEY_LAT";
    public static String KEY_LNG = "KEY_LNG";
    public static final String CURRENCY = "CURRENCY";
    public static final String Fractional = "Fractional";
    public static final String favourite_filter = "favourite";
    public static final String offered_filter  = "offered";
    public static final String featured_filter = "featured";
    public static final String quick_filter = "quick";
    public static final String SEARCH_BY_CODE_byCode = "SEARCH_BY_CODE_byCode";
    public static final String CODE = "CODE";
    public static final String SELECTED_POSITION = "SELECTED_POSITION";
    public static final String position = "position";
    public static final String CAT_LIST = "CAT_LIST";
    public static final String CAT_MODEL = "CAT_MODEL";
    public static final String delivery_choose = "delivery_choose";
    public static final String ADDRESS_ID = "ADDRESS_ID";
    public static final String ADDRESS_TITLE = "ADDRESS_TITLE";
    public static final String ADDRESS_FULL= "ADDRESS_FULL";
    public static final String CART_PRODUCT_COUNT = "CART_PRODUCT_COUNT";
    public static final String CART_SUM = "CAT_LIST";
    public static final String delivery_charges = "delivery_charges";
    public static final String CART_LIST = "CART_LIST";
    public static final String CART_MODEL = "CART_MODEL";
    public static final String ORDER_MODEL = "ORDER_MODEL";
    public static final String BARCODE_KEY = "BARCODE_KEY";
    public static final String BY_PHONE = "BY_PHONE";
    public static final String BY_SOCIAL = "BY_SOCIAL";
    public static final String PARAMS_EMAIL = "email";
    public static final String PARAMS_NAME = "name";
    public static final String PARAMS_ID = "id";
    public static final int twoRow =2;
    public static final int oneRow =1;
    public static final int OK_STATUS =200;
    public static final String past_order ="p";
    public static final String upcoming_order ="u";

    public static String status = "status";
    public static String message = "message";
    public static String data = "data";
    public static String useruseruseruser = "user";
    public static String Packer = "Packer";
    public static String Driver = "Driver";
    public static String provider = "provider";
    public static String cart = "cart";
    public static String client = "client";
    public static String chat_type = "chat_type";
    public static String chat_msg = "chat_msg";
    public static String from_id = "from_id";
    public static String to_id = "to_id";
    public static String to_name = "to_name";
    public static String from_name = "from_name";

    public static String last_msg = "last_msg";
    public static String price_from = "price_from";
    public static String price_to = "price_to";
    public static String search = "search";
    public static String ramezrequest = "ramezrequest";
    public static final String TIME_ZONE_PLUS3 = "GMT+3";
    public static String API_KEY="^~>h2q=m[h=>3?bU/!M'X!m~?4GjKJP{Q@y;~fa3Vjs/M#`8FuB;x[LKwJ&>gNrxBt8!5PZ:9QLuHBUtu{TPc2s]k74]Br?PGe6+NcFUT-8";

    //    <!-- TODO: Shared Constant -->
    public static String loginChecked = "loginChecked";
    public static String termsChecked = "termsChecked";
    public static String lang = "lang";

    //    <!-- TODO: Chat Constant -->
    public static String chat = "chat";
    public static String chat_id = "chat_id";
    public static String reply_id = "reply_id";
    public static String sender_id = "senderID";
    public static String messageTime = "messageTime";
    public static String receiver_id = "receiverID";
    public static String messageBody = "messageBody";



    public static final String FIREBASE_DATABASE_LOCATION_USERS = "users";

    //    <!-- TODO: Google Constant -->
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

    //    <!-- TODO: Firebase Constant -->
    public static String fcmServerKey = "AAAAJmarLgM:APA91bFDAHbKinT66vPR2Rr27-GRmJb0sDRovyVs7qtfXaVt9A9hwr5kq-596x8CKO71QASFNcUjWkgKkXWM1GszJV435T3q6k9sJeqnxaHHyttU7o17hzwRAWPCTHRvP98G842mRpFj";
    public static String token = "token";
    public static String activity = "activity";
    public static String ListSessions = "ListSessions";
    public static String isChosen = "isChosen";
    public static String session = "session";


    //    <!-- TODO: User Login -->
    public static String login = "login";
    public static String password = "password";
    public static String code = "code";

    //    <!-- TODO: Session Data -->
    public static String session_id = "session_id";
    public static String session_duration = "session_duration";
    public static String session_price = "session_price";
    public static String title = "title";

    //    <!-- TODO: Contact Data -->
    public static String app_mobile = "app_mobile";
    public static String app_email = "app_email";
    public static String app_support = "app_support";
    public static String shipping_cost = "shipping_cost";
    public static String app_vat = "app_vat";
    public static String order = "order";
    public static String product = "product";
    public static String productWebPage = "productWebPage";
    public static String page = "page";

    ////    <!-- TODO: User Register -->
    public static String firstname = "firstname";
    public static String lastname = "lastname";
    public static String email = "email";
    public static String mobile = "mobile";
    public static String city_id = "city_id";
    public static String user_img = "user_img";
    public static String user_header = "user_header";
    public static String orders_count = "orders_count";
    public static String whishlist_count = "whishlist_count";
    public static String how_id = "how_id";

    ////    <!-- TODO: Forget Password -->
    public static String recover = "recover";

    ////    <!-- TODO: Terms -->
    public static String terms = "terms";

    ////    <!-- TODO: FAQs -->
    public static String faq_id = "faq_id";
    public static String question = "question";
    public static String answer = "answer";

    ////    <!-- TODO: PACKAGE DATA -->
    public static String pack_id = "pack_id";
    public static String pack_img = "pack_img";
    public static String pack_price = "pack_price";
    public static String pack_credit = "pack_credit";

    ////    <!-- TODO: PACKAGE DATA -->
    public static String cat_id = "cat_id";
    public static String cat_img = "cat_img";

    ////    <!-- TODO: Update Password -->
    public static String cPassword = "cpassword";
    public static String nPassword = "npassword";

    ////    <!-- TODO: Complaints/Suggestions -->
    public static String type = "type";
    public static String suggestion = "suggestion";
    public static String complaint = "complaint";

    ////    <!-- TODO: Products -->
    public static String product_id = "product_id";
    public static String product_qua = "product_qua";
    public static String product_name = "product_name";
    public static String order_total = "order_total";
    public static String order_rate = "order_rate";
    public static String order_time = "order_time";
    public static String order_date = "order_date";
    public static String product_desc = "product_desc";
    public static String product_price = "product_price";
    public static String is_approved = "is_approved";
    public static String product_image = "product_image";
    public static String link = "link";
    public static String seen_count = "seen_count";
    public static String product_status = "product_status";
    public static String is_wishlist = "is_wishlist";
    public static String gallery = "gallery";
    public static String gallery_id = "gallery_id";
    public static String product_gallery = "product_gallery";
    public static String isGrid = "isGrid";

    //// Payment
    public static String amount = "amount";
    public static String number = "number";
    public static String month = "month";
    public static String year = "year";
    public static String cvc = "cvc";
    public static String creditcard = "creditcard";
    public static String sdad = "sadad";


    ////    <!-- TODO: User Data -->
    public static String user_id = "user_id";
    public static String role_id = "role_id";
    public static String user_firstname = "user_firstname";
    public static String user_name = "user_name";
    public static String user_lastname = "user_lastname";
    public static String user_email = "user_email";
    public static String user_mobile = "user_mobile";
    public static String is_active = "is_active";
    public static String user_lat = "user_lat";
    public static String user_lng = "user_lng";
    public static String email_verified_at = "email_verified_at";
    public static String created_at = "created_at";
    public static String wish_id = "wish_id";
    public static String updated_at = "updated_at";
    public static String city_name = "city_name";
    public static String user_rate = "user_rate";
    public static String user_rate_count = "user_rate_count";
    public static String extra_mobile = "extra_mobile";
    public static String user_bio = "user_bio";
    public static String confirm_code = "confirm_code";
    public static String is_confirmed = "is_confirmed";
    public static String is_accepted = "is_accepted";
    public static String is_verified = "is_verified";
    public static String role = "role";
    public static String user_credit = "user_credit";
    public static String use_credit = "use_credit";
    public static String city = "city";
    public static String id = "id";
    public static String name = "name";
    public static String child_count = "child_count";
    public static String offer_code = "offer_code";
    public static String offer_discount = "offer_discount";


    ////    <!-- TODO: Child Data -->
    public static String uchild_id = "uchild_id";
    public static String child_id = "child_id";
    public static String uchild_img = "uchild_img";
    public static String uchild_firstname = "uchild_firstname";
    public static String uchild_lastname = "uchild_lastname";
    public static String uchild_age = "uchild_age";
    public static String uchild_desc = "uchild_desc";
    public static String uchild_gender = "uchild_gender";

    ////    <!-- TODO: Rate Data -->
    public static String user_from = "user_from";
    public static String rev_driver_rate = "rate";
    public static String rev_app_rate = "rev_app_rate";
    public static String rev_review = "rev_review";

    ////    <!-- TODO: Provider Data -->
    public static String provider_id = "provider_id";
    public static String follow_id = "follow_id";
    public static String is_follow = "is_follow";
    public static String extra = "extra";
    public static String image = "image";
    public static String bio = "bio";
    public static String rate = "rate";
    public static String review = "review";
    public static String provider_rate = "provider_rate";

    ////    <!-- TODO: Provider Data -->
    public static String childrens = "childrens";
    public static String order_sdate = "order_sdate";
    public static String order_stime = "order_stime";
    public static String order_lat = "order_lat";
    public static String order_lng = "order_lng";
    public static String payment_type = "payment_type";
    public static String sessions = "sessions";
    public static String products = "products";

    ////    <!-- TODO: Session Data -->
    public static String order_id = "order_id";
    public static String inv_price = "inv_price";
    public static String price = "price";
    public static String inv_total = "inv_total";
    public static String cartTotalPrice = "cartTotalPrice";
    public static String inv_payment = "inv_payment";
    public static String inv_status = "inv_status";
    public static String remainingInSeconds = "remainingInSeconds";
    public static String remainingInFormat = "remainingIn";
    public static String payment_id = "payment_id";
    public static String payment_status = "payment_status";
    public static String payment_message = "payment_message";
    public static String order_datetime = "order_datetime";


    ////    <!-- TODO: Active Session Data -->
    public static String session_title = "session_title";
    public static String session_title_en = "session_title_en";
    public static String session_type = "session_type";
    public static String session_extend_title = "session_extend_title";
    public static String session_extend_title_en = "session_extend_title_en";
    public static String session_extend_duration = "session_extend_duration";
    public static String session_extend_price = "session_extend_price";
    public static String session_extend_status = "session_extend_status";
    public static String provider_lat = "provider_lat";
    public static String provider_lng = "provider_lng";
    public static String provider_mobile = "provider_mobile";
    public static String session_remaining = "session_remaining";
    public static String reason = "order_cancel_text";
    public static String cancel = "cancel";
    public static String order_status = "order_status";
    public static String active_track = "active_track";
    public static String provider_name = "provider_name";
    public static String provider_img = "provider_img";
    public static String provider_image = "provider_image";
    public static String session_extend_id = "session_extend_id";
    public static String order_extend_start_time = "order_extend_start_time";
    public static String inv_lat = "inv_lat";
    public static String inv_lng = "inv_lng";


    ////    <!-- TODO: Skills Data -->
    public static String skill_id = "skill_id";
    public static String skill_title = "skill_title";
    public static String skill_title_en = "skill_title_en";


    ////    <!-- TODO: BUNDLE Data -->
    public static String KEY_MESSAGES_MODEL = "key_messages_model";
    public static String KEY_PRODUCTS_MODEL = "key_products_model";
    public static String lname = "lname";
    public static String nationality = "nationality";
    public static String skills = "skills";


}


