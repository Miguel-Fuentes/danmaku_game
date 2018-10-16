package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;

import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 11/29/2017.
 */

public abstract class Step {
    public Step(){

    }

    public abstract Step copy();
    public abstract int nextStep(int currStep);
    public abstract boolean resume();
    public abstract boolean terminate();
    public abstract ArrayList<Bullet> newBullets();
}
