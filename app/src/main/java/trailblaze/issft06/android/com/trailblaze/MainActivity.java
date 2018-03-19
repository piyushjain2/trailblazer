package trailblaze.issft06.android.com.trailblaze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import trailblaze.issft06.android.com.trailblaze.activity.ParticipantActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ArrayList<Trail> joinedTrail = new ArrayList<Trail>();
//        Trail trailOne = new Trail();
//        Trail trailTwo = new Trail();
//
//        App.trailManager.addTrail(trailOne);
//        App.trailManager.addTrail(trailTwo);
//
//        App.trailManager.addParticipantTrail(App.participant,trailOne);
//        App.trailManager.addParticipantTrail(App.participant,trailTwo);
//
//
//        // TODO modify with real Firebase Data
//
//        TrailStation trailStation = new TrailStation("001","Utown","001",null,"Go there an do nothing",1);
//
//        trailOne.addTrailStation(trailStation);

        setContentView(R.layout.activity_main);
    }

    public void gotoTrainer_trailList(View view)
    {
        Intent intent = new Intent(MainActivity.this, Trainer_trailList.class);
        startActivity(intent);
    }


    public void gotoParticipant_activity(View view)
    {
        Intent intent = new Intent(MainActivity.this, ParticipantActivity.class);
        startActivity(intent);
    }
}
