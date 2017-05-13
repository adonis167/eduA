package kr.co.edu_a.mooil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CropImage extends ImageView {
	private static final String TAG = "Crop_Image";
	float sx, ex , sy, ey;
	static int DEP = 30;
	
	CropImageActivity cnxt;

	Bitmap bitmap;
	float mWidth;
	float mHeight;
	Paint pnt;
	
	Bitmap hBmp;
	Bitmap wBmp;
	
	private String outFilePath=niceheeUtil.strFilePath;
	
	public CropImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mWidth = display.getWidth();
        mHeight = display.getHeight();
		
        sx = mWidth/5;
        ex = mWidth*4/5;
        sy = mHeight/5;
        ey = mHeight*4/5;
        
        Log.e(TAG, outFilePath);
        cnxt = (CropImageActivity)context;
        
        Options resizeOpts = new Options();
        resizeOpts.inSampleSize = 2;
        try {
        	bitmap = BitmapFactory.decodeStream(new FileInputStream(outFilePath), null, resizeOpts);
        } catch(Exception e) {e.printStackTrace();}
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)mWidth, (int)mHeight, false);
		Log.e(TAG, ""+bitmap.getHeight()*bitmap.getWidth()); 
		hBmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.camera_crop_height);
		wBmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.camera_crop_width);
		
		pnt = new Paint();
		pnt.setColor(Color.MAGENTA);
		pnt.setStrokeWidth(3);
	}
	 
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bitmap, 0, 0, null);

		canvas.drawLine(sx, sy, ex, sy, pnt);		
		canvas.drawLine(ex, sy, ex, ey, pnt);
		canvas.drawLine(sx, sy, sx, ey, pnt);
		canvas.drawLine(sx, ey, ex, ey, pnt);
		canvas.drawBitmap(hBmp, (ex+sx)/2-19, sy-19, null);
		canvas.drawBitmap(hBmp, (ex+sx)/2-19, ey-19, null);
		canvas.drawBitmap(wBmp, sx-19, (ey+sy)/2-19, null);
		canvas.drawBitmap(wBmp, ex-19, (ey+sy)/2-19, null);
	}
	
	float dx=0, dy=0;
	float oldx, oldy;
	boolean bsx, bsy, bex, bey;
	boolean bMove = false;
	public boolean onTouchEvent(MotionEvent e) {
		int x = (int)e.getX();
		int y = (int)e.getY();
		
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			oldx = x;
			oldy = y;
			if( (x > sx-DEP ) && (x < sx+DEP ) )
				bsx = true;
			else 
				if( (x > ex-DEP ) && (x < ex+DEP) )
					bex = true;
			
			if( (y > sy-DEP ) && (y < sy+DEP ) )
				bsy = true;
			else 
				if( (y > ey-DEP ) && (y < ey+DEP ) )
					bey = true;
			 
			if( (bsx || bex || bsy || bey) ) 
				bMove = false;
			else 
				if( ((x > sx+DEP) && (x < ex-DEP))  
				  && ( (y > sy+DEP) && (y < ey-DEP)))
					bMove = true;
			
			return true;			
		}
		
		if(e.getAction() == MotionEvent.ACTION_MOVE) {
			if(bsx) sx = x;
			if(bex) ex = x;
			if(bsy) sy = y;
			if(bey) ey = y;
			
			if(ex<=sx+DEP){
				ex=sx+DEP;
				return true;
			}
			if(ey<=sy+DEP){
				ey=sy+DEP;
				return true;
			}
			
			if(bMove) {
				dx = oldx - x;
				dy = oldy - y;
				
				sx -= dx; 
				ex -= dx;
				sy -= dy; 
				ey -= dy;
				if(sx <= 0) sx = 0;
				if(ex >= mWidth) ex = mWidth-1;
				
				if(sy <= 0) sy = 0;
				if(ey >= mHeight) ey = mHeight-1;
			}
			
			invalidate();
			oldx = x;
			oldy = y;
			return true;
		}
		
		if(e.getAction() == MotionEvent.ACTION_UP) {
			bsx = bex = bsy = bey = bMove = false;
			return true;
		}
		return false;
	}

	public void save() {
		Bitmap tmp = Bitmap.createBitmap(bitmap, (int)sx, (int)sy, (int)(ex-sx), (int)(ey-sy));
		byte[] byteArray = bitmapToByteArray(tmp);
		File file = new File(outFilePath);
		Log.e("nicehee", file.getAbsolutePath());
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(byteArray);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			Toast.makeText(cnxt, "파일 저장 중 에러 발생 : " + 
					e.getMessage(), 0).show();
			return;
		}
	}
	public byte[] bitmapToByteArray( Bitmap bitmap ) {   
		ByteArrayOutputStream stream = new ByteArrayOutputStream() ;   
		bitmap.compress( CompressFormat.JPEG, 100, stream) ;   
		byte[] byteArray = stream.toByteArray() ;   
		return byteArray ;
	}
}