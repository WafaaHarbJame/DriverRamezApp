package com.ramez.driver.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.ramez.driver.R;


public class ConfirmDialog extends Dialog {

    TextView messageTxt;
    TextView cancelBtn, okBtn;

    Activity activity;

    public ConfirmDialog(Context context, int message, int okStr, int cancelStr, final Click okCall, final Click cancelCall) {
        super(context);

        activity = (Activity) context;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        setContentView(R.layout.dialog_my_confirm);
        setCancelable(false);
//        setTitle(title);


        messageTxt = findViewById(R.id.messageTxt);
        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);

        messageTxt.setText(message);

        if (okStr != 0)
            okBtn.setText(okStr);
        if (cancelStr != 0)
            cancelBtn.setText(cancelStr);

        okBtn.setOnClickListener(view -> {
            if (okCall != null)
                okCall.click();
            dismiss();
        });
        cancelBtn.setOnClickListener(view -> {
            if (cancelCall != null)
                cancelCall.click();
            dismiss();
        });

        try {
            if (activity != null && !activity.isFinishing())
                show();
        } catch (Exception e) {
            dismiss();
        }


    }

    public static abstract class Click {
        public abstract void click();
    }
}
