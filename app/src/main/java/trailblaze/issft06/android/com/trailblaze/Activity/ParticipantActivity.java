package trailblaze.issft06.android.com.trailblaze.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import trailblaze.issft06.android.com.trailblaze.App.App;
import trailblaze.issft06.android.com.trailblaze.fragment.TrailFragment;
import trailblaze.issft06.android.com.trailblaze.fragment.TrailStationFragment;
import trailblaze.issft06.android.com.trailblaze.model.Participant;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.firestoredao.FirestoredaoMgr;


import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ParticipantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TrailFragment.OnListFragmentInteractionListener,TrailStationFragment.OnListFragmentInteractionListener {

    private Participant participant ;
    private ArrayList<Trail> joinedTrail;
//    private TrailManager trailManager ;

    private TextView mUserName;
    private ImageView mProfilePic;

    private FloatingActionButton fab;

    private RecyclerView mListTrail;
    private TrailListAdapter mTrailListAdapter;


    FirebaseFirestore mdb ;
    CollectionReference mUsers ;
    FirestoredaoMgr daoMgr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity);


        // Get joined Trail
        participant = (Participant) App.user;
        joinedTrail = App.trailManager.getJoinedTrail(participant);

        FirebaseApp.initializeApp(this);
        mdb = FirebaseFirestore.getInstance();
        mUsers = mdb.collection("users");
        daoMgr = new FirestoredaoMgr();



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

        mProfilePic = (ImageView) headerView.findViewById(R.id.profile_picture);
        mUserName = (TextView) headerView.findViewById(R.id.user_name);

        mUsers
                .whereEqualTo("id", "001")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                if(document != null && document.exists()) {
                                    participant = document.toObject(Participant.class);
                                    participant.setFirebaseId(document.getId());
                                    Uri profilePictureURI =  Uri.parse(App.participant.getProfileUrl());
                                    new DownloadImageTask(mProfilePic)
                                            .execute(App.participant.getProfileUrl());
                                    mUserName.setText(App.participant.getName());

                                    CollectionReference mJoinedTrails = mUsers.document(App.participant.getFirebaseId()).collection("joinedTrails");
                                    mJoinedTrails.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for(DocumentSnapshot document: task.getResult()) {
                                                    if(document != null && document.exists()) {
                                                        App.participant.getJoinedTrail().add(document.get("id").toString());
                                                        FragmentManager fm = getFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fm.beginTransaction();

                                                        TrailFragment trailFragment = new TrailFragment();
                                                        fragmentTransaction.replace(R.id.fragment_container, trailFragment);
                                                        fragmentTransaction.commit();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });







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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onListFragmentInteraction(Trail trail) {


        FirebaseFirestore mdb = FirebaseFirestore.getInstance();
        final CollectionReference mTrails = mdb.collection("trails");
        Log.d(TAG, trail.getId() );

        mTrails
                .whereEqualTo("id", trail.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //putting in a Map and returning the map
                                if(document != null && document.exists()) {
                                     App.trail  = document.toObject(Trail.class);
                                     App.trail.setFirebaseId(document.getId());

                                    CollectionReference mTrailStations = mTrails.document(App.trail.getFirebaseId()).collection("trailStations");
                                    mTrailStations.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for(DocumentSnapshot document: task.getResult()) {
                                                    if(document != null && document.exists()) {
                                                        App.trail.getTrailStations().add(document.get("id").toString());

                                                    }
                                                }
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                                                TrailStationFragment trailStationFragment = new TrailStationFragment();

                                                trailStationFragment.setTrail(App.trail);
                                                fragmentTransaction.replace(R.id.fragment_container, trailStationFragment);
                                                fragmentTransaction.commit();
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        fab.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListFragmentInteraction(TrailStation trailStation) {
        Intent myIntent = new Intent(ParticipantActivity.this, ParticipantTrailStation.class);

//        String trailId = mSearchText.getText().toString();


        myIntent.putExtra("trailId", trailStation.getId()); //Optional parameters
        ParticipantActivity.this.startActivity(myIntent);

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        public DownloadImageTask(ImageView bmImage) {
            mProfilePic = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Bitmap roundBitmap = getCroppedBitmap( result, 250);
            mProfilePic.setImageBitmap(roundBitmap);

        }
        private  Bitmap getCroppedBitmap( @NonNull Bitmap bmp, int radius )
        {
            Bitmap bitmap;

            if ( bmp.getWidth( ) != radius || bmp.getHeight( ) != radius )
            {
                float smallest = Math.min( bmp.getWidth( ), bmp.getHeight( ) );
                float factor = smallest / radius;
                bitmap = Bitmap.createScaledBitmap( bmp, ( int ) ( bmp.getWidth( ) / factor ), ( int ) ( bmp.getHeight( ) / factor ), false );
            }
            else
            {
                bitmap = bmp;
            }

            Bitmap output = Bitmap.createBitmap( radius, radius,
                    Bitmap.Config.ARGB_8888 );
            Canvas canvas = new Canvas( output );

            final Paint paint = new Paint( );
            final Rect rect = new Rect( 0, 0, radius, radius );

            paint.setAntiAlias( true );
            paint.setFilterBitmap( true );
            paint.setDither( true );
            canvas.drawARGB( 0, 0, 0, 0 );
            paint.setColor( Color.parseColor( "#BAB399" ) );
            canvas.drawCircle( radius / 2 + 0.7f,
                    radius / 2 + 0.7f, radius / 2 + 0.1f, paint );
            paint.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.SRC_IN ) );
            canvas.drawBitmap( bitmap, rect, rect, paint );

            return output;
        }

    }

}
