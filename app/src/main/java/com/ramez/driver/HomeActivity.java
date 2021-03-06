package com.ramez.driver;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ramez.driver.Activities.ActivityBase;
import com.ramez.driver.ApiHandler.DataFetcherCallBack;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Dialogs.InfoDialog;
import com.ramez.driver.Models.ApiLocationModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.databinding.ActivityHomeBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

public class HomeActivity extends ActivityBase {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    DatabaseReference db_drivers;
    private double lat = 0, lng = 0;
    MemberModel memberModel;
    TextView navUsername;
    TextView navMobile;
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,
                R.id.nav_profile,R.id.nav_change_pass, R.id.nav_financial,
                R.id.nav_change_country, R.id.nav_lang,R.id.nav_messages,
                R.id.nav_logout).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
         navUsername = headerView.findViewById(R.id.nameTxt);
         navMobile = headerView.findViewById(R.id.mobileTxt);
         userImage = headerView.findViewById(R.id.userImage);

        if (UtilityApp.getUserData() != null) {

             memberModel = UtilityApp.getUserData();
            navUsername.setText(memberModel.getName());
            navMobile.setText(memberModel.getMobileNumber());
            Picasso.get().load(memberModel.getProfilePicture()).placeholder(R.drawable.holder_image).error(R.drawable.small_logo_screen)
                    .into(userImage);


            if (memberModel.getRole_id() == 4) {
                db_drivers = FirebaseDatabase.getInstance().getReference().child("Drivers").child(String.valueOf(memberModel.getId()));
                requestLocationPermission(getActiviy());

            }


        }


    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    void requestLocationPermission(final Context activity) {
        Log.d("tag ","Log requestLocationPermission");


        try {
            String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            Dexter.withContext(this).withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {

                        if (SmartLocation.with(getActiviy()).location().state().isGpsAvailable()) {

                            LocationParams.Builder builder = new LocationParams.Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(12).setInterval(500);

                            LocationGooglePlayServicesWithFallbackProvider provider = new LocationGooglePlayServicesWithFallbackProvider(getActiviy());
                            SmartLocation.with(getActiviy()).location(provider).continuous().config(builder.build()).start(location -> {
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
                        InfoDialog infoDialog=new InfoDialog(getActiviy(), getString(R.string.locationPermissionExplain), false, new DataFetcherCallBack() {
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

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActiviy());

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
                Log.i(getActiviy().getLocalClassName(),"updated location")).addOnFailureListener(e -> {
            Log.i(getActiviy().getLocalClassName(),"failed to updated location");
        });

    }

    public void getLatAndLong() {
        Log.i(getActiviy().getLocalClassName(),"Log getLatAndLong");

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

    @Override
    public void onResume() {
        if (UtilityApp.isLogin()) {
            memberModel = UtilityApp.getUserData();
            Picasso.get().load(memberModel.getProfilePicture()).placeholder(R.drawable.holder_image).error(R.drawable.small_logo_screen)
                    .into(userImage);

        }

        super.onResume();
    }


}