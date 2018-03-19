package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by nhatdx on 13/3/18.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
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


// need time to sort activityList or store contributions_id as milliseconds
public class Tab4Activity extends Fragment {

    public static final String COLLECTION_KEY = "contributions";
    public static final String ContributionsTSID_KEY = "trailStationId";
    public static final String UserID_KEY = "userId";

    public static final String GetTrailStationID = "TrailStationID";

    private String TrailStationID;
    private FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();
    private List<String> activityList = new ArrayList<>();
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getArguments() != null ) {
            TrailStationID = getArguments().getString(GetTrailStationID);
        }

        View rootView = inflater.inflate(R.layout.tab4_activity, container, false);

        listView = rootView.findViewById(R.id.activityList);

        mDocRef.collection(COLLECTION_KEY).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                activityList.clear();

                for (DocumentSnapshot snapshot : documentSnapshots) {

                    String getContributionsTSID = snapshot.getString(ContributionsTSID_KEY);

                    if (getContributionsTSID != null) {

//                        Log.d("TAB4", getContributionsTSID);

                        if (getContributionsTSID.equals(TrailStationID)) {

                            String str = snapshot.getString(UserID_KEY) + " has uploaded/submitted/posted a contributed item!";
                            activityList.add(str);
                        }

                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, activityList);
                adapter.notifyDataSetChanged();

                listView.setAdapter(adapter);
            }
        });


        return rootView;
    }

}
