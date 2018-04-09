package com.arctouch.codechallenge.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class Tmdb {

    public TmdbApi api = null;
    private static Tmdb instance = null;

    private Tmdb() {
        api = new Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi.class);
    }

    public static Tmdb getInstance() {
        if (instance == null) {
            instance = new Tmdb();
        }
        return instance;
    }
}
