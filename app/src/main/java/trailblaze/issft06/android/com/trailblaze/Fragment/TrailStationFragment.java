package trailblaze.issft06.android.com.trailblaze.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.R;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TrailStationFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;



    private static TextView  mTextView;
    private static ImageView mImageView;
    private  Button mUnjoinTrail;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrailStationFragment() {


    }



    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TrailStationFragment newInstance(int columnCount) {
        TrailStationFragment fragment = new TrailStationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trail_station_list, container, false);

        // Set the adapter
        if (view.findViewById(R.id.list) instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //TODO Get Trail by id replace

            FirebaseFirestore mdb = FirebaseFirestore.getInstance();
            CollectionReference mTrails = mdb.collection("trailStations");
            final ArrayList<TrailStation> trailStations = new ArrayList<TrailStation>();
            for(String id : App.trail.getTrailStations() ) {
                mTrails
                        .whereEqualTo("id", id)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());

                                        if (document != null && document.exists()) {
                                            TrailStation trailStation = document.toObject(TrailStation.class);

                                                trailStations.add(trailStation);
                                        }
                                    }

                                    recyclerView.setAdapter(new TrailStationRecyclerViewAdapter(trailStations, mListener));
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }


                            }
                        });
            }

            mUnjoinTrail = (Button) view.findViewById(R.id.unjoin_trail);

            mUnjoinTrail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUnjoinTrail.setEnabled(false);
                    mUnjoinTrail.setText("You do not join this Trail yet!");
                    FirebaseFirestore mdb = FirebaseFirestore.getInstance();
                    final CollectionReference mUsers = mdb.collection("users");
                    mUsers
                            .whereEqualTo("id", App.participant.getId())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());

                                            if (document != null && document.exists()) {
                                                final CollectionReference mTrails = mUsers.document(document.getId()).collection("joinedTrails");
                                                mTrails
                                                        .whereEqualTo("id", App.trail.getId())
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                for (DocumentSnapshot document : task.getResult()) {
                                                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                                                    if (document != null && document.exists()) {
                                                                        mTrails.document(document.getId()).delete();


                                                                    } else {
                                                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                                                    }
                                                                }
                                                            }
                                                        });
                                            }
                                        }


                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }


                                }
                            });

                }

            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TrailStation trailStation);
    }




}
