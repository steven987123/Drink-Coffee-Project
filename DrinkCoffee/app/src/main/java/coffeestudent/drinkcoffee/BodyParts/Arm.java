package coffeestudent.drinkcoffee.BodyParts;

/**
 * Created by Steven on 5/28/2017.
 */

public abstract class Arm {
    //this are in pixel coords
    private int [] joint1;
    private int [] joint2;
    private double length;

    public Arm(){

    }

    public Arm(int x1, int y1, int x2, int y2){
        joint1 = new int[2];
        joint2 = new int[2];
        joint1[0] = x1;
        joint1[1] = y1;
        joint2[0] = x2;
        joint2[1] = y2;
        length = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    public Arm(int x1, int y1, int x2, int y2, double length){
        joint1 = new int[2];
        joint2 = new int[2];
        joint1[0] = x1;
        joint1[1] = y1;
        joint2[0] = x2;
        joint2[1] = y2;
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
            i = M * (y-b)/Math.sqrt((b-y)*(b-y) + (a-x)*(a-x)) + a;
            j = M * (x-a)/Math.sqrt((b-y)*(b-y) + (a-x)*(a-x)) + b;
            ij[0] = (int) Math.round(i);
            ij[1] = (int) Math.round(j);
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
        i = (num1 + Math.sqrt(num2)) / denom;

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
        j = (num1-a*Math.sqrt(num2) + x*Math.sqrt(num3))/denom;

        ij[0] = (int) Math.round(i);
        ij[1] = (int) Math.round(j);

        return ij;
    }

}
