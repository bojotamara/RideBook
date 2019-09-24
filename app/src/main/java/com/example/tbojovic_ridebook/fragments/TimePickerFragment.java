package com.example.tbojovic_ridebook.fragments;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.tbojovic_ridebook.R;

import java.time.LocalTime;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        TextView tvTime = getActivity().findViewById(R.id.timeInput);
        LocalTime time = LocalTime.of(hourOfDay, minute);
        tvTime.setText(time.toString());
    }
}
