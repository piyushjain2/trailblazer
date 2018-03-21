package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.firestoredao.FirestoredaoMgr;
import trailblaze.issft06.android.com.trailblaze.firestoredao.Trails_dao;

import static android.content.ContentValues.TAG;

/**
 * Created by manhpd on 2/3/18.
 */

public class SearchTrailActivity extends AppCompatActivity {
    private boolean mSearchViewAdded = false;
    private WindowManager mWindowManager;
    private Toolbar mToolbar;
    private MenuItem searchItem;
    private boolean searchActive = false;
    private FloatingActionButton fab;

    private CardView mResult;
    private EditText mSearchText;
    private Button mSearchTrailButton;
    private TextView mTrailName;
    private TextView mTrailDescription;
    private TextView mError;



    FirebaseFirestore mdb =FirebaseFirestore.getInstance() ;
    CollectionReference mtrails =mdb.collection("trails");
    FirestoredaoMgr daoMgr =  new FirestoredaoMgr() ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trail);



        mSearchText = (EditText) findViewById(R.id.search_trail);
        mSearchTrailButton = (Button) findViewById(R.id.search_trail_button);
        mTrailName = (TextView) findViewById(R.id.trail_result_name);
        mTrailDescription = (TextView) findViewById(R.id.trail_result_description);
        mResult = (CardView) findViewById(R.id.search_result);
        mResult.setVisibility(View.INVISIBLE);
        mError= (TextView) findViewById(R.id.search_trail_error_msg);
        mError.setVisibility(View.INVISIBLE);


        mSearchTrailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String trailId = mSearchText.getText().toString();

                mtrails
                        .whereEqualTo("id", trailId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());

                                        if(document != null && document.exists()) {
                                            Trail trail = document.toObject(Trail.class);
                                            mTrailName.setText(trail.getName());
                                            mResult.setVisibility(View.VISIBLE);
                                            App.trail = trail;
                                        }
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });

        mResult.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent myIntent = new Intent(SearchTrailActivity.this, TrailDetailActivity.class);

                String trailId = mSearchText.getText().toString();


                myIntent.putExtra("trailId", trailId); //Optional parameters
                SearchTrailActivity.this.startActivity(myIntent);

            }
        }) ;
    }




}
