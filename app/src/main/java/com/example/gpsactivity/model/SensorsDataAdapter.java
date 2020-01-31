package com.example.gpsactivity.model;

import java.util.Arrays;

public class SensorsDataAdapter {

    public static String adapt(SensorsData sensorsData) {
        String[] data = {
                sensorsData.latitude.toString(),
                sensorsData.longitude.toString(),
                sensorsData.altitude.toString(),
                sensorsData.bearing.toString(),
                sensorsData.speed.toString(),
                sensorsData.accuracy.toString(),
                sensorsData.xPosition.toString(),
                sensorsData.yPosition.toString(),
                sensorsData.zPosition.toString()
        };

        String joinedData = String.join(", ", data);

        return joinedData;
    }
}
