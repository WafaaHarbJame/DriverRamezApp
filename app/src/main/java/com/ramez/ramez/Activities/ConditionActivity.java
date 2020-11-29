package com.ramez.ramez.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityConditionBinding;

public class ConditionActivity extends ActivityBase{
    ActivityConditionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConditionBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(getString(R.string.conditions));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }
}