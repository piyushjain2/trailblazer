package trailblaze.issft06.android.com.trailblaze.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.fragment.TrailFragment;
import trailblaze.issft06.android.com.trailblaze.fragment.TrailStationFragment;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.model.Trainer;
import java.io.InputStream;
import static android.content.ContentValues.TAG;

public class TrainerTrailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TrailFragment.OnListFragmentInteractionListener, TrailStationFragment.OnListFragmentInteractionListener {


    private TextView mUserName;
    private ImageView mProfilePic;
    private TextView mDescription;
    private ProgressBar mProgressBar;
    private FirebaseAuth mauth;
    private ProgressDialog mProgressDialog;
    private FloatingActionButton fab;

    private Trainer trainer;

    private static final String TRAILS_LIST = "TRAIL_LIST";
    private static final String TRAIL_DETAIL = "TRAIL_DETAIL";

    private String fragment ;


    FirebaseFirestore mdb;
    CollectionReference mUsers;
    CollectionReference mTrails;
    CollectionReference mTrailStations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail);
        fragment = TRAILS_LIST;
        trainer = new Trainer();
        mauth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        // Get joined Trail


        FirebaseApp.initializeApp(this);
        mdb = FirebaseFirestore.getInstance();
        mUsers = mdb.collection("users");


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
                if (fragment.equals(TRAILS_LIST)) {
                    Intent myIntent = new Intent(TrainerTrailActivity.this, AddTrailActivity.class);
                    myIntent.putExtra("key", "Add"); //Optional parameters
                    TrainerTrailActivity.this.startActivity(myIntent);
                } else if (fragment.equals(TRAIL_DETAIL)) {
                    Intent myIntent = new Intent(TrainerTrailActivity.this, AddTrailStationActivity.class);
                    myIntent.putExtra("key", "Add"); //Optional parameters
                    TrainerTrailActivity.this.startActivity(myIntent);
                }

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

        ImageButton imageButton = navigationView.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setMessage("Logging Out");
                mProgressDialog.show();
                mauth.signOut();
                LoginManager.getInstance().logOut();

                startActivity(new Intent(TrainerTrailActivity.this, LoginActivity.class));
            }
        });

        mProfilePic = (ImageView) headerView.findViewById(R.id.profile_picture);
        mUserName = (TextView) headerView.findViewById(R.id.user_name);
        mDescription = (TextView) headerView.findViewById(R.id.user_email);

        mProgressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        mUsers
                .whereEqualTo("id", App.user.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                if (document != null && document.exists()) {
                                    trainer = document.toObject(Trainer.class);
                                    trainer.setFirebaseId(document.getId());

                                    /*Uri profilePictureURI = Uri.parse(App.participant.getProfileUrl());
                                    new DownloadImageTask(mProfilePic)
                                            .execute(App.participant.getProfileUrl());*/
                                    mUserName.setText(App.user.getName());
                                    mDescription.setText(App.user.getDescription());
                                    App.user = trainer;

                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        mTrails = mdb.collection("trails");


        mTrails.whereEqualTo("userId", App.user.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document != null && document.exists()) {

                             trainer.getCreatedTrails().add(document.toObject(Trail.class) );
                        }
                    }

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    TrailFragment trailFragment = new TrailFragment();
                    trailFragment.setTrails(trainer.getCreatedTrails());
                    fragmentTransaction.replace(R.id.fragment_trainer_container, trailFragment);
                    fragmentTransaction.commit();
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (this.fragment == TRAIL_DETAIL) {

            Intent myIntent = new Intent(TrainerTrailActivity.this, TrainerTrailActivity.class);

            TrainerTrailActivity.this.startActivity(myIntent);


        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_participant_trail_station, menu);
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
        switch (id) {
            case R.id.nav_trainer:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_participant:


                Intent intent = new Intent(TrainerTrailActivity.this, ParticipantActivity.class);
                startActivity(intent);
                finish();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onListFragmentInteraction(Trail trail) {
        this.fragment = TRAIL_DETAIL;
        App.trail = trail;
        mProgressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore mdb = FirebaseFirestore.getInstance();
        Log.d(TAG, trail.getId());


        mTrailStations = mdb.collection("trailStations");
        mTrailStations
                .whereEqualTo("trailId",trail.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document != null && document.exists()) {
                            App.trail.getTrailStations().add(document.toObject(TrailStation.class));
                        }
                    }
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();

                    TrailStationFragment trailStationFragment = new TrailStationFragment();

                    fragmentTransaction.replace(R.id.fragment_trainer_container, trailStationFragment);
                    fragmentTransaction.commit();
                }
                mProgressBar.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onListFragmentInteraction(TrailStation trailStation) {
        Intent myIntent = new Intent(TrainerTrailActivity.this, ParticipantTrailStation.class);
//        String trailId = mSearchText.getText().toString();
        myIntent.putExtra("trailId", trailStation.getId()); //Optional parameters
        App.trailStation = trailStation;
        TrainerTrailActivity.this.startActivity(myIntent);

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
            if (result != null) {
                Bitmap roundBitmap = getCroppedBitmap(result, 250);
                mProfilePic.setImageBitmap(roundBitmap);
            }


        }

        private Bitmap getCroppedBitmap(@NonNull Bitmap bmp, int radius) {
            Bitmap bitmap;

            if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
                float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
                float factor = smallest / radius;
                bitmap = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() / factor), (int) (bmp.getHeight() / factor), false);
            } else {
                bitmap = bmp;
            }

            Bitmap output = Bitmap.createBitmap(radius, radius,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, radius, radius);

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#BAB399"));
            canvas.drawCircle(radius / 2 + 0.7f,
                    radius / 2 + 0.7f, radius / 2 + 0.1f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }

    }

}
