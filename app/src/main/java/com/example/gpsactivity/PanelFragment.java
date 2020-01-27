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
    private TextView positionTextView;
    private TextView bearingTextView;
    private TextView altitudeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_panel, container, false);

        timeTextView = view.findViewById(R.id.time_text_view);
        dateTextView = view.findViewById(R.id.date_text_view);
        speedTextView = view.findViewById(R.id.speed_text_view);
        accuracyTextView = view.findViewById(R.id.accuracy_text_view);
        positionTextView = view.findViewById(R.id.position_text_view);
        bearingTextView = view.findViewById(R.id.bearing_text_view);
        altitudeTextView = view.findViewById(R.id.altitude_text_view);

        return view;
    }

    public void setInfo(@NonNull Date date, @Nullable SensorsData sensorsData) {
        String dateString = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM).format(date);
        dateTextView.setText(dateString);

        String timeString = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM).format(date);
        timeTextView.setText(timeString);

        if (sensorsData == null) {
            speedTextView.setText("- km/h");
            accuracyTextView.setText("- m");
            positionTextView.setText("-");
            bearingTextView.setText("-");
            altitudeTextView.setText("-");
        }
        else {
            speedTextView.setText(sensorsData.bearing + " km/h");
            accuracyTextView.setText(sensorsData.accuracy + " m");

            DecimalFormat decimalFormat = new DecimalFormat("#.#####");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            positionTextView.setText("Lat: " + decimalFormat.format(sensorsData.latitude) + ", Long: " + decimalFormat.format(sensorsData.longitude));

            bearingTextView.setText(String.valueOf(sensorsData.bearing));
            altitudeTextView.setText(String.valueOf(sensorsData.altitude));
        }
    }
}
