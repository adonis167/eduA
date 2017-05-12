package kr.co.edu_a.mooil;

import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class niceheeImageCropActivity extends Activity implements OnClickListener {
	private static final String TAG = "nicehee.CameraCropActivity";
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private Uri mImageCaptureUri;
	private ImageView mPhotoImageView;
	private String outFilePath=niceheeUtil.strFilePath;
	
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_crop);
		
		mPhotoImageView = (ImageView) findViewById(R.id.image);
		mPhotoImageView.setOnClickListener(this);
	}

	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}
	
	private void doTakeAlbumAction() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(Images.Media.CONTENT_TYPE);
		intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != RESULT_OK) {
			if(data != null) {
				Log.e(TAG, "resultCode : " + resultCode + " Error!!! ");
			}
			return;
		}

		switch(requestCode) {
			case CROP_FROM_CAMERA : {
				Bitmap photo  = BitmapFactory.decodeFile(outFilePath);
				mPhotoImageView.setImageBitmap(photo);
				break;
			}
	
			case PICK_FROM_ALBUM : 
			case PICK_FROM_CAMERA : {
				Bitmap photo=null;
				if(requestCode == PICK_FROM_ALBUM) {
					mImageCaptureUri = data.getData();
					try {
						photo = Images.Media.getBitmap(getContentResolver(), mImageCaptureUri); 
						Log.e(TAG, "PICK_FROM_ALBUM : " + photo.getHeight()*photo.getWidth());
					} catch(Exception e) {Log.e(TAG, "PICK_FROM_ALBUM : " + e.toString());}	
				}
				
				if(requestCode == PICK_FROM_CAMERA) {
					Bundle extras = data.getExtras();
					photo = extras.getParcelable("data");
					Log.e(TAG, "PICK_FROM_CAMERA size : " + photo.getHeight()*photo.getWidth()); 
				}
				
				if(photo != null)
					try {
						FileOutputStream fos = new FileOutputStream(outFilePath);
						photo.compress(CompressFormat.JPEG, 100, fos); 
						fos.flush(); 
						fos.close();
					} catch(Exception e) {Log.e(TAG, "" + requestCode + " : " + e.toString());}
				else {
					Log.e(TAG, "Bitmap is null");
					return;
				}
				
				/*
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(mImageCaptureUri, "image/*");
				intent.putExtra("outputX", 90);
				intent.putExtra("outputY", 90);
				intent.putExtra("aspectX", 0);
				intent.putExtra("aspectY", 0);
				intent.putExtra("scale", true);
				intent.putExtra("return-data", true);
				*/
				Intent intent = new Intent(this, CropImageActivity.class);
				startActivityForResult(intent, CROP_FROM_CAMERA);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doTakePhotoAction();
			}
		};
		
		DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doTakeAlbumAction();
			}
		};
		
		DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
		
		new AlertDialog.Builder(this)
			.setTitle("사진선택")
			.setIcon(R.drawable.icon)
			.setPositiveButton("카메라에서", cameraListener)
			.setNeutralButton("앨범에서", albumListener)
			.setNegativeButton("취소", cancelListener)
			.show();
	}
	
	public void onBackPressed() {
		System.gc();
		System.exit(0);
	}
}