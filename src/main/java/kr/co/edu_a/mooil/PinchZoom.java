package kr.co.edu_a.mooil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PinchZoom extends AppCompatActivity {
    ImageView mPinchView;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinch_zoom);

        mPinchView = (ImageView) findViewById(R.id.pinchview);
        mAttacher = new PhotoViewAttacher(mPinchView);
    }
}
