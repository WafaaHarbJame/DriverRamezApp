package com.ramez.shopp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
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
import com.ramez.shopp.Classes.Constants;
import com.ramez.shopp.GPSTracker;
import com.ramez.shopp.Permissions;
import com.ramez.shopp.R;
import com.ramez.shopp.Utils.MapHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.nlopez.smartlocation.SmartLocation;

public class MapsActivity extends ActivityBase implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MapsActivity";
    //// ACTIVITY_RESULT
    private final int ACTIVITY_RESULT = 3000;
    private final int REQUEST_LOCATION_CODE = 99;
    boolean isSelectFromPlace, intentWithLocation = false;
    // TODO: Google Clients
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    //// Google Maps & Location
    private GoogleMap userLocationMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationSettingsRequest.Builder builder;
    private PendingResult<LocationSettingsResult> result;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private CameraUpdate cameraUpdate;
    //// GPS Tracker
    private GPSTracker gpsTracker;
    private double longitude, latitude;
    // TODO: Views
    private ProgressDialog progressDialog;
    private Button pickLocation;
    private TextView textPhysicalAddress;

    private float zoom = 0;
    private boolean isValid, isValidFromZoom;
    private AutocompleteSupportFragment autocompleteFragment;
    private ImageView backBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //// Initialize Location

        gpsTracker = new GPSTracker(this);
        initViews();
        requestLocationPermission(MapsActivity.this);
        getDataExtra();

    }

    private void getDataExtra() {
        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra(Constants.KEY_LAT)) {
                intentWithLocation = true;
                latitude = Double.parseDouble(getIntent().getExtras().getString(Constants.KEY_LAT, "0"));
                longitude = Double.parseDouble(getIntent().getExtras().getString(Constants.KEY_LNG, "0"));
                isValid = MapHandler.vailAddress(this, latitude + "", longitude + "");
            } else {
                intentWithLocation = false;
            }
        }
        // checkPermissions();
    }

    private void initViews() {

        //// Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIndeterminate(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        initPlaceAutoComplete();

        pickLocation = findViewById(R.id.pickLocation);
        textPhysicalAddress = findViewById(R.id.tvPhysicalAddress);
        backBut = findViewById(R.id.backBtn);

        pickLocation.setOnClickListener(v -> {
            setActivityResult(latitude + "", longitude + "");
        });


        backBut.setOnClickListener(view -> onBackPressed());

    }

    private void initPlaceAutoComplete() {
        // TODO: Places initialize
        Places.initialize(getApplicationContext(), getString(R.string.mapKey), Locale.US);
        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setHint(getString((R.string.searchaddress)));

        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "onPlaceSelected: " + place.getLatLng() + ", " + place.getId() + ", " + place.getName());
                try {
                    latitude = Objects.requireNonNull(place.getLatLng()).latitude;
                    longitude = place.getLatLng().longitude;
                    isValid = MapHandler.vailAddress(MapsActivity.this, place.getLatLng().latitude + "", place.getLatLng().longitude + "");

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
                        textPhysicalAddress.setText(MapHandler.getPhysicalLocation(getApplicationContext(), latitude + "", longitude + ""));
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

    private void setActivityResult(String latitude, String longitude) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_LAT, latitude);
        intent.putExtra(Constants.KEY_LNG, longitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMapLoadedCallback(() -> {
            if (progressDialog != null) {
                progressDialog.cancel();
            }

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (!intentWithLocation) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(MapsActivity.this, location -> {
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

            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

                dragMapUnderMarker();
                addMyLocationButton();

                mMap.setOnCameraMoveStartedListener(i -> {
                    zoom = mMap.getCameraPosition().zoom;
                    isValidFromZoom = !(zoom < 15);
                    Log.d(TAG, "vailAddress: zoom:" + zoom);

//                    try {
//                        if (isValidFromZoom && isValid) {
//                            pickLocation.setEnabled(true);
//                            pickLocation.setText(getString(R.string.confirm));
//                            pickLocation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                        } else {
//                            pickLocation.setEnabled(false);
//                            pickLocation.setText(getString(R.string.zoom));
//                            pickLocation.setBackgroundColor(getResources().getColor(R.color.gray1));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                });

            }
        });
    }

    private void addMyLocationButton() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(gpsTracker, getString(R.string.required_permissions_are_not_granted), Toast.LENGTH_SHORT).show();
                return;
            }
            mMap.setMyLocationEnabled(true);
            if (mMap != null) {
                mMap.setOnMyLocationChangeListener(arg0 -> {
                    try {
                        addMarkerOnMap(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()), cameraUpdate, "addMyLocationButton");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    private void dragMapUnderMarker() {
        if (mMap != null) {
            mMap.setOnCameraIdleListener(() -> {
                LatLng midLatLng = mMap.getCameraPosition().target;
                latitude = midLatLng.latitude;
                longitude = midLatLng.longitude;
                isValid = MapHandler.vailAddress(this, latitude + "", longitude + "");
                try {
                    textPhysicalAddress.setText(MapHandler.getPhysicalLocation(getApplicationContext(), latitude + "", longitude + ""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
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
            textPhysicalAddress.setText(MapHandler.getPhysicalLocation(getApplicationContext(), latitude + "", longitude + ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /////gps
    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(MapsActivity.this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataExtra();
    }

    private void checkPermissions() {
        try {
            if (gpsTracker != null) {
                if (!isSelectFromPlace) {
                    if (GPSTracker.isLocationEnabled(MapsActivity.this)) {
                        if (Permissions.requestLocationPermission(this)) {
                            mapFragment.getMapAsync(this);
                        } else {
                            finish();
                        }
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                }
            }
        } catch (Exception e) {
            if (Permissions.requestLocationPermission(this)) {
                mapFragment.getMapAsync(this);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        autocompleteFragment.onActivityResult(requestCode, resultCode, data);

    }


    void requestLocationPermission(final Context activity) {

        try {
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            Dexter.withContext(activity).withPermissions(permissions).withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()) {

                        if (SmartLocation.with(MapsActivity.this).location().state().isGpsAvailable()) {
                            SmartLocation.with(MapsActivity.this).location().oneFix().start(location -> {

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                checkPermissions();

                            });

                        }


                    } else if (report.isAnyPermissionPermanentlyDenied()) {
                        gpsTracker.showSettingsAlert();
                    }
                }


                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");

        }
    }
}
