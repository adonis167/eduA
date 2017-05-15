package kr.co.edu_a.mooil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;

public class PageViewer extends AppCompatActivity {

    ViewPager mPageViewer;
    Button editMask;
    Button startMemorize;

    public static int nFiles;
    public static ArrayList<String> InFolderFiles;
    public static String MainFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_viewer);

        editMask = (Button) findViewById(R.id.editmask);
        startMemorize = (Button) findViewById(R.id.startmemorize);

        Intent intent = getIntent();
        MainFilePath = intent.getStringExtra("main_file_path");
        InFolderFiles = (ArrayList<String>) intent.getSerializableExtra("in_folder_files");
        nFiles = InFolderFiles.size();

        mPageViewer = (ViewPager) findViewById(R.id.pageviewer);
        CustomPagerAdapter adapter= new CustomPagerAdapter(getLayoutInflater());
        mPageViewer.setAdapter(adapter);
    }
}
