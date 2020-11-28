package com.ramez.ramez.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ramez.ramez.Activities.ProductDetailsActivity;
import com.ramez.ramez.Adapter.ProductAdapter;
import com.ramez.ramez.Classes.Constants;
import com.ramez.ramez.Models.ProductModel;
import com.ramez.ramez.R;
import com.ramez.ramez.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends FragmentBase implements ProductAdapter.OnItemClick {
    private FragmentHomeBinding binding;
    private ProductAdapter productBestAdapter;
    private ProductAdapter productSellerAdapter;
    private ProductAdapter productOfferAdapter;

    ArrayList<ProductModel> productBestList;
    ArrayList<ProductModel> productSellerList;
    ArrayList<ProductModel> productOffersList;
    GridLayoutManager bestProductGridLayoutManager;
    GridLayoutManager bestSellerLayoutManager;
    GridLayoutManager bestOfferGridLayoutManager;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        productBestList =new ArrayList<>();
        productSellerList =new ArrayList<>();
        productOffersList =new ArrayList<>();

        productBestList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),1));

        productBestList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),0));

        productBestList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),1));

        productBestList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),0));

        //
        productSellerList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),1));

        productSellerList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),0));

        productSellerList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),1));

        productSellerList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        //
        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),1));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed),"htt",
                getString(R.string._50_off),0, getString(R.string._10220_aed),1));

        productOffersList.add(new ProductModel(1, getString(R.string.product_name),getString(R.string.product_name),getString(R.string._10220_aed1),"htt",
                getString(R.string._50_off),1, getString(R.string._10220_aed),0));

        bestProductGridLayoutManager =new GridLayoutManager(getActivityy(),2);
        bestOfferGridLayoutManager =new GridLayoutManager(getActivityy(),2);
        bestSellerLayoutManager =new GridLayoutManager(getActivityy(),2);

        binding.bestSellerRecycler.setLayoutManager(bestSellerLayoutManager);
        binding.bestProductRecycler.setLayoutManager(bestProductGridLayoutManager);
        binding.offerRecycler.setLayoutManager(bestOfferGridLayoutManager);

        binding.offerRecycler.setHasFixedSize(true);
        binding.bestProductRecycler.setHasFixedSize(true);
        binding.bestSellerRecycler.setHasFixedSize(true);


        initAdapter();


        return view;
    }

    public void initAdapter(){

        productBestAdapter = new ProductAdapter(getActivityy(), this, productBestList,2);
        productSellerAdapter = new ProductAdapter(getActivityy(), this, productSellerList,2);
        productOfferAdapter = new ProductAdapter(getActivityy(), this, productOffersList,2);

        binding.bestProductRecycler.setAdapter(productBestAdapter);
        binding.bestSellerRecycler.setAdapter(productSellerAdapter);
        binding.offerRecycler.setAdapter(productOfferAdapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {
        Intent intent=new Intent(getActivityy(), ProductDetailsActivity.class);
        intent.putExtra(Constants.DB_productModel,productModel);
        startActivity(intent);

    }
}