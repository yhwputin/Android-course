package chapter.android.aweme.ss.com.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class click extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        Button bc = findViewById(R.id.buttonc);
        final TextView textView = findViewById(R.id.textView3);
        final String re_s = getIntent().getStringExtra("name");
        textView.setText(re_s);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(click.this, Exercises3.class));
                Toast.makeText(click.this, ""+re_s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
