package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityConfirmBinding;

public class ConfirmActivity extends ActivityBase {
    ActivityConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.confirm_phone));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        binding.confirmBut.setOnClickListener(view1 -> {


        });


    }
}