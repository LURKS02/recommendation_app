package com.example.recommendationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class oldMovieActivity02 extends AppCompatActivity {

    TextView txt_movie;
    TextView txt_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_movie02);

        // 입력한 영화 제목 받아오기
        Intent intent = getIntent();
        String movieName = intent.getStringExtra("MOVIE_NAME");
        txt_movie = (TextView)findViewById(R.id.textView3);
        txt_movie.setText(movieName);

        // 입력한 영화 제목으로 영화 id 찾기
        SQLiteDatabase movieDB = this.openOrCreateDatabase("movieDB", MODE_PRIVATE, null);
        String sqlStatement01 = "SELECT 1"; //WHERE title = 'Toy Story (1995)'";
        Cursor c = movieDB.rawQuery(sqlStatement01, null);
        String id;
        if (c.moveToFirst()){
            id = Integer.toString(c.getInt(0));
            txt_id = (TextView)findViewById(R.id.textView4);
            txt_id.setText(id);
        }
    }
}