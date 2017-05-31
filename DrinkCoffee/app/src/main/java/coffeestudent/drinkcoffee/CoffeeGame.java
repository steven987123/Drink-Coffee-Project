package coffeestudent.drinkcoffee;

import android.graphics.Canvas;

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

    public CoffeeGame(int screenwidth, int screenheight){
        SCREENWIDTH = screenwidth;
        SCREENHEIGHT = screenheight;
        upperArm = new UpperArm(screenwidth*8/10,screenheight*3/10,screenwidth/2,screenheight/2);
        upperArm.setDimensions(screenwidth/20, screenwidth/20);
        forearm = new Forearm(screenwidth/2,screenheight/2,screenwidth/10,screenheight/2);
        forearm.setDimensions(screenwidth/20, screenwidth/20);
        Arm.setElbow(upperArm,forearm);
//        forearm.setJoint2(4,4);
//        Arm.setElbow(upperArm,forearm);
        point = new int [2];
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
}
