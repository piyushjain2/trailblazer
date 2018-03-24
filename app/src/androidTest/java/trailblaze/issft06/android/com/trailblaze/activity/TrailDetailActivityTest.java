package trailblaze.issft06.android.com.trailblaze.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import trailblaze.issft06.android.com.trailblaze.R;
import trailblaze.issft06.android.com.trailblaze.app.App;
import trailblaze.issft06.android.com.trailblaze.model.Participant;

import static org.junit.Assert.*;

/**
 * Created by manhpd on 23/3/18.
 */
public class TrailDetailActivityTest {
    @Rule
    public ActivityTestRule<TrailDetailActivity> mActivityTestRule = new ActivityTestRule<>(TrailDetailActivity.class);

    private TrailDetailActivity mActivity = null;

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

        View trailDetailName = mActivity.findViewById(R.id.trail_detail_name);
        View trailDetailDescription =  mActivity.findViewById(R.id.trail_result_description);

        View mJoinTrailButton = mActivity.findViewById(R.id.join_trail);

        assertNotNull(trailDetailDescription);

        assertNotNull(trailDetailName);

        assertNotNull(mJoinTrailButton);
    }
}