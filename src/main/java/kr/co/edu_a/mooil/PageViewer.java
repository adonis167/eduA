package kr.co.edu_a.mooil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PageViewer extends AppCompatActivity {


    ViewPager mPageViewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_viewer);

        mPageViewer = (ViewPager) findViewById(R.id.pageviewer);


        CustomPagerAdapter adapter= new CustomPagerAdapter(getLayoutInflater());
        mPageViewer.setAdapter(adapter);

        Intent intent = getIntent();
        String MainFilePath = intent.getStringExtra("main_file_path");
        ArrayList<String> InFolderFiles = (ArrayList<String>) intent.getSerializableExtra("in_folder_files");
//
//        File imgFile = new File(MainFilePath);
//
//        if(imgFile.exists()){
//            Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//            mPageViewer.setImageBitmap(bm);

//        }

    }
}
