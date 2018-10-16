package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;

import cisc181.danmaku.BulletMaker;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 12/1/2017.
 */

public class Spawner_Sniper extends Spawner {
    private double X;
    private double Y;
    private double R;
    private double V;

    public Spawner_Sniper(double x, double y, double r, double v){
        X = x;
        Y = y;
        R = r;
        V = v;
    }
    public Step copy(){
        return new Spawner_Sniper(X,Y,R,V);
    }
    public ArrayList<Bullet> newBullets(){
        ArrayList<Bullet> result = new ArrayList<>();
        result.add(BulletMaker.makeShotTypeTargetedA(X,Y,R,V));
        return result;
    }
}
