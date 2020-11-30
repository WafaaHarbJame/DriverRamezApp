package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityTermsBinding;

public class TermsActivity extends ActivityBase {
    private ActivityTermsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTermsBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        binding.toolBar.mainTitleTxt.setText(getString(R.string.conditions));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }
}