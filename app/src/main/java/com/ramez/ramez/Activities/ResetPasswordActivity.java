package com.ramez.ramez.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends ActivityBase {
    ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.reset_pass));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });



    }
}