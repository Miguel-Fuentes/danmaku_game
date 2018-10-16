package cisc181.danmaku.bulletPkg;

/**
 * Created by Brandon on 11/27/2017.
 */

public abstract class Bullet {
    protected double x;
    protected double y;
    protected double r;
    protected static int total_bullets;
    protected int id;
    protected boolean alive;
    protected static double game_width;
    protected static double game_height;
    protected boolean grazed;

    public static void setGameSize(double width, double height){
        game_width = width;
        game_height = height;
    }

    private void genID(){
        id = total_bullets;
        total_bullets++;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public boolean getAlive(){
        return alive;
    }
    public boolean getGrazed() { return grazed; }

    public Bullet(double x_init, double y_init, double radius){
        genID();
        alive = true;
        x = x_init;
        y = y_init;
        r = radius;
        grazed = false;
    }

    public boolean collision(double x_player, double y_player, double playerRadius){
        double distance = Math.pow(x_player - x,2) + Math.pow(y_player - y,2);
        double parameter = Math.pow(r + playerRadius,2);
        graze(distance,parameter);
        return (parameter >= distance);
    }

    private void graze(double distance, double parameter){
        if(!grazed){
            if(4*parameter >= distance){
                grazed = true;
            }
        }
    }

    public void kill(){
        alive = false;
    }

    public abstract void update();

    public abstract void live();
}
