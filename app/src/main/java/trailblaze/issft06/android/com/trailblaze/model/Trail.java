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
    private String userId; // created user
    private String name;
    private Date    date;
    private Date timestamp;
    private ArrayList<TrailStation> trailStations;
    private Integer totalTrailStation;


    private String firebaseId;

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public Trail() {

        trailStations = new ArrayList<TrailStation>();
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
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public ArrayList<TrailStation> getTrailStations() {
        return trailStations;
    }



    public void setTrailStations(ArrayList<TrailStation> trailStations) {
        this.trailStations = trailStations;
    }

    public void addTrailStation(TrailStation trailStation) {
        trailStations.add(trailStation);
    }

    public Integer getTotalTrailStation() {
        totalTrailStation = trailStations.size();
        return totalTrailStation;
    }

    public boolean contains(TrailStation trailStation2) {
        return trailStations.contains(trailStation2) ? true : false;
    }

    public void removeTrailStation(TrailStation trailStation) {
        trailStations.remove(trailStation);
    }
}
