package cisc181.danmaku;

/*

SimpleAnimate app -- animated drawing in a separate thread

Christopher Rasmussen
copyright 2017, University of Delaware

*/

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;
import java.lang.Override;
import java.util.ArrayList;
import java.util.Scanner;

import cisc181.danmaku.sequencePkg.Step;

import static android.support.v4.content.ContextCompat.startActivity;

// note that we are not implementing the SurfaceHolder.Callback interface at this level anymore

public class MyAnimatedSurfaceView extends SurfaceView {

    final int PLAYER_SIZE_HALF = 100;
    final int BULLET_SIZE_HALF = 15;
    Paint mPaint;
    Bitmap bmap_player;

    // object position, velocity
    Bitmap bmap_bullet;
    int xPos = 0;
    int yPos = 0;
    double xDelta = 0;

    // touch activity
    double yDelta = 0;
    boolean touchDown = false;
    boolean touchStarted = false;
    float touchX, touchY;
    boolean setup = true;
    Environment myGame;
    private MyThread myThread;

    private Attack attack;

    int frames_since_creation;

    int currentStage = 1;
    int lastStage = 6;

    // constructor

    public MyAnimatedSurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);

        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("stage" + currentStage,
                        "raw", "cisc181.danmaku"));

        Scanner scanner = new Scanner(ins);
        attack = new Attack(scanner);

        myThread = new MyThread(this);

        bmap_player = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_small);
        bmap_bullet = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);

        bmap_bullet = Bitmap.createScaledBitmap(bmap_bullet, BULLET_SIZE_HALF * 2, BULLET_SIZE_HALF * 2, false);
        bmap_player = Bitmap.createScaledBitmap(bmap_player, PLAYER_SIZE_HALF * 2, PLAYER_SIZE_HALF * 2, false);

        SurfaceHolder holder = getHolder();

        frames_since_creation = 0;

        // implement SurfaceHolder.Callback interface with anonymous inner class

        holder.addCallback(new SurfaceHolder.Callback() {

            // start draw thread

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                myThread.setRunning(true);
                myThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            // stop draw thread

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                myThread.setRunning(false);
                while (retry) {
                    try {
                        myThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });

    }

    // do drawing on current canvas

    public void myDraw(Canvas canvas) {

        if (setup) {
            myGame = new Environment(canvas.getWidth(), canvas.getHeight(), 2, 20, attack.getSequence());
            setup = false;
        }

        // clear background

        canvas.drawColor(Color.BLACK);

        // draw sprite

        canvas.save();

        // player took "shot"

        if (touchDown) {
            myGame.update((int) (-(canvas.getWidth() + touchX)), (int) (canvas.getHeight() - touchY));
        }

        // no shot -- draw normally

        else
            myGame.update(-1, -1);

        canvas.restore();

        // actually update position, orientation

        canvas.drawBitmap(bmap_player, canvas.getWidth() - Environment.getPlayerX() - PLAYER_SIZE_HALF, canvas.getHeight() - Environment.getPlayerY() - PLAYER_SIZE_HALF, mPaint);
        int size = myGame.size();
        for (int i = 0; i < size; i++) {
            canvas.drawBitmap(bmap_bullet, canvas.getWidth() - myGame.getX(i) - BULLET_SIZE_HALF, canvas.getHeight() - myGame.getY(i) - BULLET_SIZE_HALF, mPaint);
        }

        xPos += xDelta;
        yPos += yDelta;

        if (!myGame.getPlayerAlive()){
            endGame(myGame.getScore());
        }

        frames_since_creation++;

        if (frames_since_creation == attack.getTimeLimit()){
            currentStage++;
            frames_since_creation = 0;
            if (currentStage > lastStage){
                endGame(myGame.getScore());
            }
            else {
                InputStream ins = getResources().openRawResource(
                        getResources().getIdentifier("stage" + currentStage,
                                "raw", "cisc181.danmaku"));

                Scanner scanner = new Scanner(ins);
                attack = new Attack(scanner);
                myGame = new Environment(attack.getSequence());
            }
        }

    }

    // respond to the SurfaceView being clicked/dragged

    public boolean onTouchEvent(MotionEvent e) {

        // determine whether touch is still active or has just ended

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            touchDown = true;        // finger down
            touchStarted = true;     // finger JUST went down
            touchX = e.getX();       // where finger is
            touchY = e.getY();

        }

        // finger down and dragging

        else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            touchStarted = false;    // finger has been down for awhile now
            touchX = e.getX();
            touchY = e.getY();
        }

        // finger is up after being down

        else if (e.getAction() == MotionEvent.ACTION_UP) {
            touchDown = false;       // finger up
            touchStarted = false;    // so a touch cannot have just been started
        }

        // unrecognized motion event

        else {
            return false;
        }

        // do NOT force a redraw -- just wait for MyThread's next draw call

        return true;
    }

    public void endGame(int score_received){
        myThread.setRunning(false);
        Intent intent = new Intent(super.getContext(),EnterName.class);
        int score = score_received;
        intent.putExtra("SCORE",score);
        Bundle bundle = new Bundle();
        startActivity(super.getContext(),intent,bundle);
    }

}
