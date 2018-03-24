package trailblaze.issft06.android.com.trailblaze.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Participant;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TrailFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Trail> trails;

    public ArrayList<Trail> getTrails() {
        return trails;
    }

    public void setTrails(ArrayList<Trail> trails) {
        this.trails = trails;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrailFragment() {
        trails = new ArrayList<>();

    }

    public static TrailFragment newInstance(int columnCount) {
        TrailFragment fragment = new TrailFragment();
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
        View view = inflater.inflate(R.layout.fragment_trail_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            final RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

//            final ArrayList<Trail> trails = new ArrayList<Trail>();
//            Trail trail = new Trail();
//            trail.setId("2017");
//            trail.setName("Sample Trail");
//            trail.setUserId("001");
//            trails.add(trail);
//            FirebaseFirestore mdb = FirebaseFirestore.getInstance();
//            CollectionReference mTrails = mdb.collection("trails");

//            for(String trailId : App.participant.getJoinedTrail() ) {
//                mTrails
//                        .whereEqualTo("id", trailId)
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (DocumentSnapshot document : task.getResult()) {
//                                        Log.d(TAG, document.getId() + " => " + document.getData());
//
//                                        if (document != null && document.exists()) {
//                                            Trail trail = document.toObject(Trail.class);
//                                            trails.add(trail);
//                                        }
//                                    }
//
//                                    recyclerView.setAdapter(new TrailRecyclerViewAdapter(trails, mListener));
//                                } else {
//                                    Log.d(TAG, "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
//        }
            recyclerView.setAdapter(new TrailRecyclerViewAdapter(trails,mListener));

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
        void onListFragmentInteraction(Trail trail);
    }



}
