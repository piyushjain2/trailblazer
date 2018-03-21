package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class AddTrailActivity extends AppCompatActivity implements View.OnClickListener {

    private App app;
    private User thisUser;
    private String TAG = "Add Trail Activity";
    private Trail thisTrail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trail);
        Button addTrailButton = findViewById(R.id.btn_add_trail);
        addTrailButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add_trail){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final EditText trailName = findViewById(R.id.trail_name);
            final EditText trailDate = findViewById(R.id.trail_date);
//            Map<String, Object> trail = new HashMap<>();
//            trail.put("userID", App.user.getId());
//            trail.put("trailName", String.valueOf(trailName.getText()));
//            trail.put("trailDate", String.valueOf(trailDate.getText()));
//            trail.put("id", String.valueOf(trailDate.getText()) +"-"+ String.valueOf(trailName.getText()) );
//            trail.put("timestamp", FieldValue.serverTimestamp());

            Trail trail = new Trail();
            trail.setUserId(App.user.getId());
            trail.setName(trailName.getText().toString());
//            trail.setDate( String.valueOf(trailDate.getText()).toString());
            trail.setId( String.valueOf(trailDate.getText()) +"-"+ String.valueOf(trailName.getText()) );
//            trail.setTimestamp( FieldValue.().);

            final ProgressBar pb = findViewById(R.id.pb_add_trail);
            pb.setVisibility(View.VISIBLE);
            db.collection("trails")
                    .add(trail)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            pb.setVisibility(View.GONE);
                            thisTrail = new Trail();
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMYY");
                            try {
                                thisTrail.setDate(sdf.parse(String.valueOf(trailDate.getText())));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            thisTrail.setName(String.valueOf(trailName.getText()));
                            thisTrail.setId(thisTrail.getDate()+"-"+thisTrail.getName());
                            App.trail.setId(String.valueOf(trailDate.getText()) +"-"+ String.valueOf(trailName.getText()));
                            thisTrail.setUserId(App.user.getId());
                            Intent intent = new Intent(getApplicationContext(),TrainerTrailActivity.class);
                            intent.putExtras(intent);
//            intent.putExtra("user", (Parcelable) thisUser);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            trailName.setError(e.getMessage());
                        }
                    });


        }else{
            Intent intent = new Intent(this,TrainerTrailActivity.class);
            intent.putExtras(intent);
//            intent.putExtra("user", (Parcelable) thisUser);
            startActivity(intent);
        }

    }
}
