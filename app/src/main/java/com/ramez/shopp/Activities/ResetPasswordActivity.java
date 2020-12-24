package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityResetPasswordBinding;

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