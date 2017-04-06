package yoonjh.a170104_camera_01;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileExplorer extends Activity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    String mCurrent;
    String mRoot;
    TextView mCurrentTxt;
    ListView mFileList;
    ArrayAdapter<String> mAdapter;
    ArrayList<String> arFiles;
    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fileexplorer);

        mCurrentTxt = (TextView)findViewById(R.id.current);
        mFileList = (ListView)findViewById(R.id.filelist);
        arFiles = new ArrayList<String>();

        //SD카드 루트 가져옴
        mRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + "/eduA";
        mCurrent = mRoot;

        //어댑터를 생성하고 연결해줌
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arFiles);
        mFileList.setAdapter(mAdapter);//리스트뷰에 어댑터 연결
        mFileList.setOnItemClickListener(mItemClickListener);//리스너 연결
        refreshFiles();
    }

    //리스트뷰 클릭 리스너
    AdapterView.OnItemClickListener mItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    String Name = arFiles.get(position);//클릭된 위치의 값을 가져옴

                    //디렉토리이면
                    if(Name.startsWith("[") && Name.endsWith("]")){
                        Name = Name.substring(1, Name.length() - 1);//[]부분을 제거해줌
                    }

                    //들어가기 위해 /와 터치한 파일 명을 붙여줌
                    String Path = mCurrent + "/" + Name;
                    File f = new File(Path);//File 클래스 생성
                    if(f.isDirectory()){//디렉토리면?
                        mCurrent = Path;//현재를 Path로 바꿔줌
                        refreshFiles();//리프레쉬
                    }else{//디렉토리가 아니면 토스트 메세지를 뿌림
                        Toast.makeText(FileExplorer.this, arFiles.get(position), 0).show();
                    }
                }
            };

    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.btnroot://HOME
                if(mCurrent.compareTo(mRoot) != 0){//루트가 아니면 루트로 가기
                    mCurrent = mRoot;
                    refreshFiles();//리프레쉬
                }
                break;

            case R.id.btnup: //상위폴더
                if(mCurrent.compareTo(mRoot) != 0){//루트가 아니면
                    int end = mCurrent.lastIndexOf("/");///가 나오는 마지막 인덱스를 찾고
                    String uppath = mCurrent.substring(0, end);//그부분을 짤라버림 즉 위로가게됨
                    mCurrent = uppath;
                    refreshFiles();//리프레쉬
                }
                break;

            case R.id.btnNewDirectory: //새폴더
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("새폴더 생성");
                alert.setMessage("폴더명을 입력하세요.");

                final EditText name = new EditText(this);
                alert.setView(name);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newFolderName = name.getText().toString();
                        String dirPath = mCurrent + "/" + newFolderName;
                        File file = new File(dirPath);

                        if (!file.exists()) {
                            file.mkdir();
                            Toast.makeText(FileExplorer.this, "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(FileExplorer.this, "이미 같은 이름의 폴더가 존재합니다.", Toast.LENGTH_SHORT).show();
                        }
                        refreshFiles();//리프레쉬

                    }
                });

                alert.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();
                break;

            case R.id.btnTakePicture: //카메라로 사진찍기
                getPhotoFromCamera();
                break;

            case R.id.btnLoadPicture: //갤러리에서 가져오기
                getPhotoFromGallery();
                break;
        }
    }

    void refreshFiles(){
        mCurrentTxt.setText(mCurrent);//현재 PATH를 가져옴
        arFiles.clear();//배열리스트를 지움
        File current = new File(mCurrent);//현재 경로로 File클래스를 만듬
        String[] files = current.list();//현재 경로의 파일과 폴더 이름을 문자열 배열로 리턴

        //파일이 있다면?
        if(files != null){
            //여기서 출력을 해줌
            for(int i = 0; i < files.length;i++){
                String Path = mCurrent + "/" + files[i];
                String Name = "";
                File f = new File(Path);
                if(f.isDirectory()){
                    Name = "[" + files[i] + "]";//디렉토리면 []를 붙여주고
                }else{
                    Name = files[i];//파일이면 그냥 출력
                }
                arFiles.add(Name);//배열리스트에 추가해줌
            }
        }

        //다끝나면 리스트뷰를 갱신시킴
        mAdapter.notifyDataSetChanged();
    }

    private void getPhotoFromGallery() { // 갤러리에서 이미지 가져오기

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }
    private void getPhotoFromCamera() { // 카메라 촬영 후 이미지 가져오기

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "TMP_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(mRoot+"/tmp/", url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case CROP_FROM_iMAGE:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = mCurrent+"/"+System.currentTimeMillis()+".jpg";
                Bitmap photo = null;

                if(extras != null)
                {
                    photo = extras.getParcelable("data"); // CROP된 BITMAP
                    //iv.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌

                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }

                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra("bitmap", photo);
                startActivity(intent);
                break;
            }

            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 500); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 500); // CROP한 이미지의 y축 크기
                //intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                //intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_iMAGE case문 이동
                break;
            }
        }
    }
    private void storeCropImage(Bitmap bitmap, String filePath) {
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /** Get Bitmap's Width **/
    public static int getBitmapOfWidth( String fileName ){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);
            return options.outWidth;
        } catch(Exception e) {
            return 0;
        }
    }

    /** Get Bitmap's height **/
    public static int getBitmapOfHeight( String fileName ){

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);

            return options.outHeight;
        } catch(Exception e) {
            return 0;
        }
    }
}