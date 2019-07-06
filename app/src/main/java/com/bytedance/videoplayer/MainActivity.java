package com.bytedance.videoplayer;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("VideoView");
        final Button playbutton = findViewById(R.id.play);
        playbutton.setText("play");
        final Button file = findViewById(R.id.file);
        playbutton.setBackgroundColor(Color.GREEN);
        file.setBackgroundColor(Color.YELLOW);
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.paozi));
        final SeekBar seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int max = seekBar.getMax();
                int cur = seekBar.getProgress();
                int stime = videoView.getDuration();
                float rate = ((float) cur / max);
                int curtime = (int) (stime * rate);
                videoView.seekTo(curtime);
            }
        });



        final Handler mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    int position = videoView.getCurrentPosition();
                    int totalduration = videoView.getDuration();
                    int seekbarmax = seekBar.getMax();
                    float rate = (float) position / totalduration;
                    int newposition = (int) (seekbarmax * rate);
                    seekBar.setProgress(newposition);
                    sendEmptyMessageDelayed(1, 500);
                }
            }
        };
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),1);
            }
        });


        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playbutton.getText() == "play") {
                    mhandler.sendEmptyMessage(1);
                    videoView.start();
                    playbutton.setText("pause");
                    playbutton.setBackgroundColor(Color.RED);
                } else {
                    videoView.pause();
                    playbutton.setText("play");
                    playbutton.setBackgroundColor(Color.GREEN);
                }

            }
        });
    }

    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == 1) {
            uri = data.getData();
            videoView.setVideoURI(uri);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}