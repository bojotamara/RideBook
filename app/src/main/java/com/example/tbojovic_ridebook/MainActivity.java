package com.example.tbojovic_ridebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerView = findViewById(R.id.ridesRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        this.recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        List<Ride> mylist = new ArrayList<Ride>();
        Ride ride1 = new Ride(LocalDate.now(), LocalTime.now(), 10.0, 67,2);
        Ride ride2 = new Ride(LocalDate.now(), LocalTime.now(), 30.0, 76, 3);
        mylist.add(ride1);
        mylist.add(ride2);

        this.mAdapter = new RideRecyclerAdapter(mylist);
        this.recyclerView.setAdapter(mAdapter);
    }

    public void handleAddClick(View view) {
        Intent intent = new Intent(this, RideEditorActivity.class);
        //TODO: maybe add an intent extra to distinguish between add/edit?
        startActivity(intent);
    }
}
