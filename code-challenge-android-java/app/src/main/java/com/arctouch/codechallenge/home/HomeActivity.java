package com.arctouch.codechallenge.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.detail.DetailActivity;
import com.arctouch.codechallenge.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private long page = 1L;

    private List<Movie> movies = new ArrayList<>();
    private HomeAdapter adapter;

    private boolean isLoading = true, isLastPage = false;
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);
        this.layoutManager = new LinearLayoutManager(HomeActivity.this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.adapter = new HomeAdapter(movies, new OnItemClickMovie() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra(EXTRA_MOVIE, movie);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, Movie movie) {
                return false;
            }
        });

        recyclerView.setAdapter(adapter);

        HomeLogic.getGenres(new IResult<String>() {
            @Override
            public void onSuccess(String msg) {
                HomeLogic.getMovies(page, new IResult<List<Movie>>() {
                    @Override
                    public void onSuccess(List<Movie> m) {
                        List<Movie> movies = adapter.getMovies();
                        movies.addAll(m);

                        adapter.setMovies(movies);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        isLoading = false;
                    }

                    @Override
                    public void onError(String msg) {
                        //Whoops
                    }
                });
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
