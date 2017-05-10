package kr.co.edu_a.mooil;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by JihoYoon on 2017-04-02.
 * Modified by JoonYeong on 2017-04-02.
 */

public class CanvasThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private MyImageView myImageView;
    private boolean run = false;

    public CanvasThread(SurfaceHolder s, MyImageView m) {

        surfaceHolder = s;
        myImageView = m;
    }
    public void setRunning(boolean r) {
        run = r;
    }

    public void run() {
        Canvas c;
        while(run) {
            c=null;
            try {
                c= surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    myImageView.onDraw(c);
                }
            }
            finally {
                if(c!=null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}