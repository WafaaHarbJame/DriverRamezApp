package com.ramez.shopp.Activities;

import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ramez.shopp.Adapter.ProductAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.FavouriteResultModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.MainModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.ActivityFavoriteBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FavoriteActivity extends ActivityBase implements ProductAdapter.OnItemClick  {
    ActivityFavoriteBinding binding;
    private ProductAdapter productFavAdapter;
    ArrayList<ProductModel> productList;
    private GridLayoutManager gridLayoutManager;
    private int category_id=0, country_id, city_id;
    private String user_id, filter;
    private MemberModel user;
    private LocalModel localModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        productList=new ArrayList<>();

        gridLayoutManager =new GridLayoutManager(getActiviy(),2);

        binding.favoriteRecycler.setLayoutManager(gridLayoutManager);
        filter=Constants.favourite_filter;

        user= UtilityApp.getUserData();
        localModel=UtilityApp.getLocalData();

        user_id= String.valueOf(UtilityApp.getUserData().getId());


        binding.toolBar.mainTitleTxt.setText(getString(R.string.fav_products));

        binding.toolBar.backBtn.setOnClickListener(view1 -> {
            onBackPressed();
        });

        country_id=localModel.getCountryId();
        city_id= Integer.parseInt(localModel.getCityId());

        getFavoriteProducts(category_id,country_id,city_id,"14",filter,0,10);





    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }

    public void initAdapter(){

        productFavAdapter=new ProductAdapter(getActiviy(),productList,this,productList.size());
        binding.favoriteRecycler.setAdapter(productFavAdapter);
    }


    public void getFavoriteProducts(int category_id,int country_id,int city_id,String user_id,String filter,int page_number,int page_size ) {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(getActiviy(), (obj, func, IsSuccess) -> {
            FavouriteResultModel result = (FavouriteResultModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result.getMessage() != null) {
                    message = result.getMessage();
                }
                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.GONE);
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(message);

            } else if (func.equals(Constants.FAIL)) {

                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.GONE);
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(message);


            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        productList = result.getData();
                        Log.i(TAG, "Log productList" + productList.size());
                        initAdapter();

                    } else {

                        binding.dataLY.setVisibility(View.GONE);
                        binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);

                    }


                } else {

                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                    binding.failGetDataLY.failTxt.setText(message);


                }
            }

        }).getFavorite( category_id, country_id, city_id, user_id, filter, page_number, page_size );
    }
}