package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityConformPhoneBinding;

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
        binding.toolBar.mainTitleTxt.setText(getString(R.string.forget_pass));
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