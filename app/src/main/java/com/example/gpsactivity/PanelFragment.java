package com.example.gpsactivity;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gpsactivity.model.SensorsData;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class PanelFragment extends Fragment {

    private TextView timeTextView;
    private TextView dateTextView;
    private TextView speedTextView;
    private TextView accuracyTextView;
    private TextView positionTextView;
    private TextView accelerationTextView;
    private TextView altitudeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_panel, container, false);

        timeTextView = view.findViewById(R.id.time_text_view);
        dateTextView = view.findViewById(R.id.date_text_view);
        speedTextView = view.findViewById(R.id.speed_text_view);
        accuracyTextView = view.findViewById(R.id.accuracy_text_view);
        positionTextView = view.findViewById(R.id.position_text_view);
        accelerationTextView = view.findViewById(R.id.acceleration_text_view);
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
            accelerationTextView.setText("-");
            altitudeTextView.setText("-");
        }
    }
}
