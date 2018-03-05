package com.example.craft1k.seft06.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.craft1k.seft06.Fragment.TrailStationFragment.OnListFragmentInteractionListener;
import com.example.craft1k.seft06.Model.Trail;
import com.example.craft1k.seft06.Model.TrailStation;
import com.example.craft1k.seft06.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trail} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TrailStationRecyclerViewAdapter extends RecyclerView.Adapter<TrailStationRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<TrailStation> mTrails;
    private final OnListFragmentInteractionListener mListener;

    public TrailStationRecyclerViewAdapter(ArrayList<TrailStation> items, OnListFragmentInteractionListener listener) {

        this.mTrails = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trail_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mTrails.get(position);
        holder.mIdView.setText(mTrails.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public TrailStation mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.trail_station_name);
        }

        @Override
        public String toString() {
            return super.toString() +  "'";
        }
    }
}
