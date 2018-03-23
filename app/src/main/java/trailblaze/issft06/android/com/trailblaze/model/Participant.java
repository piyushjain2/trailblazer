package trailblaze.issft06.android.com.trailblaze.model;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class Participant extends User {

    public Participant() {
        super();
        joinedTrail = new ArrayList<Trail>();
    }

    private ArrayList<Trail> joinedTrail;

    public ArrayList<Trail> getJoinedTrail() {
        return joinedTrail;
    }

    public void setJoinedTrail(ArrayList<Trail> joinedTrail) {
        this.joinedTrail = joinedTrail;
    }

    public void joinTrail (Trail trail) {
        /* TODO (1): implement join trail funciton */
//        if (App.trailManager.getParticipantTrail().get(this) == null) {
//            HashMap<Participant,List<Trail>> trailParticipant = null;
//            List<Trail> trails = null;
//            trails.add(trail);
//            trailParticipant.put(this,trails);
//        } else {
//            App.trailManager.addParticipantTrail(this, trail);
//        }
    }

}
