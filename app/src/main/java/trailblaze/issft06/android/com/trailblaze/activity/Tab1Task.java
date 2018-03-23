package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by nhatdx on 13/3/18.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import trailblaze.issft06.android.com.trailblaze.R;


public class Tab1Task extends Fragment implements OnMapReadyCallback {

    public static final String COLLECTION_KEY = "trailStations";
    public static final String INSTRUCTION_KEY = "instruction";
    public static final String TrailStationName_KEY = "name";
    public static final String GPSLat_KEY = "gpslat";
    public static final String GPSLng_KEY = "gpslng";

    public static final String GetTrailStationID = "TrailStationID";

    private String TrailStationID;
    private String TrailStationName;

    // update for map
    private double TrailStnLat = 1.3051883;
    private double TrailStnLng = 103.7727994;

    private FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getArguments() != null ) {
            TrailStationID = getArguments().getString(GetTrailStationID);
        }

        final View rootView = inflater.inflate(R.layout.tab1_task, container, false);


        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView2);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();

            ft.replace(R.id.mapView2, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);


        mDocRef.document(COLLECTION_KEY + "/" + TrailStationID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    TextView stationName = rootView.findViewById(R.id.StationName);
                    TrailStationName = documentSnapshot.getString(TrailStationName_KEY);
                    stationName.setText(TrailStationName);

                    TextView instructionText = rootView.findViewById(R.id.instructionText);
                    instructionText.setText(documentSnapshot.getString(INSTRUCTION_KEY));

                    if (documentSnapshot.getString(GPSLat_KEY) != null) {
                        TrailStnLat = Double.parseDouble(documentSnapshot.getString(GPSLat_KEY));
                    }

                    if (documentSnapshot.getString(GPSLng_KEY) != null) {
                        TrailStnLng = Double.parseDouble(documentSnapshot.getString(GPSLng_KEY));
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        LatLng pp = new LatLng(TrailStnLat, TrailStnLng);
        MarkerOptions option = new MarkerOptions();
        option.position(pp).title(TrailStationName);

        map.addMarker(option);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pp, 16.0f));

    }
}
