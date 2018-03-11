package trailblaze.issft06.android.com.trailblaze.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import trailblaze.issft06.android.com.trailblaze.MainActivity;

/**
 * Created by craft1k on 10-03-2018.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent splashIntent = new Intent(this, LoginActivity.class);
        startActivity(splashIntent);
        finish();
    }
}
