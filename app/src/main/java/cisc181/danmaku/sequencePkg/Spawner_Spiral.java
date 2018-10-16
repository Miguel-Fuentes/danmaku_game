package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;

import cisc181.danmaku.BulletMaker;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 12/3/2017.
 */

public class Spawner_Spiral extends Spawner {
    private double x;
    private double y;
    private double r;
    private double V;
    private double A;
    private double dA;
    private int count;
    private double A_dif;

    public Spawner_Spiral(double X, double Y, double R, double Velocity, double Starting_Angle, double Angle_Move, double myCount){
        x = X;
        y = Y;
        r = R;
        V = Velocity;
        A = Starting_Angle;
        dA = Angle_Move;
        count = (int) myCount;
        A_dif = (360/myCount);
    }
    public Step copy(){
        return new Spawner_Spiral(x,y,r,V,A,dA,count);
    }

    public ArrayList<Bullet> newBullets(){
        ArrayList<Bullet> result = new ArrayList<>();
        for(int i = 0; i < count; i++){
            result.add(BulletMaker.makeShotTypeStraight(x,y,r,V,A + A_dif*i));
        }
        A += dA;
        return result;
    }
}
