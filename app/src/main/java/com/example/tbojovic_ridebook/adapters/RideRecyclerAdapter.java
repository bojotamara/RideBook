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
public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.ViewHolder> {

    //TODO: move this interface to a different file and add the other listeners
    public interface OnDeleteClickListener {
        void onDeleteClick(int pos);
    }

    private ArrayList<Ride> rideList;
    private OnDeleteClickListener deleteClickListener;
    private int selectedPosition = -1; //maybe static?

    // Initial data is passed in from the constructor
    public RideRecyclerAdapter(ArrayList<Ride> data, OnDeleteClickListener listener) {
        this.rideList = data;
        this.deleteClickListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView tvDate, tvTime, tvDistance;
        private ImageButton deleteButton;
        public ViewHolder(View v) {
            super(v);
            tvDate = v.findViewById(R.id.RowDate);
            tvTime = v.findViewById(R.id.RowTime);
            tvDistance = v.findViewById(R.id.RowDistance);
            deleteButton = v.findViewById(R.id.deleteButton);
            v.setOnLongClickListener(this);
        }

        public void bind(Ride ride) {
            //TODO: clean this shit up
            tvDate.setText(ride.getDate().toString());
            tvTime.setText(ride.getTime().toString());
            tvDistance.setText(String.valueOf(ride.getDistance()));
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPosition = -1;
                    deleteClickListener.onDeleteClick(getAdapterPosition());
                }
            });
            if (selectedPosition == -1) {
                deleteButton.setVisibility(View.INVISIBLE);
            } else if (selectedPosition == getAdapterPosition()) {
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                deleteButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            deleteButton.setVisibility(View.VISIBLE);
            if (selectedPosition != getAdapterPosition()) {
                notifyItemChanged(selectedPosition);
                selectedPosition = getAdapterPosition();
            } else if (selectedPosition == getAdapterPosition()) {
                deleteButton.setVisibility(View.INVISIBLE);
                selectedPosition = -1;
            }
            return true;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RideRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.bind(rideList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public void resetSelectedPosition() {
        selectedPosition = -1;
        notifyDataSetChanged();
    }
}
