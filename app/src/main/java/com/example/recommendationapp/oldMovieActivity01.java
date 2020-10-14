package com.example.recommendationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class oldMovieActivity01 extends AppCompatActivity {

    TextView movie_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_movie01);
        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        String[] movies = getResources().getStringArray(R.array.Movies);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies);
        textView.setAdapter(adapter);
        movie_textView = (TextView) findViewById(R.id.autoCompleteTextView);
    }

    public void oldButton01(View v){
        Intent intent = new Intent(oldMovieActivity01.this, oldMovieActivity02.class);
        intent.putExtra("MOVIE_NAME", movie_textView.getText().toString());
        startActivity(intent);
    }
}