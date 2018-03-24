package trailblaze.issft06.android.com.trailblaze.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;

/**
 * Created by Source on 20-03-2018.
 */

public class TrainerTrailStation extends AppCompatActivity{



        public static final String TAG = "Message";
        private FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();

        //    need to update here
        private String TrailStationID ;
        private String UserID ;

        private SectionsPagerAdapter mSectionsPagerAdapter;

        private ViewPager mViewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            UserID = App.user.getId();
            TrailStationID = App.trailStation.getId();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trainer_trail_station);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        }

        // Push the message to the database
        public void sendMessage(View view) {

            final EditText input = findViewById(R.id.sendPost);
            String inputText = input.getText().toString();

            if (inputText.isEmpty()) {
                return;
            }

            Map<String, Object> dataToSave = new HashMap<>();

            Date now = new Date();
            Calendar timeInMillis = Calendar.getInstance();
            timeInMillis.setTime(now);
            String timeInString = Long.toString(timeInMillis.getTimeInMillis());

            dataToSave.put("TrailStationID", TrailStationID);
            dataToSave.put("UserID", UserID);
            dataToSave.put("Msg", inputText);
            dataToSave.put("Time", now);

            mDocRef.collection("post").document(timeInString).set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Has been sent");

                    input.setText(null);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Message was not sent!", e);
                }
            });
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


        /**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
         * one of the sections/tabs/pages.
         */
        public class SectionsPagerAdapter extends FragmentPagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {

                // pass TrailStationID to fragments
                Bundle params = new Bundle();
                params.putString ("TrailStationID", TrailStationID);

                switch (position) {
                    case 0:
                        Tab1Task tab1 = new Tab1Task();
                        tab1.setArguments(params);
                        return tab1;
                    case 1:
                        Tab2Upload tab2 = new Tab2Upload();
                        tab2.setArguments(params);
                        return tab2;
                    case 2:
                        Tab3Discuss tab3 = new Tab3Discuss();
                        tab3.setArguments(params);
                        return tab3;
                    case 3:
                        Tab4Activity tab4 = new Tab4Activity();
                        tab4.setArguments(params);
                        return tab4;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                // Show 4 total pages.
                return 4;
            }
        }
}


