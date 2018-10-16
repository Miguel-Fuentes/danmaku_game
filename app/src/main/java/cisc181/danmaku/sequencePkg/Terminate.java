package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;

import cisc181.danmaku.bulletPkg.Bullet;


/**
 * Created by Brandon on 12/1/2017.
 */

public class Terminate extends Step{
    public Step copy(){
        return new Terminate();
    }
    public int nextStep(int currStep){ return currStep;}
    public boolean resume() { return false;}
    public boolean terminate() { return true;}
    public ArrayList<Bullet> newBullets(){ return new ArrayList<>();}
}
