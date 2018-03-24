package trailblaze.issft06.android.com.trailblaze.model;


import java.util.Date;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class ContributeItem {
    private String id;
    private String userId;
    private String name;
    private String trailStationId;
    private String url;
    private String desc;
    private String contentType;

    private Date cTime;

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcTime() {
        return cTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrailStationId() {
        return trailStationId;
    }

    public void setTrailStationId(String trailStationId) {
        this.trailStationId = trailStationId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
