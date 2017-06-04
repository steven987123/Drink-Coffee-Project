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
    }

    public void playSoundtrack(){
        soundtrack.setLooping(true);
        soundtrack.start();
    }

    public void stopAndReleaseAll(){
        soundtrack.stop();
        soundtrack.release();
    }
}
