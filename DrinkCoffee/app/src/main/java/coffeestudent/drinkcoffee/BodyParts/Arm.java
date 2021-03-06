package coffeestudent.drinkcoffee.BodyParts;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Steven on 5/28/2017.
 */

public abstract class Arm {
    //this are in pixel coords
    private int [] joint1;
    private int [] joint2;
    private double length;
    private Bitmap bitmap;
    private int [] dimensions;

    public Arm(){

    }

    public Arm(int x1, int y1, int x2, int y2){
        joint1 = new int[2];
        joint2 = new int[2];
        dimensions = new int [2];
        joint1[0] = x1;
        joint1[1] = y1;
        joint2[0] = x2;
        joint2[1] = y2;
        length = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    public Arm(int x1, int y1, int x2, int y2, double length){
        joint1 = new int[2];
        joint2 = new int[2];
        dimensions = new int [2];
        joint1[0] = x1;
        joint1[1] = y1;
        joint2[0] = x2;
        joint2[1] = y2;
        this.length = length;
    }

    public void setDimensions(int x, int y){
        dimensions[0] = x;
        dimensions[1] = y;
    }

    public void setLength(int length){
        this.length = length;
    }



    public int [] getJoint1(){
        return joint1.clone();
    }

    public int [] setJoint1(int x, int y){
        joint1[0] = x;
        joint1[1] = y;
        return joint1.clone();
    }

    public int [] getJoint2(){
        return joint2.clone();
    }

    public int [] setJoint2(int x, int y){
        joint2[0] = x;
        joint2[1] = y;
        return joint2.clone();
    }

    public double setLength(double l){
        length=l;
        return length;
    }

    public double getLength(){
        return length;
    }

    protected void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;

    }

