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
        void onItemClick(View itemView, int pos);
        void onItemDeleteClick(View itemView, int pos);
    }

    // Initial data is passed in from the constructor
    public RideAdapter(ArrayList<Ride> data, OnItemClickListener listener) {
        this.rideList = data;
        this.listener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView tvDate, tvTime, tvDistance;
        private ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.RowDate);
            tvTime = itemView.findViewById(R.id.RowTime);
            tvDistance = itemView.findViewById(R.id.RowDistance);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemDeleteClick(view, position);
                        selectedPosition = RecyclerView.NO_POSITION;
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(view, position);
                    }
                }
            });
            itemView.setOnLongClickListener(this);
        }

        public void bind(Ride ride) {
            //TODO: clean this shit up
            tvDate.setText(ride.getDate().toString());
            tvTime.setText(ride.getTime().toString());
            tvDistance.setText(String.valueOf(ride.getDistance()));
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
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                deleteButton.setVisibility(View.VISIBLE);
                if (selectedPosition != getAdapterPosition()) {
                    notifyItemChanged(selectedPosition);
                    selectedPosition = getAdapterPosition();
                } else if (selectedPosition == getAdapterPosition()) {
                    deleteButton.setVisibility(View.INVISIBLE);
                    selectedPosition = RecyclerView.NO_POSITION;
                }
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
        holder.bind(rideList.get(position));
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
