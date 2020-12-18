package com.ramez.shopp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.ramez.shopp.Activities.CategoryProductsActivity;
import com.ramez.shopp.Adapter.CategoryAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.CategoryModel;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Models.CategoryResultModel;
import com.ramez.shopp.Models.MainModel;
import com.ramez.shopp.R;
import com.ramez.shopp.databinding.FragmentCategoryBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CategoryFragment extends FragmentBase implements CategoryAdapter.OnItemClick {
    ArrayList<CategoryModel> categoryModelList;
    GridLayoutManager gridLayoutManager;
    private FragmentCategoryBinding binding;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        gridLayoutManager = new GridLayoutManager(getActivityy(), 3);
        binding.catRecycler.setHasFixedSize(true);
        binding.catRecycler.setLayoutManager(gridLayoutManager);

        getCategories(1);
        binding.swipeDataContainer.setOnRefreshListener(() -> {
            binding.swipeDataContainer.setRefreshing(false);

            getCategories(1);

        });

        return view;
    }

    private void initAdapter() {

        categoryAdapter = new CategoryAdapter(getActivityy(), categoryModelList, this);
        binding.catRecycler.setAdapter(categoryAdapter);

    }


    @Override
    public void onItemClicked(int position, CategoryModel categoryModel) {
        Intent intent = new Intent(getActivityy(), CategoryProductsActivity.class);
        intent.putExtra(Constants.CAT_LIST, categoryModelList);
        intent.putExtra(Constants.SELECTED_POSITION, categoryModelList.get(position).getId());
        intent.putExtra(Constants.CAT_MODEL, categoryModel);
        startActivity(intent);

    }

    public void getCategories(int storeId) {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            CategoryResultModel result = (CategoryResultModel) obj;
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


            } else {
                if (IsSuccess) {
                    if (result.getData() != null && result.getData().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        categoryModelList = result.getData();

                        Log.i(TAG, "Log productBestList" + categoryModelList.size());
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

        }).GetAllCategories(storeId);
    }
}