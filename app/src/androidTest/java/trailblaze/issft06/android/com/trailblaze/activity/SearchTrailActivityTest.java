package trailblaze.issft06.android.com.trailblaze.activity;

/**
 * Created by craft1k on 24-03-2018.
 */

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchTrailActivityTest {
    @Rule
    public ActivityTestRule<SearchTrailActivity> mActivityTestRule = new ActivityTestRule<>(SearchTrailActivity.class);

    private SearchTrailActivity mActivity = null;

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

        View mSearchText = mActivity.findViewById(R.id.search_trail);
        View mSearchTrailButton = mActivity.findViewById(R.id.search_trail_button);
        View mTrailName = mActivity.findViewById(R.id.trail_result_name);
        View mTrailDescription = mActivity.findViewById(R.id.trail_result_description);
        View mResult = mActivity.findViewById(R.id.search_result);

        View mError = mActivity.findViewById(R.id.search_trail_error_msg);

        assertNotNull(mSearchText);
        assertNotNull(mSearchTrailButton);
        assertNotNull(mTrailName);
        assertNotNull(mTrailDescription);
        assertNotNull(mResult);
        assertNotNull(mError);
    }
}