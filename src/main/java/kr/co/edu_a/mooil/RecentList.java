package kr.co.edu_a.mooil;

import android.media.Image;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by soong2hyung on 2017-05-26.
 */

public class RecentList {
    public static Vector<String> vecRecentPathList;
    public static Vector<Image> vecRecentImageList;
    public static void RecentList()
    {
        vecRecentPathList = new Vector<>();
        vecRecentImageList = new Vector<>();
    }

    public static int addList(String path)
        {
        for(int i=0; i<vecRecentPathList.size(); i++)
        {
            if(path.equals(vecRecentPathList.get(i)) || i == 20)
            {
                vecRecentPathList.remove(i);
                vecRecentPathList.add(path);
                saveList();
                return 0;
            }
        }
        vecRecentPathList.add(path);
        saveList();
        return 0;
    }
    private static void saveList()
    {
        try {
            String saveTargetText = new String("");
            ListSaveIntoCache.Clear("recent_list.txt");
            for (int i = 0; i < vecRecentPathList.size(); i++)
            {
                saveTargetText = saveTargetText + vecRecentPathList.get(i) + ":::";
            }
            ListSaveIntoCache.Write(saveTargetText, "recent_list.txt");
        }
        catch(IOException io)
        {

        }
    }


    public static void readList()
    {
        try {
            String savedText = ListSaveIntoCache.Read("recent_list.txt");
            String[] splitedText = savedText.split(":::");
            for(int i=0; i<splitedText.length; i++)
                addList(splitedText[i]);
        }
        catch(IOException io)
        {

        }
    }

    public static String get(int index)
    {
        return vecRecentPathList.get(index);
    }

    public static int size()
    {
        return vecRecentPathList.size();
    }
}
