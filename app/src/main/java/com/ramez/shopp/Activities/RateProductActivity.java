package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityRateProductBinding;

public class RateProductActivity extends ActivityBase {
    ActivityRateProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRateProductBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.rate_product));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

    }
}