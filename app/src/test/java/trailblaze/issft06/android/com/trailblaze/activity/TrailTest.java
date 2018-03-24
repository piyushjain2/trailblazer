package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by craft1k on 24-03-2018.
 */

import org.junit.Test;

import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.TrailStation;

import static org.junit.Assert.*;

/**
 * Created by manhpd on 22/3/18.
 */
public class TrailTest {

    Trail trail = new Trail();


    @Test
    public void trailStationTest() throws Exception {
        trail.setName("Sample Trail");
        trail.setId("trail-001");

        TrailStation trailStation = new TrailStation();
        trailStation.setId("trail-station001");
        trailStation.setName("Sample Station 1");


        TrailStation trailStation2 = new TrailStation();
        trailStation2.setId("trail-station002");
        trailStation2.setName("Sample Station 2");

        trail.addTrailStation(trailStation);
        assertEquals(trail.getTotalTrailStation(),(Integer) 1);


        trail.addTrailStation(trailStation);
        assertEquals(trail.getTotalTrailStation(),(Integer) 2);

        trail.addTrailStation(trailStation2);
        assertEquals(trail.getTotalTrailStation(),(Integer) 3);

        assertEquals(trail.contains(trailStation2),true);


        trail.removeTrailStation(trailStation);
        assertEquals(trail.getTotalTrailStation(),(Integer) 2);

        trail.removeTrailStation(trailStation);
        assertEquals(trail.getTotalTrailStation(),(Integer) 1);


    }
}
