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

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String MOVIE_LIST_STATE = "MOVIE_LIST_STATE";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private List<Movie> movies;
    private HomeAdapter adapter;

    private long page = 1L;
    private boolean isLoading = true, isLastPage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

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

        if (savedInstanceState != null) {
            movies = (ArrayList) savedInstanceState.getSerializable(MOVIE_LIST_STATE);
            doneLoading();
        } else {
            HomeLogic.getGenres(new IResult<String>() {
                @Override
                public void onSuccess(String msg) {
                    HomeLogic.getMovies(page, new IResult<List<Movie>>() {
                        @Override
                        public void onSuccess(List<Movie> m) {
                            movies = m;
                            doneLoading();
                        }

                        @Override
                        public void onError(String msg) {
                            //Whoops
                        }
                    });
                }

                @Override
                public void onError(String msg) {
                    //Whoops
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save our own state now
        outState.putSerializable(MOVIE_LIST_STATE, (ArrayList) movies);
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
    }

    private void doneLoading() {
        adapter.setMovies(movies);
        adapter.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);
        isLoading = false;
    }
}
