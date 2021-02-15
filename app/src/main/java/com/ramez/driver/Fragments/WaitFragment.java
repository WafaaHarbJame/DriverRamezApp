package com.ramez.driver.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ramez.driver.Adapter.Adapter_Ramez_Order;
import com.ramez.driver.ApiHandler.DataFeacher;
import com.ramez.driver.ApiHandler.DataFetcherCallBack;
import com.ramez.driver.Classes.Constants;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Dialogs.InfoDialog;
import com.ramez.driver.Models.ApiLocationModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.Models.RamesOrders;
import com.ramez.driver.Models.RamezOrderCall;
import com.ramez.driver.Models.ResultAPIModel;
import com.ramez.driver.R;
import com.ramez.driver.databinding.FragmentHomeBinding;
import com.ramez.driver.databinding.FragmentWaitBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;


public class WaitFragment extends FragmentBase {
    private FragmentWaitBinding binding;
    ArrayList<RamesOrders> list;
    String user_id = " ";
    private int category_id = 0, country_id, city_id;
    MemberModel memberModel;
    String type;
    DatabaseReference db_drivers;
    private double lat = 0, lng = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWaitBinding.inflate(inflater, container, false);
        list = new ArrayList<>();

        if (UtilityApp.isLogin()) {
            memberModel = UtilityApp.getUserData();
            user_id = String.valueOf(memberModel.getId());

        }


        if (memberModel.getRole_id() == 4) {
            db_drivers = FirebaseDatabase.getInstance().getReference().child("Drivers").child(String.valueOf(memberModel.getId()));
            requestLocationPermission(getActivityy());

        }



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityy());
        binding.recycler.setLayoutManager(linearLayoutManager);

        binding.recycler.setHasFixedSize(true);
        binding.recycler.setItemAnimator(null);

        if (memberModel.getRole_id() == 6) {
            type = Constants.Packer;
            if (memberModel.getRole_id() == 0) {

                binding.dataLY.setVisibility(View.GONE);
                binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);

            } else {
                getList(type, memberModel.getId());

            }

        } else if (memberModel.getRole_id() == 4) {
            type = Constants.Driver;

                getList(type, memberModel.getId());



        }


        return binding.getRoot();
    }



    public void initAdapter() {

        Adapter_Ramez_Order adapter = new Adapter_Ramez_Order(getActivity(), list, 0);
        binding.recycler.setAdapter(adapter);
    }


    public void getList(String type, int ramezId) {
        RamezOrderCall ramezOrderCall=new RamezOrderCall();
        ramezOrderCall.type=type;
        ramezOrderCall.mode=Constants.one;
        ramezOrderCall.user_id=ramezId;
        ramezOrderCall.store_id= Integer.parseInt(UtilityApp.getLocalData().getCityId());

        binding.loadingProgressLY.loadingProgressLY.setVisibility(View.VISIBLE);
        binding.dataLY.setVisibility(View.GONE);
        binding.noDataLY.noDataLY.setVisibility(View.GONE);
        binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);

        new DataFeacher(false, (obj, func, IsSuccess) -> {
            if (isVisible()){

                ResultAPIModel<ArrayList<RamesOrders>> result= (ResultAPIModel<ArrayList<RamesOrders>>) obj;
                String message = getString(R.string.fail_to_get_data);

                binding.loadingProgressLY.loadingProgressLY.setVisibility(View.GONE);

                if (func.equals(Constants.ERROR)) {

                    if (result != null) {
                        message = result.message;
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
                        if (result != null && result.data.size() > 0) {
                            binding.dataLY.setVisibility(View.VISIBLE);
                            binding.noDataLY.noDataLY.setVisibility(View.GONE);
                            binding.failGetDataLY.failGetDataLY.setVisibility(View.GONE);
                            list = result.data;
                            initAdapter();

                        } else {

                            binding.dataLY.setVisibility(View.GONE);
                            binding.noDataLY.noDataLY.setVisibility(View.VISIBLE);
                            binding.noDataLY.noDataTxt.setText(R.string.textEmptyOrders);

                        }


                    } else {

                        binding.dataLY.setVisibility(View.GONE);
                        binding.noDataLY.noDataLY.setVisibility(View.GONE);
                        binding.failGetDataLY.failGetDataLY.setVisibility(View.VISIBLE);
                        binding.failGetDataLY.failTxt.setText(message);


                    }
                }
            }


        }).getRamezOrder(ramezOrderCall);
    }


    void requestLocationPermission(final Context activity) {
        Log.d("tag ","Log requestLocationPermission");


        try {
            String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            Dexter.withContext(getActivityy()).withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {

                        if (SmartLocation.with(getActivityy()).location().state().isGpsAvailable()) {

                            LocationParams.Builder builder = new LocationParams.Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(12).setInterval(500);

                            LocationGooglePlayServicesWithFallbackProvider provider = new LocationGooglePlayServicesWithFallbackProvider(getActivityy());
                            SmartLocation.with(getActivityy()).location(provider).continuous().config(builder.build()).start(location -> {
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                                memberModel.setLat_location(String.valueOf(lat));
                                memberModel.setLan_location(String.valueOf(lng));
                                saveDriversToFirebase(lat, lng);

                            });
                        } else {
                            if (memberModel.getLan_location().equals("") && memberModel.getLat_location().equals("")
                                    | memberModel.getLan_location().equals("0") && memberModel.getLat_location().equals("0")) {
                                showSettingsAlert();
                            }


                        }


                    } else if (report.isAnyPermissionPermanentlyDenied()) {
                        InfoDialog infoDialog=new InfoDialog(getActivityy(), getString(R.string.locationPermissionExplain), false, new DataFetcherCallBack() {
                            @Override
                            public void Result(Object obj, String func, boolean IsSuccess) {

                            }
                        });

                        infoDialog.show();


                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        } catch (Exception e) {

        }
    }

    public void showSettingsAlert() {
        Log.d("tag","Log showSettingsAlert ");

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivityy());

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getLatAndLong();
                dialog.cancel();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void saveDriversToFirebase(Double lat, Double lng) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("lan_location", String.valueOf(lng));
        childUpdates.put("lat_location", String.valueOf(lat));
        db_drivers.updateChildren(childUpdates).addOnSuccessListener(aVoid ->
                Log.i(getActivityy().getLocalClassName(),"updated location")).addOnFailureListener(e -> {
            Log.i(getActivityy().getLocalClassName(),"failed to updated location");
        });

    }

    public void getLatAndLong() {
        Log.i(getActivityy().getLocalClassName(),"Log getLatAndLong");

        AndroidNetworking.get("http://ip-api.com/json/").setTag("test").setPriority(Priority.LOW).addQueryParameter("lang", "en").
                build().getAsObject(ApiLocationModel.class, new ParsedRequestListener<ApiLocationModel>() {
            @Override
            public void onResponse(ApiLocationModel apiLocationModel) {
                Log.d("TAG", "getLatAndLong ");

                if (apiLocationModel.getStatus().equals("success")) {
                    lat = apiLocationModel.getLat();
                    lng = apiLocationModel.getLon();
                    memberModel.setLat_location(String.valueOf(lat));
                    memberModel.setLan_location(String.valueOf(lng));
                    saveDriversToFirebase(lat, lng);

                }


            }

            @Override
            public void onError(ANError anError) {

            }
        });

    }
}