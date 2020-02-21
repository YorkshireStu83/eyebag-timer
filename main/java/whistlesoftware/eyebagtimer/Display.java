package whistlesoftware.eyebagtimer;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;


class Display {

    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mScreenPadding;
    private int mButtonHeight;
    private int mButtonWidth;

    ArrayList<Rect> buttons;
    private int START_PAUSE = 0;
    private int RESET = 1;
    private int CHANGETIME = 2;
    private int EXIT = 3;

    // Constructor
    Display(Point size) {

        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.y / 20;
        mScreenPadding = size.x / 45;

        PrepareControls();
    }

    private void PrepareControls(){

        mButtonHeight = mScreenHeight / 12;
        mButtonWidth = mScreenWidth - (2 * mScreenPadding);

        Rect start_pause = new Rect(mScreenPadding,
                mScreenHeight - (5 * mButtonHeight) - (4 * mScreenPadding),
                mScreenPadding + mButtonWidth,
                mScreenHeight - (4 *mButtonHeight) - (4 * mScreenPadding)
                );

        Rect reset = new Rect(mScreenPadding,
                mScreenHeight - (4 * mButtonHeight) - (3 * mScreenPadding),
                mScreenPadding + mButtonWidth,
                mScreenHeight - (3 * mButtonHeight) - (3 * mScreenPadding));

        Rect changeTime = new Rect(mScreenPadding,
                mScreenHeight - (3 * mButtonHeight) -  (2 * mScreenPadding),
                mScreenPadding + mButtonWidth,
                mScreenHeight - (2 * mButtonHeight) - (2 * mScreenPadding));

        Rect exit = new Rect(mScreenPadding,
                mScreenHeight - (2 * mButtonHeight) - mScreenPadding,
                mScreenPadding + mButtonWidth,
                mScreenHeight - mButtonHeight - mScreenPadding);

        buttons = new ArrayList<>();
        buttons.add(START_PAUSE, start_pause);
        buttons.add(RESET, reset);
        buttons.add(CHANGETIME, changeTime);
        buttons.add(EXIT, exit);
    }

    void draw(Canvas canvas, Paint paint, AppState appState, String minutes, String seconds){

        float textSize = paint.getTextSize();

        // Background
        canvas.drawColor(Color.argb(255, 200, 200, 200));

        // Title screen
        if (appState.getTitleScreenState()){
            paint.setTextSize(textSize * 2);
            canvas.drawText("Eyebag", mScreenWidth / 5, mScreenHeight / 4, paint);
            canvas.drawText("Timer", mScreenWidth / 4, mScreenHeight / 2, paint);
            paint.setTextSize(textSize / 2);
            canvas.drawText("© 2019 Whistle Software", 20, mScreenHeight - (textSize),
                    paint);
        } else {
            // Buttons display
            paint.setColor(Color.argb(255, 178, 178, 178));

            for (Rect r : buttons){
                canvas.drawRect(r.left, r.top, r.right, r.bottom, paint);
            }

            // Reset color
            paint.setColor(Color.argb(255, 0,0,0));

            if (appState.getClockState()){
                canvas.drawText("Pause", mScreenWidth * 0.385f, mScreenHeight * 0.595f, paint);
            } else {
                canvas.drawText("Start", mScreenWidth * 0.37f, mScreenHeight * 0.595f, paint);
            }
            canvas.drawText("Reset", mScreenWidth * 0.37f, mScreenHeight * 0.69f, paint);
            canvas.drawText("Set Time", mScreenWidth * 0.3f, mScreenHeight * 0.788f, paint);
            canvas.drawText("Exit", mScreenWidth * 0.42f, mScreenHeight * 0.882f, paint);

            // Status display
            if (!appState.getClockState()) {
                canvas.drawText("Tap to start!", 0.25f * mScreenWidth, 0.2f * mScreenHeight, paint);
            } else {
                canvas.drawText("Running", 0.25f * mScreenWidth, 0.2f * mScreenHeight, paint);
            }

            // Draw timing text
            canvas.drawText("Time", mScreenWidth / 4, 0.2f * mScreenHeight +
                            (2 * textSize), paint);
            canvas.drawText("Remaining:", mScreenWidth / 4, 0.2f * mScreenHeight +
                            (3 * textSize), paint);
            canvas.drawText(minutes + ":" + seconds, mScreenWidth / 4,
                    0.2f * mScreenHeight + (4 * textSize), paint);
        }
    }

    ArrayList<Rect> getButtons(){
        return buttons;
    }

}
