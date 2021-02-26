package com.activeitzone.activeecommercecms.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class Location {
    private final String permissionFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private final String permissionCoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;

    private final int REQUEST_CODE_LOCATION = 100;
    private FusedLocationProviderClient fusedLocationProviderClient;

    AppCompatActivity activity;
    LocationListener locationListener;
    LocationRequest locationRequest;

    private LocationCallback callback;

    public Location(AppCompatActivity activity, LocationListener locationListener) {
        this.activity = activity;
        this.locationListener = locationListener;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity.getApplicationContext());
        initializeLocationRequest();
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                locationListener.locationResponse(locationResult);
            }
        };
        initializeLocation();
    }

    public void initializeLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(50000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public boolean validatePermissionLocation(){
        boolean fineLocationAvailable = ActivityCompat.checkSelfPermission(activity.getApplicationContext(), permissionFineLocation) == PackageManager.PERMISSION_GRANTED;
        boolean coarseLocationAvailable = ActivityCompat.checkSelfPermission(activity.getApplicationContext(), permissionCoarseLocation) == PackageManager.PERMISSION_GRANTED;

        return fineLocationAvailable && coarseLocationAvailable;
    }


    public void requestPermissions(){
        boolean contextProvider = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionFineLocation);

        if (contextProvider){
            Toast.makeText(activity.getApplicationContext(), "Permission is required to obtain location", Toast.LENGTH_SHORT).show();
        }

        permissionRequest();
    }

    private void permissionRequest() {
        ActivityCompat.requestPermissions(activity, new String[]{permissionFineLocation, permissionCoarseLocation}, REQUEST_CODE_LOCATION);
    }

    public void stopUpdateLocation(){
        this.fusedLocationProviderClient.removeLocationUpdates(callback);
    }

    public void initializeLocation(){
        if (validatePermissionLocation()){
            getLocation();
        }else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        validatePermissionLocation();
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, null);
    }
}
