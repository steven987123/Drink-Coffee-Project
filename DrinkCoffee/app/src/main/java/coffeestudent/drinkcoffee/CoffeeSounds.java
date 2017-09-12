package coffeestudent.drinkcoffee;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by huynh on 6/4/2017.
 */

public class CoffeeSounds {
    private MediaPlayer soundtrack;
    private MediaPlayer gulp;
    private MediaPlayer pour;

    public CoffeeSounds(Context context){
        soundtrack = MediaPlayer.create(context, R.raw.smooth_as_latte);
        gulp = MediaPlayer.create(context,R.raw.gulp);
        pour = MediaPlayer.create(context,R.raw.pour2);
        //pour.setVolume((float)0.5,(float)0.5);
    }

    public void playSoundtrack(){
        soundtrack.setLooping(true);
        soundtrack.start();
    }

    public void playGulpEffect(){
        if(!gulp.isPlaying()){
            gulp.start();
        }
    }

    public void playPourEffect(){
        if(!pour.isPlaying()){
            pour.start();
        }
    }

    public void stopAndReleaseAll(){
        soundtrack.stop();
        soundtrack.release();
        gulp.stop();
        gulp.release();
        pour.stop();
        pour.release();
    }
}
