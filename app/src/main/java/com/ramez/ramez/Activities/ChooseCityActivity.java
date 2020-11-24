package com.ramez.ramez.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityChooseCityBinding;

public class ChooseCityActivity extends ActivityBase {
    private ActivityChooseCityBinding binding;
    private boolean toggleShowLang = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseCityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.chooseCityTv.setOnClickListener(view1 -> {
            binding.cityContainer.setVisibility(View.VISIBLE);
            binding.chooseCityTv.setVisibility(View.GONE);

        });





        binding.toolBar.homeBtn.setVisibility(View.GONE);

    }

    private void GoToChooseCity() {
        Intent intent = new Intent(getActiviy(), ChooseCityActivity.class);
        startActivity(intent);
        finish();

    }


}