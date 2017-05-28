package coffeestudent.drinkcoffee;

import android.content.Context;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

/**
 * Created by Steven on 5/27/2017.
 */

public class CoffeeView extends SurfaceView implements SurfaceHolder.Callback {
    public CoffeeView(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
