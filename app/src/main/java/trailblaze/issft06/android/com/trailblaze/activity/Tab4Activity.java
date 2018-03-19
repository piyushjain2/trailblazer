package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by nhatdx on 13/3/18.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.ContributeItem;


// need time to sort activityList or store contributions_id as milliseconds
public class Tab4Activity extends Fragment {

    public static final String COLLECTION_KEY = "contributions";
    public static final String ContributionsTSID_KEY = "trailStationId";
    public static final String UserID_KEY = "userId";

    public static final String GetTrailStationID = "trailStation001";

    private String TrailStationID;
    private FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();
    private ArrayList<ContributeItem> activityList = new ArrayList<>();
    private RecyclerView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getArguments() != null ) {
            TrailStationID = getArguments().getString(GetTrailStationID);
        }

        final View rootView = inflater.inflate(R.layout.tab4_activity, container, false);

        listView = rootView.findViewById(R.id.activityList);

        mDocRef.collection(COLLECTION_KEY).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                activityList.clear();

                for (DocumentSnapshot snapshot : documentSnapshots) {

//                    String getContributionsTSID = snapshot.getString(ContributionsTSID_KEY);
//
//                    if (getContributionsTSID != null) {

//                        Log.d("TAB4", getContributionsTSID);

                        if (snapshot.getString(ContributionsTSID_KEY).equals(App.trailStation.getId())) {

                            String str = snapshot.getString(UserID_KEY) + " has uploaded/submitted/posted a contributed item!";
                            activityList.add(snapshot.toObject(ContributeItem.class));
                        }

//                    }

                }

                ContributeAdapter adapter = new ContributeAdapter(activityList);
                adapter.notifyDataSetChanged();
                listView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

                listView.setAdapter(adapter);
            }
        });


        return rootView;
    }

}
