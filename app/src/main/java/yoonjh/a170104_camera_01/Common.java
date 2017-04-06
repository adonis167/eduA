package yoonjh.a170104_camera_01;

import android.os.Environment;

public class Common {
    /* 외부 저장소가 현재 read와 write를 할 수 있는 상태인지 확인한다 */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* 외부 저장소가 현재 read만이라도 할 수 있는 상태인지 확인한다 */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
