package whistlesoftware.eyebagtimer;

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

interface InputObserver {

    void handleInput(MotionEvent event, ArrayList<Rect> Buttons, AppState appState);
}
