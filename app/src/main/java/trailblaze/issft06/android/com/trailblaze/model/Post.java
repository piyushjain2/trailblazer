package trailblaze.issft06.android.com.trailblaze.model;

import java.sql.Timestamp;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class Post {
    private String id;
    private String trailStationId;
    private String userId;
    private String postContent;
    private Timestamp timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrailStationId() {
        return trailStationId;
    }

    public void setTrailStationId(String trailStationId) {
        this.trailStationId = trailStationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
