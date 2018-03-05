package com.example.craft1k.seft06.Application;

/**
 * Created by Lenovo on 2/27/2018.
 */

import android.app.Application;

import com.example.craft1k.seft06.Model.Participant;
import com.example.craft1k.seft06.Model.Trail;
import com.example.craft1k.seft06.Model.TrailManager;


/**
 * Created by swarna on 9/8/16.
 */
public class App extends Application {

    public static final TrailManager trailManager = new TrailManager();
    public static final Participant participant =new Participant("Manh Pham","001","Sample Participant","https://avatars2.githubusercontent.com/u/5423719?s=400&v=4");



    public  App() {

    }

    @Override public final void onCreate() {
        super.onCreate();



        // Setup sample Trail
        // TODO (1)


    }


}
