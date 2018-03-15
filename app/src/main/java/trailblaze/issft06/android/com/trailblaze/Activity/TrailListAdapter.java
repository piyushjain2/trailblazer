/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package trailblaze.issft06.android.com.trailblaze.Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import trailblaze.issft06.android.com.trailblaze.Model.Trail;
import trailblaze.issft06.android.com.trailblaze.R;


import java.util.ArrayList;

/**
 * {@link TrailListAdapter} exposes a list of trail to a
 * {@link RecyclerView}
 */
public class TrailListAdapter extends RecyclerView.Adapter<TrailListAdapter.TrailListAdapterViewHolder> {

    private String[] mTrailData;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final TrailListAdapterOnClickHandler mClickHandler;



    /**
     * The interface that receives onClick messages.
     */
    public interface TrailListAdapterOnClickHandler {
        void onClick(String trailId);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public TrailListAdapter(TrailListAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class TrailListAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final TextView mTrailDataView;

        public TrailListAdapterViewHolder(View view) {
            super(view);

            mTrailDataView = (TextView) view.findViewById(R.id.trail_participant);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String trailList = mTrailData[adapterPosition];
            mClickHandler.onClick(trailList);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public TrailListAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trails_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailListAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param trailListAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(TrailListAdapterViewHolder trailListAdapterViewHolder, int position) {
        String trailsList = mTrailData[position];
        trailListAdapterViewHolder.mTrailDataView.setText(trailsList);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mTrailData) return 0;
        return mTrailData.length;
    }

    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param joinedTrail The trail List to be displayed.
     */
    public void setTrailData(ArrayList<Trail> joinedTrail) {
        mTrailData = new String[joinedTrail.size()];
        for (int i =0; i < joinedTrail.size(); i++){
            mTrailData[i] = joinedTrail.get(i).getName();
        }
        notifyDataSetChanged();
    }
}