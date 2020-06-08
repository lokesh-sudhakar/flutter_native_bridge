package com.example.fluttermethodchannel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class FirstAndroidActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        TextView textView = findViewById(R.id.textView);
        Button btn = findViewById(R.id.button);
        Bundle b = getIntent().getExtras();
        String index = "Android First Activity";
        if (b!=null) {
            index = b.getString("title");
        }
        textView.setText(index);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstAndroidActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
