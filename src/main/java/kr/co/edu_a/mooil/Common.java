package kr.co.edu_a.mooil;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;

public class Common {

    /** 외부 저장소가 현재 read와 write를 할 수 있는 상태인지 확인한다 **/
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /** 외부 저장소가 현재 read만이라도 할 수 있는 상태인지 확인한다 **/
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /** Get Bitmap's Width **/
    public static int getBitmapOfWidth( String fileName ){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);
            return options.outWidth;
        } catch(Exception e) {
            return 0;
        }
    }

    /** Get Bitmap's height **/
    public static int getBitmapOfHeight( String fileName ){

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);

            return options.outHeight;
        } catch(Exception e) {
            return 0;
        }
    }
}
