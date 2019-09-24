package com.example.tbojovic_ridebook.fragments;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalTime;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OnTimePickerListener listener;

    public interface OnTimePickerListener {
        void onTimePicked(LocalTime time);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        //TODO: use the current date OR the one already selected in the date picker
        final LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimePickerListener) {
            listener = (OnTimePickerListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement OnTimePickerListener");
        }
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalTime time = LocalTime.of(hourOfDay, minute);
        listener.onTimePicked(time);
    }
}
