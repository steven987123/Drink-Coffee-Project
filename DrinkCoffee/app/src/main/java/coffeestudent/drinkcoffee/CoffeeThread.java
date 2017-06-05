package coffeestudent.drinkcoffee;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by huynh on 6/4/2017.
 */

public class CoffeeThread extends Thread {
    private Context context;
    private CoffeeView coffeeView;
    private CoffeeGame coffeeGame;

    public CoffeeThread(Context context, CoffeeView view, CoffeeGame game){
        this.context = context;
        this.coffeeView = view;
        this.coffeeGame = game;
    }

    @Override
    public void run() {
        SurfaceHolder holder;
        Canvas canvas;
        while(coffeeGame.isGameRunning()){
            holder = coffeeView.getHolder();
            canvas = holder.lockCanvas();
            if (canvas != null){
                coffeeView.draw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }
        System.out.println("2nd Thread ends!");
    }
}
