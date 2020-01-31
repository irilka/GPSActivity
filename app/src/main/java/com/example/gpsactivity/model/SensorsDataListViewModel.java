package com.example.gpsactivity.model;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import androidx.lifecycle.ViewModel;

import com.example.gpsactivity.R;
import com.example.gpsactivity.manager.DataFileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SensorsDataListViewModel extends ViewModel {

    static private String DATA_FILE_PATH = "/data/com.example.gpsactivity/files/SensorsData.csv";

    private ArrayList<String> rawData = new ArrayList<>();
    private HashMap<String, SensorsData> cachedData = new HashMap<>();

    private DataFileManager dataFileManager;

    private int dataCount;

    public SensorsDataListViewModel() {
        initDataFileManager();
        loadData();
    }

    @Override
    protected void onCleared() {
        try {
            dataFileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onCleared();
    }

    public ArrayList<String> getStrings() {
        return rawData;
    }

    public int getCount() {
        return dataCount;
    }

    public SensorsData getSensorsDate(int i) {
        if (i < 0 || i >= dataCount) {
            return null;
        }

        String key = Integer.toString(i);

        if (cachedData.containsKey(key)) {
            return cachedData.get(key);
        }

        SensorsData newValue = SensorsDataAdapter.unpack(rawData.get(i));

        if (newValue != null) {
            cachedData.put(key, newValue);
        }

        return newValue;
    }

    public void add(SensorsData sensorsData) {
        cachedData.put(Integer.toString(dataCount++), sensorsData);

        String writingData = SensorsDataAdapter.pack(sensorsData);

        try {
            dataFileManager.write(writingData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDataFileManager() {
        try {
            String filePath = Environment.getDataDirectory().getPath() + DATA_FILE_PATH;
            dataFileManager = new DataFileManager(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        if (dataFileManager == null) {
            return;
        }

        rawData = dataFileManager.read();
        dataCount = rawData.size();
    }
}
