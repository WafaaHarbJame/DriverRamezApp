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
import com.ramez.shopp.MainActivity;
import com.ramez.shopp.R;

public class EmptyCartDialog extends Dialog {


    Activity activity;
    TextView yesBtn, noBtn,closeBtn;

    public EmptyCartDialog(Context context, int message, int okStr, int cancelStr, final ConfirmDialog.Click okCall, final ConfirmDialog.Click cancelCall) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_empty_cart);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setCancelable(true);

        yesBtn = findViewById(R.id.yesBtn);
        noBtn = findViewById(R.id.noBtn);
        closeBtn = findViewById(R.id.closeBtn);

        try {
            if (activity != null && !activity.isFinishing())
                show();
        } catch (Exception e) {
            dismiss();
        }

        yesBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.CART, true);
            context.startActivity(intent);


        });
        noBtn.setOnClickListener(view -> {
            dismiss();
        });

        closeBtn.setOnClickListener(view -> {
            dismiss();

        });





    }


}
