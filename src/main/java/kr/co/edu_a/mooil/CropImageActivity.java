package kr.co.edu_a.mooil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class CropImageActivity extends Activity implements OnClickListener {
	CropImage ci;
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.crop_image);
	
		ci = (CropImage)findViewById(R.id.crop_view);
		findViewById(R.id.btn_ok).setOnClickListener(this);
		findViewById(R.id.btn_cancle).setOnClickListener(this);
	}
	
	public void onClick(View v) {
		Intent i = getIntent();
		if(v.getId() == R.id.btn_ok) {
			ci.save();
			setResult(RESULT_OK, i);
		}
		if(v.getId() == R.id.btn_cancle) {
			setResult(RESULT_CANCELED, i);
		}
		System.gc();
		finish();
	}
}