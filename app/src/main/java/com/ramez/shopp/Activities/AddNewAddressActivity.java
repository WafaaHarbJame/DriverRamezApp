package com.ramez.shopp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityAddNewAddressBinding;

public class AddNewAddressActivity extends ActivityBase {
    ActivityAddNewAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddNewAddressBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        binding.toolBar.mainTitleTxt.setText(R.string.new_address);

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });
        binding.locationBut.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActiviy(),MapsActivity.class);
            startActivity(intent);
        });


    }
}