package com.ramez.shopp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ramez.shopp.Adapter.FavoriteAdapter;
import com.ramez.shopp.Adapter.ProductAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.CallBack.DataCallback;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.FavouriteResultModel;
import com.ramez.shopp.Models.MainModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.FragmentOfferBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class OfferFragment extends FragmentBase implements FavoriteAdapter.OnItemClick {
    private FragmentOfferBinding binding;
    private FavoriteAdapter productOfferAdapter;
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

        getOfferList(0,country_id,city_id,user_id,Constants.offered_filter,0, 10);

        binding.dataLY.setOnRefreshListener(() -> {
          binding.dataLY.setRefreshing(false);
          getOfferList(0,country_id,city_id,user_id,Constants.offered_filter,0, 10);


      });

        binding.failGetDataLY.refreshBtn.setOnClickListener(view1 -> {
            getOfferList(0,country_id,city_id,user_id,Constants.offered_filter,0, 10);
        });


        return view;
    }

    public void initAdapter(){

//        productOfferAdapter = new FavoriteAdapter(getActivityy(), productOffersList,this,productOffersList.size());
        productOfferAdapter = new FavoriteAdapter(getActivityy(), productOffersList, 0, 0, country_id, city_id, user_id,
                productOffersList.size(), binding.offerRecycler, Constants.offered_filter, this, new DataCallback() {
            @Override
            public void dataResult(Object obj, String func, boolean IsSuccess) {

            }
        });
        binding.offerRecycler.setAdapter(productOfferAdapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {

    }
    public void getOfferList(int category_id,int country_id,int city_id,String user_id,String filter, int page_number, int page_size) {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (isVisible()){

                FavouriteResultModel result = (FavouriteResultModel) obj;
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
                    if (result.getData() != null && result.getData().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        productOffersList = result.getData();
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


        }).getFavorite(category_id, country_id, city_id, user_id, filter, page_number, page_size);
    }




}