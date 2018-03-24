package trailblaze.issft06.android.com.trailblaze.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import trailblaze.issft06.android.com.trailblaze.R;

import static org.junit.Assert.*;

/**
 * Created by manhpd on 23/3/18.
 */
public class ParticipantActivityTest {
    @Rule
    public ActivityTestRule<ParticipantActivity> mActivityTestRule = new ActivityTestRule<>(ParticipantActivity.class);

    private ParticipantActivity mActivity = null;

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

        View headerView = mActivity.findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);

        ImageView mProfilePic = (ImageView) headerView.findViewById(R.id.profile_picture);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mDescription = (TextView) headerView.findViewById(R.id.user_email);

        ProgressBar mProgressBar = (ProgressBar) mActivity.findViewById(R.id.progressBar);
        FloatingActionButton mFloatingButton = (FloatingActionButton) mActivity.findViewById(R.id.fab);

        assertNotNull(headerView);
        assertNotNull(drawer);
        assertNotNull(mProfilePic);
        assertNotNull(mUserName);
        assertNotNull(mDescription);
        assertNotNull(mProgressBar);
        assertNotNull(mProgressBar);
        assertNotNull(mFloatingButton);




    }
}