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

    public boolean isJoin (Trail trail) {
        return joinedTrail.contains(trail);
    }
    public void joinTrail (Trail trail) {
        if (isJoin(trail)) {
            return ;
        } else {
            joinedTrail.add(trail);
        }

    }

    public void unjoinTrail (Trail trail) {
        if (!isJoin(trail)) {
            return ;
        } else {
            joinedTrail.remove(trail);
        }

    }

}
