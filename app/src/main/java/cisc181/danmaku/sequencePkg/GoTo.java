package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 11/30/2017.
 */

public class GoTo extends Step {
    private int i;
    private int limit;
    private int returnStep;

    public GoTo(int maxRepeats, int returnStepValue){
        limit = maxRepeats;
        returnStep = returnStepValue;
        i = 0;
    }
    public Step copy(){
        return new GoTo(limit,returnStep);
    }
    public int nextStep(int currStep) {
        if(limit == 0){
            return returnStep;
        }
        else if(i >= limit){
            i = 0;
            return (currStep + 1);
        }
        else{
            i++;
            return returnStep;
        }
    }
    public boolean resume() {
        return true;
    }
    public boolean terminate() { return false;}
    public ArrayList<Bullet> newBullets(){ return new ArrayList<>();}
}
