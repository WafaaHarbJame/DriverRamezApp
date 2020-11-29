package com.ramez.ramez.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityChangePassBinding;
import com.ramez.ramez.databinding.ActivityResetPasswordBinding;

public class ChangePassActivity extends ActivityBase {
    ActivityChangePassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePassBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.text_edit_profile_change_password));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });



    }
}