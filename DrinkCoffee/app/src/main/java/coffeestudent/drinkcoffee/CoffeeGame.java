package coffeestudent.drinkcoffee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.SurfaceView;

import coffeestudent.drinkcoffee.BodyParts.Arm;
import coffeestudent.drinkcoffee.BodyParts.Forearm;
import coffeestudent.drinkcoffee.BodyParts.UpperArm;

/**
 * Created by Steven on 5/28/2017.
 */

public class CoffeeGame {
    private final int SCREENWIDTH;
    private final int SCREENHEIGHT;
    private UpperArm upperArm;
    private Forearm forearm;
    private int [] point;
    private CoffeeSounds coffeeSounds;


    public CoffeeGame(Context context, int screenwidth, int screenheight){
        SCREENWIDTH = screenwidth;
        SCREENHEIGHT = screenheight;
        upperArm = new UpperArm(screenwidth*8/10,screenheight*6/10,screenwidth/2,screenheight*8/10);
        upperArm.setDimensions(screenwidth/12, screenwidth/12);
        forearm = new Forearm(screenwidth*8/10,screenheight/2,screenwidth*3/10,screenheight*77/100);
        forearm.setDimensions(screenwidth/12, screenwidth/12);

        upperArm.setLength(screenheight/4);
        forearm.setLength(screenheight/4+10);
        Arm.setElbow(upperArm,forearm);
        point = new int [2];
        coffeeSounds = new CoffeeSounds(context);
    }

    //get the point where the user touches
    public void setHandPoint(int x, int y){
        point[0] = x;
        point[1] = y;
    }

    public void updateArm(){
        forearm.setJoint2(point[0],point[1]);
        Arm.setElbow(upperArm,forearm);
    }

    public void drawArm(Canvas canvas){
        upperArm.draw(canvas);
        forearm.draw(canvas);
    }

    public void drawBG(SurfaceView view, Canvas canvas){
        //set stroke
        Paint sp = new Paint();
        sp.setStyle(Paint.Style.STROKE);
        sp.setStrokeWidth(10);
        sp.setColor(Color.BLACK);

        //draw body
        Paint p = new Paint();
        p.setColor(Color.BLUE);

        Path bodyPoly = new Path();
        bodyPoly.moveTo(canvas.getWidth()*7/10, canvas.getHeight()*5/10);
        bodyPoly.lineTo(canvas.getWidth()*92/100, canvas.getHeight()*5/10);
        bodyPoly.lineTo(canvas.getWidth(), canvas.getHeight());
        bodyPoly.lineTo(canvas.getWidth()*6/10,canvas.getHeight());
        bodyPoly.lineTo(canvas.getWidth()*7/10, canvas.getHeight()*5/10);

        canvas.drawPath(bodyPoly, p);
        canvas.drawPath(bodyPoly, sp);

        //draw head
        p.setColor(0xFFFFDDA5);
        canvas.drawCircle(canvas.getWidth()*8/10, canvas.getHeight()*37/100,
                canvas.getHeight()*3/20, p);
        canvas.drawCircle(canvas.getWidth()*8/10, canvas.getHeight()*37/100,
                canvas.getHeight()*3/20, sp);

        //draw coffee machine
        Rect rect = new Rect();
        //rect.set(0,canvas.getHeight()*55/100, canvas.getWidth()*3/10, canvas.getHeight()*95/100);
        Bitmap bitmap  = BitmapFactory.decodeResource(view.getResources(), R.drawable.coffeemachine);
        CoffeeView.setRectWithDefaultRatioWithPixelCoord(rect, bitmap, -70, canvas.getHeight()*5/10,
                canvas.getWidth()*4/10, canvas.getHeight()*44/100);
        canvas.drawBitmap(bitmap, null, rect, null);

        //draw coffee table
        rect.set(0, rect.bottom, canvas.getWidth()*3/10, canvas.getHeight());
        p.setColor(0xFFad181e);
        canvas.drawRect(rect, p);
        canvas.drawRect(rect, sp);
    }

    public void drawHand(Canvas canvas){
        //set stroke
        Paint sp = new Paint();
        sp.setStyle(Paint.Style.STROKE);
        sp.setStrokeWidth(10);
        sp.setColor(Color.BLACK);

        Paint p = new Paint();
        p.setColor(0xFFFFDDA5);
        canvas.drawCircle(forearm.getJoint2()[0], forearm.getJoint2()[1], canvas.getWidth()/20, p);
        canvas.drawCircle(forearm.getJoint2()[0], forearm.getJoint2()[1], canvas.getWidth()/20, sp);
    }

    public void startSoundtrack(){
        coffeeSounds.playSoundtrack();
    }

    public void stopAllSoundtrack(){
        coffeeSounds.stopAndReleaseAll();
    }
}
