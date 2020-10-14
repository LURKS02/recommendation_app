package com.example.recommendationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class oldMovieActivity02 extends AppCompatActivity {

    TextView txt_movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_movie02);

        Intent intent = getIntent();
        String movieName = intent.getStringExtra("MOVIE_NAME");
        txt_movie = (TextView)findViewById(R.id.textView3);
        txt_movie.setText(movieName);
    }
}