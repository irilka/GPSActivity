package com.example.gpsactivity.model;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class SensorsDataAdapter {

    private static String DELIMETER = ", ";
    private static String DATE_FORMATTER = ", ";

    public static String pack(SensorsData sensorsData) {
        String[] data = {
                String.valueOf(sensorsData.date.getTime()), //0
                sensorsData.latitude.toString(),            //1
                sensorsData.longitude.toString(),           //2
                sensorsData.altitude.toString(),            //3
                sensorsData.bearing.toString(),             //4
                sensorsData.speed.toString(),               //5
                sensorsData.accuracy.toString(),            //6
                sensorsData.xPosition.toString(),           //7
                sensorsData.yPosition.toString(),           //8
                sensorsData.zPosition.toString()            //9
        };

        String joinedData = String.join(DELIMETER, data);

        return joinedData;
    }

    public static SensorsData unpack(String string) {
        String[] values = string.split(DELIMETER);

        SensorsData data = new SensorsData();
        data.date = new Date(Long.valueOf(values[0]));
        data.latitude = Double.valueOf(values[1]);
        data.longitude = Double.valueOf(values[2]);
        data.altitude = Double.valueOf(values[3]);
        data.bearing = Double.valueOf(values[4]);
        data.speed = Double.valueOf(values[5]);
        data.accuracy = Double.valueOf(values[6]);
        data.xPosition = Double.valueOf(values[7]);
        data.yPosition = Double.valueOf(values[8]);
        data.zPosition = Double.valueOf(values[9]);

        return data;
    }
}
