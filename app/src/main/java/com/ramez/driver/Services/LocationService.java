package com.ramez.driver.Services;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ramez.driver.ApiHandler.DataFetcherCallBack;
import com.ramez.driver.Classes.UtilityApp;
import com.ramez.driver.Dialogs.InfoDialog;
import com.ramez.driver.Models.ApiLocationModel;
import com.ramez.driver.Models.MemberModel;
import com.ramez.driver.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;

public class LocationService extends Service {
    DatabaseReference db_drivers;
    MemberModel memberModel;
    Context context;
    private double lat = 0, lng = 0;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        context=this;
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (UtilityApp.getUserData() != null) {
            memberModel = UtilityApp.getUserData();

            if (memberModel.getRole_id() == 4) {
                db_drivers = FirebaseDatabase.getInstance().getReference().child("Drivers").child(String.valueOf(memberModel.getId()));
                requestLocationPermission(context);

            }


        }


        return START_STICKY;
    }


    private void saveDriversToFirebase(Double lat, Double lng) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("lan_location", String.valueOf(lng));
        childUpdates.put("lat_location", String.valueOf(lat));
        db_drivers.updateChildren(childUpdates).addOnSuccessListener(aVoid ->
                Log.i("tag","updated location")).addOnFailureListener(e -> {
            Log.i("tag","failed to updated location");
        });


    }





    void requestLocationPermission(final Context activity) {
        Log.d("tag ","Log requestLocationPermission");


        try {
            String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            Dexter.withContext(this).withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {

                        if (SmartLocation.with(context).location().state().isGpsAvailable()) {

                            LocationParams.Builder builder = new LocationParams.Builder().setAccuracy(LocationAccuracy.HIGH).setDistance(12).setInterval(500);

                            LocationGooglePlayServicesWithFallbackProvider provider = new LocationGooglePlayServicesWithFallbackProvider(context);
                            SmartLocation.with(context).location(provider).continuous().config(builder.build()).start(location -> {
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
                        InfoDialog infoDialog=new InfoDialog((Activity) context, getString(R.string.locationPermissionExplain), false, new DataFetcherCallBack() {
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


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        super.onTaskRemoved(rootIntent);
    }

    public void showSettingsAlert() {
        Log.d("tag","Log showSettingsAlert ");

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);

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

    public void getLatAndLong() {
        Log.i("tag","Log getLatAndLong");

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