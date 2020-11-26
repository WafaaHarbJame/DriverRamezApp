package com.ramez.ramez.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.ramez.R;
import com.ramez.ramez.databinding.FragmentCategoryBinding;
import com.ramez.ramez.databinding.FragmentMyAccountBinding;

public class MyAccountFragment extends FragmentBase {
    private FragmentMyAccountBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}