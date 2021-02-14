package com.ramez.driver.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.driver.R;
import com.ramez.driver.databinding.FragmentFinancialReportsBinding;
import com.ramez.driver.databinding.FragmentProfileBinding;


public class FinancialReportsFragment extends Fragment {
    private FragmentFinancialReportsBinding binding;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFinancialReportsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }


}