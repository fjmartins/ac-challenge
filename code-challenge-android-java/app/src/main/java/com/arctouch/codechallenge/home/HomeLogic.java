package com.arctouch.codechallenge.home;

import com.arctouch.codechallenge.api.Tmdb;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeLogic {

    public static void getGenres(final IResult result) {
        Tmdb.getInstance().api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Cache.setGenres(response.genres);
                    result.onSuccess("OK");
                });
    }

    public static void getMovies(final long page, final IResult result) {
        try {
            Tmdb.getInstance().api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response1 -> {
                        for (Movie movie : response1.results) {
                            movie.genres = new ArrayList<>();
                            for (Genre genre : Cache.getGenres()) {
                                if (movie.genreIds.contains(genre.id)) {
                                    movie.genres.add(genre);
                                }
                            }
                        }
                        //Sending out results to the view
                        result.onSuccess(response1.results);
                    });
        } catch (Exception e) {
            result.onSuccess(e.getMessage());
        }
    }
}
