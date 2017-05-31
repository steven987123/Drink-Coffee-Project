package coffeestudent.drinkcoffee;

import android.content.Context;
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

    public CoffeeView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        System.out.println("CoffeeView constructor");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        coffeeGame = new CoffeeGame(getWidth(),getHeight());
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //System.out.println("ondraw");
        canvas.drawColor(Color.CYAN);
//        Path path = new Path();
//
//        path.moveTo(getWidth()/2-1,getHeight()/4);
//        path.lineTo(getWidth()*3/4,getHeight()/2);
//        path.lineTo(getWidth()/2-1,getHeight()*3/4);
//        path.lineTo(getWidth()/4,getHeight()/2);
//        path.lineTo(getWidth()/2-1,getHeight()/4);
//
//        Paint p = new Paint();
//        p.setColor(Color.RED);
//        canvas.drawPath(path,p);
        coffeeGame.drawArm(canvas);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //System.out.println("touch event");
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
            //System.out.println("action down");
            coffeeGame.setHandPoint(Math.round(event.getX()),Math.round(event.getY()));
            //return false;
            coffeeGame.updateArm();
            invalidate();
        }
        return true;
    }
}
