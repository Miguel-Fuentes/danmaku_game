package cisc181.danmaku.sequencePkg;

/**
 * Created by Brandon on 12/1/2017.
 */

public abstract class Spawner extends Step{
    public int nextStep(int currStep){
        return (currStep + 1);
    }
    public boolean resume(){
        return true;
    }
    public boolean terminate(){
        return false;
    }
}
