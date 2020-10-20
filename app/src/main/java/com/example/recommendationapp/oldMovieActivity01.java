package com.example.recommendationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class oldMovieActivity01 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_movie01);
        textView = (TextView)findViewById(R.id.oldButton01);
    }

    public void oldButton01(View v){
        Intent intent = new Intent(oldMovieActivity01.this, oldMovieActivity02.class);
        intent.putExtra("id", textView.getText());
        startActivity(intent);
    }
}