package com.example.craft1k.seft06.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trail);
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.
    }


}
