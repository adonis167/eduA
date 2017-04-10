package yoonjh.a170104_camera_01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;


public class ScratchView extends View {
    private int radius=60;					//터치 지점으로 부터 반경.
    private int pixelArray[];				//터치한 지점의 픽셀 값을 저장할 배열
    private int currentPixelArray[];		//라운드 처리를 위해 원본 픽셀값을 저장할 배열

    private Bitmap currentBitmap;		//현재 화면에 보여지고 있는 비트맵
    private Bitmap newBitmap;			//현재 비트맵이 지워지고 보여질 비트맵
    private Bitmap maskBitmap;			//터치한 곳의 픽셀을 변경할 때 사각형 형태를 원형태로 변경할 때 사용할 마스크
    private Paint currentBitmapPaint;	//비트맵을 그릴 페인트

    private boolean isFirst;					//최초 실행했는지 판단하는 플래그

    /**
     * 생성자
     * @param context
     */
    public ScratchView(Context context) {
        super(context);
        pixelArray = new int[radius * radius];		//터치한 곳의 픽셀 저장할 배열 생성
        currentPixelArray = new int[radius * radius];	//라운드 처리를 위해 원본 픽셀 값 저장할 배열
        isFirst = true;									//최초 실행되었다고 플래그 값 설정
    }

    /**
     * 뷰의 사이즈가 정해지면 뷰의 크기 만큼 비트맵을 만들기 위해
     * onWindowFocusChanged 메소드를 오버라이드 함.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(isFirst)				//최초 실행이면
            initSource();		//비트맵을 초기화 한다
    }

    /**
     * 비트 맵을 초기화 한다
     */
    public void initSource() {
        isFirst = false;						//최초 실행이 아니라고 플래그 설정

        int w = getWidth();					//뷰의 가로 크기. onWindowFocusChanged메소드가 불려지고 나서 뷰의 크기가 정해진다
        int h = getHeight();					//뷰의 세로 크기

        BitmapFactory.Options opts = new BitmapFactory.Options();	//비트맵 생성 옵션
        opts.inPurgeable = true;			//메모리 정리될 수 있게 설정

        //지워질 그림에 대한 이미지 저장
        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.subnote_result, opts);
        currentBitmap = Bitmap.createScaledBitmap(temp, w, h, true);

        //칠해질 그림에 대한 이미지 저장
        temp = BitmapFactory.decodeResource(getResources(), R.drawable.subnote, opts);
        newBitmap = Bitmap.createScaledBitmap(temp, w, h, true);

        Paint p = new Paint();		//마스크 용 페인트
        p.setStyle(Paint.Style.FILL);			//채우기
        p.setColor(Color.BLACK);	//검은색

        maskBitmap = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);	//마스크 비트맵 생성
        Canvas c = new Canvas(maskBitmap);		//마스크 비트맵에 그릴 캔버스 생성

        c.drawRect(0, 0, radius, radius, p);			//마스크 전체를 검은색으로
        p.setColor(Color.WHITE);						//페인트 색 흰색
        c.drawCircle(radius/2, radius/2, radius/2, p);//가운데 원은 흰색으로 그림

        currentBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);	//페인트 생성
    }

    /**
     * 현재 비트맵에 새로운 비트맵의 픽셀 값을 설정한다.
     * @param x - 터치한 좌표
     * @param y - 터치한 좌표
     */
    private void scratchBitmap(float x, float y) {
        newBitmap.getPixels(pixelArray, 0, radius, (int)x-radius/2, (int)y-radius/2, radius, radius);		//새로운 비트맵의 픽셀 값을 가져온다
        currentBitmap.getPixels(currentPixelArray, 0, radius, (int)x-radius/2, (int)y-radius/2, radius, radius);	//라운드 처리를 위해 현재 비트맵의 픽셀값을 가져온다

        setRoundArea();		//라운드 형태로 픽셀값을 설정

        currentBitmap.setPixels(pixelArray, 0, radius, (int)x-radius/2, (int)y-radius/2, radius, radius);	//현재 비트맵에 픽셀값을 설정한다
        invalidate();		//화면 새로 고침
    }

    /**
     * 스크래치되는 부분을 둥글게 만든다.
     */
    private void setRoundArea() {
        for(int i=0; i<pixelArray.length; i++) {
            int maskColor = maskBitmap.getPixel(i%radius, i/radius);		//마스크를 검사하여
            if(maskColor == Color.BLACK) {									//검은색 부분. 라운드 처리할 부분
                pixelArray[i] = currentPixelArray[i];							//원래 픽셀로 돌려서 라운드 형태로 만듬
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(currentBitmap, 0, 0, currentBitmapPaint);		//현재 비트맵을 그린다
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)				//터치 다운이면 이벤트 계속 받게 true를 리턴
            return true;
        else if(event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();				//현재 좌표
            float y = event.getY();				//현재 좌표

            //터치한 곳의 좌표가 픽셀을 가져오는 크기보다 크거나 작으면 에러가 발생하므로 좌표값을 보정함
            if(x < radius/2) x = radius/2;
            else if(x > getWidth()-radius/2) x= getWidth()-radius/2;

            if(y < radius/2) y = radius/2;
            else if(y > getHeight()-radius/2) y = getHeight()-radius/2;

            scratchBitmap(x, y);			//터치한 곳의 비트맵의 픽셀값을 변경한다.
        }
        return super.onTouchEvent(event);
    }
}