package trailblaze.issft06.android.com.trailblaze.App;

/**
 * Created by Lenovo on 2/27/2018.
 */

import android.app.Application;

import trailblaze.issft06.android.com.trailblaze.model.Participant;
import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailManager;
import trailblaze.issft06.android.com.trailblaze.model.User;


/**
 * Created by swarna on 9/8/16.
 */
public class App extends Application {

    public static final TrailManager trailManager = new TrailManager();
    public static Participant participant = new Participant();
    public static User user = new User();
//            =new Participant("Manh Pham","001","Sample Participant","https://avatars2.githubusercontent.com/u/5423719?s=400&v=4");
    public static Trail trail = new Trail();

    /*public App() {

    }
*/
    @Override public final void onCreate() {
        super.onCreate();
        // Get User from Firebase

    }


}
