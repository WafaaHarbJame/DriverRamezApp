package com.ramez.driver.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ramez.driver.R;

public class Helpers {
    public static void hideSoftKeyboard(View view, Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void openChatByWhatUp(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.whatsapp");
        intent.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s", "+973" + phone)));
        if (context.getPackageManager().resolveActivity(intent, 0) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "" + context.getResources().getString(R.string.install_whats), Toast.LENGTH_SHORT).show();

        }


    }
}
