package trailblaze.issft06.android.com.trailblaze.model;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class Trainer extends User {
    private ArrayList<String> createdTrails;

    public Trainer() {
        super();
        createdTrails = new ArrayList<String>();
    }

    public void createTrail() {
        // TODO (2) implement create trail function
    }

    public void createPost() {
        // TODO (3) implement create post function
    }

    public ArrayList<String> getCreatedTrails() {
        return createdTrails;
    }
}
