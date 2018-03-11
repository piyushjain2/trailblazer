package trailblaze.issft06.android.com.trailblaze.Model;

/**
 * Created by Lenovo on 2/26/2018.
 */

public class TrailStation {
    private String id;
    private String name;
    private String trailId;
    private String GPS;
    private String instruction;
    private int sequence;

    public TrailStation(String id, String name, String trailId, String GPS, String instruction, int sequence) {
        this.id = id;
        this.name = name;
        this.trailId = trailId;
        this.GPS = GPS;
        this.instruction = instruction;
        this.sequence = sequence;
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

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
