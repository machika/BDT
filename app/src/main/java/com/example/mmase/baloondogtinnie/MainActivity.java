package com.example.mmase.baloondogtinnie;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;

/**
 * Created by mmase on 2016/01/12.
 */
public class MainActivity extends Activity {

    private SurfaceView mMainView;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(new MainView(this, null));
        //setContentView(R.layout.activity_main);
        //mMainView = (SurfaceView)findViewById(R.id.MainView);
    }


}
