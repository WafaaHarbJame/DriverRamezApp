package com.ramez.shopp.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.MapHandler;
import com.ramez.shopp.databinding.ActivityMapBinding;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapActivity extends ActivityBase implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    ActivityMapBinding binding;
    private  String TAG="MapActivity";
    private    AutocompleteSupportFragment autocompleteFragment;
    private double longitude, latitude;
    boolean isSelectFromPlace, intentWithLocation = false;

    private float zoom = 0;
    private boolean isValid, isValidFromZoom;
    private FusedLocationProviderClient mFusedLocationClient;
    private CameraUpdate cameraUpdate;
    private GoogleMap mMap;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMapBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        requestLocationPermission(getActiviy());
        initPlaceAutoComplete();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMapLoadedCallback(() -> {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActiviy());
            if (ActivityCompat.checkSelfPermission(getActiviy(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActiviy()
                    , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (!intentWithLocation) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(getActiviy(), location -> {
                    if (location != null) {
                        try {
                            addMarkerOnMap(new LatLng(location.getLatitude(), location.getLongitude()), cameraUpdate, "getLastLocation");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                addMarkerOnMap(new LatLng(latitude, longitude), cameraUpdate, "withIntent");
            }

            if (ContextCompat.checkSelfPermission(getActiviy(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

                dragMapUnderMarker();
                addMyLocationButton();

                mMap.setOnCameraMoveStartedListener(i -> {
                    zoom = mMap.getCameraPosition().zoom;
                    isValidFromZoom = !(zoom < 15);
                    Log.d(TAG, "vailAddress: zoom:" + zoom);

                    try {
                        if (isValidFromZoom && isValid) {
                            binding.pickLocation.setEnabled(true);
                            binding.pickLocation.setText(getString(R.string.confirm));
                            binding.pickLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        } else {
                            binding.pickLocation.setEnabled(false);
                            binding.pickLocation.setText(getString(R.string.textZoomIn));
                            binding.pickLocation.setBackgroundColor(getResources().getColor(R.color.gray6));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            }
        });
    }

    private void initPlaceAutoComplete() {
        // TODO: Places initialize
        Places.initialize(getActiviy(), getString(R.string.mapKey), Locale.US);

        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        assert autocompleteFragment != null;

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.

                Log.i(TAG, "onPlaceSelected: " + place.getLatLng() + ", " + place.getId() + ", " + place.getName());
                try {
                    latitude = Objects.requireNonNull(place.getLatLng()).latitude;
                    longitude = place.getLatLng().longitude;
                    isValid = MapHandler.vailAddress(getActiviy(), place.getLatLng().latitude + "", place.getLatLng().longitude + "");

                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();
                    // Setting the position for the marker
                    markerOptions.position(new LatLng(latitude, longitude));
                    // Clears the previously touched position
                    mMap.clear();
                    // Animating to the touched position
                    // Placing a marker on the touched position
                    //mMap.addMarker(markerOptions);
                    cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15);
                    mMap.animateCamera(cameraUpdate);

                    try {
                        binding.tvPhysicalAddress.setText(MapHandler.getPhysicalLocation(getApplicationContext(), latitude + "", longitude + ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isSelectFromPlace = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
    private void addMarkerOnMap(LatLng point, CameraUpdate cameraUpdate, String fromAction) {
        Log.d(TAG, "addMarkerOnMap: " + fromAction + " " + point);
        if (!intentWithLocation) {
            latitude = point.latitude;
            longitude = point.longitude;
        }

        isValid = MapHandler.vailAddress(this, point.latitude + "", point.longitude + "");

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();
        // Setting the position for the marker
        markerOptions.position(new LatLng(latitude, longitude));
        // Clears the previously touched position
        mMap.clear();
        // Animating to the touched position
        // Placing a marker on the touched position
        //mMap.addMarker(markerOptions);
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15);
        mMap.animateCamera(cameraUpdate);

        try {
            binding.tvPhysicalAddress.setText(
                    MapHandler.getGpsAddress(getActiviy(), latitude, longitude )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /////gps
    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(getActiviy())
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        client.connect();

    }
    private void dragMapUnderMarker() {
        if (mMap != null) {
            mMap.setOnCameraIdleListener(() -> {
                LatLng midLatLng = mMap.getCameraPosition().target;
                latitude = midLatLng.latitude;
                longitude = midLatLng.longitude;
                isValid = MapHandler.vailAddress(this, latitude + "", longitude + "");
                try {
                    binding.tvPhysicalAddress.setText(
                            MapHandler.getPhysicalLocation(getApplicationContext(), latitude + "",
                                    longitude + "")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void addMyLocationButton() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            if (mMap != null) {
                mMap.setOnMyLocationChangeListener(arg0 -> {
                    try {
                        addMarkerOnMap(new LatLng(mMap.getMyLocation().getLatitude(),
                                mMap.getMyLocation().getLongitude()), cameraUpdate, "addMyLocationButton");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    void requestLocationPermission(final Context activity) {

        try {
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            Dexter.withContext((Activity) activity).withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {
                        initPlaceAutoComplete();


                    } else if (report.isAnyPermissionPermanentlyDenied()) {

                        //Dialogs.showMessage(context, getString(R.string.locationPermissionExplain), 0);
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage()+"");

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        autocompleteFragment.onActivityResult(requestCode, resultCode, data);

    }

}