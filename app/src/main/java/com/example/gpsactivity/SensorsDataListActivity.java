package com.example.gpsactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.gpsactivity.model.SensorsData;
import com.example.gpsactivity.model.SensorsDataListViewModel;

import java.util.Date;

public class SensorsDataListActivity extends AppCompatActivity implements DataRowFragment.OnListFragmentInteractionListener {

    private PanelFragment panel;

    private SensorsDataListViewModel dataListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_data_list);

        dataListViewModel = ViewModelProviders.of(this).get(SensorsDataListViewModel.class);

        if (dataListViewModel.getCount() > 0) {
            onListFragmentInteraction(dataListViewModel.getSensorsDate(dataListViewModel.getCount() - 1));
        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof PanelFragment) {
            panel = (PanelFragment) fragment;
        }
    }

    @Override
    public void onListFragmentInteraction(SensorsData data) {
        panel.setInfo(data);
    }
}
