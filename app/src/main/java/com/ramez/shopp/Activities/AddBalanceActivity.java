package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityAddBalanceBinding;

public class AddBalanceActivity extends ActivityBase {
    ActivityAddBalanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBalanceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(R.string.add_balance);
        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        binding.followBut.setOnClickListener(view1 -> {
            startNoBalanceActivity();


        });




    }
    private void startNoBalanceActivity(){
        Intent intent=new Intent(getActiviy(), NoBalanceActivity.class);
        startActivity(intent);
    }
}