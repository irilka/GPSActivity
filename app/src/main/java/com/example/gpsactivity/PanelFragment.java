package com.example.gpsactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gpsactivity.model.SensorsData;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelFragment extends Fragment {

    private TextView timeTextView;
    private TextView dateTextView;
    private TextView speedTextView;
    private TextView accuracyTextView;
    private TextView locationTextView;
    private TextView bearingTextView;
    private TextView altitudeTextView;
    private TextView positionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_panel, container, false);

        timeTextView = view.findViewById(R.id.time_text_view);
        dateTextView = view.findViewById(R.id.date_text_view);
        speedTextView = view.findViewById(R.id.speed_text_view);
        accuracyTextView = view.findViewById(R.id.accuracy_text_view);
        locationTextView = view.findViewById(R.id.location_text_view);
        bearingTextView = view.findViewById(R.id.bearing_text_view);
        altitudeTextView = view.findViewById(R.id.altitude_text_view);
        positionTextView = view.findViewById(R.id.position_text_view);

        return view;
    }

    public void setInfo(@Nullable SensorsData sensorsData) {
        dateTextView.setText(null);
        timeTextView.setText(null);
        speedTextView.setText("- km/h");
        accuracyTextView.setText("- m");
        locationTextView.setText("-");
        bearingTextView.setText("-");
        altitudeTextView.setText("-");
        positionTextView.setText("X: -, Y: -, Z: -");

        if (sensorsData == null || sensorsData.isEmpty()) {
            return;
        }

        if (sensorsData != null) {
            String dateString = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM).format(sensorsData.date);
            dateTextView.setText(dateString);

            String timeString = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM).format(sensorsData.date);
            timeTextView.setText(timeString);
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        if (sensorsData.bearing != null) {
            speedTextView.setText(sensorsData.bearing + " km/h");
        }

        if (sensorsData.accuracy != null) {
            accuracyTextView.setText(sensorsData.accuracy + " m");
        }

        if (sensorsData.latitude != null && sensorsData.longitude != null) {
            locationTextView.setText("Lat: " + decimalFormat.format(sensorsData.latitude) + "\t\tLng: " + decimalFormat.format(sensorsData.longitude));
        }

        if (sensorsData.bearing != null) {
            bearingTextView.setText(String.valueOf(sensorsData.bearing));
        }

        if (sensorsData.altitude != null) {
            altitudeTextView.setText(String.valueOf(sensorsData.altitude));
        }

        decimalFormat = new DecimalFormat("#.##");
        positionTextView.setText("X: " + decimalFormat.format(sensorsData.xPosition) +
                "\t\tY: " + decimalFormat.format(sensorsData.yPosition) +
                "\t\tZ: " + decimalFormat.format(sensorsData.zPosition));
    }
}
