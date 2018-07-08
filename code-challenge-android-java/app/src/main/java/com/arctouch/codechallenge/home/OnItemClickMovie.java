package com.arctouch.codechallenge.home;

import android.view.View;

import com.arctouch.codechallenge.model.Movie;

public interface OnItemClickMovie {

    void onItemClick(Movie movie);

    boolean onItemLongClick(View view, Movie movie);
}
