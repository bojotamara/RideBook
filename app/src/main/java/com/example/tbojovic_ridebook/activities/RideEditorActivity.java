package com.example.tbojovic_ridebook.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tbojovic_ridebook.fragments.DatePickerFragment;
import com.example.tbojovic_ridebook.R;
import com.example.tbojovic_ridebook.models.Ride;
import com.example.tbojovic_ridebook.fragments.TimePickerFragment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RideEditorActivity extends AppCompatActivity
        implements DatePickerFragment.OnDatePickerListener, TimePickerFragment.OnTimePickerListener {

    private TextView dateInput, timeInput;
    private EditText distanceInput, speedInput, cadenceInput, commentInput;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_editor);
        setupViews();

        Intent intent = getIntent();
        Ride ride = (Ride) intent.getSerializableExtra("ride");

        if (ride != null) {
            position = intent.getIntExtra("position", -1);
            setTitle(R.string.editor_title_edit);
            populateInput(ride);
        }
    }

    public void onSetDateClick(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDatePicked(LocalDate date) {
        dateInput.setText(date.toString());
    }

    public void onSetTimeClick(View view) {
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimePicked(LocalTime time) {
        timeInput.setText(time.toString());
    }

    public void onSave(View view) {
        Ride ride = getRideFromInput();
        if (ride != null) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("ride", ride);
            if (position != -1) {
                returnIntent.putExtra("position", position);
            }
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, R.string.editor_warning, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        }
    }

    @Nullable
    private Ride getRideFromInput() {
        String dateString = dateInput.getText().toString(),
            timeString = timeInput.getText().toString(),
            distanceString = distanceInput.getText().toString(),
            speedString = speedInput.getText().toString(),
            cadenceString = cadenceInput.getText().toString(),
            comment = commentInput.getText().toString();

        String []requiredStrings = {dateString, timeString, distanceString, speedString, cadenceString};
        List<String> requiredInputs = new ArrayList<>(Arrays.asList(requiredStrings));

        if (requiredInputs.contains(null) || requiredInputs.contains("")) {
            return null;
        }

        return new Ride(LocalDate.parse(dateString), LocalTime.parse(timeString),
                Double.parseDouble(distanceString), Double.parseDouble(speedString),
                Integer.parseInt(cadenceString), comment);
    }

    private void populateInput(Ride ride) {
        dateInput.setText(ride.getDate().toString());
        timeInput.setText(ride.getTime().toString());
        distanceInput.setText(String.valueOf(ride.getDistance()));
        speedInput.setText(String.valueOf(ride.getAverageSpeed()));
        cadenceInput.setText(String.valueOf(ride.getAverageCadence()));
        commentInput.setText(String.valueOf(ride.getComment()));
    }

    private void setupViews() {
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        distanceInput = findViewById(R.id.distanceInput);
        speedInput = findViewById(R.id.speedInput);
        cadenceInput = findViewById(R.id.cadenceInput);
        commentInput = findViewById(R.id.commentInput);
    }

}
