package com.example.tbojovic_ridebook.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

/**
 * This fragment class allows for the selection of a date. A dialog pops up with a calender, where
 * the user can select the date desired.
 * The purpose is to allow for easy date selection decoupled from a particular activity.
 * The activity that shows the fragment must implement the {@link OnDatePickerListener} interface
 * to receive the time picked.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private OnDatePickerListener listener;

    public interface OnDatePickerListener {
        void onDatePicked(LocalDate date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // default date in the picker dialog is the current date
        final LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue() - 1; // DatePickerDialog accepts a zero indexed month
        int day = date.getDayOfMonth();

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDatePickerListener) {
            listener = (OnDatePickerListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement OnDatePickerListener");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month + 1, day);

        // bubble the event up to the activity
        listener.onDatePicked(date);
    }
}
