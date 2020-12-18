package com.ramez.shopp.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.ramez.shopp.Activities.AllListActivity;
import com.ramez.shopp.Activities.ProductDetailsActivity;
import com.ramez.shopp.Activities.RegisterLoginActivity;
import com.ramez.shopp.Activities.SearchActivity;
import com.ramez.shopp.Adapter.ProductAdapter;
import com.ramez.shopp.ApiHandler.DataFeacher;
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.Classes.UtilityApp;
import com.ramez.shopp.Models.AreasModel;
import com.ramez.shopp.Models.AreasResultModel;
import com.ramez.shopp.Models.MainModel;
import com.ramez.shopp.Models.MemberModel;
import com.ramez.shopp.Models.ProductModel;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.Helpers;
import com.ramez.shopp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class HomeFragment extends FragmentBase implements ProductAdapter.OnItemClick {
    ArrayList<ProductModel> productBestList;
    ArrayList<ProductModel> productSellerList;
    ArrayList<ProductModel> productOffersList;
    GridLayoutManager bestProductGridLayoutManager;
    GridLayoutManager bestSellerLayoutManager;
    GridLayoutManager bestOfferGridLayoutManager;
    private FragmentHomeBinding binding;
    private ProductAdapter productBestAdapter;
    private ProductAdapter productSellerAdapter;
    private ProductAdapter productOfferAdapter;
    private int category_id = 0, country_id, city_id;
    String user_id="";
    // TODO Barcode
    private Barcode barcodeResult;
    private String result;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        productBestList = new ArrayList<>();
        productSellerList = new ArrayList<>();
        productOffersList = new ArrayList<>();

        if(UtilityApp.isLogin()){
            MemberModel memberModel = UtilityApp.getUserData();
            user_id = String.valueOf(memberModel.getId());
        }

        country_id = UtilityApp.getLocalData().getCountryId();
        city_id = Integer.parseInt(UtilityApp.getLocalData().getCityId());

        bestProductGridLayoutManager = new GridLayoutManager(getActivityy(), 2);
        bestOfferGridLayoutManager = new GridLayoutManager(getActivityy(), 2);
        bestSellerLayoutManager = new GridLayoutManager(getActivityy(), 2);

        binding.bestSellerRecycler.setLayoutManager(bestSellerLayoutManager);
        binding.bestProductRecycler.setLayoutManager(bestProductGridLayoutManager);
        binding.offerRecycler.setLayoutManager(bestOfferGridLayoutManager);

        binding.offerRecycler.setHasFixedSize(true);
        binding.bestProductRecycler.setHasFixedSize(true);
        binding.bestSellerRecycler.setHasFixedSize(true);

        GetHomePage();

        binding.moreBestBut.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivityy(), AllListActivity.class);
            intent.putExtra(Constants.LIST_MODEL, productBestList);
            intent.putExtra(Constants.LIST_MODEL_NAME, getString(R.string.best_products));
            startActivity(intent);

        });

        binding.searchBut.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivityy(), SearchActivity.class);
            startActivity(intent);

        });

        binding.moreBoughtBut.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivityy(), AllListActivity.class);
            intent.putExtra(Constants.LIST_MODEL, productSellerList);
            intent.putExtra(Constants.LIST_MODEL_NAME, getString(R.string.best_sell));
            startActivity(intent);

        });
        binding.moreOfferBut.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivityy(), AllListActivity.class);
            intent.putExtra(Constants.LIST_MODEL, productOffersList);
            intent.putExtra(Constants.LIST_MODEL_NAME, getString(R.string.offers));
            startActivity(intent);

        });

        binding.barcodeBut.setOnClickListener(view1 -> {
            checkCameraPermission();

        });

        return view;
    }

    public void initAdapter() {

        productBestAdapter = new ProductAdapter(getActivityy(), productBestList, this, 2);
        productSellerAdapter = new ProductAdapter(getActivityy(), productSellerList, this, 2);
        productOfferAdapter = new ProductAdapter(getActivityy(), productOffersList, this, 2);

        binding.bestProductRecycler.setAdapter(productBestAdapter);
        binding.bestSellerRecycler.setAdapter(productSellerAdapter);
        binding.offerRecycler.setAdapter(productOfferAdapter);
    }

    @Override
    public void onItemClicked(int position, ProductModel productModel) {
        Intent intent = new Intent(getActivityy(), ProductDetailsActivity.class);
        intent.putExtra(Constants.DB_productModel, productModel);
        startActivity(intent);

    }


    public void GetHomePage() {
        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            MainModel result = (MainModel) obj;
            String message = getString(R.string.fail_to_get_data);

            binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

            if (func.equals(Constants.ERROR)) {

                if (result != null&&result.getMessage()!=null) {
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
                    if (result.getFeatured() != null && result.getFeatured().size() > 0) {

                        binding.dataLY.setVisibility(View.VISIBLE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                        productBestList = result.getFeatured();
                        productSellerList = result.getQuickProducts();
                        productOffersList = result.getOfferedProducts();
                        Log.i(TAG, "Log productBestList" + productOffersList.size());
                        Log.i(TAG, "Log productSellerList" + productSellerList.size());
                        Log.i(TAG, "Log productOffersList" + productOffersList.size());
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

        }).GetMainPage(0, country_id, city_id, user_id);
    }

    private void checkCameraPermission() {
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                startScan();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(getActivityy(), "" + getString(R.string.permission_camera_rationale), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getActivityy(), "" + getString(R.string.error_in_data), Toast.LENGTH_SHORT).show();

            }
        }).onSameThread().check();
    }

    private void startScan() {

        final MaterialBarcodeScanner materialBarcodeScanner =
                new MaterialBarcodeScannerBuilder().withActivity(requireActivity()).withEnableAutoFocus
                        (true).withBleepEnabled(false).withBackfacingCamera()
                        .withTrackerColor(R.color.colorPrimaryDark).withCenterTracker(R.drawable.square_frame_normal,
                        R.drawable.square_frame_succeed).withText(getString(R.string.scan_text))
                        .withResultListener(barcode -> {

                            barcodeResult = barcode;
                            result = barcode.rawValue;
                            if (result != null) {
                                Intent intent = new Intent(getActivityy(), SearchActivity.class);
                              //  intent.putExtra(Constants.CODE, "2700093");
                                intent.putExtra(Constants.CODE, "2700093");
                                intent.putExtra(Constants.SEARCH_BY_CODE_byCode, true);
                                startActivity(intent);

                            }
                        }).build();
        materialBarcodeScanner.startScan();

    }

    @Override
    public void onDestroy() {
        if(Thread.getAllStackTraces()!=null)
        super.onDestroy();
    }
}