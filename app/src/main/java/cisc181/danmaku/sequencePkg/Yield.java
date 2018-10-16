package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 11/30/2017.
 */

public class Yield extends Step{
    public Step copy(){
        return new Yield();
    }
    public int nextStep(int currStep){
        return currStep + 1;
    }
    public boolean resume(){
        return false;
    }
    public boolean terminate(){
        return false;
    }
    public ArrayList<Bullet> newBullets(){
        return new ArrayList<>();
    }
}