package kr.co.edu_a.mooil;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

public class EditActivity extends AppCompatActivity {
    public static Bitmap original;
    public static Bitmap masking;
    public static ImageView mPinchView;
    public static PhotoViewAttacher mAttacher;

    String currentOriginalPath;
    String currentMaskingPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        currentOriginalPath = intent.getStringExtra("currentPath");

        int end = currentOriginalPath.lastIndexOf("/");
        String uppath = currentOriginalPath.substring(0, end);
        String fileName = currentOriginalPath.substring(end+1, currentOriginalPath.length());
        currentMaskingPath = uppath + "/" + "MSK_" + fileName;

        File originalFile = new File(currentOriginalPath);
        File maskingFile = new File(currentMaskingPath);

        original = BitmapFactory.decodeFile(originalFile.getAbsolutePath());

        if(!maskingFile.exists()) {
            FileExplorer.storeMaskingImage(original, currentMaskingPath);
            masking = BitmapFactory.decodeFile(maskingFile.getAbsolutePath());
        }

        mPinchView = (ImageView) findViewById(R.id.pinchview);
        mPinchView.setImageBitmap(original);
        mAttacher = new PhotoViewAttacher(mPinchView);

    }
}







