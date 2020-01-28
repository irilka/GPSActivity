package com.example.gpsactivity.manager;

import com.example.gpsactivity.model.SensorsData;

public interface BaseSensorManager {

    /**
     * Data from current sensors. {@link SensorsData}
     */
    public SensorsData currentData = new SensorsData();

    /**
     * Starts monitoring if it's possible. All permissions should be granted before.
     */
    public void tryStart();

    /**
     * Stops monitoring.
     */
    public void stop();
}
