package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 作业1：
 * 打印出Activity屏幕切换 Activity会执行什么生命周期？
 */
public class Exercises1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("-----1", "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("-----2", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("-----3", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("-----4", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("-----5", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("-----6", "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("-----7", "onRestart: ");
    }

}
