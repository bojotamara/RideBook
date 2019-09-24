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
import com.example.tbojovic_ridebook.adapters.RideRecyclerAdapter;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements RideRecyclerAdapter.OnDeleteClickListener {
    private RecyclerView recyclerView;
    private RideRecyclerAdapter recyclerAdapter;
    private ArrayList<Ride> rideList;
    final int ADD_RIDE_REQUEST = 1;
    final int EDIT_RIDE_REQUEST = 2;

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

        recyclerAdapter = new RideRecyclerAdapter(rideList, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerAdapter.resetSelectedPosition();
    }

    public void onAddClick(View view) {
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
