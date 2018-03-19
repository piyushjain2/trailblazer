package trailblaze.issft06.android.com.trailblaze.firestoredao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Source on 11-03-2018.
 */

public class Trail_Station_dao {

    public Trail_Station_dao() {
    }

    FirebaseFirestore mdb = FirebaseFirestore.getInstance();
    CollectionReference mtrailstn = mdb.collection("trail_station");
    FirestoredaoMgr daoMgr = new FirestoredaoMgr();


    /*  method to create a trail_station
        @params String trailID, String trailstnID, String stnName, String seq, String instr, String geoLoc
        */
    public void createTrail_Stn(String trailID, String trailstnID, String stnName, String seq, String instr, GeoPoint geoLoc){


        Map<String, Object> trail_stn = new HashMap<>();
        trail_stn.put("ID", trailstnID);
        trail_stn.put("trailID", trailID);
        trail_stn.put("instructions", instr);
        trail_stn.put("stn_name", stnName);
        trail_stn.put("Sequence", seq);
        trail_stn.put("GPS", geoLoc);


        DocumentReference mdoc = mtrailstn.document(trailID + stnName);
        //set creates new or will overwrite last
        mdoc.set(trail_stn);


    }


    public Map<String, Object> getTrailstnbytrailID(String trailID){


        mtrailstn
                .whereEqualTo("trailID", trailID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //putting in a Map and returning the map
                                if(document != null && document.exists()) {
                                    daoMgr.setTrail_stn(document.getData());
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return null;
    }




}
