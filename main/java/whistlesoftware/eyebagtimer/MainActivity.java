package whistlesoftware.eyebagtimer;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Point;
import android.view.Display;

public class MainActivity extends Activity {

    private AppEngine mAppEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the device's screen resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialise clock
        mAppEngine = new AppEngine(this, size);
        setContentView(mAppEngine);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mAppEngine.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mAppEngine.pause();
    }
}