package com.example.craft1k.seft06.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.craft1k.seft06.App.App;
import com.example.craft1k.seft06.Model.Trail;
import com.example.craft1k.seft06.R;

/**
 * Created by manhpd on 2/3/18.
 */

public class SearchTrailActivity extends AppCompatActivity {
    private boolean mSearchViewAdded = false;
    private WindowManager mWindowManager;
    private Toolbar mToolbar;
    private MenuItem searchItem;
    private boolean searchActive = false;
    private FloatingActionButton fab;

    private CardView mResult;
    private EditText mSearchText;
    private Button mSearchTrailButton;
    private TextView mTrailName;
    private TextView mTrailDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trail);

        mSearchText = (EditText) findViewById(R.id.search_trail);
        mSearchTrailButton = (Button) findViewById(R.id.search_trail_button);
        mTrailName = (TextView) findViewById(R.id.trail_result_name);
        mTrailDescription = (TextView) findViewById(R.id.trail_result_description);
        mResult = (CardView) findViewById(R.id.search_result);
        mResult.setVisibility(View.INVISIBLE);

        mSearchTrailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String trailId = mSearchText.getText().toString();
                Trail trail = App.trailManager.getTrailById(trailId);
                if (trail != null) {
                    mTrailName.setText(trail.getName());
                    mTrailDescription.setText(trail.getUserId());
                    mResult.setVisibility(View.VISIBLE);
                }




            }
        });

        mResult.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent myIntent = new Intent(SearchTrailActivity.this, TrailDetailActivity.class);

                String trailId = mSearchText.getText().toString();


                myIntent.putExtra("trailId", trailId); //Optional parameters
                SearchTrailActivity.this.startActivity(myIntent);

            }
        }) ;
    }




}
