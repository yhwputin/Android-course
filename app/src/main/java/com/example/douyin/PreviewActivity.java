package com.example.douyin;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.douyin.bean.PostVideoResponse;
import com.example.douyin.network.IMiniDouyinService;
import com.example.douyin.utils.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewActivity extends AppCompatActivity {

    private VideoView videoView;
    Uri uri;
    Uri imageuri;
    Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Button file = findViewById(R.id.file);
        final Button upload = findViewById(R.id.upload);
        videoView = findViewById(R.id.videoView);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),1);
            }
        });
        Button record = findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PreviewActivity.this,RecordActivity.class),2);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("上传".equals(upload.getText().toString())){
                    postvideo();
                }
                else{
                    Toast.makeText(PreviewActivity.this,"请选择视频!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == 1) {
            uri = data.getData();
            videoView.setVideoURI(uri);
            videoView.start();
        }
        if (resultCode == RESULT_OK && null != data && requestCode == 2) {
            uri = Uri.parse(data.getExtras().getString("data"));
            videoView.setVideoURI(uri);
            videoView.start();
        }
    }

    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        // if NullPointerException thrown, try to allow storage permission in system settings
        String[] permit =  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permit,1);
        File f = new File(ResourceUtils.getRealPath(PreviewActivity.this, uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }


    private void postvideo() {
        upload.setText("POSTING...");
        upload.setEnabled(false);

        // TODO-C2 (6) Send Request to post a video with its cover image
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://test.androidcamp.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMiniDouyinService request = retrofit.create(IMiniDouyinService.class);
        MultipartBody.Part image = getMultipartFromUri("cover_image", imageuri);
        MultipartBody.Part video = getMultipartFromUri("video", uri);
        Call<PostVideoResponse> callu = request.upload1("16182614","yinhongwei",image,video);
        callu.enqueue(new Callback<PostVideoResponse>() {
            @Override
            public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                Toast.makeText(PreviewActivity.this,"上传成功!",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<PostVideoResponse> call, Throwable t) {
                Toast.makeText(PreviewActivity.this,"上传失败!",Toast.LENGTH_LONG).show();
            }
        });
        upload.setText("上传");
        upload.setEnabled(true);
        // if success, make a text Toast and show
    }
}
