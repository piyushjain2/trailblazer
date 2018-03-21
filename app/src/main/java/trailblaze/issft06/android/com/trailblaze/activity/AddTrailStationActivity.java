package trailblaze.issft06.android.com.trailblaze.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.model.User;

import java.util.HashMap;
import java.util.Map;

public class AddTrailStationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Add Trail Station";
    private User thisUser;
    private Trail thisTrail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trail_station);
        MapView mapView = (MapView) this.findViewById(R.id.mapView);


        Button addTrailStationButton = findViewById(R.id.btn_add_trail_station);
//        thisUser = getIntent().getExtras().getParcelable("user");
//        thisTrail = getIntent().getExtras().getParcelable("trail");
        addTrailStationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_trail_station){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final EditText sequence = findViewById(R.id.trail_station_sequence_no);
            final EditText loc = findViewById(R.id.trail_station_GPSLocation);
            final EditText name = findViewById(R.id.trail_station_name);
            final EditText instructions = findViewById(R.id.instructions);
//            Map<String, Object> trail = new HashMap<>();
//            trail.put("seqNo", Integer.parseInt(String.valueOf(sequence.getText())));
//            trail.put("GPSLocation", String.valueOf(loc.getText() ));
//            trail.put("trailStationName", String.valueOf(name.getText()));
//            trail.put("trailID", App.trail.getId());
//            trail.put("id", App.trail.getId()+String.valueOf(name.getText()) );
//            trail.put("instructions", String.valueOf(instructions.getText()));


            TrailStation trailStation = new TrailStation();
            trailStation.setGPS(String.valueOf(loc.getText() ));
            trailStation.setSequence(Integer.parseInt(String.valueOf(sequence.getText())));
            trailStation.setTrailId(App.trail.getId());
            trailStation.setId(App.trail.getId()+String.valueOf(name.getText()));
            trailStation.setInstruction(String.valueOf(instructions.getText()));
            trailStation.setName(String.valueOf(name.getText()));
            final ProgressBar pb = findViewById(R.id.pb_add_trail_station);
            pb.setVisibility(View.VISIBLE);
            db.collection("trailStations")
                    .add(trailStation)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            pb.setVisibility(View.GONE);
                            thisTrail = new Trail();
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
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
    }
}
