package whistlesoftware.eyebagtimer;

import android.view.MotionEvent;
import android.graphics.Rect;

import java.util.ArrayList;

class UIController implements InputObserver {

    @Override
    public void handleInput(MotionEvent event, ArrayList<Rect> buttons, AppState appState){

        // New code
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {

            if (buttons.get(0).contains(x, y)) {
                // Start/pause
                if (!appState.getClockState()) {
                    appState.startClock();
                } else if (appState.getClockState() && !appState.getPausedState()) {
                    appState.pauseClock();
                }
                else if (appState.getClockState() && appState.getPausedState()){
                    appState.unPauseClock();
                }
            } else if (buttons.get(1).contains(x, y)) {
                // Reset if clock not running
                if (!appState.getClockState()){
                    appState.resetClock();
                }

            } else if (buttons.get(2).contains(x, y)) {
                // Change Time
            } else if (buttons.get(3).contains(x, y)) {
                // Quit app
                appState.quit();
                System.exit(0);
            }
        }
    }
}
