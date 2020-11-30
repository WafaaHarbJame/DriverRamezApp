package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends ActivityBase {

    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        binding.toolBar.mainTitleTxt.setText(getString(R.string.text_title_edit_profile));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }
}