package trailblaze.issft06.android.com.trailblaze.firestoredao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Source on 12-03-2018.
 */

public class FirestoredaoMgr {

    public FirestoredaoMgr() {
    }

    Map<String, Object> trail = new HashMap<>();
    Map<String, Object> trail_stn = new HashMap<>();
    Map<String, Object> contributions = new HashMap<>();
    Map<String, Object> posts = new HashMap<>();

    public Map<String, Object> getTrail() {
        return trail;
    }

    public void setTrail(Map<String, Object> trail) {
        this.trail = trail;
    }

    public Map<String, Object> getTrail_stn() {
        return trail_stn;
    }

    public void setTrail_stn(Map<String, Object> trail_stn) {
        this.trail_stn = trail_stn;
    }

    public Map<String, Object> getContributions() {
        return contributions;
    }

    public void setContributions(Map<String, Object> contributions) {
        this.contributions = contributions;
    }

    public Map<String, Object> getPosts() {
        return posts;
    }

    public void setPosts(Map<String, Object> posts) {
        this.posts = posts;
    }
}