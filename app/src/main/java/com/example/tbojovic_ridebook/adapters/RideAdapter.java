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

/**
 * This class is the adapter class to a {@link RecyclerView} containing {@link Ride} objects.
 * The purpose is to connect the Ride data with the RecyclerView and to create the
 * {@link ViewHolder} objects that will display the data. It converts a Ride object at a position
 * to the view in the list.
 */
public class RideAdapter extends RecyclerView.Adapter<RideAdapter.ViewHolder> {

    private ArrayList<Ride> rideList;
    private OnItemClickListener listener;

    // holds the index of the ride that's selected, and thus has a delete button beside it
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(int pos);
        void onItemDeleteClick(int pos);
    }

    // A listener must be provided to handle item clicks and deletes of item.
    // The adapter shouldn't handle manipulation of data, it should only handle
    // binding the views and data.
    public RideAdapter(ArrayList<Ride> data, OnItemClickListener listener) {
        this.rideList = data;
        this.listener = listener;
    }

    /**
     * This class provides a reference to the view in the the RecyclerView for each data item.
     * The purpose is to hold information about the views and to handle clicks on items.
     */
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener, View.OnClickListener {

        // views are package-private, only available to the ViewHolder & encompassing RideAdapter
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
                    listener.onItemDeleteClick(position);
                    selectedPosition = RecyclerView.NO_POSITION;
                }
            });
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onItemClick(position);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();

            // only one item can be selected at a time
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

            return true;
        }
    }

    @Override
    public RideAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ridesview_row, parent, false);
        return new ViewHolder(itemView);
    }

    // Bind the view with the data from the Ride object
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ride ride = rideList.get(position);
        holder.tvDate.setText(ride.getDate().toString());
        holder.tvTime.setText(ride.getTime().toString());
        holder.tvDistance.setText(String.valueOf(ride.getDistance()));

        // the delete button should only show on the item that has been selected with a long click
        if (selectedPosition == position) {
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    // deselect the selected item, this effectively removes the delete button
    public void resetSelectedPosition() {
        selectedPosition = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }
}
