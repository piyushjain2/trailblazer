package trailblaze.issft06.android.com.trailblaze.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import trailblaze.issft06.android.com.trailblaze.Fragment.TrailFragment.OnListFragmentInteractionListener;
import trailblaze.issft06.android.com.trailblaze.Model.Trail;
import trailblaze.issft06.android.com.trailblaze.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trail} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TrailRecyclerViewAdapter extends RecyclerView.Adapter<TrailRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Trail> mTrails;
    private final OnListFragmentInteractionListener mListener;

    public TrailRecyclerViewAdapter(ArrayList<Trail> items, OnListFragmentInteractionListener listener) {

        this.mTrails = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mTrails.get(position);
        holder.mIdView.setText(mTrails.get(position).getName());
        holder.mContentView.setText(mTrails.get(position).getUserId());

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
        public final TextView mContentView;
        public Trail mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.trail_name);
            mContentView = (TextView) view.findViewById(R.id.trail_participant);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
