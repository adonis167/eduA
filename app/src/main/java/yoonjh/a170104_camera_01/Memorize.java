package yoonjh.a170104_camera_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class Memorize extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memorize);

        ScratchView sv = new ScratchView(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 10, 10, 10);

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.root);
        rl.addView(sv, lp);
    }

    public void End_OnClick(View v) {
        RemoveMasking.canvasthread.setRunning(false);
        Intent a = new Intent(this, FileExplorer.class);
        startActivity(a);
    }
}
