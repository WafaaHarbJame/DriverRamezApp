package com.ramez.shopp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ramez.shopp.Adapter.ProductAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.MainModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.FragmentOfferBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class OfferFragment extends FragmentBase implements ProductAdapter.OnItemClick {
    private FragmentOfferBinding binding;
    private ProductAdapter productOfferAdapter;
    ArrayList<ProductModel> productOffersList;
    GridLayoutManager gridLayoutManager;
    private int category_id = 0, country_id, city_id;
    String user_id=" ";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOfferBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        productOffersList=new ArrayList<>();


        if(UtilityApp.isLogin()){
            MemberModel memberModel = UtilityApp.getUserData();
            user_id = String.valueOf(memberModel.getId());

        }

        country_id = UtilityApp.getLocalData().getCountryId();
        city_id = Integer.parseInt(UtilityApp.getLocalData().getCityId());

        gridLayoutManager =new GridLayoutManager(getActivityy(),2);
        binding.offerRecycler.setLayoutManager(gridLayoutManager);

        binding.offerRecycler.setHasFixedSize(true);
        binding.offerRecycler.setItemAnimator(null);

        getOfferList(0,country_id,city_id,user_id);

        binding.dataLY.setOnRefreshListener(() -> {
          binding.dataLY.setRefreshing(false);
          getOfferList(0,country_id,city_id,user_id);


      });

        binding.failGetDataLY.refreshBtn.setOnClickListener(view1 -> {
            getOfferList(0,country_id,city_id,user_id);
        });


        return view;
    }

    public void initAdapter(){

        productOfferAdapter = new ProductAdapter(getActivityy(), productOffersList,this,productOffersList.size());
        binding.offerRecycler.setAdapter(productOfferAdapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }
    public void getOfferList(int category_id,int country_id,int city_id,String user_id) {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (isVisible()){

                MainModel result = (MainModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result != null) {
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

            } else if (func.equals(Constants.NO_CONNECTION)) {
                binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                binding.failGetDataLY.failTxt.setText(R.string.no_internet_connection);
                binding.failGetDataLY.noInternetIv.setVisibility(View.VISIBLE);
                binding.dataLY.setVisibility(View.GONE);

            } else {
                if (IsSuccess) {
                    if (result.getOfferedProducts() != null && result.getOfferedProducts().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        productOffersList = result.getOfferedProducts();

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
        }


        }).GetMainPage(category_id, country_id, city_id, user_id);
    }
}