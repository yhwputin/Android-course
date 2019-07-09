package com.example.douyin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.example.douyin.bean.Feed;
import com.example.douyin.bean.FeedResponse;
import com.example.douyin.network.IMiniDouyinService;
import com.example.douyin.widget.OnViewPagerListener;
import com.example.douyin.widget.PagerLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    List<Feed> mFeeds = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArrayList<Bean> mDatas = new ArrayList<>();
    private VideoAdapter mAdapter;
    private IjkVideoView mVideoView;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        mVideoView = findViewById(R.id.video_view);

        PagerLayoutManager mLayoutManager = new PagerLayoutManager(this, OrientationHelper.VERTICAL);
        try {
            fetchFeed();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDatas.addAll(DataUtils.getDatas(mFeeds));
        mAdapter = new VideoAdapter(this, mDatas);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        ImageView imageView = findViewById(R.id.imgview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
                }
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},2);
                }
                Intent intent = new Intent(MainActivity.this,PreviewActivity.class);
                startActivity(intent);
            }
        });
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                playVideo(view);
            }


            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                playVideo(view);
            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(view);
            }
        });
    }

    /**
     * 播放视频
     */
    private void playVideo(View view) {
        if (view != null) {
            mVideoView = view.findViewById(R.id.video_view);
            mVideoView.start();
        }
    }

    /**
     * 停止播放
     */
    private void releaseVideo(View view) {
        if (view != null) {
            IjkVideoView videoView = view.findViewById(R.id.video_view);
            videoView.stopPlayback();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoViewManager.instance().releaseVideoPlayer();
    }

//    public void fetchFeed(){
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://test.androidcamp.bytedance.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        IMiniDouyinService request = retrofit.create(IMiniDouyinService.class);
//        Call<FeedResponse> callg = request.down();
//        callg.enqueue(new Callback<FeedResponse>() {
//            @Override
//            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
//                mFeeds = response.body().getFeeds();
//                recyclerView.getAdapter().notifyDataSetChanged();
//            }
//            @Override
//            public void onFailure(Call<FeedResponse> call, Throwable t) {
//            }
//        });
//        try {
//            callg.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void fetchFeed() throws ExecutionException, InterruptedException {

        FetchFeedTask fetchFeedTask = new FetchFeedTask();
        fetchFeedTask.execute();
        if (fetchFeedTask.get() != null) {
            mFeeds = fetchFeedTask.get().getFeeds();
        }
    }

    class FetchFeedTask extends AsyncTask<String,Integer, FeedResponse> {

        @Override
        protected FeedResponse doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://test.androidcamp.bytedance.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Response<FeedResponse> response = null;
            try {
                response = retrofit.create(IMiniDouyinService.class).down().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(response);
            return response == null?null:response.body();
        }
    }
}
