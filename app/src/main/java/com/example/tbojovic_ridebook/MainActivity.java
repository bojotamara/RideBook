package com.example.tbojovic_ridebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RideRecyclerAdapter.OnDeleteClickListener {
    private RecyclerView recyclerView;
    private RideRecyclerAdapter mAdapter;
    private List<Ride> rideList;
    final int ADD_RIDE_REQUEST = 1;
    final int EDIT_RIDE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerView = findViewById(R.id.ridesRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        this.recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        rideList = new ArrayList<>();

        updateDistanceTotal();

        this.mAdapter = new RideRecyclerAdapter(rideList, this);
        this.recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.resetSelectedPosition();
    }

    public void handleAddClick(View view) {
        Intent intent = new Intent(this, RideEditorActivity.class);

        startActivityForResult(intent, ADD_RIDE_REQUEST);
    }

    //TODO: implement this like delete listener?
    public void onRideClick(View view) {
        Intent intent = new Intent(this, RideEditorActivity.class);
        int position = recyclerView.getChildLayoutPosition(view);

        intent.putExtra("ride", rideList.get(position));
        intent.putExtra("position", position);
        startActivityForResult(intent, EDIT_RIDE_REQUEST);
    }

    @Override
    public void onDeleteClick(int position) {
        rideList.remove(position);
        mAdapter.notifyItemRemoved(position);
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
            mAdapter.notifyItemInserted(rideList.size()-1);
            if (rideList.size() == 1) {
                findViewById(R.id.emptyList).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == EDIT_RIDE_REQUEST && resultCode == RESULT_OK) {
            Ride ride = (Ride) resultIntent.getSerializableExtra("ride");
            int position = resultIntent.getIntExtra("position", -1);
            rideList.set(position, ride);
            mAdapter.notifyItemChanged(position);
        }
        updateDistanceTotal();
    }

    private void updateDistanceTotal() {
        double totalDistance = Ride.totalDistance(rideList);
        TextView tvDistance = findViewById(R.id.totalDistanceText);
        tvDistance.setText(getString(R.string.total_ride_distance, totalDistance));
    }

}
