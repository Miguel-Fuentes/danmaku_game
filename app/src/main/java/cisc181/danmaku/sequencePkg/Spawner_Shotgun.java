package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;
import java.util.Random;

import cisc181.danmaku.BulletMaker;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 12/1/2017.
 */

public class Spawner_Shotgun extends Spawner {
    private double X;
    private double Y;
    private double R;
    private double min_V;
    private double V_dif;
    private double min_A;
    private double A_dif;
    private int quantity;
    private Random rand;

    public Spawner_Shotgun(double x, double y, double r, double minV, double maxV, double minA, double maxA, int Q){
        rand = new Random();
        X = x;
        Y = y;
        R = r;
        min_V = minV;
        V_dif = maxV - minV;
        min_A = minA;
        A_dif = maxA - minA;
        quantity = Q;
    }
    public Step copy(){
        return new Spawner_Shotgun(X,Y,R,min_V,min_V + V_dif,min_A,min_A+A_dif,quantity);
    }
    public ArrayList<Bullet> newBullets(){
        ArrayList<Bullet> result = new ArrayList<>();
        double velocity;
        double angle;
        for(int i = 0; i < quantity; i++){
            velocity = min_V + V_dif*rand.nextDouble();
            angle = min_A + A_dif*rand.nextDouble();
            result.add(BulletMaker.makeShotTypeStraight(X,Y,R,velocity,angle));
        }
        return result;
    }
}
