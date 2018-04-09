package com.arctouch.codechallenge.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.home.HomeActivity;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailActivity extends AppCompatActivity {

    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

    private TextView titleTextView;
    private TextView genresTextView;
    private TextView overviewTextView;
    private TextView releaseDateTextView;
    private ImageView posterImageView;
    private ImageView backdropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = findViewById(R.id.titleTextView);
        genresTextView = findViewById(R.id.genresTextView);
        overviewTextView = findViewById(R.id.overviewTextView);
        releaseDateTextView = findViewById(R.id.releaseDateTextView);
        posterImageView = findViewById(R.id.posterImageView);
        backdropImageView = findViewById(R.id.backdropImageView);

        bind(getIntent().getParcelableExtra(HomeActivity.EXTRA_MOVIE));
    }

    public void bind(Movie movie) {
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
}
