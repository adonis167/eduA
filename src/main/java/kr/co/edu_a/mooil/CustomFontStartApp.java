package kr.co.edu_a.mooil;

import android.app.Application;
import com.tsengvn.typekit.Typekit;


public class CustomFontStartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Custom font
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "nbgothic_normal.otf"))
                .addBold(Typekit.createFromAsset(this, "nbgothic_bold.otf"));
    }
}
