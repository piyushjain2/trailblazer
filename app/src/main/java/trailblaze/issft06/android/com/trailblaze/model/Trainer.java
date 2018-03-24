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

    public void createTrail() {
        // TODO (2) implement create trail function
    }

    public void createTrail(Trail trail) {
        createdTrails = new ArrayList<Trail>();
        createdTrails.add(trail);
    }

    public void createPost() {
        // TODO (3) implement create post function
    }

    public ArrayList<Trail> getCreatedTrails() {
        return createdTrails;
    }
}
