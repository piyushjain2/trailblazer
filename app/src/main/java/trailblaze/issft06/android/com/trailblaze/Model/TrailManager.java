package trailblaze.issft06.android.com.trailblaze.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class TrailManager {
    private HashMap<Trainer,ArrayList<Trail>> trainerTrail;
    private HashMap<Participant,ArrayList<Trail>> participantTrail;
    private ArrayList<Trail> createdTrails;

    public ArrayList<Trail> getCreatedTrails() {
        return createdTrails;
    }

    public void setCreatedTrails(ArrayList<Trail> createdTrails) {
        this.createdTrails = createdTrails;
    }

    public TrailManager() {
        this.trainerTrail = new HashMap<Trainer,ArrayList<Trail>>();
        this.participantTrail = new HashMap<Participant,ArrayList<Trail>> ();
        this.createdTrails = new ArrayList<Trail>();
    }

    public HashMap<Trainer, ArrayList<Trail>> getTrainerTrail() {
        return trainerTrail;
    }

    public void setTrainerTrail(HashMap<Trainer, ArrayList<Trail>> trainerTrail) {
        this.trainerTrail = trainerTrail;
    }

    public HashMap<Participant, ArrayList<Trail>> getParticipantTrail() {
        return participantTrail;
    }

    public void setParticipantTrail(HashMap<Participant, ArrayList<Trail>> participantTrail) {
        this.participantTrail = participantTrail;
    }



    public void addParticipantTrail(Participant participant, Trail trail) {

        ArrayList<Trail> trails = participantTrail.get(participant);
        if (trails == null) {
            trails = new ArrayList<Trail>();
            trails.add(trail);
            participantTrail.put(participant,trails);
        } else {
            participantTrail.get(participant).add(trail);
        }

    }

    public ArrayList<Trail> getJoinedTrail(Participant participant) {
        ArrayList<Trail> trails = participantTrail.get(participant);
        return trails;
    }

    public void addTrail(Trail trail) {
        this.createdTrails.add(trail);
    }

    public Trail getTrailById(String trailId) {
         for (Trail trail: this.createdTrails) {
             if (trail.getId().equals(trailId)) {
                 return trail;
             }
         }
        return null;
    }
}
