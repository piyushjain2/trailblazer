package com.example.craft1k.seft06.Model;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class Participant extends User {

    public Participant(String name, String id, String description, String profileUrl) {
        super(name, id, description, profileUrl);
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
