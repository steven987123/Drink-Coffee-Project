package coffeestudent.drinkcoffee;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Steven on 9/11/2017.
 */

public class HealthBar {
    private int healthPts;
    public HealthBar() {
        healthPts = 100;
    }

    public int increaseHealthPts(){
        if (healthPts < 100)
            healthPts++;
        return healthPts;
    }

    public int increaseHealthPts(int i){
        if (healthPts <= 100 - i)
            healthPts += i;
        return healthPts;
    }

    public int decreaseHealthPts(){
        if (healthPts > 0){
            healthPts--;
        }
        return healthPts;
    }

    public void drawHealthBar(Canvas canvas){
        Rect rect = new Rect();
        Paint p = new Paint();

        //black border
        rect.set(canvas.getWidth()*11/20, canvas.getHeight()/50, canvas.getWidth()*19/20,canvas.getHeight()*3/50);
        p.setColor(Color.BLACK);
        canvas.drawRect(rect,p);

        // bar
        //color of bar can depend on healthpts
        int barcolor;
        if (healthPts >= 50){
            barcolor = 0xFFFFFF00 - 0x00010000 * (255 * (healthPts-50)/50);
        }
        else
            barcolor = 0xFFFF0000 + 0x00000100 * (255* healthPts/50);
        int thickness = canvas.getHeight()/100;
        int length = canvas.getWidth()*8/20 - thickness*2;
        length = length * healthPts/100;
        rect.set(canvas.getWidth()*11/20 + thickness,canvas.getHeight()*3/100, canvas.getWidth()*11/20
                        + thickness+ length,canvas.getHeight()*5/100);
        p.setColor(barcolor);
        canvas.drawRect(rect, p);

    }
}
