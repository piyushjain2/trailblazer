package trailblaze.issft06.android.com.trailblaze;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import trailblaze.issft06.android.com.trailblaze.view.Trainer_TrailListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoTrainer_trailList(View view)
    {
        Intent intent = new Intent(MainActivity.this, Trainer_TrailListActivity.class);
        startActivity(intent);
    }
}
