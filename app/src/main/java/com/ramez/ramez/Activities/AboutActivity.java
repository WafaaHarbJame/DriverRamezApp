package com.ramez.ramez.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityAboutBinding;

public class AboutActivity extends ActivityBase {
    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        binding.toolBar.mainTitleTxt.setText(R.string.text_title_about_us);
        binding.appVersionTv.setText(getString(R.string.app_version).concat(UtilityApp.getAppVersion()+""));
        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }
}