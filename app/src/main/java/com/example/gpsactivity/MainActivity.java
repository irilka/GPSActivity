package com.example.gpsactivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gpsactivity.manager.GpsManager;
import com.example.gpsactivity.model.SensorsData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private PanelFragment panel;
    private GoogleMap map;

    private GpsManager gpsManager;

    private SensorsData lastSensorsData = new SensorsData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGpsManager();

    }

    private void initGpsManager() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                showLocationOnMap(location);
                updateSensorsDataFromLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        gpsManager = new GpsManager(this, listener);
    }

    private void updateSensorsDataFromLocation(Location location) {
        lastSensorsData.updateFromLocation(location);
        panel.setInfo(new Date(), lastSensorsData);
    }

    private void showLocationOnMap(@NonNull Location location) {
        LatLng mapLocation = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions().position(mapLocation));
        map.moveCamera(CameraUpdateFactory.newLatLng(mapLocation));
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof PanelFragment) {
            panel = (PanelFragment) fragment;
        }
        else if (fragment instanceof SupportMapFragment) {
            ((SupportMapFragment) fragment).getMapAsync(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        gpsManager.tryEnableLocationsListening();
        panel.setInfo(new Date(), null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        gpsManager.disableLocationListening();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        gpsManager.checkLocationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        gpsManager.checkPermissionsResult(requestCode, permissions, grantResults);
    }
}
