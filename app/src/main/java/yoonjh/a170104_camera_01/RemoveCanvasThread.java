package yoonjh.a170104_camera_01;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class RemoveCanvasThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private RemoveMasking myImageView;
    private boolean run = false;

    public RemoveCanvasThread(SurfaceHolder s, RemoveMasking m) {

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