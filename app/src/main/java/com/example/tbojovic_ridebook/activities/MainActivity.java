package com.example.tbojovic_ridebook.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tbojovic_ridebook.R;
import com.example.tbojovic_ridebook.models.Ride;
import com.example.tbojovic_ridebook.adapters.RideAdapter;

import java.util.ArrayList;

/**
 * This class is an activity that displays the bike ride list. From here the user can add, edit,
 * view and delete rides. A total distance of all the rides is also displayed.
 * The purpose is to display and manipulate the bike ride list. While the details of the
 * manipulation are sent to other classes (eg. {@link RideEditorActivity} ), the actual
 * addition/deletion/change to the data is performed here, after the change is received through
 * callback functions.
 */
public class MainActivity extends AppCompatActivity implements RideAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RideAdapter recyclerAdapter;
    private ArrayList<Ride> rideList;
    static final int ADD_RIDE_REQUEST = 1;
    static final int EDIT_RIDE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.ridesRecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        rideList = new ArrayList<>();

        updateDistanceTotal();

        recyclerAdapter = new RideAdapter(rideList, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        // clear the delete button when returning to the activity
        recyclerAdapter.resetSelectedPosition();
    }

    public void onAddClick(View view) {
        Intent intent = new Intent(this, RideEditorActivity.class);
        startActivityForResult(intent, ADD_RIDE_REQUEST);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, RideEditorActivity.class);
        intent.putExtra("ride", rideList.get(position));
        intent.putExtra("position", position);

        startActivityForResult(intent, EDIT_RIDE_REQUEST);
    }

    @Override
    public void onItemDeleteClick(int position) {
        rideList.remove(position);
        recyclerAdapter.notifyItemRemoved(position);

        updateDistanceTotal();

        if (rideList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.emptyList).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (requestCode == ADD_RIDE_REQUEST && resultCode == RESULT_OK) {
            Ride ride = (Ride) resultIntent.getSerializableExtra("ride");
            rideList.add(ride);
            recyclerAdapter.notifyItemInserted(rideList.size()-1);
            if (rideList.size() == 1) {
                findViewById(R.id.emptyList).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == EDIT_RIDE_REQUEST && resultCode == RESULT_OK) {
            Ride ride = (Ride) resultIntent.getSerializableExtra("ride");
            int position = resultIntent.getIntExtra("position", -1);
            rideList.set(position, ride);
            recyclerAdapter.notifyItemChanged(position);
        }
        updateDistanceTotal();
    }

    private void updateDistanceTotal() {
        double totalDistance = Ride.totalDistance(rideList);
        TextView tvDistance = findViewById(R.id.totalDistanceText);
        tvDistance.setText(getString(R.string.total_ride_distance, totalDistance));
    }

}
