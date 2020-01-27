package com.example.gpsactivity.model;

import android.location.Location;

public class SensorsData {
    public Double latitude = 0.0;
    public Double longitude = 0.0;
    public float bearing = 0;
    public Double altitude = 0.0;
    public float speed = 0;
    public float accuracy = 0;
    public Double xPosition = 0.0;
    public Double yPosition = 0.0;
    public Double zPosition = 0.0;

    public void updateFromLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        bearing = location.getBearing();
        altitude = location.getAltitude();
        speed = location.getSpeed();
        accuracy = location.getAccuracy();
    }
}
