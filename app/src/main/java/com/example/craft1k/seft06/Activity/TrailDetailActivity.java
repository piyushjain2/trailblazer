package com.example.craft1k.seft06.Activity;

import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.craft1k.seft06.App.App;
import com.example.craft1k.seft06.Model.Trail;
import com.example.craft1k.seft06.R;

public class TrailDetailActivity extends AppCompatActivity {
    private String trailId;
    private TextView trailDetailName;
    private TextView trailDetailDescription;
    private FloatingActionButton mJoinTrailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);
        trailDetailName = (TextView) findViewById(R.id.trail_detail_name);
        trailDetailDescription = (TextView) findViewById(R.id.trail_result_description);

        Intent intent = getIntent();

        trailId = intent.getStringExtra("trailId");

        trailDetailName.setText(trailId);

        mJoinTrailButton = (FloatingActionButton) findViewById(R.id.join_trail);
        mJoinTrailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.trailManager.addParticipantTrail(App.participant,App.trailManager.getTrailById(trailId));

            }
        });

    }
}
