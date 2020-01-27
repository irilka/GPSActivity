package com.example.gpsactivity.manager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gpsactivity.R;

import java.lang.ref.WeakReference;

public class GpsManager {

    private static final int PERMISSION_REQUEST_LOCATION = 99;
    private static final String PERMISSION_NAME = Manifest.permission.ACCESS_FINE_LOCATION;

    private WeakReference<Activity> owner;
    private LocationManager locationManager;
    private LocationProvider locationProvider;
    private LocationListener locationListener;

    private boolean isEnabledLocationUpdates;

    public GpsManager(@NonNull Activity owner, @NonNull LocationListener listener) {
        this.owner = new WeakReference(owner);
        locationListener = listener;
        locationManager = (LocationManager) owner.getSystemService(Context.LOCATION_SERVICE);
        isEnabledLocationUpdates = false;
    }

    public boolean checkLocationPermission() {
        final Activity activity = owner.get();

        if (activity == null) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(activity, PERMISSION_NAME) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_NAME)) {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.location_permission_title)
                    .setMessage(R.string.location_permission_text)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{PERMISSION_NAME}, PERMISSION_REQUEST_LOCATION);
                        }
                    })
                    .create()
                    .show();
        }
        else {
            ActivityCompat.requestPermissions(activity, new String[]{PERMISSION_NAME}, PERMISSION_REQUEST_LOCATION);
        }

        return false;
    }

    public void checkPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_LOCATION) {
            return;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkGpsSettings();
        }
    }

    public void tryEnableLocationsListening() {
        Activity activity = owner.get();

        if (activity == null || locationManager == null || isEnabledLocationUpdates) {
            return;
        }

        if (ContextCompat.checkSelfPermission(activity, PERMISSION_NAME) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
        isEnabledLocationUpdates = true;
    }

    public void disableLocationListening() {
        isEnabledLocationUpdates = false;
        locationManager.removeUpdates(locationListener);
    }

    /**
     *
     */
    private void checkGpsSettings() {
        final Activity activity = owner.get();

        if (activity == null) {
            return;
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.gps_disabled_title)
                    .setMessage(R.string.gps_disabled_text)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(settingsIntent);
                        }
                    })
                    .create()
                    .show();

            return;
        }

        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        tryEnableLocationsListening();
    }

}
