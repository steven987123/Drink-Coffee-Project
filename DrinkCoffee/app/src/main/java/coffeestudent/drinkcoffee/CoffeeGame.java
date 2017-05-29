package coffeestudent.drinkcoffee;

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

    public CoffeeGame(int screenwidth, int screenheight){
        SCREENWIDTH = screenwidth;
        SCREENHEIGHT = screenheight;
        upperArm = new UpperArm(8,2,6,6);
        forearm = new Forearm(6,6,0,4);
        Arm.setElbow(upperArm,forearm);
    }
}
