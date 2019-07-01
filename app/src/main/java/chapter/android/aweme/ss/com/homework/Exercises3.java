package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 大作业:实现一个抖音消息页面,所需资源已放在res下面
 */
public class Exercises3 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tips);
        ListView listview = findViewById(R.id.rv_list);
        listview.setAdapter(new ListViewAdapter(this));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Exercises3.this, "当前位置" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Exercises3.this, chat.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        final Button bu1 = findViewById(R.id.button1);
        final Button bu2 = findViewById(R.id.button2);
        final Button bu3 = findViewById(R.id.button3);
        final Button bu4 = findViewById(R.id.button4);
        final TextView textView = findViewById(R.id.textView3);
        bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = bu1.getText().toString().trim();
                Intent in1 = new Intent(Exercises3.this, click.class);
                in1.putExtra("name",data);
                startActivity(in1);
            }
        });
        bu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = bu2.getText().toString().trim();
                Intent in1 = new Intent(Exercises3.this, click.class);
                in1.putExtra("name",data);
                startActivity(in1);
            }
        });
        bu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = bu3.getText().toString().trim();
                Intent in1 = new Intent(Exercises3.this, click.class);
                in1.putExtra("name",data);
                startActivity(in1);
            }
        });
        bu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = bu4.getText().toString().trim();
                Intent in1 = new Intent(Exercises3.this, click.class);
                in1.putExtra("name",data);
                startActivity(in1);
            }
        });
    }
    public static class ListViewAdapter extends BaseAdapter{

        private Context mContext;

        public ListViewAdapter(Context context) {

            mContext = context;
        }

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.im_list_item, null);
            } else {
                view = convertView;
            }
            ImageView im1 = view.findViewById(R.id.iv_avatar);
            ImageView im2 = view.findViewById(R.id.robot_notice);
            TextView tx1 = view.findViewById(R.id.tv_title);
            TextView tx2 = view.findViewById(R.id.tv_description);
            TextView tx3 = view.findViewById(R.id.tv_time);
            im1.setImageResource(R.drawable.icon_girl);
            im2.setImageResource(R.drawable.im_icon_notice_official);
            tx1.setText("AABBCC");
            tx2.setText("123321123");
            tx3.setText("5分钟前");
            return view;
        }
    }

}
