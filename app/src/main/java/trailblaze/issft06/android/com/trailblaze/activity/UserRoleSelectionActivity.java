package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.Trainer_trailList;
import trailblaze.issft06.android.com.trailblaze.model.User;

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
                intent = new Intent(UserRoleSelectionActivity.this,Trainer_trailList.class);
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
