package kr.co.edu_a.mooil;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.File;
import java.util.Vector;

import uk.co.senab.photoview.PhotoViewAttacher;

public class EditActivity extends AppCompatActivity {

    public static Bitmap original;
    public static Bitmap masking;
    public static ImageView mPinchView;
    public static PhotoViewAttacher mAttacher;
    int nowBtnState = 0; //0 = None, 1 = Pan, 2 = Stroke, 3 = Color
    Animation aniBtnOn2;
    Animation aniBtnOff2;

    String currentOriginalPath;
    String currentMaskingPath;

    Vector nowSelectedIcon;
    Vector vecIconSet1;
    Vector vecIconSet2;
    Vector vecIconSet3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        currentOriginalPath = intent.getStringExtra("currentPath");

        int end = currentOriginalPath.lastIndexOf("/");
        String uppath = currentOriginalPath.substring(0, end);
        String fileName = currentOriginalPath.substring(end+1, currentOriginalPath.length()-4);
        currentMaskingPath = uppath + "/" + "MSK_" + fileName + ".png";

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


        iconInitialization();
    }

    protected void iconInitialization()
    {
        //Animation Load

        aniBtnOn2 = AnimationUtils.loadAnimation(this, R.anim.edit_btn_on2);
        aniBtnOff2 = AnimationUtils.loadAnimation(this, R.anim.edit_btn_off2);

        //선택된 Icon Id
        nowSelectedIcon = new Vector();
        nowSelectedIcon.add(R.id.editPan);
        nowSelectedIcon.add(R.id.editStroke1);
        nowSelectedIcon.add(R.id.editColor);

        vecIconSet1 = new Vector();
        vecIconSet1.add(R.id.editPan);
        vecIconSet1.add(R.id.editEraser);
        vecIconSet1.add(R.id.editClearAll);

        vecIconSet2 = new Vector();
        vecIconSet2.add(R.id.editStroke1);
        vecIconSet2.add(R.id.editStroke2);
        vecIconSet2.add(R.id.editStroke3);
        vecIconSet2.add(R.id.editStroke4);

        vecIconSet3 = new Vector();
        vecIconSet3.add(R.id.editColor);
        vecIconSet3.add(R.id.editOrange);
        vecIconSet3.add(R.id.editGreen);
        vecIconSet3.add(R.id.editRed);
    }

    public void onButtonClickEdit(View v){
        switch (v.getId()) {
            case R.id.editBack:
                /** 아래코드 수정 반드시 해야함. 오류 안나게 스태틱으로 바꾸고 커스텀뷰 매소드 강제 호출한 상태임. **/
                CanvasThread.setRunning(false);
                finish();
                break;
        }
        if(nowBtnState == 0)
        {
            iconAnimationOff(nowSelectedIcon);
            if(v.getId() == (int)nowSelectedIcon.get(0))
            {
                nowBtnState = 1;
                iconAnimationOn(vecIconSet1);
            }
            if(v.getId() == (int)nowSelectedIcon.get(1))
            {
                nowBtnState = 2;
                iconAnimationOn(vecIconSet2);
            }
            if(v.getId() == (int)nowSelectedIcon.get(2))
            {
                nowBtnState = 3;
                iconAnimationOn(vecIconSet3);
            }
        }
        else
        {
            if(nowBtnState == 1)
            {
                iconAnimationOff(vecIconSet1);
                iconAnimationOn(nowSelectedIcon);
                nowBtnState = 0;
            }
            if(nowBtnState == 2)
            {
                if(v.getId() == (int)vecIconSet2.get(0))
                    MyImageView.propertySet(10, 90);
                else if(v.getId() == (int)vecIconSet2.get(1))
                    MyImageView.propertySet(20, 90);
                else if(v.getId() == (int)vecIconSet2.get(2))
                    MyImageView.propertySet(30, 90);
                else if(v.getId() == (int)vecIconSet2.get(3))
                    MyImageView.propertySet(40, 90);
                iconAnimationOff(vecIconSet2);
                iconAnimationOn(nowSelectedIcon);
                nowBtnState = 0;
            }
            if(nowBtnState == 3)
            {
                iconAnimationOff(vecIconSet3);
                iconAnimationOn(nowSelectedIcon);
                nowBtnState = 0;
            }
        }
    }

    public void iconAnimationOff(Vector vecTargetViewId)
    {
        for(int i=0; i<vecTargetViewId.size(); i++)
        {
            findViewById((int)vecTargetViewId.get(i)).setVisibility(View.GONE);
        }
    }

    public void iconAnimationOn(Vector vecTargetViewId2)
    {
        for(int i=0; i<vecTargetViewId2.size(); i++)
        {
            findViewById((int)vecTargetViewId2.get(i)).startAnimation(aniBtnOn2);
            findViewById((int)vecTargetViewId2.get(i)).setVisibility(View.VISIBLE);
        }
    }
}







