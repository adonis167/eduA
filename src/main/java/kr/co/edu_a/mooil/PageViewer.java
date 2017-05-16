package kr.co.edu_a.mooil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class PageViewer extends AppCompatActivity {

    ViewPager mPageViewer;
    Button editMask;
    Button startMemorize;

    public static int nFiles;
    public static ArrayList<String> InFolderFiles;
    public static String MainFilePath;
    public static int current;
    String currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_viewer);

        editMask = (Button) findViewById(R.id.editmask);
        startMemorize = (Button) findViewById(R.id.startmemorize);

        Intent intent = getIntent();
        MainFilePath = intent.getStringExtra("main_file_path");
        currentPath = MainFilePath;
        InFolderFiles = (ArrayList<String>) intent.getSerializableExtra("in_folder_files");
        nFiles = InFolderFiles.size();

        mPageViewer = (ViewPager) findViewById(R.id.pageviewer);
        CustomPagerAdapter adapter= new CustomPagerAdapter(getLayoutInflater());
        mPageViewer.setAdapter(adapter);

        for (int i=0; i<nFiles; i++) {
            if (InFolderFiles.get(i).equals(MainFilePath)) {
                mPageViewer.setCurrentItem(i);
                break;
            }
        }

        mPageViewer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                current = position;
                currentPath = InFolderFiles.get(current);
            }
        });
    }


    public void viewerOnClick(View v) {
        switch (v.getId()) {
            case R.id.editmask:
                Intent intent_editmask = new Intent(PageViewer.this, EditActivity.class);
                intent_editmask.putExtra("currentPath", currentPath);
                startActivity(intent_editmask);
                break;

            case R.id.startmemorize:
                Intent intent_memorize = new Intent(PageViewer.this, Memorize.class);
                startActivity(intent_memorize);
                break;

            case R.id.viewerBack:
                finish();
                break;
        }
    }
}
