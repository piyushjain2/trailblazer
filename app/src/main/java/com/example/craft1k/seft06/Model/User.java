package com.example.craft1k.seft06.Model;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class User {
    private String name;
    private String id;
    private String description;
    private String profileUrl;

    public User(String name, String id, String description, String profileUrl) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
