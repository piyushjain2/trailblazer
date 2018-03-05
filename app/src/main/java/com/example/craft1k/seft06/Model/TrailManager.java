package com.example.craft1k.seft06.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class TrailManager {
    private HashMap<Trainer,ArrayList<Trail>> trainerTrail;
    private HashMap<Participant,ArrayList<Trail>> participantTrail;

    public TrailManager() {
        this.trainerTrail = new HashMap<Trainer,ArrayList<Trail>>();
        this.participantTrail = new HashMap<Participant,ArrayList<Trail>> ();
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
}
