package cisc181.danmaku.sequencePkg;

import java.util.ArrayList;

import cisc181.danmaku.BulletMaker;
import cisc181.danmaku.bulletPkg.Bullet;

/**
 * Created by Brandon on 12/5/2017.
 */

public class Spawner_Trial extends Spawner {
    private double circle_x;
    private double circle_y;
    private double circle_radius;
    private double bullet_radius;
    private double velocity;
    private double spawn_Angle;
    private double shot_Angle;
    private double change_Angle;
    private int count;

    private double angle_Difference; //Angle between shot spawns

    public Spawner_Trial(double cx, double cy, double cr, double r, double v, double startAngleShot, double angleChange, double c){
        circle_x = cx;
        circle_y = cy;
        circle_radius = cr;
        bullet_radius = r;
        velocity = v;
        spawn_Angle = 0;
        shot_Angle = startAngleShot;
        change_Angle = angleChange;
        count = (int) c;
        angle_Difference = (360/c);
    }

    public Spawner_Trial copy(){
        return new Spawner_Trial(circle_x,circle_y,circle_radius,bullet_radius,velocity,shot_Angle,change_Angle,count);
    }

    public ArrayList<Bullet> newBullets(){
        ArrayList<Bullet> result = new ArrayList<>();

        double Angle_Of_Spawn = spawn_Angle;
        double radianAngle;
        double x;
        double y;
        for(int i = 0; i< count; i++){
            Angle_Of_Spawn += angle_Difference;
            radianAngle = (Math.PI/180)*Angle_Of_Spawn;
            x = circle_x + circle_radius*Math.cos(radianAngle);
            y = circle_y + circle_radius*Math.sin(radianAngle);
            result.add(BulletMaker.makeShotTypeStraight(x,y,bullet_radius,velocity,Angle_Of_Spawn + shot_Angle));
        }
        spawn_Angle += change_Angle;
        return result;
    }
}
