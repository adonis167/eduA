package kr.co.edu_a.mooil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.R.attr.path;

/**
 * Created by JihoYoon on 2017-04-02.
 */

public class MyImageView extends SurfaceView implements SurfaceHolder.Callback {
    private static int STROKE_WIDTH = 25;
    private  static int OPACITY = 75;

    private CanvasThread canvasthread;
    Paint paint = new Paint();
    static Path path = new Path();    // 자취를 저장할 객체


    public MyImageView(Context context) {

        super(context);

        paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(STROKE_WIDTH); // 선의 굵기 지정
        paint.setAlpha(OPACITY);

        getHolder().addCallback(this);
        canvasthread = new CanvasThread(getHolder(), this);
        setFocusable(true);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context,attrs);

        paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(STROKE_WIDTH); // 선의 굵기 지정
        paint.setAlpha(OPACITY);

        getHolder().addCallback(this);
        canvasthread = new CanvasThread(getHolder(), this);
        setFocusable(true);
    }
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public MyImageView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);

        paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(STROKE_WIDTH); // 선의 굵기 지정
        paint.setAlpha(OPACITY);

        getHolder().addCallback(this);
        canvasthread = new CanvasThread(getHolder(), this);
        setFocusable(true);
    }

    protected void onDraw(Canvas canvas) {
        Bitmap mapImg = BitmapFactory.decodeResource(getResources(), R.drawable.subnote); //EditActivity.bm;
        canvas.drawBitmap(mapImg, 0, 0, null);
        canvas.drawPath(path, paint); // 저장된 path 를 그려라

        //canvas.drawColor(Color.BLACK);
        //p.setColor(Color.RED);
        //canvas.drawLine(0, 0, 5000, 5000, p);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        canvasthread.setRunning(true);
        canvasthread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        canvasthread.setRunning(false);
        while (retry) {
            try {
                canvasthread.join(); retry = false;
            }
            catch (InterruptedException e) { // TODO: handle exception
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y); // 자취에 그리지 말고 위치만 이동해라
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y); // 자취에 선을 그려라
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate(); // 화면을 다시그려라
        return true;
    }
}
