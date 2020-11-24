package com.ramez.ramez.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ramez.ramez.Adapter.WelcomeSliderAdapter;
import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.Models.WelcomeModel;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.ActivityWelcomeBinding;

import java.util.ArrayList;

public class WelcomeActivity extends ActivityBase {
    private ActivityWelcomeBinding binding;
    public ArrayList<WelcomeModel> welcomeSliderModels;
    private WelcomeSliderAdapter welcomeSliderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!UtilityApp.isFirstRun()) {
            startMainActivity();
            finish();
        }
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        welcomeSliderModels=new ArrayList<>();
        welcomeSliderModels.add(new WelcomeModel(getString(R.string.dummy2),getString(R.string.dummy),getString(R.string.dummy1)));
        welcomeSliderModels.add(new WelcomeModel(getString(R.string.dummy2),getString(R.string.dummy),getString(R.string.dummy1)));
        welcomeSliderModels.add(new WelcomeModel(getString(R.string.dummy2),getString(R.string.dummy),getString(R.string.dummy1)));
        welcomeSliderAdapter=new  WelcomeSliderAdapter(this,welcomeSliderModels);
        binding.viewPager.setAdapter(welcomeSliderAdapter);



        binding.nextBut.setOnClickListener(view12 -> {
            binding.viewPager.setCurrentItem(getItem(+1), true);

            if(binding.viewPager.getAdapter().getCount()==binding.viewPager.getCurrentItem()+1){
                UtilityApp.setIsFirstRun(false);
                navigateChangeLanguage();

            }




        });

        binding.skipBtn.setOnClickListener(view1 -> navigateChangeLanguage());


    }


    private int getItem(int i) {
        return binding.viewPager.getCurrentItem() + i;
    }





    public void navigateChangeLanguage() {
        startActivity(new Intent(WelcomeActivity.this, ChangeLanguageActivity.class));
        finish();
    }

    private void startMainActivity(){
        UtilityApp.setIsFirstRun(false);
        startActivity(new Intent(WelcomeActivity.this, SplashScreenActivity.class));
        finish();
    }

}

