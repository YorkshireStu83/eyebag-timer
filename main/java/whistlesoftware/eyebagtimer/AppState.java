package whistlesoftware.eyebagtimer;

import android.content.Context;
import android.content.SharedPreferences;

class AppState {

    // Static variables can be accessed from inside and outside the thread
    private static volatile boolean mThreadRunning = true;
    private static volatile boolean mClockRunning = false;
    private static volatile boolean mTitleScreen = true;
    private static volatile boolean mPaused = false;

    private long mTotalMilliseconds;
    private long mStartTime;
    private long mElapsedTime;

    private SharedPreferences.Editor mEditor;

    // Constructor
    AppState (Context context) {

        // Get the current time setting. Default is 240000 (four minutes)
        SharedPreferences prefs;
        // Read from or create file called TimeSetting
        prefs = context.getSharedPreferences("TimeSetting", Context.MODE_PRIVATE);
        // Initialise the editor - allows editing of the TimeSetting file
        SharedPreferences.Editor editor = prefs.edit();
        // Load time setting from file
        mTotalMilliseconds = prefs.getInt("TimeSetting", 240000);

    }

    void startClock(){
        mStartTime = System.currentTimeMillis();
        mClockRunning = true;
    }

    void stopClock(){
        mClockRunning = false;
    }

    void pauseClock() { mPaused = true; }

    void unPauseClock() {mPaused = false; }

    boolean getPausedState() { return mPaused; }

    boolean getClockState(){
        return mClockRunning;
    }

    void resetClock() {
        mClockRunning = false;
        mPaused = false;
        mStartTime = System.currentTimeMillis();
        mElapsedTime = 0;
    }

    long getTotalTime(){
        return mTotalMilliseconds;
    }

    long getRemainingTime(){
        if (mPaused){
            // If paused, return previous value
            return mElapsedTime;
        }
        else{
            long currentTime = System.currentTimeMillis();
            mElapsedTime = currentTime - mStartTime;        // Get milliseconds elapsed
            return mElapsedTime;          // Return remaining milliseconds
        }
    }

    boolean getThreadState(){
        return mThreadRunning;
    }

    void showTitleScreen(){
        mTitleScreen = true;
    }

    void hideTitleScreen(){
        mTitleScreen = false;
    }

    boolean getTitleScreenState(){
        return mTitleScreen;
    }

    void quit(){
        mClockRunning = false;
        mThreadRunning = false;
    }
}
