package com.example.gpsactivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gpsactivity.manager.AccelerometerManager;
import com.example.gpsactivity.manager.GpsManager;
import com.example.gpsactivity.model.SensorsData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static long SENSORS_READ_TIMER_TASK_DELAY = 1000;
    final static long SENSORS_READ_TIMER_TASK_PERIOD = 5000;
    final static long WRITE_DATA_TIMER_TASK_DELAY = 0;
    final static long WRITE_DATA_TIMER_TASK_PERIOD = 10000;

    private PanelFragment panel;
    private GoogleMap map;

    private GpsManager gpsManager;
    private AccelerometerManager accelerometerManager;

    private Timer timer = new Timer();
    private TimerTask sensorsReadTimerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateSensorInfo();
                }
            });
        }
    };

    private TimerTask writeDataTimerTask;

    private View.OnClickListener recordingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (writeDataTimerTask == null) {
                startRecording((Button) v);
            }
            else {
                stopRecording((Button) v);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSensorsManagers();
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

        Button recordingButton = panel.getView().findViewById(R.id.recording_button);
        recordingButton.setOnClickListener(recordingClickListener);

        updateSensorInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();

        gpsManager.tryStart();
        accelerometerManager.tryStart();

        startTimerTask();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopTimerTask();

        gpsManager.stop();
        accelerometerManager.stop();
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

    private void initSensorsManagers() {
        gpsManager = new GpsManager(this);
        accelerometerManager = new AccelerometerManager(this);
    }

    private void updateSensorInfo() {
        SensorsData data = gpsManager.currentData;
        data.merge(accelerometerManager.currentData);

        if (data.latitude != null && data.longitude != null) {
            LatLng mapLocation = new LatLng(data.latitude, data.longitude);
            map.addMarker(new MarkerOptions().position(mapLocation));
            map.moveCamera(CameraUpdateFactory.newLatLng(mapLocation));
        }

        panel.setInfo(new Date(), data);
    }

    private void startTimerTask() {
        timer.schedule(sensorsReadTimerTask, SENSORS_READ_TIMER_TASK_DELAY, SENSORS_READ_TIMER_TASK_PERIOD);
    }

    private void stopTimerTask() {
        timer.cancel();
    }

    private void stopRecording(Button target) {
        writeDataTimerTask.cancel();
        writeDataTimerTask = null;

        target.setText(R.string.start_recording);
    }

    private void startRecording(Button target) {
        writeDataTimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO: write data to CSV-file
                    }
                });
            }
        };

        timer.schedule(writeDataTimerTask, WRITE_DATA_TIMER_TASK_DELAY, WRITE_DATA_TIMER_TASK_PERIOD);

        target.setText(R.string.stop_recording);
    }
}
