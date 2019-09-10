package com.example.tbojovic_ridebook;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter creates view holders as needed, and binds view holders to their data
public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.ViewHolder> {
    private List<Ride> mRides;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvDate;
        public TextView tvTime;
        public TextView tvDistance;
        public ViewHolder(View v) {
            super(v);
            tvDate = v.findViewById(R.id.RowDate);
            tvTime = v.findViewById(R.id.RowTime);
            tvDistance = v.findViewById(R.id.RowDistance);
        }
    }

    // Initial data is passed in from the constructor
    public RideRecyclerAdapter(List<Ride> data) {
        this.mRides = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RideRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ridesview_row, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Ride ride = mRides.get(position);
        holder.tvDate.setText(ride.getDate().toString());
        holder.tvTime.setText(ride.getTime().toString());
        holder.tvDistance.setText(String.valueOf(ride.getDistance()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mRides.size();
    }
}
