package com.ramez.ramez.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ramez.ramez.R;
import com.ramez.ramez.Utils.LocaleUtils;


public class ActivityBase extends AppCompatActivity {

    protected boolean isMainActivity = false;

    int onStartCount = 0;

    protected TextView mainTitle, home;
    protected RelativeLayout toolbar;


    public ActivityBase() {
        LocaleUtils.updateConfig(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


    }

    @Override
    public void setTitle(CharSequence title) {

        toolbar = findViewById(R.id.tool_bar);
        home = toolbar.findViewById(R.id.home);
        mainTitle = toolbar.findViewById(R.id.title);


        mainTitle.setText(title);

        if (!isMainActivity) {
            home.setVisibility(View.VISIBLE);


        } else {

            home.setVisibility(View.GONE);

        }

        home.setOnClickListener(view -> onBackPressed());

        super.setTitle(title);

    }


    protected Activity getActiviy() {
        return this;
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {

            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public static void showSoftKeyboard(Activity activity) {
        try {

            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
//            inputMethodManager.showSoftInput(_searchText, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
//                    System.out.println("Log event Action " + event.getAction());
                    if (event.getAction() != MotionEvent.ACTION_SCROLL) {
                        hideSoftKeyboard(getActiviy());
                    }

                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void Toast(String msg) {

        Toast.makeText(getActiviy(), msg, Toast.LENGTH_SHORT).show();
    }

    public void Toast(int resId) {

        Toast.makeText(getActiviy(), getString(resId), Toast.LENGTH_SHORT).show();
    }


}

