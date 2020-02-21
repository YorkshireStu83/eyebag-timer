package whistlesoftware.eyebagtimer;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Paint;

class AppEngine extends SurfaceView implements Runnable {

    // Timing objects
    private long mTotalMilliseconds;
    private long mHalfwayMilliseconds;
    private long mElapsedTime;

    // Drawing objects
    private int textSize;
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    private Point mSize;
    private Display mDisplay;

    // Sound object
    private SoundEngine mSoundPool;

    // Thread object
    private Thread mAppThread = null;

    // Controller objects
    UIController mUIController;
    private AppState mAppState;

    //Constructor
    public AppEngine(Context context, Point size){
        super(context);

        // Set timing variables
        mTotalMilliseconds =  240000;        // Four minutes
        mHalfwayMilliseconds = mTotalMilliseconds / 2;

        // Initialise all drawing objects
        mOurHolder = getHolder();
        mPaint = new Paint();
        mSize = size;
        mDisplay = new Display(mSize);

        // Set flags
        mAppState = new AppState(context);
        mAppState.showTitleScreen();
        mAppState.stopClock();

        // Initialise text size
        textSize = mSize.x / 10;

        mSoundPool = new SoundEngine(context);
        mUIController = new UIController();

    }

    public void run(){
        while (mAppState.getThreadState()){
            if (mAppState.getClockState()){
                    mElapsedTime = mAppState.getRemainingTime();
                if (mElapsedTime >= mHalfwayMilliseconds && mElapsedTime <= mHalfwayMilliseconds + 50){
                    mSoundPool.playHalfwaySound();
                }
                else if (mElapsedTime >= mTotalMilliseconds){
                    mSoundPool.playFinishedSound();
                    // Stop clock
                    mAppState.stopClock();
                    mAppState.resetClock();
                }
            }
            prepDrawing();
            if (!mAppState.getThreadState()){
                pause();
            }
        }
    }

    void prepDrawing(){

        if (mOurHolder.getSurface().isValid()){
             // Lock & prepare the canvas ready to draw
            mCanvas = mOurHolder.lockCanvas();
            mPaint.setTextSize(textSize);

            // If clock running, measure time elapsed
            long timeRemaining;
            Log.d("Elapsed TIME", "" + mElapsedTime);
            if (mAppState.getClockState()) {
                timeRemaining = (mAppState.getTotalTime() - mAppState.getRemainingTime()) / 1000;
            }
            else {
                    timeRemaining = (mAppState.getTotalTime() / 1000);
            }

            // Convert to minutes and seconds as 2-digit strings
            int wholeMinutesRemaining = (int) timeRemaining / 60;
            int partMinuteRemaining = (int) timeRemaining % 60;

            String minutesString = String.format("%02d", wholeMinutesRemaining);
            String secondsString = String.format("%02d", partMinuteRemaining);

            // Send to display to draw
            mDisplay.draw(mCanvas, mPaint, mAppState, minutesString, secondsString);

            mOurHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
            if (mAppState.getTitleScreenState()){
                mAppState.hideTitleScreen();
            }
            else if (!mAppState.getTitleScreenState()){
                mUIController.handleInput(motionEvent, mDisplay.buttons, mAppState);
            }
        }

        return true;
    }

    // Method call when app is started. Initialises
    // and starts the thread
    void resume(){
        mAppThread = new Thread(this);
        mAppThread.start();
    }

    // Method call when app is exited.
    // Stops thread
    void pause(){
        try{
            mAppThread.join();
        } catch (InterruptedException e){
            // Error - no log code
        }
    }
}
