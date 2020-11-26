package com.ramez.ramez.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ramez.ramez.databinding.FragmentCategoryBinding;

public class CategoryFragment extends FragmentBase {
    private FragmentCategoryBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}