package trailblaze.issft06.android.com.trailblaze.Activity;

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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import trailblaze.issft06.android.com.trailblaze.R;


public class Tab3Discuss extends Fragment {

    private FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();
    private List<String> postList = new ArrayList<>();
    ListView listView;
    private String TrailStationID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getArguments() != null ) {
            TrailStationID = getArguments().getString("TrailStationID");
        }

        View rootView = inflater.inflate(R.layout.tab3_discuss, container, false);

        listView = rootView.findViewById(R.id.list_of_messages);

        // Showing messages in real time
        mDocRef.collection("post").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                postList.clear();

                for (DocumentSnapshot snapshot : documentSnapshots) {

                    String getTrailStationID = snapshot.getString("TrailStationID");

                    if (getTrailStationID != null) {

                        if (getTrailStationID.equals(TrailStationID)) {

                            String str = snapshot.getString("UserID") + ": " + snapshot.getString("Msg");
                            postList.add(str);

                        }
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, postList);
                adapter.notifyDataSetChanged();

                listView.setAdapter(adapter);
            }
        });

        return rootView;
    }

}

