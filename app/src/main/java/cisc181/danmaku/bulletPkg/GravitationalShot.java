package cisc181.danmaku.bulletPkg;

/**
 * Created by Brandon on 11/28/2017.
 */

public class GravitationalShot extends Bullet {
    private double cosA;
    private double sinA;

    private double velocity;
    private double acceleration;

    private double minVelocity;
    private double maxVelocity;

    public GravitationalShot(double x, double y, double r, double a, double startV, double maxV, double minV, double angle){
        super(x,y,r);
        cosA = Math.cos((Math.PI*angle)/180.0);
        sinA = Math.sin((Math.PI*angle)/180.0);

        velocity = startV;
        acceleration = a;

        minVelocity = minV;
        maxVelocity = maxV;
    }

    public void update(){
        velocity += acceleration;
        if(velocity > maxVelocity){
            velocity = maxVelocity;
        }
        else if (velocity < minVelocity){
            velocity = minVelocity;
        }
        x += velocity*cosA;
        y += velocity*sinA;
    }

    public void live(){
        if(Math.abs(2*x - game_width) > game_width){
            alive = false;
        }
        else if(Math.abs(2*y - game_height) > game_height){
            alive = false;
        }
    }
}
