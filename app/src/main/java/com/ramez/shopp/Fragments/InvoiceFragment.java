package com.ramez.shopp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.shopp.Adapter.CategoryAdapter;
import com.ramez.shopp.Classes.CategoryModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.FragmentCategoryBinding;
import com.ramez.shopp.databinding.FragmentInvoiceBinding;

import java.util.ArrayList;

public class InvoiceFragment extends FragmentBase {
    private FragmentInvoiceBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
}




