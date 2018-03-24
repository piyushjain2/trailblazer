package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.model.User;

/**
 * Created by Piyush on 2/3/18.
 */

public class UserRoleSelectionActivity extends AppCompatActivity implements View.OnClickListener {


    private User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_role_selection);

        View trainerCard = findViewById(R.id.cv_user_selection_trainer);
        View participantCard = findViewById(R.id.cv_user_selection_participant);
        thisUser = App.user;
        trainerCard.setOnClickListener(this);
        participantCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()){
            case R.id.cv_user_selection_trainer:
                intent = new Intent(UserRoleSelectionActivity.this,TrainerTrailActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.cv_user_selection_participant:
                intent = new Intent(UserRoleSelectionActivity.this, ParticipantActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
