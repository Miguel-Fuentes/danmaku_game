package cisc181.danmaku.bulletPkg;

/**
 * Created by Brandon on 11/28/2017.
 */

public class TargetedShot extends Bullet{
    private double dx;
    private double dy;

    public TargetedShot(double x_coord, double y_coord, double radius, double velocity, double x_target, double y_target){
        super(x_coord, y_coord, radius);
        double x_distance = x_target - x_coord;
        double y_distance = y_target - y_coord;
        double distance = Math.pow(x_distance,2) + Math.pow(y_distance,2);
        distance = Math.pow(distance,0.5);

        dx = ((velocity*x_distance)/distance);
        dy = ((velocity*y_distance)/distance);
    }

    public void update(){
        x += dx;
        y += dy;
    }

    public void live(){
        if(Math.abs(2*x - game_width) > 2*game_width){
            alive = false;
        }
        if(Math.abs(2*y - game_height) > 2*game_height){
            alive = false;
        }
    }
}
