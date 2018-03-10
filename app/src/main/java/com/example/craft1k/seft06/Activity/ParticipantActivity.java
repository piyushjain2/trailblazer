package com.example.craft1k.seft06.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.craft1k.seft06.App.App;
import com.example.craft1k.seft06.Model.Participant;
import com.example.craft1k.seft06.R;
import com.example.craft1k.seft06.Model.Trail;
import com.example.craft1k.seft06.Fragment.TrailFragment;
import com.example.craft1k.seft06.Model.TrailStation;
import com.example.craft1k.seft06.Fragment.TrailStationFragment;

import java.util.ArrayList;

public class ParticipantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TrailFragment.OnListFragmentInteractionListener,TrailStationFragment.OnListFragmentInteractionListener {

    private Participant participant;
    private ArrayList<Trail> joinedTrail;
//    private TrailManager trailManager ;

    private TextView mUserName;
    private ImageView mProfilePic;

    private FloatingActionButton fab;

    private RecyclerView mListTrail;
    private TrailListAdapter mTrailListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity);


        // Get joined Trail

        joinedTrail = App.trailManager.getJoinedTrail(participant);

//        mListTrail = (RecyclerView) findViewById(R.id.list_trail);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//
//        mListTrail.setLayoutManager(layoutManager);
//
//        /*
//         * Use this setting to improve performance if you know that changes in content do not
//         * change the child layout size in the RecyclerView
//         */
//        mListTrail.setHasFixedSize(true);
//
//        /*
//         * The ForecastAdapter is responsible for linking our weather data with the Views that
//         * will end up displaying our weather data.
//         */
//        mTrailListAdapter = new TrailListAdapter(this);
//
//        /* Setting the adapter attaches it to the RecyclerView in our layout. */
//        mListTrail.setAdapter(mTrailListAdapter);
//
//        mTrailListAdapter.setTrailData(joinedTrail);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        TrailFragment trailFragment = new TrailFragment();
        fragmentTransaction.replace(R.id.fragment_container, trailFragment);
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ParticipantActivity.this, SearchTrailActivity.class);
                myIntent.putExtra("key", "Search"); //Optional parameters
                ParticipantActivity.this.startActivity(myIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);





        mUserName = (TextView) headerView.findViewById(R.id.user_name);

        mUserName.setText(App.participant.getName());

        mProfilePic = (ImageView) headerView.findViewById(R.id.profile_picture);
        Uri profilePictureURI =  Uri.parse(App.participant.getProfileUrl());
        mProfilePic.setImageURI(profilePictureURI);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    @Override
    public void onListFragmentInteraction(Trail trail) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        TrailStationFragment trailStationFragment = new TrailStationFragment();
        fragmentTransaction.replace(R.id.fragment_container, trailStationFragment);
        fragmentTransaction.commit();

        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListFragmentInteraction(TrailStation trailStation) {

    }


}
