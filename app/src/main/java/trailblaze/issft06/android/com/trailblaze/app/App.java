package trailblaze.issft06.android.com.trailblaze.app;

/**
 * Created by Lenovo on 2/27/2018.
 */

import android.app.Application;

import trailblaze.issft06.android.com.trailblaze.model.Participant;
import trailblaze.issft06.android.com.trailblaze.model.Trainer;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailManager;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.model.User;

public class App extends Application {

    public static final TrailManager trailManager = new TrailManager();
    public static Trail trail = new Trail();
    public static TrailStation trailStation = new TrailStation();
    public static User user = new User();

    public App() {

    }

    @Override public final void onCreate() {
        super.onCreate();
        // Get User from Firebase
    }

}
