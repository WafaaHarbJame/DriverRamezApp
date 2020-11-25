package com.ramez.ramez.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityConformPhoneBinding;

public class ConfirmPhoneActivity extends ActivityBase {
    private ActivityConformPhoneBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConformPhoneBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        binding.confirmBut.setOnClickListener(view1 -> {
            GoToConfirm();
        });
        binding.toolBar.mainTitleTxt.setText(getString(R.string.text_profile_phone_number));
        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });


    }

    private void GoToConfirm() {
        Intent intent = new Intent(getActiviy(), ConfirmActivity.class);
        startActivity(intent);
        finish();

    }


}