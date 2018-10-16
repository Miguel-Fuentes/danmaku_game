package cisc181.danmaku;

import java.util.ArrayList;

import cisc181.danmaku.bulletPkg.Bullet;
import cisc181.danmaku.sequencePkg.*;

/**
 * Created by Brandon on 11/27/2017.
 */

public class Environment {
    static private ArrayList<Bullet> projectiles;
    private ArrayList<Step> sequence;
    private int sequencePoint;

    static private int Score;
    static private int dS;
    static private int grazeBonus;

    static private double player_x;
    static private double player_y;
    static private double player_x_0;
    static private double player_y_0;
    static private double playerRadius;
    static private double touch_x_0;
    static private double touch_y_0;

    private static int game_width;
    private static int game_height;
    private boolean playerAlive;
    private boolean gameActive;

    public Environment(int width, int height, int scoreIncrement, int grazeIncrement,ArrayList<Step> sequence_in){
        game_width = width;
        game_height = height;
        Bullet.setGameSize(width,height);
        BulletMaker.setGameParameters(width,height);
        player_x = width/2;
        player_y = height/4;
        playerRadius = 30;
        BulletMaker.setPlayer(player_x,player_y);
        dS = scoreIncrement;
        grazeBonus = grazeIncrement;
        projectiles = new ArrayList<>();
        sequence = sequence_in;
        sequencePoint = 0;
        playerAlive = true;
        gameActive = true;
        Score = 0;
    }

    public Environment(ArrayList<Step> sequence_in){
        sequence = sequence_in;
        sequencePoint = 0;
        playerAlive = true;
        gameActive = true;
    }

    public void update(int touchX, int touchY){
        //if touchX == -1, then it is registered as no input
        Score += dS;
        playerMove(touchX,touchY); //Move the player in accordance with their movements
        BulletMaker.setPlayer(player_x,player_y); //Update BulletMaker to know the player's location
        bulletUpdate(); //Update the movements of all bullets
        bulletDeathCheck(); //Check all bullets for removal
        sequenceUpdate(); //Create new bullets
        playerDeath(); //Register the player's death if are hit
    }

    private void playerMove(int touchX, int touchY){
        if(touchX == -1){
            touch_x_0 = -1;
            touch_y_0 = -1;
        }
        else {
            if(touch_x_0 == -1){
                player_x_0 = player_x;
                player_y_0 = player_y;
                touch_x_0 = touchX;
                touch_y_0 = touchY;
            }
            else {
                double X = player_x_0 + touchX - touch_x_0;
                double Y = player_y_0 + touchY - touch_y_0;
                if (X < 0) {
                    player_x_0 += (0 - X);
                    X = 0;
                } else if (X > game_width) {
                    player_x_0 += (game_width - X);
                    X = game_width;
                }
                if (Y < 0) {
                    player_y_0 += (0 - Y);
                    Y = 0;
                } else if (Y > game_height) {
                    player_y_0 += (game_height - Y);
                    Y = game_height;
                }
                player_x = X;
                player_y = Y;
            }
        }
    }
    private void bulletUpdate(){
        for(Bullet b : projectiles){
            b.update();
            b.live();
        }
    }
    private void bulletDeathCheck(){
        int size = projectiles.size();
        for(int i = size - 1; i >= 0; i--){
            if(!projectiles.get(i).getAlive()){
                if(projectiles.get(i).getGrazed()){
                    Score += grazeBonus;
                }
                projectiles.remove(i);
            }
        }
    }
    private void playerDeath(){
        int size = projectiles.size();
        for(int b = 0; (b < size) & playerAlive ; b++){
            playerAlive = playerAlive & !projectiles.get(b).collision(player_x,player_y, playerRadius);
        }
        if (!playerAlive){
            bulletKill();
        }
    }
    private void sequenceUpdate(){
        boolean resume = true;
        Step currStep;
        while(resume){
            currStep = sequence.get(sequencePoint);
            for(Bullet b : currStep.newBullets()){
                projectiles.add(b);
            }
            sequencePoint = currStep.nextStep(sequencePoint);
            gameActive = gameActive & currStep.terminate();
            resume = currStep.resume();
        }
    }

    private void bulletKill(){
        for(Bullet b: projectiles){
            b.kill();
        }
        bulletDeathCheck();
    }

    public int size(){
        return projectiles.size();
    }
    public int getX(int index){
        return (int) projectiles.get(index).getX();
    }
    public int getY(int index){
        return (int) projectiles.get(index).getY();
    }
    public int getScore(){
        return Score;
    }
    public static int getPlayerX(){
        return (int) player_x;
    }
    public static int getPlayerY(){
        return (int) player_y;
    }
    public boolean getPlayerAlive(){
        return playerAlive;
    }
}
