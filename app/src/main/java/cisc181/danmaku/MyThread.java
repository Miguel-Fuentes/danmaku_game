package cisc181.danmaku;

/*

SimpleAnimate app -- animated drawing in a separate thread

Christopher Rasmussen
copyright 2017, University of Delaware

*/

import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;

public class MyThread extends Thread {

    MyAnimatedSurfaceView myView;
    private boolean running = false;  // default run state
    long sleepMillis = 10;            // default sleep time
    SoundPool SP;

    // initialize thread, using default sleep time between each draw call

    public MyThread(MyAnimatedSurfaceView view) {

        myView = view;

    }

    // change whether thread is currently running or not

    public void setRunning(boolean running) {
        this.running = running;
    }

    // define what happens when thread is running

    @Override
    public void run() {
        while (running) {

            // draw one time...

            Canvas canvas = myView.getHolder().lockCanvas();

            if (canvas != null) {
                synchronized (myView.getHolder()) {
                    myView.myDraw(canvas);
                }
                myView.getHolder().unlockCanvasAndPost(canvas);
            }

            // sounds -- should not play if already playing -- maxstreams affects behavior here

            // ...then wait a little before drawing again

            try {
                sleep(sleepMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
