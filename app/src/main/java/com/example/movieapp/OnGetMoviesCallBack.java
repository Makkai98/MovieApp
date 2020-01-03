package com.example.movieapp;

import com.example.movieapp.Model.Movie;

import java.util.List;

public interface OnGetMoviesCallBack {

    void onSuccess(List<Movie> movies);

    void onError();
}
