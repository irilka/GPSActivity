package com.example.gpsactivity.model;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SensorsDataAdapter {

    private static String DELIMETER = ", ";

    public static String pack(SensorsData sensorsData) {
        String[] data = {
                sensorsData.latitude.toString(),    //0
                sensorsData.longitude.toString(),   //1
                sensorsData.altitude.toString(),    //2
                sensorsData.bearing.toString(),     //3
                sensorsData.speed.toString(),       //4
                sensorsData.accuracy.toString(),    //5
                sensorsData.xPosition.toString(),   //6
                sensorsData.yPosition.toString(),   //7
                sensorsData.zPosition.toString()    //8
        };

        String joinedData = String.join(DELIMETER, data);

        return joinedData;
    }

    public static SensorsData unpack(String string) {
        String[] values = string.split(DELIMETER);

        SensorsData data = new SensorsData();
        data.latitude = Double.valueOf(values[0]);
        data.longitude = Double.valueOf(values[1]);
        data.altitude = Double.valueOf(values[2]);
        data.bearing = Double.valueOf(values[3]);
        data.speed = Double.valueOf(values[4]);
        data.accuracy = Double.valueOf(values[5]);
        data.xPosition = Double.valueOf(values[6]);
        data.yPosition = Double.valueOf(values[7]);
        data.zPosition = Double.valueOf(values[8]);

        return data;
    }
}
