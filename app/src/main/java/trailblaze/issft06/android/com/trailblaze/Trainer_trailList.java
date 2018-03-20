package trailblaze.issft06.android.com.trailblaze;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import trailblaze.issft06.android.com.trailblaze.activity.LoginActivity;
import trailblaze.issft06.android.com.trailblaze.activity.ParticipantActivity;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.firestoredao.Trails_dao;
import trailblaze.issft06.android.com.trailblaze.fragment.TrailFragment;
import trailblaze.issft06.android.com.trailblaze.fragment.TrailRecyclerViewAdapter;
import trailblaze.issft06.android.com.trailblaze.model.Trail;

import static android.content.ContentValues.TAG;

public class Trainer_trailList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mauth;
    private ProgressDialog mProgressDialog;
    private int mColumnCount = 1;
    private TrailFragment.OnListFragmentInteractionListener mListener;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trail_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();


            final RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            final ArrayList<Trail> trails = new ArrayList<Trail>();
            FirebaseFirestore mdb = FirebaseFirestore.getInstance();
            CollectionReference mTrails = mdb.collection("trails");
            Trails_dao mTrailDao = new Trails_dao();
            for (String trailId : App.trainer.getCreatedTrails()) {
                mTrails
                        .whereEqualTo("userID", App.trainer.getId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());

                                        if (document != null && document.exists()) {
                                            Trail trail = document.toObject(Trail.class);
                                            trails.add(trail);
                                        }
                                    }

                                    recyclerView.setAdapter(new TrailRecyclerViewAdapter(trails, mListener));
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }


                            }
                        });

//                trails.add(mTrailDao.getTrailById(trailId));
            }
        }
        setContentView(R.layout.activity_trainer_trail_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mauth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton imageButton = navigationView.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Logging Out");
                mProgressDialog.show();
                mauth.signOut();
                LoginManager.getInstance().logOut();

                startActivity(new Intent(Trainer_trailList.this, LoginActivity.class));
            }
        });

        return view;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trainer_trail_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trainer) {

        } else if (id == R.id.nav_participant) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
