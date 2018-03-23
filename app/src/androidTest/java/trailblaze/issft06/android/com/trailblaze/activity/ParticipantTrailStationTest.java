package trailblaze.issft06.android.com.trailblaze.activity;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
import trailblaze.issft06.android.com.trailblaze.R;

/**
 * Created by nhatdx on 23/3/18.
 */
public class ParticipantTrailStationTest {

    @Rule
    public ActivityTestRule<ParticipantTrailStation> mActivityTestRule = new ActivityTestRule<>(ParticipantTrailStation.class);

    private ParticipantTrailStation mActivity = null;

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }

    @Test
    public void testLaunch() {

        View view1 = mActivity.findViewById(R.id.appbar);
        View view2 = mActivity.findViewById(R.id.container);
        View view3 = mActivity.findViewById(R.id.tabs);

        assertNotNull(view1);
        assertNotNull(view2);
        assertNotNull(view3);

    }

}