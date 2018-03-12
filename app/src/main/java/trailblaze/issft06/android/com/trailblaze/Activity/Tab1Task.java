package trailblaze.issft06.android.com.trailblaze.Activity;

/**
 * Created by nhatdx on 13/3/18.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import trailblaze.issft06.android.com.trailblaze.R;

public class Tab1_Task extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_task, container, false);

        return rootView;
    }

}
