package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityRatingBinding;

public class RatingActivity extends ActivityBase {

    ActivityRatingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRatingBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.rate_app));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

    }
}