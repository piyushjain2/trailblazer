package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.firestoredao.FirestoredaoMgr;
import trailblaze.issft06.android.com.trailblaze.model.Participant;


public class TrailDetailActivity extends AppCompatActivity {
    private String trailId;
    private TextView trailDetailName;
    private TextView trailDetailDescription;
    private Button mJoinTrailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);
        trailDetailName = (TextView) findViewById(R.id.trail_detail_name);
        trailDetailDescription = (TextView) findViewById(R.id.trail_result_description);

        Participant participant;
        participant = (Participant) App.user;

        Intent intent = getIntent();

        trailId = intent.getStringExtra("trailId");

        trailDetailName.setText(trailId);


        mJoinTrailButton = (Button) findViewById(R.id.join_trail);

        if (participant.getJoinedTrail().contains(App.trail) ) {
            mJoinTrailButton.setText("Joined Already");
            mJoinTrailButton.setEnabled(false);
        }
        mJoinTrailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore mdb = FirebaseFirestore.getInstance();
                CollectionReference mUsers = mdb.collection("users");
                FirestoredaoMgr daoMgr = new FirestoredaoMgr();
                mJoinTrailButton.setText("Joined Already");
                mJoinTrailButton.setEnabled(false);

                CollectionReference mJoinedTrails = mUsers.document(App.user.getFirebaseId()).collection("joinedTrails");
                //set creates new or will overwrite last
                mJoinedTrails.add(App.trail);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(TrailDetailActivity.this, ParticipantActivity.class);

        TrailDetailActivity.this.startActivity(myIntent);
    }
}
