package com.ramez.ramez.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramez.ramez.Adapter.OfferAdapter;
import com.ramez.ramez.Adapter.ProductAdapter;
import com.ramez.ramez.Models.ProductModel;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.FragmentHomeBinding;
import com.ramez.ramez.databinding.FragmentOfferBinding;

import java.util.ArrayList;


public class OfferFragment extends FragmentBase implements OfferAdapter.OnItemClick {
    private FragmentOfferBinding binding;
    private OfferAdapter productOfferAdapter;
    ArrayList<ProductModel> productOffersList;
    GridLayoutManager gridLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOfferBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        productOffersList=new ArrayList<>();

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),1));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),1));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),1));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));
        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),1));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        gridLayoutManager =new GridLayoutManager(getActivityy(),2);
        binding.offerRecycler.setLayoutManager(gridLayoutManager);

        binding.offerRecycler.setHasFixedSize(true);

        initAdapter();

        return view;
    }

    public void initAdapter(){

        productOfferAdapter = new OfferAdapter(getActivityy(), this, productOffersList);
        binding.offerRecycler.setAdapter(productOfferAdapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }
}