package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.databinding.ActivitySuccessOrdersBinding;

public class SuccessOrders extends ActivityBase {
    ActivitySuccessOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessOrdersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.backBtn.setVisibility(View.GONE);

        binding.backHomeBut.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActiviy(), SuccessOrders.class);
            startActivity(intent);
        });

    }
}