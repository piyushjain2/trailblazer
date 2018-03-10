package com.example.craft1k.seft06.Model;

import java.sql.Timestamp;
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
    private Timestamp timestamp;
    private ArrayList<TrailStation> trailStations;

    public void addTrailStation(TrailStation trailStation) {

        this.trailStations.add(trailStation);
    }

    public Trail(String id, String userId, String name, Date date, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.timestamp = timestamp;
        this.trailStations = new ArrayList<TrailStation>();

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<TrailStation> getTrailStations() {
        return trailStations;
    }

    public void setTrailSation(ArrayList<TrailStation> trailStations) {
        this.trailStations = trailStations;
    }
}
