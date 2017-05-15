package kr.co.edu_a.mooil;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class EditActivity extends AppCompatActivity {
    public static Bitmap bm;
    public static ImageView mPinchView;
    public static PhotoViewAttacher mAttacher;
    boolean isBtnLayerOn = false;

    Animation aniBtnOn2;
    Animation aniBtnOff2;
    Animation aniBtnOn3;
    Animation aniBtnOff3;







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        bm = intent.getParcelableExtra("bitmap");
        mPinchView = (ImageView) findViewById(R.id.pinchview);
        mAttacher = new PhotoViewAttacher(mPinchView);

        //Animation Load
        aniBtnOn2 = AnimationUtils.loadAnimation(this, R.anim.edit_btn_on2);
        aniBtnOff2 = AnimationUtils.loadAnimation(this, R.anim.edit_btn_off2);
        aniBtnOn3 = AnimationUtils.loadAnimation(this, R.anim.edit_btn_on3);
        aniBtnOff3 = AnimationUtils.loadAnimation(this, R.anim.edit_btn_off3);
    }


    public void onButtonClickEdit(View v){
        //닫기
        /*if(isLayerOpen){
            //애니메이션 시작
            slidingPage01.startAnimation(translateLeftAnim);
            for(int i=0; i<mFileList.getChildCount(); i++)//mainPage 이하 활성화
                mFileList.getChildAt(i).setClickable(true);
            isPageOpen = false;
        }
        //열기
        else{
            slidingPage01.setVisibility(View.VISIBLE);
            slidingPage01.startAnimation(translateRightAnim);
            slidingPage01.setClickable(true);
            for(int i=0; i<mFileList.getChildCount(); i++)//비활성화
                mFileList.getChildAt(i).setClickable(false);
            isPageOpen = true;
        }*/

        //닫음
        if(isBtnLayerOn)
        {
            findViewById(R.id.editButton2).startAnimation(aniBtnOff2);
            findViewById(R.id.editButton1).startAnimation(aniBtnOff3);
            findViewById(R.id.editButton4).startAnimation(aniBtnOff2);
            isBtnLayerOn = false;
        }
        else//열기
        {
            findViewById(R.id.editButton2).startAnimation(aniBtnOn2);
            findViewById(R.id.editButton1).startAnimation(aniBtnOn3);
            findViewById(R.id.editButton4).startAnimation(aniBtnOn2);
            isBtnLayerOn = true;
        }
        //이하 버튼 동작 분기
        if(v.getId()==R.id.editButton1)
        {
            MyImageView.propertySet(20, 75);
        }
        if(v.getId()==R.id.editButton2)
        {
            MyImageView.propertySet(40, 75);
        }
        if(v.getId()==R.id.editButton3)
        {
            MyImageView.propertySet(80, 75);
        }
        if(v.getId()==R.id.editButton4)
        {

        }
        if(v.getId()==R.id.editButton5)
        {

        }
        if(v.getId()==R.id.editButton6)
        {

        }
    }
}







