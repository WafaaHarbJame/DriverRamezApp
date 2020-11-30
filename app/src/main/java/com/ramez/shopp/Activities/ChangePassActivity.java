package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityChangePassBinding;

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