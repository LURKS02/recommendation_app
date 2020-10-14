package com.example.recommendationapp.cls;

import java.io.Serializable;

public class Movie implements Serializable {

    private Integer movieId;
    private String title;
    private String genres;

    public Movie(Integer movieId, String title, String genres){
        this.movieId = movieId;
        this.title = title;
        this.genres = genres;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", genres='" + genres + '\'' +
                '}';
    }
}
