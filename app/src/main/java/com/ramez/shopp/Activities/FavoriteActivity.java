package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.ramez.shopp.Adapter.ProductAdapter;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityFavoriteBinding;

import java.util.ArrayList;

public class FavoriteActivity extends ActivityBase implements ProductAdapter.OnItemClick  {
    ActivityFavoriteBinding binding;
    private ProductAdapter productFavAdapter;
    ArrayList<ProductModel> productList;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        productList=new ArrayList<>();

        gridLayoutManager =new GridLayoutManager(getActiviy(),2);

        binding.favoriteRecycler.setLayoutManager(gridLayoutManager);

        initAdapter();


        binding.toolBar.mainTitleTxt.setText(getString(R.string.fav_products));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });





    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }

    public void initAdapter(){

        productFavAdapter=new ProductAdapter(getActiviy(),productList,this,productList.size());
        binding.favoriteRecycler.setAdapter(productFavAdapter);
    }
}