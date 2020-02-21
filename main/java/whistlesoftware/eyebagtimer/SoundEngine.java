package whistlesoftware.eyebagtimer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

class SoundEngine {

    // Sound objects
    private SoundPool mSoundPool;
    private int mHalfWayPointID;
    private int mFinishedID;

    // Constructor
    SoundEngine(Context context){

        // Initialise the items for the sounds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }

        // Load sounds to RAM
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("HalfWayPointReached.ogg");
            mHalfWayPointID = mSoundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("FinishedReached.ogg");
            mFinishedID = mSoundPool.load(descriptor, 0);
        } catch (IOException e) {
            // Error
        }

    }

    void playHalfwaySound(){
        mSoundPool.play(mHalfWayPointID, 1, 1, 0, 0, 1);
    }

    void playFinishedSound(){
        mSoundPool.play(mFinishedID, 1, 1, 0, 0, 1);
    }

}
