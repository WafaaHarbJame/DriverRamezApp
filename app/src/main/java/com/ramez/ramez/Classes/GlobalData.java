package com.ramez.ramez.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ramez.ramez.RootApplication;


public class GlobalData {

    public static final String BetaBaseURL = "http://abdullah.sahaba.tech/jazan/public_html/apiV2.php/";
    public static final String ReleaseBaseURL = "https://estates-jazan.com/";
    public static final String BaseURL = ReleaseBaseURL;
    public static final String ApiURL = BaseURL + "api/";

    public static int Position = 0;

    public static boolean EDIT_PROFILE = false;
    public static boolean REFRESH_ADV = false;
    public static int CHAT_ID_OPEN = 0;

    static ProgressDialog progressDialog;

    //============================================================================


    //============================================================================


    public static void GlideImg(Object image, int placeholder, ImageView imageView) {

//        Log.i("Global", "Log url " + image);
        Glide.with(RootApplication.getInstance())
                .asBitmap()
                .load(image)
                .apply(new RequestOptions()
                        .placeholder(placeholder)
                )

                .into(imageView);

    }


    public static void progressDialog(Context c, String msg, boolean status) {
        // to show dialog insert status = true to dismiss doialog status = false
        Activity activity = (Activity) c;
        if (status) {
            progressDialog = new ProgressDialog(c);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            try {
                if (activity != null && !activity.isFinishing())
                    progressDialog.show();
            } catch (Exception e) {
                progressDialog.dismiss();
            }

        } else {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }


    public static void Toast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void Toast(Context context, int resId) {

        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
    }

}
