package com.bytedance.camera.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.media.MediaBrowserService;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bytedance.camera.demo.utils.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bytedance.camera.demo.utils.Utils.MEDIA_TYPE_IMAGE;

public class TakePictureActivity extends AppCompatActivity {

    File imgFile;
    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int REQUEST_EXTERNAL_STORAGE = 101;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        imageView = findViewById(R.id.img);
        findViewById(R.id.btn_picture).setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(TakePictureActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //todo 在这里申请相机、存储的权限
                ActivityCompat.requestPermissions(TakePictureActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_CAPTURE);
            }
            if(ContextCompat.checkSelfPermission(TakePictureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(TakePictureActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_EXTERNAL_STORAGE);
            }
            takePicture();

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        //todo 根据imageView裁剪
        int i = this.imageView.getWidth();
        int j = this.imageView.getHeight();
        Object localObject = new BitmapFactory.Options();
        ((BitmapFactory.Options)localObject).inJustDecodeBounds = true;
        BitmapFactory.decodeFile(this.imgFile.getAbsolutePath(), (BitmapFactory.Options)localObject);
        int k = ((BitmapFactory.Options)localObject).outWidth;
        int m = ((BitmapFactory.Options)localObject).outHeight;
        i = Math.min(k / i, m / j);
        ((BitmapFactory.Options)localObject).inJustDecodeBounds = false;
        ((BitmapFactory.Options)localObject).inSampleSize = i;
        ((BitmapFactory.Options)localObject).inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(this.imgFile.getAbsolutePath(), (BitmapFactory.Options)localObject);
        localObject = Utils.rotateImage(bitmap, this.imgFile.getAbsolutePath());
        this.imageView.setImageBitmap((Bitmap)localObject);
        //todo 根据缩放比例读取文件，生成Bitmap

        //todo 如果存在预览方向改变，进行图片旋转

        //todo 如果存在预览方向改变，进行图片旋转
    }

//    private static File getOutputMediaFile(int type){
//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CameraDemo");
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
//        return mediaFile;
//    }

    public static Bitmap rotateImage(Bitmap bitmap,String path)throws Exception{
        ExifInterface srcExif = new ExifInterface(path);
        Matrix matrix = new Matrix();
        int angle = 0;
        int orientation = srcExif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
            default:
                break;
        }
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    private void takePicture() {
        //todo 打开相机
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"CameraDemo");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        imgFile = mediaFile;
        if(imgFile!=null){
            Uri fileuri = FileProvider.getUriForFile(this,"com.bytedance.camera.demo",imgFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileuri);
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }
}
