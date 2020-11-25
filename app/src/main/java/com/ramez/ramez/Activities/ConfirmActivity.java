package com.ramez.ramez.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityConfirmBinding;

public class ConfirmActivity extends ActivityBase {
    ActivityConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.forget_pass));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        binding.confirmBut.setOnClickListener(view1 -> {


        });


    }
}