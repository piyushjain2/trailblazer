package trailblaze.issft06.android.com.trailblaze.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class Trail {

    public static final String DATE_FORMAT = "d-MMM-yyyy";
    public static final String TIME_FORMAT = "d-MMM-yyyy H:mm";

    private String   id;
    private String userID; // created user
    private String name;
    private Date    date;
    private Date timestamp;
    private ArrayList<String> trailStations;


    private String firebaseId;

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public Trail() {

        trailStations = new ArrayList<String>();
    }

    public static String getDateFormat() {
        return DATE_FORMAT;
    }

    public static String getTimeFormat() {
        return TIME_FORMAT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userID;
    }

    public void setUserId(String userId) {
        this.userID = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<String> getTrailStations() {
        return trailStations;
    }

    public void setTrailStations(ArrayList<String> trailStations) {
        this.trailStations = trailStations;
    }
}
