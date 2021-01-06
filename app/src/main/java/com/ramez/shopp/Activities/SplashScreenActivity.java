package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.SettingModel;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.Models.FavouriteResultModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProfileData;
import com.ramez.shopp.Models.ResultAPIModel;
import com.ramez.shopp.R;

import static android.content.ContentValues.TAG;

public class SplashScreenActivity extends ActivityBase {
    private static final int SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startSplash();

    }


    private void startSplash() {

        setContentView(R.layout.activity_splash_screen);

        getSetting();

        initData();

    }

    private void initData() {

        new Handler().postDelayed(() -> {

            if (UtilityApp.isLogin()) {
                if (UtilityApp.getUserData() != null) {
                    getUserData(UtilityApp.getUserData().getId());

                }

            } else {

                if (!UtilityApp.isFirstRun()) {
                    Intent intent = new Intent(getActiviy(), MainActivity.class);
                    intent.putExtra(Constants.LOGIN, true);
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
        startActivity(new Intent(getActiviy(), WelcomeActivity.class));
        finish();
    }


    public void getUserData(int user_id) {

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ResultAPIModel<ProfileData> result = (ResultAPIModel<ProfileData>) obj;
            String message = getString(R.string.fail_to_get_data);

            if (IsSuccess) {
                MemberModel memberModel = UtilityApp.getUserData();
                memberModel.setName(result.data.getName());
                memberModel.setEmail(result.data.getEmail());
                memberModel.setProfilePicture(result.data.getProfilePicture());
                UtilityApp.setUserData(memberModel);
                Intent intent = new Intent(getActiviy(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(getActiviy(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

        }).getUserDetails(user_id);
    }

    public void getSetting() {

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            ResultAPIModel<SettingModel> result = (ResultAPIModel<SettingModel>) obj;

            if (IsSuccess) {
                SettingModel settingModel = new SettingModel();
                settingModel.setAbout(result.data.getAbout());
                settingModel.setConditions(result.data.getConditions());
                settingModel.setPrivacy(result.data.getPrivacy());
                UtilityApp.setSetting(settingModel);
            }

        }).getSetting();
    }

}