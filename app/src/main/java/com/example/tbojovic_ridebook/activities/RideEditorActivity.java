package com.example.tbojovic_ridebook.activities;


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
import java.util.HashMap;

/**
 * This class is an activity that displays a form to add a new {@link Ride} or to edit one.
 * The purpose is to handle user input and pass a new or edited Ride object back to {@link MainActivity},
 * where it will be added to the list or changed.
 * It validates user input through the {@link EditText} views and verifies that the user filled in
 * the required fields before sending a Ride back.
 * It also receives the selected date/time from the DatePickerFragment/TimePickerFragment.
 */
public class RideEditorActivity extends AppCompatActivity
        implements DatePickerFragment.OnDatePickerListener, TimePickerFragment.OnTimePickerListener {

    private TextView dateInput, timeInput;
    private EditText distanceInput, speedInput, cadenceInput, commentInput;
    private Ride ride;

    // the position in the list of the ride being edited. -1 if a new ride is being added
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_editor);
        setupViews();

        Intent intent = getIntent();
        ride = (Ride) intent.getSerializableExtra("ride");

        // A Ride is passed to the intent on an edit request
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
        // getRideFromInput() returns null if a Ride could not be constructed from the view
        HashMap<TextView, String> inputs = getInputs();
        if (!inputs.isEmpty()) {
            Intent returnIntent = new Intent();
            if (position == -1) {
                ride = getNewRideFromInput(inputs);
            } else {
                editRideFromInput(inputs);
                returnIntent.putExtra("position", position);
            }
            returnIntent.putExtra("ride", ride);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, R.string.editor_warning, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 200);
            toast.show();
        }
    }

    // Set the class attributes up with the corresponding views
    private void setupViews() {
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        distanceInput = findViewById(R.id.distanceInput);
        speedInput = findViewById(R.id.speedInput);
        cadenceInput = findViewById(R.id.cadenceInput);
        commentInput = findViewById(R.id.commentInput);
    }

    // Given a Ride to edit, set the input views to match the Ride attributes
    private void populateInput(Ride ride) {
        dateInput.setText(ride.getDate().toString());
        timeInput.setText(ride.getTime().toString());
        distanceInput.setText(String.valueOf(ride.getDistance()));
        speedInput.setText(String.valueOf(ride.getAverageSpeed()));
        cadenceInput.setText(String.valueOf(ride.getAverageCadence()));
        commentInput.setText(String.valueOf(ride.getComment()));
    }

    // Return a hashmap with the string value of the user inputs
    // Return an empty hashmap if the required fields are not inputted
    private HashMap<TextView, String> getInputs() {
        HashMap<TextView, String> inputMap = new HashMap<TextView, String>();

        TextView []requiredViews = {dateInput, timeInput, distanceInput, speedInput, cadenceInput};

        for (TextView view: requiredViews) {
            String string = view.getText().toString();
            inputMap.put(view, string);
        }

        if (inputMap.containsValue(null) || inputMap.containsValue("")) {
            inputMap.clear();
            return inputMap;
        }

        String comment = commentInput.getText().toString();
        if (!comment.isEmpty()) {
            inputMap.put(commentInput, comment);
        }

        return inputMap;
    }

    // Construct a Ride object from the user input.
    private Ride getNewRideFromInput(HashMap<TextView, String> inputs) {
        if (inputs.containsKey(commentInput)) {
            return new Ride(LocalDate.parse(inputs.get(dateInput)), LocalTime.parse(inputs.get(timeInput)),
                    Double.parseDouble(inputs.get(distanceInput)), Double.parseDouble(inputs.get(speedInput)),
                    Integer.parseInt(inputs.get(cadenceInput)), inputs.get(commentInput));
        }
        return new Ride(LocalDate.parse(inputs.get(dateInput)), LocalTime.parse(inputs.get(timeInput)),
                Double.parseDouble(inputs.get(distanceInput)), Double.parseDouble(inputs.get(speedInput)),
                Integer.parseInt(inputs.get(cadenceInput)));
    }

    // Edit the existing ride based on the user input
    private void editRideFromInput(HashMap<TextView, String> inputs) {
        ride.setDate(LocalDate.parse(inputs.get(dateInput)));
        ride.setTime(LocalTime.parse(inputs.get(timeInput)));
        ride.setDistance(Double.parseDouble(inputs.get(distanceInput)));
        ride.setAverageSpeed(Double.parseDouble(inputs.get(speedInput)));
        ride.setAverageCadence(Integer.parseInt(inputs.get(cadenceInput)));
        if (inputs.containsKey(commentInput)) {
            ride.setComment(inputs.get(commentInput));
        }
    }

}
