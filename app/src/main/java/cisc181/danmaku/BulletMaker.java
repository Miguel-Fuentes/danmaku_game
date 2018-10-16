package cisc181.danmaku;

import cisc181.danmaku.bulletPkg.*;

/**
 * Created by Brandon on 11/28/2017.
 */

public class BulletMaker {
    private static double playerX;
    private static double playerY;

    private static int game_width;
    private static int game_height;

    public static void setPlayer(double X, double Y){
        playerX = X;
        playerY = Y;
    }
    public static void setGameParameters(int width, int height){
        game_width = width;
        game_height = height;
    }

    public static Bullet makeShotTypeStraight(double x_coord, double y_coord, double radius, double velocity, double angle){
        return new StraightShot(x_coord,y_coord,radius,velocity,angle);
    }
    public static Bullet makeShotTypeStraightB(double x_coord, double y_coord, double radius, double dx, double dy){
        return new StraightShot(x_coord,y_coord,radius,dx,dy,true);
    }
    public static Bullet makeShotTypeReflectA(double x, double y, double r, double v, double a){
            return new ReflectiveShot(x,y,r,v,a,true,false,false);
    }
    public static Bullet makeShotTypeReflectB(double x, double y, double r, double v, double a){
        return new ReflectiveShot(x,y,r,v,a,true,true,true);
    }
    public static Bullet makeShotTypeReflectC(double x, double y, double r, double dx, double dy, double limit){
        return new ReflectiveShot(x,y,r,dx,dy,limit);
    }
    public static Bullet makeShotTypeCurved(double x,double y,double r,double A, double dD, double dA){
        return new CurvedShot(x,y,r,A,dD,dA);
    }
    public static Bullet makeShotTypeTargetedA(double x, double y, double r, double v){
        return new TargetedShot(x,y,r,v,playerX,playerY);
    }
    public static Bullet makeShotTypeAccelerated(double x, double y, double r, double a, double v, double maxV, double minV, double A){
        return new GravitationalShot(x,y,r,a,v,maxV,minV,A);
    }
}
