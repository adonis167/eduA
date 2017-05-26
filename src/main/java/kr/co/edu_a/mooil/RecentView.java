package kr.co.edu_a.mooil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by soong2hyung on 2017-05-26.
 */

public class RecentView extends Activity {

    ListViewAdapter adapter;
    ListView recentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_listview);
        adapter = new ListViewAdapter();
        recentList = (ListView)findViewById(R.id.recent_list);
        recentList.setAdapter(adapter);
        recentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ArrayList<String> InFolderFiles = new ArrayList<String>();
                for(int i=0; i<RecentList.size(); i++)
                {
                    InFolderFiles.add(RecentList.get(i));
                }
                Intent intent = new Intent(RecentView.this, PageViewer.class);
                intent.putExtra("main_file_path", RecentList.get(position));
                intent.putExtra("in_folder_files", InFolderFiles);
                startActivity(intent);
            }
        }) ;
        refreshFiles();
    }

    void refreshFiles(){
        adapter.clearAll();
        for(int i=RecentList.size()-1; i>=0; i--)
        {
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.list_file), new File(RecentList.get(i)).getName(), RecentList.get(i), "File", FavoriteList.getIsFavor(RecentList.get(i)));
        }
        //다끝나면 리스트뷰를 갱신시킴
        adapter.notifyDataSetChanged();
    }
}
