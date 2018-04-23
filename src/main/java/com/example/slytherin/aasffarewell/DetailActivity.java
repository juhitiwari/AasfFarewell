package com.example.slytherin.aasffarewell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        String img= intent.getStringExtra("title");
        ImageView imageView=findViewById(R.id.img);
        Glide.with(DetailActivity.this).load(img).into(imageView);
    }
}
