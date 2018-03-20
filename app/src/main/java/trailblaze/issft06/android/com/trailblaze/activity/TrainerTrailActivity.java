package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.User;
import trailblaze.issft06.android.com.trailblaze.activity.TrailGridAdapter;

import java.util.ArrayList;

import static trailblaze.issft06.android.com.trailblaze.app.App.trailStation;

public class TrainerTrailActivity extends AppCompatActivity {

    private static final String TAG = "Trail Activity";
    private MenuItem mRoleAction;
    private MenuItem mAddTrailAction;
    private User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail);
        thisUser = App.user;
//        getActionBar().setTitle("Trainer Trail List" + thisUser.getName());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference trailsRef = db.collection("trails");
        Query query = trailsRef.whereEqualTo("userID", thisUser.getId());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Trail> trails = new ArrayList<>();

                    for(DocumentSnapshot doc : task.getResult()){
                        Trail e = doc.toObject(Trail.class);
                        e.setId(doc.getId());
                        e.setName(doc.getString("trailName"));
                        trails.add(e);
                    }
                    Log.d("event list ", String.valueOf(trails));
                    //do something with list of pojos retrieved
                    GridView gv = (GridView) findViewById(R.id.gv_trail_list);
                    TrailGridAdapter adapter = new TrailGridAdapter(TrainerTrailActivity.this, trails);
                    gv.setAdapter(adapter);
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent myIntent = new Intent(TrainerTrailActivity.this, TrainerTrailStation.class);
                            setContentView(R.layout.activity_trail);
                            trailStation = trailStation;
                            TrainerTrailActivity.this.startActivity(myIntent);

                        }
                    });
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
            mAddTrailAction = menu.findItem(R.id.action_add_trail);
            menu.findItem(R.id.action_add_trail).setVisible(true);
            return super.onPrepareOptionsMenu(menu);

//        mRoleAction = menu.findItem(R.id.action_switch_role);
//        mAddTrailAction = menu.findItem(R.id.action_add_trail);
//        return super.onPrepareOptionsMenu(menu);
//
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_add_trail)
        {
            addTrail();
            addPost();

        }

//        else if (id == R.id.action_switch_role){
//            Intent intent = new Intent(this,UserRoleSelectionActivity.class);
//            intent.putExtra("user",thisUser);
//            startActivity(intent);
//            finish();
        return super.onOptionsItemSelected(item);
    }

    private void addPost() {
    }
//todo add the add trail view
    private void addTrail() {
        Intent intent = new Intent(this,AddTrailActivity.class);
        intent.putExtra("user",thisUser.getId());
        startActivity(intent);

    }
}
