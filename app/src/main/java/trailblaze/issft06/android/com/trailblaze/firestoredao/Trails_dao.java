package trailblaze.issft06.android.com.trailblaze.firestoredao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import trailblaze.issft06.android.com.trailblaze.model.Trail;

import static android.content.ContentValues.TAG;

/**
 * Class for CRUD for Trails_dao
 * method for creating a trails by trainer
 * method for editing a trails by trainer
 * Created by Source on 10-03-2018.
 */

public class Trails_dao {

    // Access a Cloud Firestore instance from your Activity

    FirebaseFirestore mdb ;
    CollectionReference mtrails ;
    FirestoredaoMgr daoMgr ;



    public Trails_dao(Context context) {
        FirebaseApp.initializeApp(context);
        mdb = FirebaseFirestore.getInstance();
        mtrails = mdb.collection("trails");
        daoMgr = new FirestoredaoMgr();
    }


/*
*        Create n Edit Data in firebase
*
*/

        /*  method to create a trail
        params String trailID, Date crTimestamp, String trailName, String UserID
        */
        private void createTrail(String trailID, Date crTimestamp, String trailName, String userID){

            Map<String, Object> trail = new HashMap<>();
            trail.put("ID", trailID);
            trail.put("UserID", userID);
            trail.put("TrailName", trailName);
            trail.put("date", crTimestamp);
            trail.put("timeStamp", FieldValue.serverTimestamp());


                    DocumentReference mdoc = mtrails.document(trailID);
                    //set creates new or will overwrite last
                    mdoc.set(trail);


        }

        /*method to edit a trail
        params String trailID, Date crTimestamp, String trailName, String UserID
        */
        private void editTrail(String trailID, Date crTimestamp, String trailName, String userID){

            Map<String, Object> trail = new HashMap<>();
            trail.put("ID", trailID);
            trail.put("UserID", userID);
            trail.put("TrailName", trailName);
            trail.put("date", crTimestamp);

            mdb.collection("trails").document(trailID)
                    .set(trail, SetOptions.merge());

        }

/*
*        Retrieve Data from firebase
*
*/

    // Getting trails by trailID
    private Map<String, Object> getTrailbyID(String trailID){


        mtrails
                .whereEqualTo("trailId", trailID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                if(document != null && document.exists()) {
                                    daoMgr.setTrail(document.getData());

                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //return object containing HashMap
        return null;

    }


    public Trail getTrailById(String trailID){

        final Trail[] trail = {new Trail()};

        mtrails
                .whereEqualTo("id", trailID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                if(document != null && document.exists()) {
                                     trail[0] = document.toObject(Trail.class);

                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //return object containing HashMap
        return trail[0];

    }



}
