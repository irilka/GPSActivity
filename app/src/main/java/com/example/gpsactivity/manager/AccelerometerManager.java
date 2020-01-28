package com.example.gpsactivity.manager;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;

public class AccelerometerManager implements BaseSensorManager, SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    public AccelerometerManager(@NonNull Activity owner) {
        sensorManager = (SensorManager) owner.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    @Override
    public void tryStart() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        currentData.xPosition = new Double(event.values[0]);
        currentData.yPosition = new Double(event.values[1]);
        currentData.zPosition = new Double(event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
