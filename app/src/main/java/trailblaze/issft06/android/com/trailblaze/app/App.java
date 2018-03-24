package trailblaze.issft06.android.com.trailblaze.app;

/**
 * Created by Lenovo on 2/27/2018.
 */

import android.app.Application;

import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;
import trailblaze.issft06.android.com.trailblaze.model.User;



/**
 * Created by swarna on 9/8/16.
 */
public class App extends Application {


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
