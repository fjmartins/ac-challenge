package com.arctouch.codechallenge.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.home.HomeActivity;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private final String MOVIE_STATE = "MOVIE_STATE";

    private Movie movie;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.genresTextView)
    TextView genresTextView;
    @BindView(R.id.overviewTextView)
    TextView overviewTextView;
    @BindView(R.id.releaseDateTextView)
    TextView releaseDateTextView;
    @BindView(R.id.posterImageView)
    ImageView posterImageView;
    @BindView(R.id.backdropImageView)
    ImageView backdropImageView;

    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        //A+ design skillz
        if (savedInstanceState != null)
            movie = savedInstanceState.getParcelable(MOVIE_STATE);
        else
            movie = getIntent().getParcelableExtra(HomeActivity.EXTRA_MOVIE);

        bind(movie);
    }

    public void bind(Movie movie) {
        if (movie == null) return;

        getSupportActionBar().setTitle(movie.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTextView.setText(movie.title);
        genresTextView.setText(TextUtils.join(", ", movie.genres));
        releaseDateTextView.setText(movie.releaseDate);
        overviewTextView.setText(movie.overview);

        String posterPath = movie.posterPath;
        if (TextUtils.isEmpty(posterPath) == false) {
            Glide.with(DetailActivity.this)
                    .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(posterImageView);
        }

        String backdropPath = movie.backdropPath;
        if (TextUtils.isEmpty(backdropPath) == false) {
            Glide.with(DetailActivity.this)
                    .load(movieImageUrlBuilder.buildPosterUrl(backdropPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(backdropImageView);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save our own state now
        outState.putParcelable(MOVIE_STATE, movie);
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
    }
}
