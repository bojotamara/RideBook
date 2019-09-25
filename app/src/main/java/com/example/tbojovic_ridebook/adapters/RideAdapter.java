package com.example.tbojovic_ridebook.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tbojovic_ridebook.R;
import com.example.tbojovic_ridebook.models.Ride;

import java.util.ArrayList;

// Adapter creates view holders as needed, and binds view holders to their data
// Convert an object at a position into a list row item
public class RideAdapter extends RecyclerView.Adapter<RideAdapter.ViewHolder> {

    private ArrayList<Ride> rideList;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(int pos);
        void onItemDeleteClick(int pos);
    }

    // Initial data is passed in from the constructor
    public RideAdapter(ArrayList<Ride> data, OnItemClickListener listener) {
        this.rideList = data;
        this.listener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener, View.OnClickListener {

        TextView tvDate, tvTime, tvDistance;
        ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.RowDate);
            tvTime = itemView.findViewById(R.id.RowTime);
            tvDistance = itemView.findViewById(R.id.RowDistance);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemDeleteClick(position);
                        selectedPosition = RecyclerView.NO_POSITION;
                    }
                }
            });
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (selectedPosition == position) {
                    selectedPosition = RecyclerView.NO_POSITION;
                } else {
                    int oldSelected = selectedPosition;
                    selectedPosition = position;
                    if (oldSelected != RecyclerView.NO_POSITION) {
                        notifyItemChanged(oldSelected);
                    }
                }
                notifyItemChanged(position);
            }
            return true;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RideAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ridesview_row, parent, false);
        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Ride ride = rideList.get(position);
        holder.tvDate.setText(ride.getDate().toString());
        holder.tvTime.setText(ride.getTime().toString());
        holder.tvDistance.setText(String.valueOf(ride.getDistance()));

        if (selectedPosition == position) {
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public void resetSelectedPosition() {
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }
}
