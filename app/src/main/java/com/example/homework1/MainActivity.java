package com.example.homework1;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = findViewById(R.id.button1);
        SeekBar sb = findViewById(R.id.seekBar);
        Switch sw = findViewById(R.id.switch1);
        final TextView tt = findViewById(R.id.textView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("ljfl!ljfl!ljfl!");
                Log.d("lalall","onClick: ");
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tt.setTextColor(Color.parseColor("#40e0d0"));
            }
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tt.setText("ljmjn!ljcyx!ljfl!");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tt.setText("这是滚动条");
            }
        });
        Log.e("ccccc", "onCreate: ");
    }
}
