package trailblaze.issft06.android.com.trailblaze.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.model.User;
public class AddTrailStationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TAG = "Add Trail Station";
    private User thisUser;
    private Trail thisTrail;
    private final int PLACE_PICKER_REQUEST = 1;
    TrailStation trailStation = new TrailStation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "##### View Loaded");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trail_station);
            //Select Location Button
        Button selectLoc = findViewById(R.id.select_loc);
            selectLoc.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        try {
                             startActivityForResult(builder.build( AddTrailStationActivity.this), PLACE_PICKER_REQUEST);

                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                         } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                 }
      });

        Button addTrailStationButton = findViewById(R.id.btn_add_trail_station);
        if(trailStation.getGPS() != null) {
        addTrailStationButton.setOnClickListener(this);
        }
        else{
            Toast.makeText(AddTrailStationActivity.this, "Select Location!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void addTrailStation(View view) {

        Log.d(TAG, "##### clicked");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final EditText sequence = findViewById(R.id.trail_station_sequence_no);
        final EditText loc = findViewById(R.id.trail_station_GPSLocation);
        final EditText name = findViewById(R.id.trail_station_name);
        final EditText instructions = findViewById(R.id.instructions);

        //setting location to trailStation object in onActivityResult method
        trailStation.setSequence(Integer.parseInt(String.valueOf(sequence.getText())));
        trailStation.setTrailId(App.trail.getId());
        trailStation.setId(App.trail.getId()+String.valueOf(name.getText()));
        trailStation.setInstruction(String.valueOf(instructions.getText()));
        trailStation.setName(String.valueOf(name.getText()));

        final ProgressBar pb = findViewById(R.id.pb_add_trail_station);
        pb.setVisibility(View.VISIBLE);
        db.collection("trailStations")
                .document(trailStation.getId())
                .set(trailStation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pb.setVisibility(View.GONE);
                        thisTrail = new Trail();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + trailStation.getId());
                        Toast.makeText(AddTrailStationActivity.this, "Trail Station Successfully Added", Toast.LENGTH_SHORT).show();
                        sequence.setText("");
                        loc.setText("");
                        name.setText("");
                        instructions.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(AddTrailStationActivity.this, "Error adding document: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                StringBuilder stBuilder = new StringBuilder();

                trailStation.setGPS(String.valueOf(place.getLatLng()));

                EditText editText = (EditText)findViewById(R.id.trail_station_GPSLocation);
                editText.setText("Selected:" + String.valueOf(place.getLatLng()));  //set the edit text field
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);

                trailStation.setGPSLat(latitude);
                trailStation.setGPSLng(longitude);

                String toastMsg = String.format("Place: %s", placename+"/"+latitude+"/"+longitude);
                Toast.makeText(AddTrailStationActivity.this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
