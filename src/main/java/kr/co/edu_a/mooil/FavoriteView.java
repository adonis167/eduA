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

public class FavoriteView extends Activity {

    ListViewAdapter adapter;
    ListView favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_listview);
        adapter = new ListViewAdapter();
        favoriteList = (ListView)findViewById(R.id.recent_list);
        favoriteList.setAdapter(adapter);
        favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ArrayList<String> InFolderFiles = new ArrayList<String>();
                File clickedPath = new File(FavoriteList.get(position));
                if(clickedPath.isDirectory())
                {
                    Intent intent = new Intent(FavoriteView.this, FileExplorer.class);
                    intent.putExtra("main_file_path", FavoriteList.get(position));
                    startActivity(intent);
                }
                else
                {
                    for (int i = 0; i < FavoriteList.size(); i++) {
                        File listPath = new File(FavoriteList.get(i));
                        if (listPath.isFile() == true)
                            InFolderFiles.add(FavoriteList.get(i));
                    }
                    Intent intent = new Intent(FavoriteView.this, PageViewer.class);
                    RecentList.addList(FavoriteList.get(position));
                    intent.putExtra("main_file_path", FavoriteList.get(position));
                    intent.putExtra("in_folder_files", InFolderFiles);
                    startActivity(intent);
                }
            }
        }) ;
        refreshFiles();
    }

    void refreshFiles(){
        adapter.clearAll();
        for(int i=0; i<FavoriteList.size(); i++)
        {
            File f = new File(FavoriteList.get(i));
            if(f.isFile())
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.list_file), new File(FavoriteList.get(i)).getName(), FavoriteList.get(i), "File", 1) ;
            else
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.list_folder), new File(FavoriteList.get(i)).getName(), FavoriteList.get(i), "Folder", 1);

        }
        //다끝나면 리스트뷰를 갱신시킴
        adapter.notifyDataSetChanged();
    }
}
