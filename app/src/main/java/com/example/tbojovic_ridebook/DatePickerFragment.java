package com.example.tbojovic_ridebook;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        //TODO: use the current date OR the one already selected in the date picker
        final LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue() - 1; // DatePickerDialog accepts a zero indexed month
        int day = date.getDayOfMonth();

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView tvDate = getActivity().findViewById(R.id.dateInput);
        LocalDate date = LocalDate.of(year, month + 1, day);
        tvDate.setText(date.toString());
    }
}
