package com.ramez.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.HomeActivity;
import com.ramez.driver.Models.LocalModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.ProfileData;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;

import static android.content.ContentValues.TAG;

public class SplashScreenActivity extends ActivityBase {
    private static final int SPLASH_TIMER = 3000;
    int storeId, userId;
    MemberModel user;
    LocalModel localModel;
    int cartNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startSplash();

    }


    private void startSplash() {

        setContentView(R.layout.activity_splash_screen);
        initData();

    }

    private void initData() {

        new Handler().postDelayed(() -> {

            if (UtilityApp.isLogin() && UtilityApp.getUserData()!=null) {
                if (UtilityApp.getUserData().getRole_id() != null) {
                    localModel = UtilityApp.getLocalData();
                    storeId = Integer.parseInt(localModel.getCityId());
                    user = UtilityApp.getUserData();
                    userId = user.getId();
                    Log.i(TAG, "Log user_id " + userId);
                    Log.i(TAG, "Log storeId " + storeId);

                    Intent intent = new Intent(getActiviy(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                    // getUserData(userId);


                }

            } else {

                if (!UtilityApp.isFirstRun()) {
                    Intent intent = new Intent(getActiviy(), SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    startWelcomeActivity();
                }
            }


        }, SPLASH_TIMER);
    }

    private void startWelcomeActivity() {
        startActivity(new Intent(getActiviy(), ChangeLanguageActivity.class));

    }


    public void getUserData(int user_id) {

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ResultAPIModel<ProfileData> result = (ResultAPIModel<ProfileData>) obj;
            String message = getString(R.string.fail_to_get_data);

            if (func.equals(Constants.ERROR)) {

                Intent intent = new Intent(getActiviy(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else if (func.equals(Constants.FAIL)) {

                Intent intent = new Intent(getActiviy(), SignInActivity.class);
                intent.putExtra(Constants.LOGIN, true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            } else if (func.equals(Constants.NO_CONNECTION)) {
                //  Toasty.error(getActiviy(),R.string.no_internet_connection, Toast.LENGTH_SHORT, true).show();

            } else if (IsSuccess) {
                MemberModel memberModel = UtilityApp.getUserData();
                if (result != null && result.data != null) {
                    memberModel.setName(result.data.getUserName());
                    memberModel.setId(result.data.getUserId());
                    memberModel.setEmail(result.data.getEmail());
                    memberModel.setProfilePicture(result.data.getProfilePicture());
                    memberModel.setRole_id(memberModel.getRole_id());
                    UtilityApp.setUserData(memberModel);
                    Intent intent = new Intent(getActiviy(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }


            } else {
                Intent intent = new Intent(getActiviy(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }


        }).getUserDetails(user_id);
    }




}