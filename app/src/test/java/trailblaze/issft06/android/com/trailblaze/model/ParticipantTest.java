package trailblaze.issft06.android.com.trailblaze.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by manhpd on 22/3/18.
 */
public class ParticipantTest {
    Participant participant;

    @Test
    public void joinTrail() throws Exception {
        participant = new Participant();
        participant.setName("Manh Pham");
        participant.setId("manhpd-001");

        Trail trail = new Trail();
        trail.setId("trail-001");
        trail.setName("Sample trail 1");


        Trail trail2 = new Trail();
        trail.setId("trail-002");
        trail.setName("Sample trail 2");

        participant.joinTrail(trail);
        assertEquals(participant.getJoinedTrail().size(), 1);


        participant.joinTrail(trail);
        assertEquals(participant.getJoinedTrail().size(), 1);


        participant.joinTrail(trail2);
        assertEquals(participant.getJoinedTrail().size(), 2);
        participant.unjoinTrail(trail2);
        assertEquals(participant.getJoinedTrail().size(), 1);

        assertEquals(participant.getJoinedTrail().contains(trail),true);
        assertEquals(participant.getJoinedTrail().contains(trail2),false);

    }

}