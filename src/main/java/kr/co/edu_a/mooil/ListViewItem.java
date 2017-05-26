package kr.co.edu_a.mooil;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private int iconFavor ;
    private boolean listCheck;
    private String path;
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setFavor(int favor) {
        iconFavor = favor ;
    }
    public void setCheck(boolean checker) { listCheck = checker; }
    public void setPath(String inputpath) {path = inputpath;}

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public int getFavor() {
        return this.iconFavor ;
    }
    public boolean getCheck() { return this.listCheck;}
    public String getPath() {return this.path;}
}