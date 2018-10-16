package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;

import cisc181.danmaku.BulletMaker;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 11/29/2017.
 */

public class Spawner_River extends Spawner{
    private double x_center;
    private double x;
    private double y;
    private double r;
    private double amplitudeX;
    private double freqX;
    private double biasX;
    private double velocity;
    private double time;

    public Spawner_River(double X, double Y, double R, double A, double F, double B, double V){
        time = 0;
        x_center = X;
        y = Y;
        r = R;
        amplitudeX = A;
        freqX = F;
        biasX = B;
        velocity = V;
    }
    public Step copy(){
        return new Spawner_River(x_center,y,r,amplitudeX,freqX,biasX,velocity);
    }

    public ArrayList<Bullet> newBullets(){
        x = x_center + amplitudeX*Math.sin((Math.PI)*(freqX*time + biasX)/180);
        time++;
        ArrayList<Bullet> result = new ArrayList<>();
        result.add(BulletMaker.makeShotTypeStraight(x,y,r,velocity,270));
        return result;
    }
}
