package trailblaze.issft06.android.com.trailblaze.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.fragment.TrailStationFragment.OnListFragmentInteractionListener;
import trailblaze.issft06.android.com.trailblaze.model.ContributeItem;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trail} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ContributeAdapter extends RecyclerView.Adapter<ContributeAdapter.ViewHolder> {

    private final ArrayList<ContributeItem> mContributeItems;
    private Context context;
    FirebaseFirestore mFirebaseFirestore ;
    CollectionReference mCollectionRef;

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
        mFirebaseFirestore =  FirebaseFirestore.getInstance();
        mCollectionRef = mFirebaseFirestore.collection("contributions");

        holder.mItem = mContributeItems.get(position);
        holder.mDescriptionView.setText("Description: " + holder.mItem.getDesc());
        holder.mUserView.setText("User: " + holder.mItem.getUserName());
        holder.mPostedTime.setText("Posted at: " + holder.mItem.getcTime());
        holder.mfileType.setText("File Type: " + holder.mItem.getContentType());

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
                final CharSequence[] items ;

                if (App.user.getId().equals(holder.mItem.getUserId())) {
                    items = new CharSequence[]{"View", "Delete"};

                } else {
                    items = new CharSequence[]{"View"};
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);



                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item] == "View") {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.getUrl()));
                            context.startActivity(intent);
                        } else if (items[item] == "Delete") {


                            mCollectionRef
                                    .whereEqualTo("id", holder.mItem.getId())
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                            for (DocumentSnapshot document : documentSnapshots.getDocuments()) {
                                                if (document != null) {
                                                    document.getReference().delete();
                                                } else {
                                                    Log.w(TAG, "No item here");
                                                }
                                            }

                                        }
                                    });
                            new AlertDialog.Builder(context)
                                .setTitle("Success")
                                .setMessage("Item Removed")
                                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                        }

//

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
        public final TextView mfileType;
        public final TextView mDescriptionView;
        public final TextView mUserView;
        public final ImageView mImageView;
        public final TextView mPostedTime;


        public ContributeItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.photoImageView);
            mfileType = (TextView) view.findViewById(R.id.fileType);
            mDescriptionView = (TextView) view.findViewById(R.id.descriptionText);
            mUserView = (TextView) view.findViewById(R.id.userPost);
            mPostedTime = (TextView) view.findViewById(R.id.postedTime);



        }

        @Override
        public String toString() {
            return super.toString() +  "'";
        }
    }

}
