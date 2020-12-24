package com.ramez.shopp.Activities;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityConditionBinding;

public class ConditionActivity extends ActivityBase{
    ActivityConditionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConditionBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        setTitle(R.string.conditions);

    }
}