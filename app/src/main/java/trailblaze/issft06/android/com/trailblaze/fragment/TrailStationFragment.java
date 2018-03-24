package trailblaze.issft06.android.com.trailblaze.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Participant;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.model.Trainer;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TrailStationFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private static TextView  mTextView;
    private static ImageView mImageView;

    private static TextView mDescription;
    private static LinearLayout mLinearLayout;
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
        if(App.user.getClass().equals(Trainer.class)) {
            setHasOptionsMenu(true);
        }
        View view = inflater.inflate(R.layout.fragment_trail_station_list, container, false);
        mLinearLayout = view.findViewById(R.id.linear_layout);
        // Set the adapter
        if (mLinearLayout.findViewById(R.id.list) instanceof RecyclerView) {
            Context context = mLinearLayout.getContext();
            final RecyclerView recyclerView = (RecyclerView) mLinearLayout.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //Get joint Trail

            FirebaseFirestore mdb = FirebaseFirestore.getInstance();

            recyclerView.setAdapter(new TrailStationRecyclerViewAdapter(App.trail.getTrailStations(), mListener));


            mUnjoinTrail = (Button) mLinearLayout.findViewById(R.id.unjoin_trail);
            mTextView = (TextView) mLinearLayout.findViewById(R.id.trail_name);
            mDescription = (TextView) mLinearLayout.findViewById(R.id.trail_description);

            mTextView.setText(App.trail.getName());
            mDescription.setText(App.trail.getDescription());


            if (App.user.getClass().equals(Participant.class)) {
                mUnjoinTrail.setText("Unjoin This Trail!");
                mUnjoinTrail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mUnjoinTrail.setEnabled(false);
                        mUnjoinTrail.setText("You do not join this Trail yet!");
                        FirebaseFirestore mdb = FirebaseFirestore.getInstance();
                        final CollectionReference mUsers = mdb.collection("users");
                        mUsers
                                .whereEqualTo("id", App.user.getId())
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
            } else if (App.user.getClass().equals(Trainer.class)) {
                mUnjoinTrail.setVisibility(View.GONE);
            }

        }



        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater){
        // Inflate the menu; this adds items to the action bar if it is present.
        if(App.user.getClass().equals(Trainer.class)) {
            inflater.inflate(R.menu.main, menu);
//                menu.add(R.id.action_del_trail);
//                menu.getItem(R.id.action_del_trail).setVisible(true);
            }
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
