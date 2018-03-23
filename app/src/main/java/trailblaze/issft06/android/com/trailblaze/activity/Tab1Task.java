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
import trailblaze.issft06.android.com.trailblaze.app.App;


public class Tab1Task extends Fragment implements OnMapReadyCallback {

    public static final String COLLECTION_KEY = "trail_station";
    public static final String INSTRUCTION_KEY = "instructions";
    public static final String TrailStationName_KEY = "stn_name";
    public static final String GPS_KEY = "GPS";

    public static final String GetTrailStationID = "TrailStationID";

    private String TrailStationID;
    private String TrailStationName;

    // update for map
    // Log.d("TAB1", documentSnapshot.getString(GPS_KEY));
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


        TextView stationName = rootView.findViewById(R.id.StationName);

        stationName.setText(App.trailStation.getName());

        TextView instructionText = rootView.findViewById(R.id.instructionText);
        instructionText.setText(App.trailStation.getInstruction());

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