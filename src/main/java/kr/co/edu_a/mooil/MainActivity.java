package kr.co.edu_a.mooil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.tsengvn.typekit.TypekitContextWrapper;
public class MainActivity extends AppCompatActivity {
    //슬라이드 열기/닫기 플래그
    boolean isPageOpen = false;
    //슬라이드 열기 애니메이션
    Animation translateLeftAnim;
    Animation fadeInRightAnim;
    //슬라이드 닫기 애니메이션
    Animation translateRightAnim;
    Animation fadeOutLeftAnim;
    //슬라이드 레이아웃
    LinearLayout slidingPage01;
    //메인페이지 레이아웃
    LinearLayout mainPage01;

    Intent slide_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI
        mainPage01 = (LinearLayout)findViewById(R.id.mainPage01);
        slidingPage01 = (LinearLayout)findViewById(R.id.slidingPage01);
        //애니메이션
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        fadeInRightAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_right);
        fadeOutLeftAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out_left);
        //애니메이션 리스너 설정
        SlidingPageAnimationListener animationListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animationListener);
        translateRightAnim.setAnimationListener(animationListener);
        fadeInRightAnim.setAnimationListener(animationListener);
        fadeOutLeftAnim.setAnimationListener(animationListener);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    //버튼
    public void onButton1Clicked(View v){
        //닫기
        if(isPageOpen){
            //애니메이션 시작
            slidingPage01.startAnimation(translateLeftAnim);
            mainPage01.startAnimation(fadeInRightAnim);
            for(int i=0; i<mainPage01.getChildCount(); i++)//mainPage 이하 활성화
                mainPage01.getChildAt(i).setClickable(true);
            isPageOpen = false;
        }
        //열기
        else{
            slidingPage01.setVisibility(View.VISIBLE);
            slidingPage01.startAnimation(translateRightAnim);
            mainPage01.startAnimation(fadeOutLeftAnim);
            for(int i=0; i<mainPage01.getChildCount(); i++)//비활성화
                mainPage01.getChildAt(i).setClickable(false);
            isPageOpen = true;
        }
    }
    //애니메이션 리스너
    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            //슬라이드 열기->닫기
            if(isPageOpen){
                slidingPage01.setVisibility(View.INVISIBLE);
            }
            //슬라이드 닫기->열기
            else{
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
        @Override
        public void onAnimationStart(Animation animation) {

        }
    }

    public void MFOnClick(View v) {
        switch (v.getId()) {
            case R.id.mainpage:
                break;
            case R.id.pagelist:
                slide_intent = new Intent(this, FileExplorer.class);
                startActivity(slide_intent);
                break;
            case R.id.pagemake:
                break;
            case R.id.notice:
                break;
            case R.id.favorite:
                break;
            case R.id.recentread:
                break;
            case R.id.importfile:
                break;
            case R.id.pdfrender:
                slide_intent = new Intent(this, PDFRenderer.class);
                startActivity(slide_intent);
                break;
        }
    }

}
