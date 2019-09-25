package com.example.tbojovic_ridebook.fragments;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalTime;

/**
 * This fragment class allows for the selection of a time. A dialog pops up with a clock, where
 * the user can select the time desired.
 * The purpose is to allow for easy time selection decoupled from a particular activity.
 * The activity that shows the fragment must implement the {@link OnTimePickerListener} interface
 * to receive the date picked.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OnTimePickerListener listener;

    public interface OnTimePickerListener {
        void onTimePicked(LocalTime time);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // default time in the picker dialog is the current time
        final LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();

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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalTime time = LocalTime.of(hourOfDay, minute);

        // bubble the event up to the activity
        listener.onTimePicked(time);
    }
}
