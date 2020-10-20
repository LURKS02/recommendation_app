package com.example.recommendationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mainNewClick(View v){
        Intent intent = new Intent(getApplicationContext(), newMovieActivity01.class);
        startActivity(intent);
    }

    public void mainOldClick(View v){
        Intent intent = new Intent(getApplicationContext(), oldMovieActivity01.class);
        startActivity(intent);
    }

    public void mainSearchClick(View v){
        Intent intent = new Intent(getApplicationContext(), searchMovieActivity01.class);
        startActivity(intent);
    }
}