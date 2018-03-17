package trailblaze.issft06.android.com.trailblaze;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import trailblaze.issft06.android.com.trailblaze.Activity.TrailListAdapter;
import trailblaze.issft06.android.com.trailblaze.App.App;
import trailblaze.issft06.android.com.trailblaze.Fragment.RVAdapter;
import trailblaze.issft06.android.com.trailblaze.Fragment.TrailFragment;
import trailblaze.issft06.android.com.trailblaze.Fragment.TrailStationFragment;
import trailblaze.issft06.android.com.trailblaze.Model.Trail;
import trailblaze.issft06.android.com.trailblaze.Model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.cardPOJO;

public class Trainer_trailList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TrailFragment.OnListFragmentInteractionListener,TrailStationFragment.OnListFragmentInteractionListener  {

    public List<cardPOJO> persons;
    public RecyclerView rv;

    private TextView mUserName;
    private ImageView mProfilePic;

    private FloatingActionButton fab;

    private RecyclerView mListTrail;
    private TrailListAdapter mTrailListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_trail_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Used to add trails", Snackbar.LENGTH_LONG)
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
        View headerView = navigationView.getHeaderView(0);





        mUserName = (TextView) headerView.findViewById(R.id.user_name);

        mUserName.setText(App.participant.getName());

        mProfilePic = (ImageView) headerView.findViewById(R.id.profile_picture);
        Uri profilePictureURI =  Uri.parse(App.participant.getProfileUrl());
        mProfilePic.setImageURI(profilePictureURI);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new cardPOJO("Trail 1", "A short description/Trail ID", R.drawable.lavery));
        persons.add(new cardPOJO("Trail 2", "A short description/Trail ID", R.drawable.emma));
        persons.add(new cardPOJO("Trail 3", "A short description/Trail ID", R.drawable.lillie));
        persons.add(new cardPOJO("Trail 4", "A short description/Trail ID", R.drawable.emma));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
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
