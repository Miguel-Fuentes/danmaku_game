package cisc181.danmaku.bulletPkg;

/**
 * Created by Brandon on 11/27/2017.
 */

public class ReflectiveShot extends Bullet{
    private double dx;
    private double dy;
    private boolean ceil;
    private boolean right;
    private boolean left;
    private int max_bounce;
    private int count;

    public ReflectiveShot(double x_coord, double y_coord, double radius, double velocity, double angle,
                          boolean ceiling, boolean left_wall, boolean right_wall){
        super(x_coord,y_coord,radius);
        angle = (Math.PI*angle/180.0);
        dx = velocity*Math.cos(angle);
        dy = velocity*Math.sin(angle);
        ceil = ceiling;
        right = right_wall;
        left = left_wall;
        max_bounce = 10000;
        count = 0;
    }

    public ReflectiveShot(double x_coord, double y_coord, double radius, double x_velocity, double y_velocity, double limit){
        super(x_coord,y_coord,radius);
        dx = x_velocity;
        dy = y_velocity;
        max_bounce = (int)limit;
        ceil = true;
        right = true;
        left = true;
        count = 0;
    }

    public void update(){
        if(count < max_bounce) {
            bounceCeiling();
            bounceLeft();
            bounceRight();
        }
        x += dx;
        y += dy;
    }

    private void bounceCeiling(){
        if(ceil & (y + dy > game_height)){
            dy = -dy;
            count++;
        }
    }
    private void bounceRight(){
        if(right & (x + dx > game_width)){
            dx = -dx;
            count++;
        }
    }
    private void bounceLeft(){
        if(left & (x + dx < 0)){
            dx = -dx;
            count++;
        }
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