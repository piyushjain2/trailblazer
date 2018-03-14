package trailblaze.issft06.android.com.trailblaze.App;

/**
 * Created by Lenovo on 2/27/2018.
 */

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import trailblaze.issft06.android.com.trailblaze.Model.Participant;
import trailblaze.issft06.android.com.trailblaze.Model.Trail;
import trailblaze.issft06.android.com.trailblaze.Model.TrailManager;
import trailblaze.issft06.android.com.trailblaze.firestoredao.FirestoredaoMgr;

import static android.content.ContentValues.TAG;


/**
 * Created by swarna on 9/8/16.
 */
public class App extends Application {

    public static final TrailManager trailManager = new TrailManager();
    public static Participant participant = new Participant();
//            =new Participant("Manh Pham","001","Sample Participant","https://avatars2.githubusercontent.com/u/5423719?s=400&v=4");
    public static Trail trail = new Trail();

    public App() {

    }

    @Override public final void onCreate() {
        super.onCreate();
        // Get User from Firebase






    }


}
