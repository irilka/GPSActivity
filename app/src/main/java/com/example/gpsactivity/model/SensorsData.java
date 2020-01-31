package com.example.gpsactivity.model;

import android.location.Location;

import java.util.Date;

public class SensorsData {
    public Date date = null;
    public Double latitude = null;
    public Double longitude = null;
    public Double altitude = null;

    public Double bearing = null;
    public Double speed = null;
    public Double accuracy = null;

    public Double xPosition = null;
    public Double yPosition = null;
    public Double zPosition = null;

    /**
     * Sets all nonnull-values from argument into current instance's properties.
     * @param otherData from it new values will be set.
     */
    public void merge(SensorsData otherData) {
        if (otherData.latitude != null) {
            latitude = otherData.latitude;
        }

        if (otherData.longitude != null) {
            longitude = otherData.longitude;
        }

        if (otherData.altitude != null) {
            altitude = otherData.altitude;
        }

        if (otherData.bearing != null) {
            bearing = otherData.bearing;
        }

        if (otherData.speed != null) {
            speed = otherData.speed;
        }

        if (otherData.accuracy != null) {
            accuracy = otherData.accuracy;
        }

        if (otherData.xPosition != null) {
            xPosition = otherData.xPosition;
        }

        if (otherData.yPosition != null) {
            yPosition = otherData.yPosition;
        }

        if (otherData.zPosition != null) {
            zPosition = otherData.zPosition;
        }
    }

    /**
     * Sets all nonnull-values from argument into current instance's locations properties.
     * @param location from it new values will be set.
     */
    public void updateFromLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();

        final float bearing = location.getBearing();

        if (bearing >= 0) {
            this.bearing = new Double(bearing);
        }

        final float speed = location.getSpeed();

        if (speed >= 0) {
            this.speed = new Double(speed);
        }

        final float accuracy = location.getAccuracy();

        if (accuracy >= 0) {
            this.accuracy = new Double(accuracy);
        }
    }

    public boolean isEmpty() {
        if (date != null || latitude != null || longitude != null || altitude != null || bearing != null ||
            speed != null || accuracy != null || zPosition != null || yPosition != null || zPosition != null) {
            return false;
        }

        return true;
    }
}