    public void draw(Canvas canvas){
        double theta = Math.atan2((joint1[0]-joint2[0]),(joint2[1]-joint1[1]));

        //calculate new coord to draw on rotated canvas
        double r1 = Math.sqrt((joint1[0])*joint1[0] + joint1[1]*joint1[1]);
        double newAlpha1 = Math.atan2(joint1[1],joint1[0]) - theta;
        int x1 = (int) Math.round(r1*Math.cos(newAlpha1));
        int y1 = (int) Math.round(r1*Math.sin(newAlpha1));

        double r2 = Math.sqrt(joint2[0]*joint2[0] + joint2[1]*joint2[1]);
        double newAlpha2 = Math.atan2(joint2[1],joint2[0])-theta;
        int x2 = (int) Math.round(r2*Math.cos(newAlpha2));
        int y2 = (int) Math.round(r2*Math.sin(newAlpha2));

        //assert(x1==x2);

        int coordLeft = x1-dimensions[0]/2;
        int coordRight = x1+dimensions[0]/2;
        int coordTop = y1-dimensions[1]/2;
        int coordBottom = y2+dimensions[1]/2;
        Rect rect = new Rect(coordLeft,coordTop,coordRight,coordBottom);

        //begin rotating
        canvas.save();
        canvas.rotate((float)(theta*180/Math.PI));
        //canvas.drawBitmap(bitmap, null, rect, null);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        //canvas.drawRect(rect, p);
        canvas.drawRoundRect(new RectF(rect), canvas.getWidth()/24, canvas.getWidth()/24, p);

        Paint sp = new Paint();
        sp.setStyle(Paint.Style.STROKE);
        sp.setStrokeWidth(10);
        sp.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(rect),canvas.getWidth()/24, canvas.getWidth()/24,sp);
        canvas.restore();
    }

    public static int [] setElbow(UpperArm upperArm, Forearm foreArm){
        double a = upperArm.getJoint1()[0], b = upperArm.getJoint1()[1]; //shoulder's coords
        double M = upperArm.getLength();//length of upperarm
        double x = foreArm.getJoint2()[0], y = foreArm.getJoint2()[1]; //hand's coord
        double L = foreArm.getLength();
        //upperjoint.joint2 = forearm.joint1 is the elbow
        double i, j; //elbow's coord, what we want to findx
        int [] ij = new int[2];

        //distance between a,b and x,y
        double distanceBWCoords = Math.sqrt((a-x)*(a-x) + (b-y)*(b-y));

        //if distance is within range of arm
        if (distanceBWCoords < M+L && distanceBWCoords > Math.abs(M-L)){
            ij = setElbowAcceptableCoords(a,b,x,y,M,L);
        }
        else{
            //(i,j) is in same direction as (x,y) from (a,b)
            i = M * (x-a)/Math.sqrt((b-y)*(b-y) + (a-x)*(a-x)) + a;
            j = M * (y-b)/Math.sqrt((b-y)*(b-y) + (a-x)*(a-x)) + b;
            ij[0] = (int) Math.round(i);
            ij[1] = (int) Math.round(j);
            //keep forearm length
            if (distanceBWCoords > Math.abs(M-L)){
                x = (M+L)/M*(i-a)+a;
                y = (M+L)/M*(j-b)+b;
            }
            else {
                x = (M - L) / M * (i - a) + a;
                y = (M-L)/M * (j-b) + b;
            }
            foreArm.setJoint2((int)Math.round(x),(int)Math.round(y));
        }

        upperArm.setJoint2(ij[0],ij[1]);
        foreArm.setJoint1(ij[0],ij[1]);

        return ij;
    }

    private static int[] setElbowAcceptableCoords(double a, double b, double x, double y,
                                                  double M, double L){
        int [] ij = new int[2];
        double i, j;

        //break up calculations
        double num1 = Math.pow(a,3) + a*b*b + a*L*L - a*M*M - a*a*x + b*b*x - L*L*x + M*M*x - a*x*x
                + x*x*x -2*a*b*y - 2*b*x*y + a*y*y + x*y*y;
        double num2 = -(b-y)*(b-y)*(Math.pow(a,4) + Math.pow(b,4) + Math.pow(L,4) - 2*L*L*M*M +
                Math.pow(M,4) - 4*a*a*a*x - 2*L*L*x*x - 2*M*M*x*x + Math.pow(x,4) - 4*b*b*b*y -
                2*L*L*y*y - 2*M*M*y*y + 2*x*x*y*y + Math.pow(y,4) - 2*b*b*(L*L+M*M-x*x-3*y*y) +
                4*b*y*(L*L+M*M-x*x-y*y) - 4*a*x*(b*b-L*L-M*M+x*x-2*b*y+y*y) +
                2*a*a*(b*b-L*L-M*M+3*x*x-2*b*y+y*y));
        double denom = 2*(a*a + b*b -2*a*x + x*x - 2*b*y + y*y);
        if (y>b)
            i = (num1 + Math.sqrt(num2)) / denom;
        else
            i = (num1 - Math.sqrt(num2)) / denom;

        if (b-y == 0)
            y = b+0.0001;//prevents 1/0
        num1 = a*a*b*b + Math.pow(b,4) + b*b*L*L - b*b*M*M - 2*a*b*b*x + b*b*x*x - 2*b*b*b*y -
                2*b*L*L*y + 2*b*M*M*y - a*a*y*y + L*L*y*y - M*M*y*y + 2*a*x*y*y -
                x*x*y*y + 2*b*y*y*y - Math.pow(y,4);
        num2 = -(b - y)*(b-y)*(a*a*a*a + b*b*b*b + L*L*L*L - 2*L*L*M*M + M*M*M*M - 4*a*a*a*x -
                2*L*L*x*x - 2*M*M*x*x + x*x*x*x - 4*b*b*b*y - 2*L*L*y*y - 2*M*M*y*y + 2*x*x*y*y +
                y*y*y*y - 2*b*b*(L*L + M*M - x*x - 3*y*y) + 4*b*y*(L*L + M*M - x*x - y*y) -
                4*a*x*(b*b - L*L - M*M + x*x - 2*b*y + y*y) +
                2*a*a*(b*b - L*L - M*M + 3*x*x - 2*b*y + y*y));
        double num3;
        num3 = (-(b - y)*(b - y)*(a*a*a*a + b*b*b*b + L*L*L*L - 2*L*L*M*M + M*M*M*M - 4*a*a*a*x -
                2*L*L*x*x - 2*M*M*x*x + x*x*x*x - 4*b*b*b*y - 2*L*L*y*y - 2*M*M*y*y +
                2*x*x*y*y + y*y*y*y - 2*b*b*(L*L + M*M - x*x - 3*y*y) +
                4*b*y*(L*L + M*M - x*x - y*y) - 4*a*x*(b*b - L*L - M*M + x*x - 2*b*y + y*y) +
                2*a*a*(b*b - L*L - M*M + 3*x*x - 2*b*y + y*y)));
        denom = 2*(b - y)*(a*a + b*b - 2*a*x + x*x - 2*b*y + y*y);

        if (y>b)
            j = (num1 - a*Math.sqrt(num2) + x*Math.sqrt(num3))/denom;
        else
            j = (num1 + a*Math.sqrt(num2) - x*Math.sqrt(num3))/denom;

        ij[0] = (int) Math.round(i);
        ij[1] = (int) Math.round(j);

        return ij;
    }

}
