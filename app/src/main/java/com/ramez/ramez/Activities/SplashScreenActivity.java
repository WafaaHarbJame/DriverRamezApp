package com.ramez.ramez.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.MainActivity;
import com.ramez.ramez.R;

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

        initData();

    }
    private void initData() {

        new Handler().postDelayed(() -> {

                    if (UtilityApp.isLogin()) {
                        Intent intent = new Intent(getActiviy(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } else {

                        Intent intent = new Intent(getActiviy(), RegisterLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                }
                , SPLASH_TIMER);
    }

}