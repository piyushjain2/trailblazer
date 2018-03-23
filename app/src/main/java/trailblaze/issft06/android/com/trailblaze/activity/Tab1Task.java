package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by nhatdx on 13/3/18.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;


public class Tab1Task extends Fragment {

    public static final String COLLECTION_KEY = "trailStations";
    public static final String INSTRUCTION_KEY = "instructions";
    public static final String TrailStationName_KEY = "name";
    public static final String GPS_KEY = "GPS";

    public static final String GetTrailStationID = "TrailStationID";

    private String trailStationID;
    private FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        trailStationID = App.trailStation.getId();
//
        final View rootView = inflater.inflate(R.layout.tab1_task, container, false);

//        mDocRef.document(COLLECTION_KEY + "/" + trailStationID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                if (documentSnapshot.exists()) {
//
//                    // need to add Google Map
//                    // Log.d("TAB1", documentSnapshot.getString(GPS_KEY));
//
//                    TextView stationName = rootView.findViewById(R.id.StationName);
//                    stationName.setText(documentSnapshot.getString(TrailStationName_KEY));
//
//                    TextView instructionText = rootView.findViewById(R.id.instructionText);
//                    instructionText.setText(documentSnapshot.getString(INSTRUCTION_KEY));
//                }
//            }
//        });


        TextView stationName = rootView.findViewById(R.id.StationName);
        stationName.setText(App.trailStation.getName());

        TextView instructionText = rootView.findViewById(R.id.instructionText);
        instructionText.setText(App.trailStation.getInstruction());

        return rootView;
    }

}
