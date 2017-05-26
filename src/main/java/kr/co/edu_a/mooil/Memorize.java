package kr.co.edu_a.mooil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by JihoYoon on 2017-04-02.
 */

public class Memorize extends AppCompatActivity{

    ScratchImageView siv;
    int cur;
    PhotoViewAttacher mAttacher;

    ImageView memorizePrevious;
    ImageView memorizeNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memorize);

        siv = (ScratchImageView) findViewById(R.id.memorizeImageView);

        memorizePrevious = (ImageView) findViewById(R.id.memorizePrevious);
        memorizeNext = (ImageView) findViewById(R.id.memorizeNext);

        cur = PageViewer.current;

        File imgFile = new File(PageViewer.InFolderFiles.get(cur));

        if(imgFile.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            siv.setImageBitmap(bm);
        }

        updateUI();

//        mAttacher = new PhotoViewAttacher(siv);
    }

    public void memOnClick(View v) {
        File imgFile;

        switch (v.getId()) {
            case R.id.memorizePrevious:
                cur--;
                imgFile = new File(PageViewer.InFolderFiles.get(cur));

                if(imgFile.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    siv.setImageBitmap(bm);
                }
                updateUI();
                break;

            case R.id.memorizeNext:
                cur++;
                imgFile = new File(PageViewer.InFolderFiles.get(cur));

                if(imgFile.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    siv.setImageBitmap(bm);
                }
                updateUI();
                break;

            case R.id.memorizeBack:
                finish();
                break;
        }
    }

    public void updateUI() {
        memorizePrevious.setEnabled(cur > 0 && cur < PageViewer.InFolderFiles.size());
        memorizeNext.setEnabled(cur >= 0 && cur < PageViewer.InFolderFiles.size()-1);
    }


    /*protected class MyView extends View {
        public MyView(Context context) {
            super(context);
        }
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);

            Canvas c = new Canvas();

            Bitmap mainImage = BitmapFactory.decodeResource(getResources(),R.drawable.test01);
            Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.test01_m);
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
    }*/

}
