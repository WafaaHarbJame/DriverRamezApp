package com.ramez.driver.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.driver.R;
import com.ramez.driver.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends ActivityBase {
    ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle(R.string.reset_pass);




    }
}