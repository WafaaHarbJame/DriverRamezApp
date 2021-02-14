package com.ramez.driver.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.driver.R;
import com.ramez.driver.databinding.ActivityNoBalanceBinding;

public class NoBalanceActivity extends ActivityBase {
    ActivityNoBalanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoBalanceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.addBalanceBut.setOnClickListener(view1 -> {

        });

        setTitle(R.string.add_balance);


    }

    private void startNoBalanceActivity(){
        Intent intent=new Intent(getActiviy(), NoBalanceActivity.class);
        startActivity(intent);
    }
}