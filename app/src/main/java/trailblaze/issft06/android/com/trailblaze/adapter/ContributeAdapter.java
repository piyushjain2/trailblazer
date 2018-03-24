package trailblaze.issft06.android.com.trailblaze.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

        holder.mIdView.setText(holder.mItem.getContentType());

        if (holder.mItem.getContentType().equals("application/pdf")) {

            holder.mImageView.setImageResource(R.drawable.pdf_icon);
        } else if (holder.mItem.getContentType().equals("image/jpeg")){

            Glide.with(context)
                    .load(holder.mItem.getUrl())
                    .into(holder.mImageView);
        } else if (holder.mItem.getContentType().equals("video/mp4")) {
            holder.mImageView.setImageResource(R.drawable.video_file);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final CharSequence[] items = { "View" };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Action:");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.getUrl()));
                        context.startActivity(intent);

//                        new AlertDialog.Builder(context)
//                                .setTitle("Success")
//                                .setMessage("Item Removed")
//                                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                })
//                                .show();

                    }

                });

                AlertDialog alert = builder.create();

                alert.show();
                //do your stuff here

                return true;
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
