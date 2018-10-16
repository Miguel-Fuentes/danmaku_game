package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;
import java.util.Random;

import cisc181.danmaku.BulletMaker;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 12/5/2017.
 */

public class Spawner_BounceCircle extends Spawner {
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private double radius;
    private double velocity;
    private int count;
    private int bounce_limit;
    private Random rand;

    private double delta_sin;
    private double delta_cos;

    public Spawner_BounceCircle(double x0, double y0, double x1, double y1, double r, double v, double c, double limit){
        minX = Math.min(x0,x1);
        maxX = x0 + x1 - minX;
        minY = Math.min(y0,y1);
        maxY = y0 + y1 - minY;
        radius = r;
        velocity = v;
        count = (int) c;
        bounce_limit = (int) limit;
        rand = new Random();

        double Angle = ((Math.PI * 2)/c);
        delta_cos = Math.cos(Angle);
        delta_sin = Math.sin(Angle);
    }
    public Spawner_BounceCircle copy(){
        return new Spawner_BounceCircle(minX,minY,maxX,maxY,radius,velocity,count,bounce_limit);
    }
    public ArrayList<Bullet> newBullets(){
        ArrayList<Bullet> result = new ArrayList<>();
        double A = (Math.PI/180.0)*(rand.nextDouble());
        double dx = velocity*Math.cos(A);
        double dy = velocity*Math.sin(A);
        double holder;
        double x = minX + (maxX - minX)*(rand.nextDouble());
        double y = minY + (maxY - minY)*(rand.nextDouble());
        for(int i = 0; i < count; i++){
            result.add(BulletMaker.makeShotTypeReflectC(x,y,radius,dx,dy,bounce_limit));
            holder = dx;
            dx = dx*delta_cos - dy*delta_sin;
            dy = holder*delta_sin + dy*delta_cos;
        }
        return result;
    }
}
