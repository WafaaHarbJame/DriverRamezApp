package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.databinding.FragmentRegisterBinding;

public class RegisterFragment extends FragmentBase {
    private FragmentRegisterBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.loginBut.setOnClickListener(view1 -> {
            startLogin();

        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void startLogin(){
        Intent intent=new Intent(getActivityy(), RegisterLoginActivity.class);
        intent.putExtra(Constants.LOGIN,true);
        startActivity(intent);
    }
}