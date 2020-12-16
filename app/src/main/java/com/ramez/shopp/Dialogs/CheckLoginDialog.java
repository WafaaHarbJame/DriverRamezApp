package com.ramez.shopp.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.R;

public class CheckLoginDialog extends Dialog {


    Activity activity;
    TextView loginBut, registerBut,closeBtn;

    public CheckLoginDialog(Context context, int message, int okStr, int cancelStr, final ConfirmDialog.Click okCall, final ConfirmDialog.Click cancelCall) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_check_login);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setCancelable(true);

        loginBut = findViewById(R.id.loginBut);
        registerBut = findViewById(R.id.registerBut);
        closeBtn = findViewById(R.id.closeBtn);
        try {
            if (activity != null && !activity.isFinishing()) show();
        } catch (Exception e) {
            dismiss();
        }

        loginBut.setOnClickListener(view -> {
            Intent intent = new Intent(context, RegisterLoginActivity.class);
            intent.putExtra(Constants.LOGIN, true);
            context.startActivity(intent);
            ((Activity) context).finish();

        });
        registerBut.setOnClickListener(view -> {

            Intent intent = new Intent(context, RegisterLoginActivity.class);
            intent.putExtra(Constants.REGISTER, true);
            context.startActivity(intent);
            ((Activity) context).finish();

        });

        closeBtn.setOnClickListener(view -> {
         dismiss();

        });



    }

}
