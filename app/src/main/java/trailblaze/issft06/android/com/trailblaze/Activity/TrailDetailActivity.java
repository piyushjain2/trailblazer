package trailblaze.issft06.android.com.trailblaze.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import trailblaze.issft06.android.com.trailblaze.App.App;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.firestoredao.FirestoredaoMgr;


public class TrailDetailActivity extends AppCompatActivity {
    private String trailId;
    private TextView trailDetailName;
    private TextView trailDetailDescription;
    private FloatingActionButton mJoinTrailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);
        trailDetailName = (TextView) findViewById(R.id.trail_detail_name);
        trailDetailDescription = (TextView) findViewById(R.id.trail_result_description);



        Intent intent = getIntent();

        trailId = intent.getStringExtra("trailId");

        trailDetailName.setText(trailId);

        mJoinTrailButton = (FloatingActionButton) findViewById(R.id.join_trail);
        mJoinTrailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> trail = new HashMap<>();
                trail.put("id", trailId);

                FirebaseFirestore mdb = FirebaseFirestore.getInstance();
                CollectionReference mUsers = mdb.collection("users");
                FirestoredaoMgr daoMgr = new FirestoredaoMgr();

                CollectionReference mJoinedTrails = mUsers.document(App.participant.getFirebaseId()).collection("joinedTrails");
                //set creates new or will overwrite last
                mJoinedTrails.add(trail);

            }
        });

    }
}
