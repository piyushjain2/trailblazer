package trailblaze.issft06.android.com.trailblaze.model;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class Trainer extends User {
    private ArrayList<Trail> createdTrails;

    public Trainer() {
        super();
        createdTrails = new ArrayList<Trail>();
    }

    public boolean isCreated(Trail trail) {
        return  createdTrails.contains(trail);
    }

    public void createTrail(Trail trail) {
        if(isCreated(trail)) {
            return;
        } else {
            this.createdTrails.add(trail);
        }
    }


    public ArrayList<Trail> getCreatedTrails() {
        return createdTrails;
    }
}
