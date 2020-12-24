package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityAboutBinding;

public class AboutActivity extends ActivityBase {
    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        setTitle(R.string.text_title_about_us);

        binding.appVersionTv.setText(getString(R.string.app_version).concat(UtilityApp.getAppVersion()+""));


    }
}