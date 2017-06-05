package coffeestudent.drinkcoffee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

/**
 * Created by Steven on 5/27/2017.
 */

public class CoffeeView extends SurfaceView implements SurfaceHolder.Callback {
    private CoffeeGame coffeeGame;
    private final Context context;

    public CoffeeView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.context = context;
        System.out.println("CoffeeView constructor");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //setWillNotDraw(false);
        coffeeGame = new CoffeeGame(context, this, getWidth(),getHeight());
        coffeeGame.startSoundtrack();
        (new CoffeeThread(context, this, coffeeGame)).start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        coffeeGame.draw(this, canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        coffeeGame.stopAllSoundtrack(); //important to stop playing soundtrack after
                                        //user exits
        coffeeGame.setGameIsOver();
        System.out.println("Bye");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //System.out.println("touch event");
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
            //System.out.println("action down");
            coffeeGame.setHandPoint(Math.round(event.getX()),Math.round(event.getY()));
            coffeeGame.updateArm();
            //coffeeGame.updateArm();
            //return false;
            //invalidate();
        }
        return true;
    }

    public static void setRectWithDefaultRatioWithPixelCoord(Rect rect, Bitmap icon,
                                                             int pixelCoordx, int pixelCoordy,
                                                             int rowSize, int colSize){
        int centerx, centery;
        int coordLeft, coordTop, coordRight, coordBottom;
        float rectAspectRatio, iconAspectRatio;

        rectAspectRatio = colSize/((float) rowSize);
        iconAspectRatio = icon.getHeight()/((float) icon.getWidth());

        centerx = (2*pixelCoordx+rowSize)/2;
        centery = (2*pixelCoordy+colSize)/2;

        if (rectAspectRatio < iconAspectRatio) {
            coordLeft = (int) (centerx - colSize / iconAspectRatio /2);
            coordRight = (int) (centerx + colSize / iconAspectRatio /2);
            coordTop = pixelCoordy;
            coordBottom = pixelCoordy+colSize;
        }
        else if (rectAspectRatio > iconAspectRatio) {
            coordTop = (int) (centery - rowSize*iconAspectRatio/2.0);
            coordBottom = (int) (centery + rowSize*iconAspectRatio/2.0);
            coordLeft = pixelCoordx;
            coordRight = pixelCoordx+rowSize;
        }
        else {
            coordLeft = pixelCoordx;
            coordRight = pixelCoordx+rowSize;
            coordTop = pixelCoordy;
            coordBottom = pixelCoordy+colSize;
        }

        rect.set(coordLeft, coordTop, coordRight, coordBottom);
    }
}
