package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by craft1k on 24-03-2018.
 */

import org.junit.Test;

import trailblaze.issft06.android.com.trailblaze.model.Trail;
import trailblaze.issft06.android.com.trailblaze.model.Trainer;

import static org.junit.Assert.assertEquals;

/**
 * Created by manhpd on 22/3/18.
 */
public class TrainerTest {
    Trainer trainer;

    @Test
    public void joinTrail() throws Exception {
        trainer = new Trainer();
        trainer.setName("Manh Pham");
        trainer.setId("manhpd-001");

        Trail trail = new Trail();
        trail.setId("trail-001");
        trail.setName("Sample trail 1");


        Trail trail2 = new Trail();
        trail.setId("trail-002");
        trail.setName("Sample trail 2");

        trainer.createTrail(trail);
        assertEquals(trainer.getCreatedTrails().size(), 1);


        trainer.createTrail(trail);
        assertEquals(trainer.getCreatedTrails().size(), 1);


        trainer.createTrail(trail2);
        assertEquals(trainer.getCreatedTrails().size(), 1);

        assertEquals(trainer.getCreatedTrails().contains(trail),false);
        assertEquals(trainer.getCreatedTrails().contains(trail2),true);

    }

}