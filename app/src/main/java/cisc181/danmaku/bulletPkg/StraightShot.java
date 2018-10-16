package cisc181.danmaku.bulletPkg;

/**
 * Created by Brandon on 11/27/2017.
 */

public class StraightShot extends Bullet{
    protected double dx;
    protected double dy;

    public StraightShot(double x_coord, double y_coord, double radius, double velocity, double angle){
        super(x_coord,y_coord,radius);
        angle = (Math.PI*angle/180.0);
        dx = velocity*Math.cos(angle);
        dy = velocity*Math.sin(angle);
    }

    public StraightShot(double x_coord, double y_coord, double radius, double DX, double DY, boolean dummy){
        super(x_coord,y_coord,radius);
        dx = DX;
        dy = DY;
    }

    public void update(){
        x += dx;
        y += dy;
    }

    public void live(){
        if(Math.abs(x - (0.5)*game_width) > game_width){
            alive = false;
        }
        else if(Math.abs(y - (0.5)*game_height) > game_height){
            alive = false;
        }
    }
}
