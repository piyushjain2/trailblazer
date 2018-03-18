package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import trailblaze.issft06.android.com.trailblaze.fragment.TrailStationFragment.OnListFragmentInteractionListener;
import trailblaze.issft06.android.com.trailblaze.model.ContributeItem;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.R;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trail} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ContributeAdapter extends RecyclerView.Adapter<ContributeAdapter.ViewHolder> {

    private final ArrayList<ContributeItem> mContributeItems;
    private Context context;

    public ContributeAdapter(ArrayList<ContributeItem> items) {

        this.mContributeItems = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contribute_item, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mContributeItems.get(position);
        holder.mIdView.setText(mContributeItems.get(position).getId());
        Glide.with(context)
                .load(holder.mItem.getUrl())
                .into(holder.mImageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mContributeItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mImageView;
        public ContributeItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.photoImageView);
            mIdView = (TextView) view.findViewById(R.id.messageTextView);
        }

        @Override
        public String toString() {
            return super.toString() +  "'";
        }
    }
}
