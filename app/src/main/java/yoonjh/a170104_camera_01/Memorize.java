package yoonjh.a170104_camera_01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by JihoYoon on 2017-04-02.
 */

public class Memorize extends AppCompatActivity{
    private MyView vw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.memorize);
        vw = new MyView(this);
        setContentView(vw);
    }
    protected class MyView extends View {
        public MyView(Context context) {
            super(context);
        }
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.CYAN);

            Canvas c = new Canvas();

            Bitmap mainImage = BitmapFactory.decodeResource(getResources(),R.drawable.subnote);
            Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.subnote_masking);
            Bitmap result = Bitmap.createBitmap(mainImage .getWidth(), mainImage .getHeight(), Bitmap.Config.ARGB_8888);

            c.setBitmap(result);
            c.drawBitmap(mainImage, 0, 0, null);
            Paint paint = new Paint();
            paint.setFilterBitmap(false);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT) ); // DST_OUT
            c.drawBitmap(mask, 0, 0, paint);
            paint.setXfermode(null);
            canvas.drawBitmap(result, 0, 0, null);
        }
    }

}
