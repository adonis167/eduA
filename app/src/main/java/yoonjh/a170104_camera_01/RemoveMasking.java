package yoonjh.a170104_camera_01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class RemoveMasking extends SurfaceView implements SurfaceHolder.Callback {
    private static int STROKE_WIDTH = 25;
    private  static int OPACITY = 75;

    static RemoveCanvasThread canvasthread;
    Paint paint = new Paint();
    static Path path = new Path();


    public RemoveMasking(Context context) {

        super(context);

        paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(STROKE_WIDTH); // 선의 굵기 지정
        paint.setAlpha(OPACITY);
        paint.setAntiAlias(true);

        getHolder().addCallback(this);
        canvasthread = new RemoveCanvasThread(getHolder(), this);
        setFocusable(true);
    }

    public RemoveMasking(Context context, AttributeSet attrs) {
        super(context,attrs);

        paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(STROKE_WIDTH); // 선의 굵기 지정
        paint.setAlpha(OPACITY);
        paint.setAntiAlias(true);

        getHolder().addCallback(this);
        canvasthread = new RemoveCanvasThread(getHolder(), this);
        setFocusable(true);
    }
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public RemoveMasking(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);

        paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(STROKE_WIDTH); // 선의 굵기 지정
        paint.setAlpha(OPACITY);
        paint.setAntiAlias(true);

        getHolder().addCallback(this);
        canvasthread = new RemoveCanvasThread(getHolder(), this);
        setFocusable(true);
    }

    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);

        Canvas c = new Canvas();

        Bitmap mainImage = BitmapFactory.decodeResource(getResources(),R.drawable.subnote);
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.subnote_masking);
        Bitmap result = Bitmap.createBitmap(mainImage.getWidth(), mainImage.getHeight(), Bitmap.Config.ARGB_8888);

        c.setBitmap(result);
        c.drawBitmap(mainImage, 0, 0, null);

        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT) ); // DST_OUT
        c.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        canvas.drawBitmap(result, 0, 0, null);
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
