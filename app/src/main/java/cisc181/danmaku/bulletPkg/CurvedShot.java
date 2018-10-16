package cisc181.danmaku.bulletPkg;

/**
 * Created by Brandon on 11/28/2017.
 */

public class CurvedShot extends Bullet {
    private double distance;
    private double angle;
    private double dD;
    private double dA;
    private double x_spawn;
    private double y_spawn;
    private double max;

    public CurvedShot(double x_init,double y_init,double radius,double angle_init, double d_distance, double d_angle){
        super(x_init,y_init,radius);
        angle = (Math.PI*angle_init)/180.0;
        dD = d_distance;
        dA = (Math.PI*d_angle)/180.0;
        x_spawn = x_init;
        y_spawn = y_init;
        max = Math.max(game_height,game_width);
    }

    public void update(){
        distance += dD;
        angle += dA;
        x = x_spawn + distance*Math.cos(angle);
        y = y_spawn + distance*Math.sin(angle);
    }

    public void live(){
        if(Math.abs(2*x - game_width) > 2*max + game_width){
            alive = false;
        }
        if(Math.abs(2*y - game_height) > 2*max + game_height){
            alive = false;
        }
    }
}
