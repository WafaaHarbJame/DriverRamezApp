package com.ramez.ramez.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.ramez.Activities.RegisterLoginActivity;
import com.ramez.ramez.Activities.ResetPasswordActivity;
import com.ramez.ramez.Activities.SplashScreenActivity;
import com.ramez.ramez.Activities.WelcomeActivity;
import com.ramez.ramez.Classes.Constants;
import com.ramez.ramez.Classes.UtilityApp;
import com.ramez.ramez.databinding.FragmentLoginBinding;

public class LoginFragment extends FragmentBase {
    private FragmentLoginBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.textForgotPassword.setOnClickListener(view1 -> {
            startRestPassword();
        });

        binding.skipButton.setOnClickListener(view1 -> {

        });
        binding.loginBut.setOnClickListener(view1 -> {


        });

        binding.registerBut.setOnClickListener(view1 -> {
            startLogin();


        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void startRestPassword(){
        startActivity(new Intent(getActivityy(), ResetPasswordActivity.class));

    }

    private void startLogin(){
        Intent intent=new Intent(getActivityy(), RegisterLoginActivity.class);
        intent.putExtra(Constants.REGISTER,true);
        startActivity(intent);

    }
}