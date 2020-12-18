package com.ramez.shopp.Activities;


import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ramez.shopp.Adapter.ProductAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Dialogs.CheckLoginDialog;
import com.ramez.shopp.Models.AutoCompeteResult;
import com.ramez.shopp.Models.AutoCompleteModel;
import com.ramez.shopp.Models.FavouriteResultModel;
import com.ramez.shopp.Models.LocalModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.Models.ResultAPIModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.Helpers;
import com.ramez.shopp.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static android.content.ContentValues.TAG;

public class SearchActivity extends ActivityBase implements ProductAdapter.OnItemClick {
    ActivitySearchBinding binding;
    ArrayList<ProductModel> productList;
    GridLayoutManager gridLayoutManager;
    boolean searchByCode = false;
    private ProductAdapter adapter;
    private int country_id, city_id;
    private String user_id, filter;
    private MemberModel user;
    private LocalModel localModel;
    private CheckLoginDialog checkLoginDialog;
    private Barcode barcodeResult;
    private String result;
    private ArrayList<AutoCompleteModel> data = null;
    private ArrayList<String> autoCompleteList;
    int numColumn=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        productList = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(getActiviy(), numColumn);
        binding.recycler.setLayoutManager(gridLayoutManager);
        data = new ArrayList<>();
        autoCompleteList = new ArrayList<>();


        if (!UtilityApp.isLogin()) {

            showLoginDialog();
            binding.dataLY.setVisibility(View.GONE);

        } else {

            localModel = UtilityApp.getLocalData();
            user = UtilityApp.getUserData();
            country_id = localModel.getCountryId();
            city_id = Integer.parseInt(localModel.getCityId());
            user_id = String.valueOf(user.getId());

            getIntentExtra();

        }

        binding.searchEt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                autoComplete(country_id, city_id, user_id, String.valueOf(s), 0, 10);
                searchTxt(country_id, city_id, user_id, String.valueOf(s), 0, 10);


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                autoComplete(country_id, city_id, user_id, String.valueOf(s), 0, 10);
                searchTxt(country_id, city_id, user_id, String.valueOf(s), 0, 10);

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoComplete(country_id, city_id, user_id, String.valueOf(s), 0, 10);
                searchTxt(country_id, city_id, user_id, String.valueOf(s), 0, 10);
            }
        });


        binding.view1But.setOnClickListener(view1 -> {
            numColumn=1;
            gridLayoutManager.setSpanCount(numColumn);
            adapter.notifyDataSetChanged();


        });

        binding.view2But.setOnClickListener(view1 -> {
            numColumn=2;
            gridLayoutManager.setSpanCount(numColumn);
            adapter.notifyDataSetChanged();

        });

        binding.priceBut.setOnClickListener(view1 -> {

           // Collections.sort(productList);
            Collections.sort(productList,Collections.reverseOrder());

        });

        binding.categoriesCountTv.setText(String.valueOf(productList.size()));
        binding.offerCountTv.setText(String.valueOf(productList.size()));

    }

    public void initAdapter() {

        adapter = new ProductAdapter(getActiviy(), productList, this, 0);
        binding.recycler.setAdapter(adapter);
        binding.categoriesCountTv.setText(String.valueOf(productList.size()));
        binding.offerCountTv.setText(String.valueOf(productList.size()));
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {
        Intent intent = new Intent(getActiviy(), ProductDetailsActivity.class);
        intent.putExtra(Constants.DB_productModel, productModel);
        startActivity(intent);


    }


    public void searchBarcode(int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {

        productList.clear();
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            FavouriteResultModel result = (FavouriteResultModel) obj;
            String message = getActiviy().getString(R.string.fail_to_get_data);

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

        }).barcodeSearch(country_id, city_id, user_id, filter, page_number, page_size);
    }

    public void searchTxt(int country_id, int city_id, String user_id, String filter, int page_number, int page_size) {

        productList.clear();
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            FavouriteResultModel result = (FavouriteResultModel) obj;
            String message = getActiviy().getString(R.string.fail_to_get_data);

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

        }).searchTxt(country_id, city_id, user_id, filter, page_number, page_size);
    }


    private void showLoginDialog() {
        checkLoginDialog = new CheckLoginDialog(getActiviy(), R.string.please_login, R.string.text_login_login, R.string.register, null, null);
        checkLoginDialog.show();
    }


    private void getIntentExtra() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            result = getIntent().getStringExtra(Constants.CODE);
            searchByCode = getIntent().getBooleanExtra(Constants.SEARCH_BY_CODE_byCode, false);
            searchBarcode(country_id, city_id, user_id, result, 0, 10);


        }
    }

    public void autoComplete(int country_id, int city_id, String user_id, String text, int page_number, int page_size) {
        data.clear();

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            AutoCompeteResult result = (AutoCompeteResult) obj;

            if (IsSuccess) {
                if (result != null && result.getData().size() > 0) {

                    binding.dataLY.setVisibility(View.VISIBLE);
                    binding.noDataLY.noDataLY.setVisibility(View.GONE);
                    binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                    data = result.getData();
                    getAutoNames();
                    Log.i(TAG, "Log autoComplete list " + productList.size());

                } else {

                    binding.dataLY.setVisibility(View.GONE);
                    binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);

                }


            }

        }).autocomplete(country_id, city_id, user_id, text, page_number, page_size);
    }

    private void getAutoNames() {
        for (int i = 0; i < data.size(); i++) {
            autoCompleteList.add(data.get(i).getDataName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActiviy(), android.R.layout.simple_dropdown_item_1line, autoCompleteList);
        binding.searchEt.setAdapter(adapter);

    }


}