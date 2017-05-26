package kr.co.edu_a.mooil;

import android.media.Image;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by soong2hyung on 2017-05-26.
 */

public class FavoriteList {
    public static Vector<String> vecFavoritePathList;
    public static Vector<Image> vecFavoriteImageList;
    public static void FavoriteList()
    {
        vecFavoritePathList = new Vector<>();
        vecFavoriteImageList = new Vector<>();
    }

    public static void addList(String path)
    {
        vecFavoritePathList.add(path);
        saveList();
    }

    public static void remove(String path)
    {
        for(int i=0; i<vecFavoritePathList.size(); i++)
        {
            if (path.equals(vecFavoritePathList.get(i)))
            {
                vecFavoritePathList.remove(i);
            }
        }
    }

    public static int getIsFavor(String path)
    {
        for(int i=0; i<vecFavoritePathList.size(); i++)
        {
            if (path.equals(vecFavoritePathList.get(i)))
                return 1;
        }
        return 0;
    }

    public static void saveList()
    {
        try{
            String saveTargetText = new String("");
            ListSaveIntoCache.Clear("favorite_list.txt");
            for(int i=0; i<vecFavoritePathList.size(); i++) {
                saveTargetText = saveTargetText + vecFavoritePathList.get(i) + ":::";
            }
            ListSaveIntoCache.Write(saveTargetText,"favorite_list.txt");
            //캐시에 쓰기
        }
        catch(IOException io)
        {

        }
    }

    public static void readList()
    {
        try {
            String savedText = ListSaveIntoCache.Read("favorite_list.txt");
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
        return vecFavoritePathList.get(index);
    }

    public static int size()
    {
        return vecFavoritePathList.size();
    }
}
