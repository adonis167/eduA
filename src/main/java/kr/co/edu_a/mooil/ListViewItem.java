package kr.co.edu_a.mooil;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private Drawable iconFavor ;
    private boolean listCheck;
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setFavor(Drawable favor) {
        iconFavor = favor ;
    }
    public void setCheck(boolean checker) { listCheck = checker; }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public Drawable getFavor() {
        return this.iconFavor ;
    }
    public boolean getCheck() { return this.listCheck;}
}