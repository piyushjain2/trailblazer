package com.example.craft1k.seft06.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.craft1k.seft06.App.App;
import com.example.craft1k.seft06.Model.Trail;
import com.example.craft1k.seft06.Model.TrailStation;
import com.example.craft1k.seft06.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mParticipantActivity;
    private Button mTrainerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sampple Data TODO tobe deleted


        ArrayList<Trail> joinedTrail = new ArrayList<Trail>();
        Trail trailOne = new Trail("trail001","002","Utown",null,null);
        Trail trailTwo = new Trail("trail002","003","Iss",null,null);

        App.trailManager.addTrail(trailOne);
        App.trailManager.addTrail(trailTwo);

        App.trailManager.addParticipantTrail(App.participant,trailOne);
        App.trailManager.addParticipantTrail(App.participant,trailTwo);


        // TODO modify with real Firebase Data

        TrailStation trailStation = new TrailStation("001","Utown","001",null,"Go there an do nothing",1);

        trailOne.addTrailStation(trailStation);

        mParticipantActivity = (Button) findViewById(R.id.participant_activity);
        mTrainerActivity = (Button) findViewById(R.id.trainer_activity);

        mParticipantActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MainActivity.this;

                /* This is the class that we want to start (and open) when the button is clicked. */
                Class destinationActivity = ParticipantActivity.class;

                /*
                 * Here, we create the Intent that will start the Activity we specified above in
                 * the destinationActivity variable. The constructor for an Intent also requires a
                 * context, which we stored in the variable named "context".
                 */
                Intent startChildActivityIntent = new Intent(context, destinationActivity);

                // COMPLETED (2) Use the putExtra method to put the String from the EditText in the Intent
                /*
                 * We use the putExtra method of the Intent class to pass some extra stuff to the
                 * Activity that we are starting. Generally, this data is quite simple, such as
                 * a String or a number. However, there are ways to pass more complex objects.
                 */
                startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, "participant");

                /*
                 * Once the Intent has been created, we can use Activity's method, "startActivity"
                 * to start the ChildActivity.
                 */
                startActivity(startChildActivityIntent);

            }
        });
    }
}
