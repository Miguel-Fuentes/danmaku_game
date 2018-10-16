package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;

import cisc181.danmaku.BulletMaker;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 12/1/2017.
 */

public class Spawner_Arc extends Spawner {
    private double X;
    private double Y;
    private double R;
    private int Shots;
    private int Layers;
    private double Angle;
    private double dA;
    private double Velocity;
    private double dV;

    public Spawner_Arc(double x, double y, double r, int shots, int layers, double angleA, double angleB,double vMin, double vMax){
        X = x;
        Y = y;
        R = r;
        Shots = shots;
        Layers = layers;
        Angle = angleA;
        dA = (angleB - angleA)/Shots;
        Velocity = vMax;
        dV = (vMax - vMin)/Layers;
    }
    public Step copy(){
        return new Spawner_Arc(X,Y,R,Shots,Layers, Angle, Angle + Shots*dA,Velocity - dV*Layers,Velocity);
    }
    public ArrayList<Bullet> newBullets(){
        ArrayList<Bullet> result = new ArrayList<>();
        for(int i = 0; i < Shots; i++ ) {
            for(int j = 0; j < Layers; j++){
                result.add(BulletMaker.makeShotTypeStraight(X,Y,R,Velocity - j*dV,Angle + i*dA));
            }
        }
        return result;
    }
}
